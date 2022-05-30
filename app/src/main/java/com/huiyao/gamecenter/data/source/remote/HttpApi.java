package com.huiyao.gamecenter.data.source.remote;

import com.huiyao.gamecenter.data.entity.NewsList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chengzj on 2017/6/18.
 */

public interface HttpApi {
    //http://gank.io/api/day/2016/10/12
    public static final String base_url = "http://sit.wecarelove.com/api/";


    @GET("list")
    Observable<NewsList> getNewsList(@Query("req_funType") String funType,
                                     @Query("req_count") String count);

}
