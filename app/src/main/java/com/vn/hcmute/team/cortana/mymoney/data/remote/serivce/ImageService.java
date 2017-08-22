package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 8/21/17.
 */

public interface ImageService {
    
    @GET(MyMoneyApi.GET_IMAGE_URL)
    Observable<JsonResponse<List<Image>>> get(@Query("uid") String userid,
              @Query("token") String token);
    
    @GET(MyMoneyApi.GET_IMAGE_URL + "/{id}")
    Observable<JsonResponse<Image>> getImageById(@Query("uid") String userid,
              @Query("token") String token, @Path("id") String id);
    
    @Multipart
    @POST(MyMoneyApi.UPLOAD_IMAGE_URL)
    Observable<JsonResponse<String>> upload(@Part("uid") RequestBody userid,
              @Part("token") RequestBody token, @Part("detail") RequestBody detail,
              @Part MultipartBody.Part file);
    
    
    @POST(MyMoneyApi.REMOVE_IMAGE_URL)
    Observable<JsonResponse<String>> remove(@Query("uid") String userid,
              @Query("token") String token, @Query("id") String id);
    
    @POST(MyMoneyApi.UPDATE_IMAGE_URL)
    Observable<JsonResponse<String>> update(@Query("uid") String userid,
              @Query("token") String token, @Query("id") String id);
}
