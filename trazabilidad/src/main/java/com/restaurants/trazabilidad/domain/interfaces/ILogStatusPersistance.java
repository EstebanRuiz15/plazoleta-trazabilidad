package com.restaurants.trazabilidad.domain.interfaces;


import com.restaurants.trazabilidad.domain.model.StatusLog;

import java.util.List;

public interface  ILogStatusPersistance {
   void save(StatusLog status);
    StatusLog findByOrderId(Integer ordeId);
    List<StatusLog> findByCustomerIdAndDateStarBetween(Integer customId,String startOfDay, String endOfDay);
    List<StatusLog> findAll();
}
