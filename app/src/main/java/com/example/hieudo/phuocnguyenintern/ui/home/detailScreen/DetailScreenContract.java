package com.example.hieudo.phuocnguyenintern.ui.home.detailScreen;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.DetailService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;

import java.util.List;

import retrofit2.Retrofit;

public interface DetailScreenContract {
    interface View {
        void showDetail(List<QuestionItem> questionItem);
        void showTag(List<String> tagArray);
    }
    interface Presenter {
        void callDetail(Retrofit retrofit, DetailService detailService, int id, String order, String sort, String withBody, String siteApi);
    }
}
