package com.restaurants.trazabilidad.infraestructur.driving_http.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurants.trazabilidad.domain.model.Raking;
import com.restaurants.trazabilidad.domain.model.TimeOrderEnd;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class OrderEfficiencyResponseDto {

    private List<Raking> raking;
    private List<TimeOrderEnd> timeOrderEnd;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Raking> getRaking() {
        return raking;
    }

    public void setRaking(List<Raking> raking) {
        this.raking = raking;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<TimeOrderEnd> getTimeOrderEnd() {
        return timeOrderEnd;
    }

    public void setTimeOrderEnd(List<TimeOrderEnd> timeOrderEnd) {
        this.timeOrderEnd = timeOrderEnd;
    }
}
