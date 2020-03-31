package com.example.hieudo.phuocnguyenintern.ui.home.newHome;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.Question;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;

import java.util.List;

public interface NewHomeContract {
    interface View {
        void showData(List<QuestionItem> questionItems);
    }
    interface Presenter {
        void loadQuestion();
    }
}
