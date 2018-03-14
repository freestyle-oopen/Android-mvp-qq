package aiyagirl.nanchen.com.myapplication.net;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.entity.Wether;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by renyukai on 2017/6/12.
 */

public interface ServiceInterface {

    @GET("data/sk/101010100.html")
   Observable<Wether> loadWheater();

    @FormUrlEncoded
    @POST("addUser")
    Observable<Response<Boolean>> addUserToDB(@Field("name") String name, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("delectUser")
    Observable<Response<Boolean>> delectUser(@Field("name") String name, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("searchUser")
    Observable<Response<List<User>>> searchUser(@Field("keyword") String keyword,@Field("exclude") String exclude);
}
