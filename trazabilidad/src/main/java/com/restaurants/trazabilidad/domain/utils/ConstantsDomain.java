package com.restaurants.trazabilidad.domain.utils;

public final class ConstantsDomain {
    private ConstantsDomain(){
        throw new IllegalStateException("Utility class");
    }
    public static final String ERROR_VALIDATION="Error of validation";
    public static final String NAME_EMPTY = "Name cannot be empty.";
    public static final String ERROR_USER="The user ";
    public static final String NOT_EXIST=" not exist";
    public static final String NOT_HAVE_OWNER_ROL=" not have the rol owner";
    public static final String COMUNICATION_ERROR_WITH_SERVICE="Error communicating with User service ";
    public static final String OWNER="OWNER";
    public static final String CATEGORY_NOT_FOUND="Category not found";
    public static final String Dish_NOT_FOUND="Dish not found: ";
    public static final String RESTAURANT_NOT_FOUND="Restaurant not found";
    public static final String NAME_AL_READY_EXIST="The name is already exist";
    public static final String CREAT_SUCCESSFULL="Create new successfully";
    public static final String NOT_ALL_FIELD_EMPTY="The 3 fields cannot be empty, at least one must contain to modify";
    public static final String NO_OWNER_THIS_REST="You no Owner of this restaurant";
    public static final String ALREADY_DISABLE="the dish is already disabled";
    public static final String ALREADY_ENABLE="the dish is already enable";
    public static final String PAGE_OR_SIZE_ERROR="The page or the size cannot be 0";
    public static final String REST_ID_NOT_NULL="Restaurant id cannot be null";
    public static final String SOME_DISHES_NOT_FOUND="Some dishes in the order are not available at the restaurant";
    public static final String NOT_FOUND_CLIENT="Not found client";
    public static final String NOT_FOUND_CHEF="Not found chef";
    public static final String ALREADY_RESTAURANT_WITH_OWNER="The owner already one restaurant";
    public static final String INVALID_STATUS_PARAM="Invalid param status";
    public static final String ORDER_NOT_FOUND="Order not found";
    public static final String ORDER_IS_ALREADY_ASSIGNED="Order is already assigned or canceled";
    public static final String ORDER_NOT_FOUND_OR_PIN_ERROR="Order not found or wrong pin";
    public static final String ORDER_IS_ALREADY_DELIVERED="Order is already delivered";
    public static final String ORDER_NOT_REAY="the order is not ready yet";
    public static final String ORDER_NOT_CANCELLED="Sorry, your order is already in preparation and cannot be canceled";
    public static final String NOW_ALREADY_PENDING_ORDER="At this moment you already have a pending order";

}

