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
package com.github.clboettcher.bonappetit.server.menu.impl;

import com.github.clboettcher.bonappetit.server.menu.api.MenuManagement;
import com.github.clboettcher.bonappetit.server.menu.api.dto.MenuDto;
import com.github.clboettcher.bonappetit.server.menu.impl.dao.MenuDao;
import com.github.clboettcher.bonappetit.server.menu.impl.entity.menu.MenuEntity;
import com.github.clboettcher.bonappetit.server.menu.impl.mapping.todto.MenuDtoMapper;
import com.github.clboettcher.bonappetit.server.menu.impl.mapping.toentity.MenuEntityMapper;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Default impl of the {@link MenuManagement}.
 */
@Component
public class MenuManagementImpl implements MenuManagement {

    @Context
    private UriInfo uri;

    /**
     * The DAO for stored menus.
     */
    private MenuDao menuDao;

    /**
     * The entity to dto mapper.
     */
    @Autowired
    private MenuDtoMapper dtoMapper;

    /**
     * The dto to entity mapper.
     */
    @Autowired
    private MenuEntityMapper menuEntityMapper;

    /**
     * Constructor setting the specified properties.
     *
     * @param menuDao see {@link #menuDao}.
     */
    @Autowired
    public MenuManagementImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public MenuDto getCurrentMenu() {
        return dtoMapper.mapToMenuDto(menuDao.getCurrentMenu());
    }

    @Override
    public Response createMenu(@ApiParam(value = "The menu to create.", required = true) MenuDto menuDto) {
        MenuEntity menuEntity = this.menuEntityMapper.mapToMenuEntity(menuDto);
        MenuEntity saved = menuDao.save(menuEntity);

        UriBuilder baseUriBuilder = uri.getBaseUriBuilder().path(MenuManagement.ROOT_PATH + "/" + saved.getId());

        return Response.ok()
                .location(baseUriBuilder.build())
                .build();
    }
}
