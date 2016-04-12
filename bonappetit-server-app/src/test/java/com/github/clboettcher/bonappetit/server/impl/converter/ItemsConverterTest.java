/*
 * Copyright (c) 2016 Claudius Boettcher (pos.bonappetit@gmail.com)
 *
 * This file is part of BonAppetit. BonAppetit is an Android based
 * Point-of-Sale client-server application for small restaurants.
 *
 * BonAppetit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BonAppetit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BonAppetit.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.clboettcher.bonappetit.server.impl.converter;

import com.github.clboettcher.bonappetit.domain.builder.ItemBuilder;
import com.github.clboettcher.bonappetit.domain.menu.Item;
import com.github.clboettcher.bonappetit.domain.menu.ItemType;
import com.github.clboettcher.bonappetit.domain.menu.Option;
import com.github.clboettcher.bonappetit.dto.builder.ItemDtoBuilder;
import com.github.clboettcher.bonappetit.dto.menu.ItemDto;
import com.github.clboettcher.bonappetit.dto.menu.ItemDtoType;
import com.github.clboettcher.bonappetit.dto.menu.OptionDto;
import com.github.clboettcher.bonappetit.server.impl.converter.api.ItemsConverter;
import com.github.clboettcher.bonappetit.server.impl.converter.api.OptionsConverter;
import com.github.clboettcher.bonappetit.server.impl.converter.impl.ItemsConverterImpl;
import com.github.clboettcher.bonappetit.server.impl.converter.impl.OptionsConverterImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ItemsConverterImpl}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ItemsConverterTest.Context.class)
public class ItemsConverterTest {

    @Autowired
    private ItemsConverterImpl itemsConverter;

    @Autowired
    private OptionsConverter optionsConverterMock;

    private LinkedHashSet<Option> inputOptions;
    private LinkedHashSet<OptionDto> expectedOptionDtos;


    @Before
    public void setUp() throws Exception {
        inputOptions = Sets.newLinkedHashSet(Lists.newArrayList(Mockito.mock(Option.class)));
        expectedOptionDtos = Sets.newLinkedHashSet(Lists.newArrayList(Mockito.mock(OptionDto.class)));
        Mockito.when(optionsConverterMock.convert(inputOptions)).thenReturn(expectedOptionDtos);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(optionsConverterMock);
    }

    @Test
    public void testConversion() throws Exception {
        Set<Item> inputItems = Sets.newHashSet(
                ItemBuilder.anItem()
                        .withId(1)
                        .withTitle("Test Item w/ options")
                        .withPrice(new BigDecimal("1.5"))
                        .withType(ItemType.FOOD)
                        .withOptions(inputOptions)
                        .build(),
                ItemBuilder.anItem()
                        .withId(2)
                        .withTitle("Test Item w/o options")
                        .withPrice(new BigDecimal("2.5"))
                        .withType(ItemType.DRINK_ALCOHOLIC)
                        .withOptions(null)
                        .build()
        );

        Set<ItemDto> expectedItemDtos = Sets.newHashSet(
                ItemDtoBuilder.anItemDto()
                        .withId(1L)
                        .withTitle("Test Item w/ options")
                        .withPrice(new BigDecimal("1.5"))
                        .withType(ItemDtoType.FOOD)
                        .withOptionDtos(expectedOptionDtos)
                        .build(),
                ItemDtoBuilder.anItemDto()
                        .withId(2L)
                        .withTitle("Test Item w/o options")
                        .withPrice(new BigDecimal("2.5"))
                        .withType(ItemDtoType.DRINK_ALCOHOLIC)
                        .withOptionDtos(null)
                        .build()
        );

        assertThat(itemsConverter.convertToItemDtos(inputItems), is(expectedItemDtos));
    }

    static class Context {
        @Bean
        public ItemsConverter itemsConverter(OptionsConverter optionsConverter) {
            return new ItemsConverterImpl(optionsConverter);
        }

        @Bean
        public OptionsConverter optionsConverter() {
            return Mockito.mock(OptionsConverterImpl.class);
        }
    }
}