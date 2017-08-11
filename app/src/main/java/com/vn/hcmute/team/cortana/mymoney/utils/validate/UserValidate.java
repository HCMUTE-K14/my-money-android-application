package com.vn.hcmute.team.cortana.mymoney.utils.validate;

import static com.vn.hcmute.team.cortana.mymoney.utils.ObjectUtil.requireNotNull;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import java.util.regex.Pattern;

/**
 * Created by infamouSs on 8/11/17.
 */

public class UserValidate {
    
    
    public static final String TAG = UserValidate.class.getSimpleName();
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{4,15}$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{4,15}$";
    private static final String NAME_PATTERN = "^[a-zA-Z0-9]{4,15}$";
    public static UserValidate sInstance = new UserValidate();
    private Pattern mPatternUsername;
    private Pattern mPatternPassword;
    private Pattern mPatternName;
    
    /**
     * Username Pattern & Password Pattern
     * Length: 4-15 char
     * Character: a-z,A-Z,0-9
     */
    
    
    private UserValidate() {
        mPatternUsername = Pattern.compile(USERNAME_PATTERN);
        mPatternPassword = Pattern.compile(PASSWORD_PATTERN);
        mPatternName = Pattern.compile(NAME_PATTERN);
    }
    
    public static UserValidate getInstance() {
        return sInstance;
    }
    
    public boolean validateUser(User user) {
        requireNotNull(user);
        String username = user.getUsername();
        String password = user.getPassword();
        String name = user.getName();
        
        if (username == null || password == null || name == null) {
            return false;
        }
        
        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            return false;
        }
        
        if (!mPatternUsername.matcher(username).matches()) {
            return false;
        }
        
        if (!mPatternPassword.matcher(password).matches()) {
            return false;
        }
        
        if (!mPatternName.matcher(name).matches()) {
            return false;
        }
        
        return true;
    }
    
    public boolean validateUser(UserCredential userCredential) {
        requireNotNull(userCredential);
        
        String username = userCredential.getUsername();
        String password = userCredential.getPassword();
        
        if (username == null || password == null) {
            return false;
        }
        
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        
        if (!mPatternUsername.matcher(username).matches()) {
            return false;
        }
        
        if (!mPatternPassword.matcher(password).matches()) {
            return false;
        }
        
        return true;
    }
}
