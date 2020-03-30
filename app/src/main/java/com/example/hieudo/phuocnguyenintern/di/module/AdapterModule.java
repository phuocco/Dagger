package com.example.hieudo.phuocnguyenintern.di.module;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.di.scopes.ActivityScope;
import com.example.hieudo.phuocnguyenintern.others.adapters.QuestionsAdapter;
import com.example.hieudo.phuocnguyenintern.others.interfaces.RecyclerViewItemClickListener;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module(includes = {HomeScreenContextModule.class})
public class AdapterModule {
    @Provides
    @ActivityScope
    public QuestionsAdapter getQuestionList(List<QuestionItem> questionsList){
        return new QuestionsAdapter(questionsList);
    }

}
