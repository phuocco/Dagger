package com.example.hieudo.phuocnguyenintern.ui.home.detailScreen;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.DetailService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.RetrofitClient;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseFragment;
import com.example.hieudo.phuocnguyenintern.utils.AppUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailScreenFragment extends BaseFragment implements DetailScreenContract.View{

    @BindView(R.id.detailFrag_ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.detailFrag_tvTitle)
    TextView tvTitle;
    @BindView(R.id.detailFrag_tvUserPoint)
    TextView tvUserPoint;
    @BindView(R.id.detailFrag_tvTime)
    TextView tvTime;
    @BindView(R.id.detailFrag_tvUsername)
    TextView tvUsername;
    @BindView(R.id.detailFrag_tvPoint)
    TextView tvPoint;
    @BindView(R.id.detailFrag_webView)
    WebView webView;
    @BindView(R.id.detailFrag_flexTag)
    FlexboxLayout flexboxLayout;
    private String siteApi = Constants.SITE_API;
    private int id = 0;
    private Retrofit retrofit;
    private DetailService detailService;
    private DetailScreenPresenter presenter;

    public static DetailScreenFragment newInstance(int id, String siteApiParam){
        DetailScreenFragment detailFragment = new DetailScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID,id);
        bundle.putString(Constants.ARG_SITE,siteApiParam);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_detail_screen;
    }

    @Override
    protected void initView(View view) {
        showActionBar(view,getString(R.string.stack_over_flow));
        init();
        initPresenter();
        getID();
        presenter.callDetail(retrofit,detailService,id,"desc", "activity", "withBody", siteApi);
    }

    private void init() {
        retrofit = RetrofitClient.getInstance();
        detailService = retrofit.create(DetailService.class);
    }

    private void initPresenter() {
        presenter = new DetailScreenPresenter();
        presenter.setView(this);
    }

    private void getID() {
        if (getArguments() != null){
            siteApi = getArguments().getString(Constants.ARG_SITE);
            id = getArguments().getInt(Constants.ID);
        }
    }

    @Override
    public void showDetail(List<QuestionItem> questionItem) {
        tvTitle.setText(questionItem.get(0).getTitle());
        tvUserPoint.setText(String.valueOf(questionItem.get(0).getOwner().getReputation()));
        Picasso.get().load(questionItem.get(0).getOwner().getProfileImage()).into(ivAvatar);
        tvUsername.setText(questionItem.get(0).getOwner().getDisplayName());
        tvPoint.setText(String.valueOf(questionItem.get(0).getScore()));
        int time = (int) (new Date().getTime() / 1000) - questionItem.get(0).getCreationDate();
        tvTime.setText(AppUtils.showTime(getContext(), time));
        webView.loadData(questionItem.get(0).getBody(), "text/html", "UTF-8");
    }

    @Override
    public void showTag(List<String> tagArray) {
        for (int i = 0; i < tagArray.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding(2, 2, 2, 2);
            textView.setBackgroundResource(R.drawable.tag_background);
            textView.setText(tagArray.get(i));
            View divider = new View(getContext());
            divider.setLayoutParams(new FlexboxLayout.LayoutParams(5, 0));
            flexboxLayout.addView(divider);
            flexboxLayout.addView(textView);
        }
    }
}
