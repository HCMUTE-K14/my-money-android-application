package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 10/18/17.
 */

public interface DebtLoanService {
    
    @GET(MyMoneyApi.GET_ALL_DEBT_LOAN)
    Observable<JsonResponse<List<DebtLoan>>> getDebtLoanByType(@Query("uid") String userid,
              @Query("token") String token, @Query("wallet_id") String wallet_id,
              @Query("type") String type);
    
    
    @POST(MyMoneyApi.ADD_DEBT_LOAN)
    Observable<JsonResponse<String>> addDebtLoan(@Query("uid") String userid,
              @Query("token") String token,
              @Body DebtLoan debtLoan);
    
    @POST(MyMoneyApi.UPDATE_DEBT_LOAN)
    Observable<JsonResponse<String>> updateDebtLoan(@Query("uid") String userid,
              @Query("token") String token, @Query("wallet_id") String wallet_id,
              @Body DebtLoan debtLoan);
    
    @POST(MyMoneyApi.DELETE_DEBT_LOAN)
    Observable<JsonResponse<String>> deleteDebtLoan(@Query("uid") String userid,
              @Query("token") String token,
              @Body DebtLoan debtLoan);
}
