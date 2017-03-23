package com.dog.model.http;

import com.dog.model.bean.TopClassifyBean;
import com.dog.model.bean.TopListBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TDogApis {

    //API 文档 http://www.tngou.net/doc/top

    String HOST = "http://www.tngou.net/api/";

    //该API返回来的图片路径不是绝对路径，需要这个加上 如果需要制定图片大小 在最后面拼接_180x120
    //如：http://tnfs.tngou.net/image/top/default.jpg_180x120
    String IMAGE_HOST = "http://tnfs.tngou.net/image";
    String IMAGE_SIZE = "_128x96";

    /**
     * 得到热点分类
     */
    @GET("top/classify")
    Observable<TopClassifyBean> getTopClassify();

    /**
     * 热点列表
     */
    @GET("top/list")
    Observable<TopListBean> getTopList(@Query("id") int id, @Query("page") int page, @Query("rows") int rows);

}
