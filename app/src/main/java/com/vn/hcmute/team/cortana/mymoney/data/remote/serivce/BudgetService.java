package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface BudgetService {
    
    @GET(MyMoneyApi.GET_BUDGET)
    Observable<JsonResponse<List<Budget>>> getBudget(@Query("userid") String userid,
              @Query("token") String token);
    
    @POST(MyMoneyApi.CREATE_BUDGET)
    Observable<JsonResponse<String>> createBudget(@Body Budget budget,
              @Query("userid") String userid, @Query("token") String token);
    
    @POST(MyMoneyApi.UPDATE_BUDGET)
    Observable<JsonResponse<String>> updateBudget(@Body Budget budget,
              @Query("userid") String userid, @Query("token") String token);
    
    @GET(MyMoneyApi.DELETE_BUDGET)
    Observable<JsonResponse<String>> deleteBudget(@Query("userid") String userid,
              @Query("token") String token, @Query("budgetId") String budgetId);
}
