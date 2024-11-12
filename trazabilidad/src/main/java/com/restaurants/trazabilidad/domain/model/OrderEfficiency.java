package com.restaurants.trazabilidad.domain.model;

import java.util.List;

public class OrderEfficiency {

    private List<Raking> raking;
    private List<TimeOrderEnd> timeOrderEnd;

    public OrderEfficiency(List<Raking> raking, List<TimeOrderEnd> timeOrderEnd) {
        this.raking = raking;
        this.timeOrderEnd = timeOrderEnd;
    }

    public OrderEfficiency() {
    }

    public List<Raking> getRaking() {
        return raking;
    }

    public void setRaking(List<Raking> raking) {
        this.raking = raking;
    }

    public List<TimeOrderEnd> getTimeOrderEnd() {
        return timeOrderEnd;
    }

    public void setTimeOrderEnd(List<TimeOrderEnd> timeOrderEnd) {
        this.timeOrderEnd = timeOrderEnd;
    }
}
