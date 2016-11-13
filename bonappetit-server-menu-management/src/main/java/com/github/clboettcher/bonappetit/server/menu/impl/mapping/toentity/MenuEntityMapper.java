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
package com.github.clboettcher.bonappetit.server.menu.impl.mapping.toentity;

import com.github.clboettcher.bonappetit.server.menu.api.dto.MenuDto;
import com.github.clboettcher.bonappetit.server.menu.impl.entity.menu.MenuEntity;
import org.mapstruct.Mapper;

@Mapper(uses = ItemEntityMapper.class, componentModel = "spring")
public interface MenuEntityMapper {

    /**
     * Maps the given {@link MenuDto} to a {@link MenuEntity}.
     *
     * @param menu The {@link MenuDto} to map.
     * @return The resulting {@link MenuEntity}.
     */
    MenuEntity mapToMenuEntity(MenuDto menu);
}
