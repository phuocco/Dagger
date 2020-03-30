package com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.Site;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.Question;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.searchInTitle.SearchTitle;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.tags.Tag;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.users.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeService {


    @GET("/questions?key=UE7EJ*9vN3mwxVyPWNlqvA((")
    Call<Question> getQuestions(
            @Query("page") int page,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site);

    @GET("/tags?key=UE7EJ*9vN3mwxVyPWNlqvA((")
    Call<Tag> getTags(
            @Query("page") int page,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("inname") String inname,
            @Query("site") String site);


    @GET("/users?key=UE7EJ*9vN3mwxVyPWNlqvA((")
    Call<User> getUsers(
            @Query("page") int page,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site);

    @GET("/search?key=UE7EJ*9vN3mwxVyPWNlqvA((")
    Call<SearchTitle> getSearchTitle(
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("intitle") String intitle,
            @Query("site") String site);

    @GET("sites?key=UE7EJ*9vN3mwxVyPWNlqvA((")
    Call<Site> getSites(
            @Query("page") int page
    );
}
