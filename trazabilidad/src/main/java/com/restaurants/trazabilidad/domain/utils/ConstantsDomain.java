package com.restaurants.trazabilidad.domain.utils;

public final class ConstantsDomain {
    private ConstantsDomain(){
        throw new IllegalStateException("Utility class");
    }
    public static final String COMUNICATION_ERROR_WITH_SERVICE="Error communicating with User service ";
    public static final String PATERN_DATE="yyyy-MM-dd HH:mm";
    public static final String TIME_END="timeEnd";
    public static final String RANKING="ranking";
    public static final String MESAGE_INVALID_FILTE="the filter can only ranking or timeEnd";
}
