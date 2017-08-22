package com.vn.hcmute.team.cortana.mymoney.data.remote;

import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.ImageService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.UserService;
import com.vn.hcmute.team.cortana.mymoney.exception.ImageException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserRegisterException;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;


/**
 * Created by infamouSs on 8/10/17.
 */

public class RemoteRepository implements RemoteTask.UserTask,RemoteTask.ImageTask {
    
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
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new UserRegisterException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<List<Image>> getImage(String userid, String token) {
        ImageService imageService=mServiceGenerator.getService(ImageService.class);
        return imageService.get(userid,token)
                  .map(new Function<JsonResponse<List<Image>>, List<Image>>() {
                      @Override
                      public List<Image> apply(@NonNull JsonResponse<List<Image>> listJsonResponse)
                                throws Exception {
                          
                          if(listJsonResponse.getStatus().equals("success")){
                              return listJsonResponse.getData();
                          }else{
                              throw new ImageException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> uploadImage() {
        return null;
    }
}
