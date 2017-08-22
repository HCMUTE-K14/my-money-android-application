package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 8/21/17.
 */

public interface ImageService {
    
    @GET(MyMoneyApi.GET_IMAGE_URL)
    Observable<JsonResponse<List<Image>>> get(@Query("uid") String userid,
              @Query("token") String token);
    
}
