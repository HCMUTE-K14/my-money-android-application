package com.vn.hcmute.team.cortana.mymoney.api;

/**
 * Created by infamouSs on 8/10/17.
 */

public class MyMoneyApi {
    
    public static final String BASE_URL = "http://10.0.3.2:8080/";
    
    public static final String LOGIN_URL = "users/login";
    
    public static final String REGISTER_URL = "users/register";
    
    public static final String UPLOAD_IMAGE_URL = "image/upload";
    
    public static final String REMOVE_IMAGE_URL = "image/remove";
    
    public static final String GET_IMAGE_URL = "image/get";
    //wallet
    public static final String CREATE_WALLET = "wallet/create";
    public static final String UPDATE_WALLET = "wallet/updateWallet";
    public static final String DELETE_WALLET = "wallet/deleteWallet";
    public static final String GET_ALL_WALLET = "wallet/getInfoWallet";
    public static final String MOVE_WALLET = "wallet/moveMoneyWallet";
    //currencies
    public static final String GET_CURRENCIES="currency";
    
    //event
    public static final String GET_EVENT="event/getEvent";
    public static final String CREATE_EVENT="event/createEvent";
    public static final String UPDATE_EVENT="event/updateEvent";
    public static final String DELETE_EVENT="event/deleteEvent";
    
    
}
