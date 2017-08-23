package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface EventService {
    
    @GET(MyMoneyApi.GET_EVENT + "/{userid}/{token}")
    Observable<JsonResponse<List<Event>>> getEvent(@Path("userid") String userid,
              @Path("token") String token);
    
    @POST(MyMoneyApi.CREATE_EVENT + "/{userid}/{token}")
    Observable<JsonResponse<String>> createEvent(@Body Event event, @Path("userid") String userid,
              @Path("token") String token);
    
    @POST(MyMoneyApi.UPDATE_EVENT + "/{userid}/{token}")
    Observable<JsonResponse<String>> updateEvent(@Body Event event, @Path("userid") String userid,
              @Path("token") String token);
    
    @GET(MyMoneyApi.DELETE_EVENT + "/{userid}/{token}/{idEvent}")
    Observable<JsonResponse<String>> deleteEvent(@Path("userid") String userid,
              @Path("token") String token, @Path("idEvent") String idEvent);
}
