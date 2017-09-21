package com.vn.hcmute.team.cortana.mymoney.data.remote.serivce;

import com.vn.hcmute.team.cortana.mymoney.api.MyMoneyApi;
import com.vn.hcmute.team.cortana.mymoney.data.remote.JsonResponse;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 9/15/17.
 */

public interface CategoryService {
    
    @GET(MyMoneyApi.GET_CATEGORY)
    Observable<JsonResponse<List<Category>>> getListCategory(@Query("uid") String userid,
              @Query("token") String token, @Query("type") String type);
    
    @POST(MyMoneyApi.ADD_CATEGORY)
    Observable<JsonResponse<String>> addCategory(@Query("uid") String userid,
              @Query("token") String token,
              @Query("parent_id") String parentId,
              @Body Category category);
    
    @POST(MyMoneyApi.UPDATE_CATEGORY)
    Observable<JsonResponse<String>> updateCategory(@Query("uid") String userid,
              @Query("token") String token,
              @Query("old_parent_id") String oldParentId,
              @Query("new_parent_id") String newParentId,
              @Body Category category);
    
    @POST(MyMoneyApi.DELETE_CATEGORY)
    Observable<JsonResponse<String>> deleteCategory(@Query("uid") String userid,
              @Query("token") String token,
              @Query("parent_id") String parentId,
              @Body Category category);
    
    @POST(MyMoneyApi.SYNC_CATEGORY)
    Observable<JsonResponse<String>> syncCategory(@Query("uid") String userid,
              @Query("token") String token, @Body List<Category> categories);
    
    @GET(MyMoneyApi.GET_CATEGORY_BY_TYPE)
    Observable<JsonResponse<List<Category>>> getListCategoryByType(@Query("uid") String userid,
              @Query("token") String token, @Query("type") String type,
              @Query("trans_type") String transType);
}
