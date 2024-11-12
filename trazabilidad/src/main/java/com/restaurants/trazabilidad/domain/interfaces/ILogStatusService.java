package com.restaurants.trazabilidad.domain.interfaces;

import com.restaurants.trazabilidad.domain.model.OrderStatus;
import com.restaurants.trazabilidad.domain.model.StatusLog;

import java.util.List;

public interface ILogStatusService {
    void registerStar(Integer orderId, Integer customID, String clientEmail);
    void registerChange(OrderStatus status, Integer orderId, String employeEmail, Integer employeId);
    List<StatusLog> getAllRegisters();
    List<StatusLog> getOrderStatus();
}
