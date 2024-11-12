package com.restaurants.trazabilidad.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.StatusLog;
import com.restaurants.trazabilidad.infraestructur.driven_rp.entity.OrderStatusLogEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStatusLogMapper {
    StatusLog toStatusLog(OrderStatusLogEntity status);
    List<StatusLog> toListStatusLog(List<OrderStatusLogEntity> listStatus);
    OrderStatusLogEntity toStatusEntity(StatusLog status);
    List<OrderStatusLogEntity> toListStatusEntity(List<StatusLog> status);
}
