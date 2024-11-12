package com.restaurants.trazabilidad.domain.interfaces;

import com.restaurants.trazabilidad.domain.model.OrderStatus;

import java.util.List;

public interface  ILogStatusPersistance {
   void save(com.restaurant.plazoleta.domain.model.StatusLog status);
    com.restaurant.plazoleta.domain.model.StatusLog findByOrderId(Integer ordeId);
    List<com.restaurant.plazoleta.domain.model.StatusLog> findByCustomerIdAndDateStarBetween(Integer customId,String startOfDay, String endOfDay);
}
