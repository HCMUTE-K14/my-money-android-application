package com.vn.hcmute.team.cortana.mymoney.data.remote;

import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.UserService;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserRegisterException;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import javax.inject.Inject;


/**
 * Created by infamouSs on 8/10/17.
 */

public class RemoteRepository implements RemoteTask {
    
    public static final String TAG = RemoteRepository.class.getSimpleName();
    
    private ServiceGenerator mServiceGenerator;
    
    @Inject
    public RemoteRepository(ServiceGenerator serviceGenerator) {
        this.mServiceGenerator = serviceGenerator;
    }
    
    @Override
    public Observable<User> login(UserCredential userCredential) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        return userService.login(userCredential)
                  .map(new Function<JsonResponse<User>, User>() {
                      
                      @Override
                      public User apply(@NonNull JsonResponse<User> userJsonResponse)
                                throws Exception {
                          if (userJsonResponse.getStatus().equals("success")) {
                              return userJsonResponse.getData();
                          } else {
                              throw new UserLoginException(userJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> register(User user) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        return userService.register(user)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getMessage();
                          }else{
                              throw new UserRegisterException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
}
