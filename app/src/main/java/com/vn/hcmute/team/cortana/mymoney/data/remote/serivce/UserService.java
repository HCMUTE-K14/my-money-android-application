package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface UserService {
    
    @POST(MyMoneyApi.LOGIN_URL)
    Observable<JsonResponse<User>> login(@Body UserCredential userCredential);
    
    @POST(MyMoneyApi.LOGIN_WITH_FACEBOOK)
    Observable<JsonResponse<User>> login(@Body User userCredential);
    
    @POST(MyMoneyApi.IS_EXIST_FACEBOOK_ACCOUNT)
    Observable<JsonResponse<String>> isExistFacebookAccount(@Body User userCredential);
    
    @POST(MyMoneyApi.REGISTER_URL)
    Observable<JsonResponse<String>> register(@Body User user);
    
    @POST(MyMoneyApi.FORGET_PASSWORD)
    Observable<JsonResponse<String>> forgetPassword(@Query("email") String email);
    
    @POST(MyMoneyApi.CHANGE_PASSWORD)
    Observable<JsonResponse<String>> changePassword(@Query("uid") String userid,
              @Query("token") String token, @Query("oldpassword") String oldPassword,
              @Query("newpassword") String newPassword);
    
    @POST(MyMoneyApi.CHANGE_PROFILE)
    Observable<JsonResponse<String>> changeProfile(@Query("uid") String userid,
              @Query("token") String token, @Body User user);
}
