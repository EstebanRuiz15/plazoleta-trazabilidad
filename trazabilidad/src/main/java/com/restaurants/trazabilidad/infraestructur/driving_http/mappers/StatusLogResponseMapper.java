package com.restaurants.trazabilidad.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.StatusLog;
import com.restaurants.trazabilidad.infraestructur.driving_http.dtos.response.StatusLogResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusLogResponseMapper {
    List<StatusLogResponseDto> toListResponseDto(List<StatusLog> listStatusLog);
    StatusLogResponseDto toStatusLogResponse(StatusLog status);
}
