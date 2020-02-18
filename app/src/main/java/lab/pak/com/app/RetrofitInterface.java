package lab.pak.com.app;

import java.util.List;

import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.Models.Messages;
import lab.pak.com.app.Models.Providerinformation;
import lab.pak.com.app.Models.Users;
import lab.pak.com.app.Models.bids;
import lab.pak.com.app.Models.filter;
import lab.pak.com.app.Models.Userinformation;
import lab.pak.com.app.Notifications.notification;
import lab.pak.com.app.Review.Review;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @POST("user_model/create.php")
    @FormUrlEncoded
    Call<Object> getData(@Field("id") String id);

    //@GET("custom/drivers/insert.php")
    //Call<Object> getinsertdata(@("did") String id,@Field("lat")String lat,@Field("lng")String lng);

    @GET
    Call<List<Users>> getusers(@Url String url);



    @POST("custom/driverwebservices/acceptOrder.php")
   @FormUrlEncoded
    Call<Object> acceptorder(@Field("orderid") String id);

    @POST("custom/driverwebservices/cancelOrder.php")
 @FormUrlEncoded
    Call<Object> cancelorder(@Field("orderid") String id);

    @POST("custom/drivers/action.php")
    @FormUrlEncoded
    Call<Object> uploadLocation(@Field("id") String id, @Field("lat")String lat, @Field("lng") String lng);// @Field("lat") String lat, @Field("lng") String lng)




    @GET
    Call<Object>  signup(@Url String url);
    @GET
    Call<List<Jobs>> getjobs(@Url String url);

    @GET
    Call<List<Userinformation>> login(@Url String url);

    @GET
    Call<List<Providerinformation>> loginprovider(@Url String url);


    @Multipart
    @POST("Models/uploadd.php")
    Call<ResponseBody> postFile(@Part MultipartBody.Part file, @Part("description") RequestBody description,@Part("var") RequestBody jsonobject);

    @Multipart
    @POST("Models/uploadd.php")
    Call<Object> postFileex(@Part MultipartBody.Part file, @Part("description") RequestBody description,@Part("var") RequestBody jsonobject);

    @Multipart
    @POST("Models/post.php")
    Call<ResponseBody> postjob(@Part MultipartBody.Part file, @Part("description") RequestBody description,@Part("var") RequestBody jsonobject);



    @Multipart
    @POST("/uploadd.php")
    Call<ResponseBody> uploadMultipleFilesDynamic(

            @Part MultipartBody.Part files);
    @GET
    Call<List<Review>> getreview(@Url String url);
    @GET
    Call<Object> submitbid(@Url String url);

    @GET
    Call<List<Messages>> getmessage(@Url String url);

    @GET
    Call<List<bids>> getbids(@Url String url);
    @GET
    Call siup(@Url String url);
    @GET
    Call<List<notification>> getnotifications(@Url String url);
    @GET
    Call<List<filter>> getcountries(@Url String url);

   @Multipart
   @POST("Models/banktransfer.php")
   Call<ResponseBody> postbank(@Part MultipartBody.Part file, @Part("description") RequestBody description,@Part("var") RequestBody jsonobject);

   @GET
    Call<List<Messages>> getconversations(@Url String s);
}
