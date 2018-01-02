package com.vn.hcmute.team.cortana.mymoney.utils;

import com.vn.hcmute.team.cortana.mymoney.model.Person;

/**
 * Created by infamouSs on 8/21/17.
 */

public class Constraints {
    
    public static final Person SOME_ONE_PERSON = new Person("some_one_id", "someone", "");
    
    
    public class RequestCode {
        
        public static final int LOGIN_REQUEST_CODE = 1;
        public static final int CURRENCY_REQUEST_CODE = 2;
        public static final int ADD_WALLET_REQUEST_CODE = 3;
        public static final int EDIT_WALLET_REQUEST_CODE = 4;
        public static final int CALCULATOR_REQUEST_CODE = 5;
        public static final int CHOOSE_WALLET_REQUEST_CODE = 6;
        public static final int SELECT_ICON_REQUEST_CODE = 7;
        public static final int CHOOSE_CATEGORY_REQUEST_CODE = 8;
        public static final int ADD_CATEGORY_REQUEST_CODE = 9;
        public static final int CHOOSE_PARENT_CATEGORY_REQUEST_CODE = 10;
        public static final int UPDATE_CATEGORY_REQUEST_CODE = 11;
        public static final int CHANGE_CATEGORY_REQUEST_CODE = 99;
        public static final int CHOOSE_CONTACT_REQUEST_CODE = 100;
        public static final int CHOOSE_IMAGE_REQUEST_CODE = 101;
        public static final int CHOOSE_EVENT_REQUEST_CODE = 102;
        public static final int UPDATE_TRANSACTION_REQUEST_CODE = 103;
        public static final int ADD_DEBT_LOAN_REQUEST_CODE = 104;
        public static final int OPEN_INFO_TRANSACTION_MODE_DEBT_LOAN_REQUEST_CODE = 105;
        public static final int WALLET_FROM_REQUEST_CODE = 106;
        public static final int WALLET_TO_REQUEST_CODE = 107;
    }
    
    public class ResultCode {
        
        public static final int EDIT_WALLET_RESULT_CODE = -5;
        public static final int REMOVE_WALLET_RESULT_CODE = -6;
        public static final int ADD_WALLET_RESULT_CODE = -7;
        public static final int ADD_TRANSACTION_RESULT_CODE = -8;
        public static final int EDIT_TRANSACTION_RESULT_CODE = -9;
        public static final int REMOVE_TRANSACTION_RESULT_CODE = -10;
        public static final int NEED_UPDATE_CURRENT_WALLET_RESULT_CODE = -12;
        public static final int NEED_RELOAD_DATA = -13;
        public static final int CHANGE_WALLET_RESULT_CODE = -15;
    }
    
}
