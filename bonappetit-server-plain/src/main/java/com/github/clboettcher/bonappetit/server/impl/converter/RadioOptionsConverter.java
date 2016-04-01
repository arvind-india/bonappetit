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

import com.github.clboettcher.bonappetit.common.dto.menu.RadioOptionDto;
import com.github.clboettcher.bonappetit.serverentities.menu.RadioItem;
import com.github.clboettcher.bonappetit.serverentities.menu.RadioOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts {@link RadioOption}s to {@link RadioOptionDto}s.
 */
@Component
public class RadioOptionsConverter {

    private RadioItemsConverter radioItemsConverter;

    /**
     * Constructor setting the specified properties.
     *
     * @param radioItemsConverter The converter for {@link RadioItem}s.
     */
    @Autowired
    public RadioOptionsConverter(RadioItemsConverter radioItemsConverter) {
        this.radioItemsConverter = radioItemsConverter;
    }

    /**
     * Converts a {@link RadioOption} to a {@link RadioOptionDto}.
     *
     * @param option The {@link RadioOption} to convert.
     * @return The resulting {@link RadioOptionDto}.
     */
    /* package-private */ RadioOptionDto convert(RadioOption option) {
        RadioOptionDto result = new RadioOptionDto();
        result.setRadioItemDtos(radioItemsConverter.convert(option.getRadioItems()));
        result.setDefaultSelectedId(option.getDefaultSelected().getId());
        result.setTitle(option.getTitle());
        return result;
    }
}
