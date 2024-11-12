package com.restaurants.trazabilidad.infraestructur.driving_http.mappers;


import com.restaurants.trazabilidad.domain.model.OrderEfficiency;
import com.restaurants.trazabilidad.infraestructur.driving_http.dtos.response.OrderEfficiencyResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEfficyenceResponseMapper {
    OrderEfficiencyResponseDto toOrderEfficienceResponse(OrderEfficiency order);
}
