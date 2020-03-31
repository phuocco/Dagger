package com.example.hieudo.phuocnguyenintern.ui.home.newHome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.di.component.ApplicationComponent;
import com.example.hieudo.phuocnguyenintern.di.component.NewHomeFragmentComponent;
import com.example.hieudo.phuocnguyenintern.di.qualifier.ApplicationContext;
import com.example.hieudo.phuocnguyenintern.others.adapters.QuestionsAdapter;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseApplication;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends BaseFragment {
    @BindView(R.id.homeFrag_recyclerView)
     RecyclerView recyclerView;

    NewHomeFragmentComponent newHomeFragmentComponent;

    @Inject
    public QuestionsAdapter questionsAdapter;
    @Inject
    public HomeService homeService;
    @Inject
    @ApplicationContext
    public Context context;
    @Inject
    @ApplicationContext
    public Context activityContext;



    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ApplicationComponent applicationComponent = BaseApplication.get(getActivity())
                .getApplicationComponent();
        
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_new_home;
    }
}
