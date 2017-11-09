package com.vn.hcmute.team.cortana.mymoney.api;

/**
 * Created by infamouSs on 8/10/17.
 */

public class MyMoneyApi {
    
    public static final String BASE_URL = "http://10.0.2.2:8080/";
    
    /*-----------------*/
    /*USER             */
    /*-----------------*/
    public static final String LOGIN_URL = "users/login";
    public static final String LOGIN_WITH_FACEBOOK = "users/login_with_facebook";
    public static final String IS_EXIST_FACEBOOK_ACCOUNT = "users/isExistFacebookAccount";
    public static final String REGISTER_URL = "users/register";
    public static final String FORGET_PASSWORD = "users/forget";
    public static final String CHANGE_PASSWORD = "users/changepassword";
    public static final String CHANGE_PROFILE = "users/changeprofile";
    
    /*-----------------*/
    /*IMAGE            */
    /*-----------------*/
    public static final String UPLOAD_IMAGE_URL = "image/upload";
    public static final String REMOVE_IMAGE_URL = "image/remove";
    public static final String GET_IMAGE_URL = "image/get";
    public static final String UPDATE_IMAGE_URL = "image/update";
    
    /*-----------------*/
    /*WALLET           */
    /*-----------------*/
    public static final String CREATE_WALLET = "wallet/create";
    public static final String UPDATE_WALLET = "wallet/updateWallet";
    public static final String DELETE_WALLET = "wallet/deleteWallet";
    public static final String GET_ALL_WALLET = "wallet/getInfoWallet";
    public static final String MOVE_WALLET = "wallet/moveMoneyWallet";
    public static final String GET_WALLET_BY_ID = "wallet/getWalletById";
    
    /*-----------------*/
    /*Currency         */
    /*-----------------*/
    public static final String GET_CURRENCIES = "currency";
    public static final String CONVERT_CURRENCY = "converter";
    public static final String GET_REAL_TIME_CURRENCY = CONVERT_CURRENCY + "/update";
    
    /*-----------------*/
    /*Event            */
    /*-----------------*/
    public static final String GET_EVENT = "event/getEvent";
    public static final String CREATE_EVENT = "event/createEvent";
    public static final String UPDATE_EVENT = "event/updateEvent";
    public static final String DELETE_EVENT = "event/deleteEvent";
    
    /*-----------------*/
    /*Saving         */
    /*-----------------*/
    public static final String GET_SAVING = "saving/getSaving";
    public static final String CREATE_SAVING = "saving/create";
    public static final String UPDATE_SAVING = "saving/update";
    public static final String DELETE_SAVING = "saving/delete";
    public static final String TAKE_IN_SAVING = "saving/takeIn";
    public static final String TAKE_OUT_SAVING = "saving/takeOut";
    
    /*-----------------*/
    /*Person         */
    /*-----------------*/
    public static final String GET_PERSON = "person/getPerson";
    public static final String ADD_PERSON = "person/add";
    public static final String REMOVE_PERSON = "person/remove";
    public static final String UPDATE_PERSON = "person/update";
    public static final String SYNC_PERSON = "person/sync";
    
    /*-----------------*/
    /*Budget           */
    /*-----------------*/
    public static final String GET_BUDGET = "budget/getBudget";
    public static final String CREATE_BUDGET = "budget/createBudget";
    public static final String UPDATE_BUDGET = "budget/updateBudget";
    public static final String DELETE_BUDGET = "budget/deleteBudget";
    
    /*-----------------*/
    /*Category         */
    /*-----------------*/
    public static final String GET_CATEGORY = "category/get";
    public static final String GET_CATEGORY_BY_TYPE = "category/getByType";
    public static final String ADD_CATEGORY = "category/add";
    public static final String UPDATE_CATEGORY = "category/update";
    public static final String DELETE_CATEGORY = "category/remove";
    public static final String SYNC_CATEGORY = "category/sync";
    
    /*-----------------*/
    /*Transaction      */
    /*-----------------*/
    public static final String ADD_TRANSACTION = "transaction/add";
    public static final String UPDATE_TRANSACTION = "transaction/update";
    public static final String REMOVE_TRANSACTION = "transaction/remove";
    public static final String GET_TRANSACTION_BY_CATEGORY = "transaction/getByCategory";
    public static final String GET_ALL_TRANSACTION = "transaction/getAll";
    public static final String GET_TRANSACTION_BY_ID = "transaction/get";
    public static final String GET_TRANSACTION_BY_TYPE = "transaction/getByType";
    public static final String GET_TRANSACTION_BY_TIME = "transaction/getByTime";
    public static final String GET_TRANSACTION_BY_EVENT = "transaction/getTransactionByEvent";
    public static final String GET_TRANSACTION_BY_BUDGET = "transaction/getTransactionByBudget";
    public static final String GET_TRANSACTION_BY_SAVING = "transaction/getTransactionBySaving";
    public static final String SYNC_TRANSACTION = "transaction/sync";
    
    public static final String GET_ALL_DEBT_LOAN = "debt_loan/get";
    public static final String ADD_DEBT_LOAN = "debt_loan/add";
    public static final String UPDATE_DEBT_LOAN = "debt_loan/update";
    public static final String DELETE_DEBT_LOAN = "debt_loan/delete";
}
