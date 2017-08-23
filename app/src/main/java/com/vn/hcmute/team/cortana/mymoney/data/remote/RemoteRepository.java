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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by infamouSs on 8/10/17.
 */

public class RemoteRepository implements RemoteTask.UserTask, RemoteTask.ImageTask {
    
    public static final String TAG = RemoteRepository.class.getSimpleName();
    
    private ServiceGenerator mServiceGenerator;
    
    @Inject
    public RemoteRepository(ServiceGenerator serviceGenerator) {
        this.mServiceGenerator = serviceGenerator;
    }
    
    @Override
    public Observable<User> login(UserCredential userCredential) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if(userService ==null){
            return null;
        }
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
        if(userService ==null){
            return null;
        }
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
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if(imageService ==null){
            return null;
        }
        return imageService.get(userid, token)
                  .map(new Function<JsonResponse<List<Image>>, List<Image>>() {
                      @Override
                      public List<Image> apply(@NonNull JsonResponse<List<Image>> listJsonResponse)
                                throws Exception {
                          
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new ImageException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<Image> getImageByid(String userid, String token, String imageid) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if(imageService ==null){
            return null;
        }
        return imageService.getImageById(userid, token, imageid)
                  .map(new Function<JsonResponse<Image>, Image>() {
                      @Override
                      public Image apply(@NonNull JsonResponse<Image> imageJsonResponse)
                                throws Exception {
                          if (imageJsonResponse.getStatus().equals("success")) {
                              return imageJsonResponse.getData();
                          } else {
                              throw new ImageException(imageJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    
    @Override
    public Observable<String> removeImage(String userid, String token, String imageid) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if(imageService ==null){
            return null;
        }
        return imageService.remove(userid, token, imageid)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> uploadImage(RequestBody userid, RequestBody token, RequestBody detail,
              MultipartBody.Part file) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if(imageService ==null){
            return null;
        }
        return imageService.upload(userid, token, detail, file)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> updateImage(String userid, String token, String imageid) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if(imageService ==null){
            return null;
        }
        return imageService.update(userid, token, imageid)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    
}
