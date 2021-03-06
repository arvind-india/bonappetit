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
package com.github.clboettcher.bonappetit.server.order.dao;

import com.github.clboettcher.bonappetit.server.order.entity.ItemOrderEntity;
import com.github.clboettcher.bonappetit.server.order.entity.OrderEntityStatus;

import java.util.Collection;
import java.util.List;

public interface OrderDao {

    /**
     * Saves the given orders in the database.
     *
     * @param orders The orders to save, may be empty.
     * @return The saved entites.
     */
    List<ItemOrderEntity> create(Collection<ItemOrderEntity> orders);

    /**
     * Updates the given orders in in the database.
     *
     * @param itemOrderEntities The orders to update.
     */
    List<ItemOrderEntity> update(Collection<ItemOrderEntity> itemOrderEntities);

    /**
     * Returns all orders.
     *
     * @return A list of orders, may be empty.
     */
    List<ItemOrderEntity> getAllOrders();

    /**
     * Returns the order with the given ID.
     *
     * @param id The id.
     * @return The order, may be null, if it does not exist.
     */
    ItemOrderEntity getOrderById(Long id);

    /**
     * Returns the orders with the given ids.
     *
     * @param orderIds The ids.
     * @return The orders with the given ids.
     */
    List<ItemOrderEntity> getOrdersByIds(List<Long> orderIds);

    /**
     * Deletes the given orders in the database.
     * <p>
     * Only orders with status {@link OrderEntityStatus#CREATED} can be deleted.
     *
     * @param orderEntities The orders to delete.
     */
    void delete(List<ItemOrderEntity> orderEntities);

    /**
     * Returns whether an order exists in the db with the given id.
     *
     * @param orderId The id.
     * @return True, if an order exists in the db, false otherwise.
     */
    boolean exists(Long orderId);
}
