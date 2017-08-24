package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface CurrenciesService {
    
    @GET(MyMoneyApi.GET_CURRENCIES)
    Observable<JsonResponse<List<Currencies>>> getCurrencies();
}
