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
package com.github.clboettcher.bonappetit.server.resository;

import com.github.clboettcher.bonappetit.common.entity.ItemType;
import com.github.clboettcher.bonappetit.server.BonappetitServerApplication;
import com.github.clboettcher.bonappetit.server.entity.builder.*;
import com.github.clboettcher.bonappetit.server.entity.menu.*;
import com.github.clboettcher.bonappetit.server.repository.MenuRepository;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link com.github.clboettcher.bonappetit.server.repository.MenuRepository}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = BonappetitServerApplication.class)
public class MenuRepositoryTests {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testSaveMenuItemWithoutOptions() throws Exception {
        // Setup
        Item item = ItemBuilder.anItem()
                .withTitle("Item Title")
                .withPrice(new BigDecimal("1.9"))
                .withType(ItemType.DRINK_NON_ALCOHOLIC)
                .build();

        Menu menu = MenuBuilder.aMenu()
                .withItems(Sets.newHashSet(item))
                .build();

        // Test
        menuRepository.save(menu);

        // Verify
        Menu dbMenu = menuRepository.findOne(menu.getId());
        assertThat(dbMenu, notNullValue());
        assertThat(dbMenu.getItems(), not(empty()));
        Item dbItem = dbMenu.getItems().iterator().next();
        assertThat(dbItem.getTitle(), is("Item Title"));
        assertThat(dbItem.getOptions(), nullValue());
        assertThat(dbItem.getPrice(), is(new BigDecimal("1.9")));
        assertThat(dbItem.getType(), is(ItemType.DRINK_NON_ALCOHOLIC));
    }

    @Test
    public void testSaveMenuItemWithIntegerOption() throws Exception {
        // Setup
        Item item = ItemBuilder.anItem()
                .withTitle("Item Title")
                .withPrice(new BigDecimal("1.9"))
                .withType(ItemType.DRINK_NON_ALCOHOLIC)
                .withOptions(Sets.<Option>newHashSet(
                        IntegerOptionBuilder.anIntegerOption()
                                .withTitle("Integer Option")
                                .withIndex(17)
                                .withPriceDiff(BigDecimal.ONE)
                                .withDefaultValue(1337)
                                .build()
                ))
                .build();

        Menu menu = MenuBuilder.aMenu()
                .withItems(Sets.newHashSet(item))
                .build();

        // Test
        menuRepository.save(menu);

        // Verify
        Menu dbMenu = menuRepository.findOne(menu.getId());
        Item dbItem = dbMenu.getItems().iterator().next();
        assertThat(dbItem.getOptions(), not(empty()));
        Option dbOption = dbItem.getOptions().iterator().next();
        assertThat(dbOption, is(instanceOf(IntegerOption.class)));
        IntegerOption dbIntegerOption = (IntegerOption) dbOption;
        assertThat(dbIntegerOption.getTitle(), is("Integer Option"));
        assertThat(dbIntegerOption.getDefaultValue(), is(1337));
        assertThat(dbIntegerOption.getPriceDiff(), is(BigDecimal.ONE));
        assertThat(dbIntegerOption.getIndex(), is(17));
    }

    @Test
    public void testSaveItemWithValueOption() throws Exception {
        // Setup
        Item item = ItemBuilder.anItem()
                .withTitle("Item Title")
                .withPrice(new BigDecimal("1.9"))
                .withType(ItemType.DRINK_NON_ALCOHOLIC)
                .withOptions(Sets.<Option>newHashSet(
                        ValueOptionBuilder.aValueOption()
                                .withTitle("Value Option")
                                .withIndex(17)
                                .withDefaultChecked(true)
                                .withPriceDiff(BigDecimal.ONE)
                                .build()
                ))
                .build();

        Menu menu = MenuBuilder.aMenu()
                .withItems(Sets.newHashSet(item))
                .build();

        // Test
        menuRepository.save(menu);

        // Verify
        Menu dbMenu = menuRepository.findOne(menu.getId());
        Item dbItem = dbMenu.getItems().iterator().next();
        assertThat(dbItem.getOptions(), not(empty()));
        Option dbOption = dbItem.getOptions().iterator().next();
        assertThat(dbOption, is(instanceOf(ValueOption.class)));
        ValueOption dbValueOption = (ValueOption) dbOption;
        assertThat(dbValueOption.getTitle(), is("Value Option"));
        assertThat(dbValueOption.getIndex(), is(17));
        assertThat(dbValueOption.getDefaultChecked(), is(true));
        assertThat(dbValueOption.getPriceDiff(), is(BigDecimal.ONE));
    }

    @Test
    public void testSaveItemWithRadioOptions() throws Exception {
        RadioItem defaultSelected = RadioItemBuilder.aRadioItem()
                .withTitle("Default selected")
                .withIndex(0)
                .withPriceDiff(BigDecimal.ONE)
                .build();

        Item item = ItemBuilder.anItem()
                .withTitle("Item Title")
                .withPrice(new BigDecimal("1.9"))
                .withType(ItemType.DRINK_NON_ALCOHOLIC)
                .withOptions(Sets.<Option>newHashSet(
                        RadioOptionBuilder.aRadioOption()
                                .withTitle("Radio Option")
                                .withIndex(17)
                                .withDefaultSelected(defaultSelected)
                                .withRadioItems(Sets.newHashSet(
                                        defaultSelected,
                                        RadioItemBuilder.aRadioItem()
                                                .withTitle("Radio Item 2")
                                                .withIndex(1)
                                                .withPriceDiff(BigDecimal.ONE)
                                                .build()
                                ))
                                .build()
                ))
                .build();

        Menu menu = MenuBuilder.aMenu()
                .withItems(Sets.newHashSet(item))
                .build();

        menuRepository.save(menu);

        // Verify
        Menu dbMenu = menuRepository.findOne(menu.getId());
        Item dbItem = dbMenu.getItems().iterator().next();
        assertThat(dbItem.getOptions(), not(empty()));
        Option dbOption = dbItem.getOptions().iterator().next();
        assertThat(dbOption, is(instanceOf(RadioOption.class)));
        RadioOption dbRadioOption = (RadioOption) dbOption;
        assertThat(dbRadioOption.getTitle(), is("Radio Option"));
        assertThat(dbRadioOption.getIndex(), is(17));
        RadioItem dbDefaultSelected = dbRadioOption.getDefaultSelected();
        assertThat(dbDefaultSelected.getTitle(), is("Default selected"));
        assertThat(dbDefaultSelected.getIndex(), is(0));
        assertThat(dbDefaultSelected.getPriceDiff(), is(BigDecimal.ONE));
        assertThat(dbDefaultSelected.getIndex(), is(0));
        assertThat(dbRadioOption.getRadioItems().size(), is(2));
    }
}
