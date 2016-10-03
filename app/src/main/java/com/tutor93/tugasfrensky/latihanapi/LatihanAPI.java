package com.tutor93.tugasfrensky.latihanapi;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by frensky on 7/31/15.
 */
public interface LatihanAPI {

    /*@POST("/deals/add/{user_id}")
    public LatihanAPIResponse addDealing(@Path("user_id") String userId, @Body DealAPIRequest request);*/

    /*@PUT("/deals/edit")
    public LatihanAPIResponse editDealing(@Path("user_id") String userId, @Body DealAPIRequest request);*/

    /*@DELETE("/deals/delete?")
    public LatihanAPIResponse deleteDealing(@Query("deals_id") String deal_id);
    */

    /*@PUT("/deals/edit?")
    public void editDealing(@Path("user_id") String userId,
                                          @Body DealAPIRequest request,
                                          Callback<LatihanAPIResponse> callback);
    */

    //java.lang.IllegalArgumentException: LatihanAPI.editDealing: URL "/deals/edit" does not contain "{user_id}". (parameter #1)

    /*@PUT("/deals/edit")
    public void editDealing(@Path("user_id") String userId,
                            @Body DealAPIRequest request,
                            Callback<LatihanAPIResponse> callback);*/

    @GET("/deals/{user_id}")
    public LatihanAPIResponse getDealing(@Path("user_id") String userId);

    @DELETE("/deals/delete?")
    public void deleteDealing(@Query("deals_id") String deal_id, Callback<LatihanAPIResponse> callback);

    @PUT("/deals/edit")
    public void editDealing(@Body DealAPIRequest request,
                            Callback<LatihanAPIResponse> callback);

    @POST("/deals/add/{user_id}")
    public void addDealing(@Path("user_id") String userId,
                           @Body DealAPIRequest request,
                           Callback<LatihanAPIResponse> callback);

}
