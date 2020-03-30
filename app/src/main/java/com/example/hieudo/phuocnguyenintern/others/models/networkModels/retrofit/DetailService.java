package com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.Question;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailService {

    @GET("/questions/{id}?key=UE7EJ*9vN3mwxVyPWNlqvA((")
    Call<Question> getDetailQuestion(
            @Path("id") int id,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("filter") String withBody,
            @Query("site") String site);

}

