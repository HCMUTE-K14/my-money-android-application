package com.vn.hcmute.team.cortana.mymoney.data.remote;

import static com.vn.hcmute.team.cortana.mymoney.ApplicationConfig.CACHE_MAX_AGE;
import static com.vn.hcmute.team.cortana.mymoney.ApplicationConfig.CACHE_MAX_STALE;
import static com.vn.hcmute.team.cortana.mymoney.ApplicationConfig.CONNECT_TIMEOUT;
import static com.vn.hcmute.team.cortana.mymoney.ApplicationConfig.READ_TIMEOUT;

import android.content.Context;
import com.google.gson.Gson;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.exception.NetworkException;
import com.vn.hcmute.team.cortana.mymoney.utils.NetworkUtil;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by infamouSs on 8/10/17.
 */

@Singleton
public class ServiceGenerator {
    
    private Gson mGson;
    private OkHttpClient mClient;
    
    private Context mContext;
    
    @Inject
    public ServiceGenerator(Context context, Gson gson) {
        this.mContext = context;
        mClient = new OkHttpClient.Builder()
                  .cache(createDefaultCache(this.mContext))
                  .addInterceptor(createCacheControlInterceptor())
                  .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                  .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                  .build();
        
        this.mGson = gson;
    }
    
    
    private Cache createDefaultCache(Context context) {
        File cacheDir = new File(context.getCacheDir().getAbsolutePath(), "/my-money-cache/");
        if (cacheDir.mkdirs() || cacheDir.isDirectory()) {
            return new Cache(cacheDir, 1024 * 1024 * 10);
        }
        return null;
    }
    
    
    private Interceptor createCacheControlInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try{
                    Request modifiedRequest = chain.request().newBuilder()
                              .addHeader("Cache-Control", String.format("max-age=%d, max-stale=%d",
                                        CACHE_MAX_AGE, CACHE_MAX_STALE))
                              .build();
                    return chain.proceed(modifiedRequest);
                }catch (SocketTimeoutException e){
                    throw new NetworkException(mContext.getString(R.string.message_connect_server_error));
                }
               
            }
        };
    }
    
    public <T> T getService(final Class<T> clazz) {
        return getService(clazz, MyMoneyApi.BASE_URL);
        
    }
    
    public <T> T getService(final Class<T> clazz, String baseurl) {
        if(!NetworkUtil.isNetworkAvailable(mContext)){
            return null;
        }
        try{
            Retrofit retrofit = new Retrofit.Builder()
                      .baseUrl(baseurl)
                      .client(mClient)
                      .addConverterFactory(GsonConverterFactory.create(mGson))
                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                      .build();
            return retrofit.create(clazz);
        }catch (Exception e){
            throw new NetworkException(mContext.getString(R.string.message_connect_server_error));
        }
    }
}
