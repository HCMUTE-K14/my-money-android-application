package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface PersonService {
    
    @GET(MyMoneyApi.GET_PERSON + "/{userid}/{token}")
    Observable<JsonResponse<List<Person>>> getPerson(@Path("userid") String userid,
              @Path("token") String token);
    
    @POST(MyMoneyApi.ADD_PERSON + "/{userid}/{token}")
    Observable<JsonResponse<String>> addPerson(@Body Person person, @Path("userid") String userid,
              @Path("token") String token);
    
    @GET(MyMoneyApi.REMOVE_PERSON + "/{userid}/{token}/{personid}")
    Observable<JsonResponse<String>> removePerson(@Path("userid") String userid,
              @Path("token") String token, @Path("personid") String personid);
    
    @POST(MyMoneyApi.UPDATE_PERSON + "/{userid}/{token}")
    Observable<JsonResponse<String>> updatePerson(@Path("userid") String userid,
              @Path("token") String token, @Body Person person);
    
    @POST(MyMoneyApi.SYNC_PERSON + "/{userid}/{token}")
    Observable<JsonResponse<String>> synchPerson(@Path("userid") String userid,
              @Path("token") String token, @Body List<Person> person);
}
