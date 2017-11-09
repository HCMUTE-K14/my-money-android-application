package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface WalletService {
    
    @POST(MyMoneyApi.CREATE_WALLET + "/{userid}/{token}")
    Observable<JsonResponse<String>> createWallet(@Body Wallet wallet,
              @Path("userid") String userid, @Path("token") String token);
    
    @POST(MyMoneyApi.UPDATE_WALLET + "/{userid}/{token}")
    Observable<JsonResponse<String>> updateWallet(@Body Wallet wallet,
              @Path("userid") String userid, @Path("token") String token);
    
    @GET(MyMoneyApi.DELETE_WALLET + "/{userid}/{token}/{idWallet}")
    Observable<JsonResponse<String>> deleteWallet(@Path("userid") String userid,
              @Path("token") String token, @Path("idWallet") String idWallet);
    
    @GET(MyMoneyApi.GET_ALL_WALLET + "/{userid}/{token}")
    Observable<JsonResponse<List<Wallet>>> getAllWallet(@Path("userid") String userid,
              @Path("token") String token);
    
    @GET(MyMoneyApi.MOVE_WALLET)
    Observable<JsonResponse<String>> moveWallet(
              @Query("uid") String userid,
              @Query("token") String token,
              @Query("wallet_from") String wallet1,
              @Query("wallet_to") String wallet2,
              @Query("money_minus") String moneyMinus,
              @Query("money_plus") String moneyPlus,
              @Query("date_created") String dateCreated
    );
    
    @GET(MyMoneyApi.GET_WALLET_BY_ID)
    Observable<JsonResponse<Wallet>> getWalletById(@Query("uid") String userid,
              @Query("token") String token,
              @Query("wallet_id") String wallet_id);
}
