package com.vn.hcmute.team.cortana.mymoney.data.remote;

import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface RemoteTask {
    public interface UserTask{
        Observable<User> login(UserCredential userCredential);
    
        Observable<String> register(User user);
    }
    public interface ImageTask{
        Observable<List<Image>> getImage(String userid,String token);
        
        Observable<Image> getImageByid(String userid,String token,String imageid);
        
        Observable<String> removeImage(String userid,String token,String imageid);
        
        Observable<String> uploadImage(RequestBody userid,RequestBody token,RequestBody detail,
                  MultipartBody.Part file);
        
        Observable<String> updateImage(String userid,String token,String imageid);
    }
}
