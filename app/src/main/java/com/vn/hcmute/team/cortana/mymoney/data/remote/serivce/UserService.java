package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface UserService {
    
    @POST(MyMoneyApi.LOGIN_URL)
    Observable<JsonResponse<User>> login(@Body UserCredential userCredential);
    
    @POST(MyMoneyApi.REGISTER_URL)
    Observable<JsonResponse<String>> register(@Body User user);
}
