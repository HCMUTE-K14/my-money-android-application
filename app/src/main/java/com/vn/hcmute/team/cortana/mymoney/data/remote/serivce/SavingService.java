package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface SavingService {
    
    @GET(MyMoneyApi.GET_SAVING + "/{userid}/{token}")
    Observable<JsonResponse<List<Saving>>> getSaving(@Path("userid") String useid,
              @Path("token") String token);
    
    @POST(MyMoneyApi.CREATE_SAVING + "/{userid}/{token}")
    Observable<JsonResponse<String>> createSaving(@Body Saving saving, @Path("userid") String useid,
              @Path("token") String token);
    
    @POST(MyMoneyApi.UPDATE_SAVING + "/{userid}/{token}")
    Observable<JsonResponse<String>> updateSaving(@Body Saving saving, @Path("userid") String useid,
              @Path("token") String token);
    
    @GET(MyMoneyApi.DELETE_SAVING + "/{userid}/{token}/{idSaving}")
    Observable<JsonResponse<String>> deleteSaving(@Path("userid") String useid,
              @Path("token") String token, @Path("idSaving") String idSaving);
    
    @GET(MyMoneyApi.TAKE_IN_SAVING +
         "/{userid}/{token}/{idWallet}/{idSaving}/{moneyUpdateWallet}/{moneyUpdateSaving}")
    Observable<JsonResponse<String>> takeInSaving(@Path("userid") String useid,
              @Path("token") String token, @Path("idWallet") String idWallet,
              @Path("idSaving") String idSaving,
              @Path("moneyUpdateWallet") String moneyUpdateWallet,
              @Path("moneyUpdateSaving") String moneyUpdateSaving);
    
    @GET(MyMoneyApi.TAKE_OUT_SAVING +
         "/{userid}/{token}/{idWallet}/{idSaving}/{moneyUpdateWallet}/{moneyUpdateSaving}")
    Observable<JsonResponse<String>> takeOutSaving(@Path("userid") String useid,
              @Path("token") String token, @Path("idWallet") String idWallet,
              @Path("idSaving") String idSaving,
              @Path("moneyUpdateWallet") String moneyUpdateWallet,
              @Path("moneyUpdateSaving") String moneyUpdateSaving);
}
