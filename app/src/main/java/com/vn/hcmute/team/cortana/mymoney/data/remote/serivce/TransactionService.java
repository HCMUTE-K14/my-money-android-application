package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 9/28/17.
 */

public interface TransactionService {
    
    @POST(MyMoneyApi.ADD_TRANSACTION)
    Observable<JsonResponse<String>> addTransaction(@Query("uid") String uid,
              @Query("token") String token,
              @Body Transaction transaction);
    
    @POST(MyMoneyApi.UPDATE_TRANSACTION)
    Observable<JsonResponse<String>> updateTransaction(@Query("uid") String uid,
              @Query("token") String token,
              @Body Transaction transaction);
    
    @POST(MyMoneyApi.REMOVE_TRANSACTION)
    Observable<JsonResponse<String>> deleteTransaction(@Query("uid") String uid,
              @Query("token") String token, @Query("transactionid") String trans_id);
    
    
    @GET(MyMoneyApi.GET_TRANSACTION_BY_CATEGORY + "/{walletId}")
    Observable<JsonResponse<List<Transaction>>> getTransactionByCategory(
              @Path("walletId") String walletId, @Query("uid") String uid,
              @Query("token") String token, @Query("categoryid") String categoryId);
    
    @GET(MyMoneyApi.GET_TRANSACTION_BY_ID + "{id}")
    Observable<JsonResponse<Transaction>> getTransactionById(@Path("id") String id,
              @Query("uid") String userid, @Query("token") String token);
    
    @GET(MyMoneyApi.GET_ALL_TRANSACTION)
    Observable<JsonResponse<List<Transaction>>> getTransaction(@Query("uid") String userid,
              @Query("token") String token);
    
    @GET(MyMoneyApi.GET_TRANSACTION_BY_TYPE + "/{walletId}")
    Observable<JsonResponse<List<Transaction>>> getTransactionByType(
              @Path("walletId") String walletId, @Query("uid") String uid,
              @Query("token") String token, @Query("type") String type);
    
    @GET(MyMoneyApi.GET_TRANSACTION_BY_TIME + "/{walletId}")
    Observable<JsonResponse<List<Transaction>>> getTransactionByTime(
              @Path("walletId") String walletId, @Query("uid") String uid,
              @Query("token") String token, @Query("startdate") String startDate,
              @Query("enddate") String endDate);
    
    @GET(MyMoneyApi.GET_TRANSACTION_BY_EVENT + "/{eventId}")
    Observable<JsonResponse<List<Transaction>>> getTransactionByEvent(
              @Path("eventId") String eventId, @Query("uid") String uid,
              @Query("token") String token);
    
    @GET(MyMoneyApi.GET_TRANSACTION_BY_BUDGET + "/{startDate}/{endDate}/{categoryId}/{walletId}")
    Observable<JsonResponse<List<Transaction>>> getTransactionByBudget(
              @Path("startDate") String startDate, @Path("endDate") String endDate,
              @Path("categoryId") String categoryId,
              @Path("walletId") String walletId,
              @Query("uid") String uid,
              @Query("token") String token);
    
    @GET(MyMoneyApi.GET_TRANSACTION_BY_SAVING + "/{idSaving}")
    Observable<JsonResponse<List<Transaction>>> getTransactionBySaving(
              @Path("idSaving") String idSaving,
              @Query("uid") String uid,
              @Query("token") String token
    );
    
    @POST(MyMoneyApi.SYNC_TRANSACTION)
    Observable<JsonResponse<String>> syncTransaction(@Query("uid") String uid,
              @Query("token") String token);
}
