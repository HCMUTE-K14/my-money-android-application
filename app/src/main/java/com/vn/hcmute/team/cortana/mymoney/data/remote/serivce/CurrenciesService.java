package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.ResultConvert;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface CurrenciesService {
    
    @GET(MyMoneyApi.GET_CURRENCIES)
    Observable<JsonResponse<List<Currencies>>> getCurrencies();
    
    @GET(MyMoneyApi.CONVERT_CURRENCY)
    Observable<JsonResponse<ResultConvert>> convertCurrency(@Query("a") String amount,
              @Query("from") String from, @Query("to") String to);
    
    @GET(MyMoneyApi.GET_REAL_TIME_CURRENCY)
    Observable<JsonResponse<RealTimeCurrency>> getRealTimeCurrency();
}
