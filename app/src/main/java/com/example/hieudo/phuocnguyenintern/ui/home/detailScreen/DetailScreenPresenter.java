package com.example.hieudo.phuocnguyenintern.ui.home.detailScreen;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.Question;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.DetailService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailScreenPresenter implements DetailScreenContract.Presenter {

    private DetailScreenContract.View view;

    public void setView(DetailScreenContract.View view) {
        this.view = view;
    }


    @Override
    public void callDetail(Retrofit retrofit, DetailService detailService, int id, String order, String sort, String withBody, String siteApi) {
        Call<Question> call = detailService.getDetailQuestion(id,order,sort,withBody,siteApi);
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()){
                    view.showDetail(response.body().getItems());
                    view.showTag(response.body().getItems().get(0).getTags());
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });
    }
}
