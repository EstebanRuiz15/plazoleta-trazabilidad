package com.restaurants.trazabilidad.infraestructur.driving_http.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusLogResponseDto {
    private Integer orderId;
    private String customerEmail;
    private String dateStar;
    private String lastUpdate;
    private String previousStatus;
    private String newStatus;
}
