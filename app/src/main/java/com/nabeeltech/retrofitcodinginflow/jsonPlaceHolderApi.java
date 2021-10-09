package com.nabeeltech.retrofitcodinginflow;

import com.nabeeltech.retrofitcodinginflow.Model.Comment;
import com.nabeeltech.retrofitcodinginflow.Model.Post;
import com.nabeeltech.retrofitcodinginflow.Model.PostObject;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface jsonPlaceHolderApi
{
    @GET("posts")
    Call<List<Post>> getPosts();

    //get comment of postid
    @GET("posts/1/comments")
    Call<List<Comment>> getCommentsBySelectedId();

    @GET("posts{id}")
    Call<List<Post>> getPostById (@Path("id") int id);

    //get comments by its postid this is a path parameter
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    //get post by user id this is a query parameter first version
    @GET("posts")
    Call<List<Post>> getPostsByQuery(@Query("userId") int userId);

    //same  as above @query("userId") but difference is that we sort and orderby also we change int to Integer because int can be nullable
    //posts?userId=1&_sort=id&_order=desc
    @GET("posts")
    Call<List<Post>> getPostsByQueryDesc(
            @Query("userId") Integer [] userId,
            //@Query("userId") Integer userId2, //instead of putting to many query we can make Integer as array
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    //combination of query parameters and dont want to define the in method declaration
    @GET("posts")
    Call<List<Post>> getPostsByQueryMap(@QueryMap Map<String, String> parameters);

    //get full url
    @GET
    Call<List<Comment>> getCommentsByUrl(@Url String url);

    @GET
    Call<List<PostObject>> getObject(@Url String url);

    //post by body
    @POST("posts")
    Call<Post> createPostByBody(@Body Post post);

    //post by formurlencoded
    //it is encoded the same way as url encoded it replaces spaces and other special characters usually use in html forms suitable for key value pairs
    //userId=23&title=newtitle&body=thisisabouttitle
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPostByFormUrlEncoded_one(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );


    //form urlencoded second method
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPostByFormUrlEncoded_two(@FieldMap Map<String, String> fields);

    //put with id
    @Headers({"put-Static-Header1: 123", "put-Static-Header2: 456"})
    @PUT("posts/{id}")
    Call<Post> putPost(@Header("put-Dynamic-Header") String header,
                       @Path("id") int id,
                       @Body Post post);

    //patch with id
    @PATCH("posts/{id}")
    Call<Post> patchPost(@HeaderMap Map<String, String> headers,
                         @Path("id") int id,
                         @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
