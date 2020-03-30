package com.example.hieudo.phuocnguyenintern.ui.home.homeFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.adapters.QuestionsAdapter;
import com.example.hieudo.phuocnguyenintern.others.adapters.SearchTitleAdapter;
import com.example.hieudo.phuocnguyenintern.others.adapters.SiteAdapter;
import com.example.hieudo.phuocnguyenintern.others.adapters.TagAdapter;
import com.example.hieudo.phuocnguyenintern.others.adapters.UserAdapter;
import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.others.interfaces.PopUpItemListener;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.Header;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.SpinnerList;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Navigation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.SiteItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.RetrofitClient;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.searchInTitle.SearchTitleItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.tags.TagItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.users.UserItem;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseFragment;
import com.example.hieudo.phuocnguyenintern.ui.home.detailScreen.DetailScreenFragment;
import com.example.hieudo.phuocnguyenintern.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends BaseFragment implements HomeFragmentContract.View, PopUpItemListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.actionBarHome_mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.actionBarHome_mainLayoutSite)
    RelativeLayout mainLayoutSite;
    @Nullable
    @BindView(R.id.homeFrag_llHide)
    LinearLayout llHide;
    @Nullable
    @BindView(R.id.homeFrag_llShow)
    LinearLayout llShow;
    @BindView(R.id.actionBarHome_llTitle)
    LinearLayout llTitle;
    @BindView(R.id.actionBarHome_tvTag)
    TextView tvTag;
    @BindView(R.id.homeFrag_tvSpinner)
    TextView tvSpinner;
    @BindView(R.id.homeFrag_etSearch)
    EditText search;
    @BindView(R.id.homeFrag_ivClose)
    ImageView close;
    @BindView(R.id.homeFrag_recyclerView)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.nav_rvSitesShow)
    RecyclerView recyclerViewNavShow;
    @Nullable
    @BindView(R.id.nav_rvSitesHide)
    RecyclerView recyclerViewNavHide;
    @BindView(R.id.homeFrag_swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.homeFrag_progressBar)
    ProgressBar progressBar;


    //list topbar, spinner, navigation(tablet)
    private ArrayList<Header> headerArrayList = new ArrayList<>();
    private ArrayList<SpinnerList> spinnerArrayList = new ArrayList<>();
    private ArrayList<Navigation> navigationArrayList = new ArrayList<>();

    private boolean showNav = false;

    //list call api
    private List<QuestionItem> questionItems = new ArrayList<>();
    private List<TagItem> tagItems = new ArrayList<>();
    private List<UserItem> userItems = new ArrayList<>();
    private List<SearchTitleItem> searchTitleItem = new ArrayList<>();
    private List<SiteItem> siteItems = new ArrayList<>();


    private String siteApi = Constants.SITE_API;
    private String siteName = Constants.SITE_NAME;
    private String sortTags = Constants.SORT_TAGS;
    private String order = Constants.ORDER;

    private boolean isSite = false;
    private HomeService homeService;

    private QuestionsAdapter questionsAdapter;
    private TagAdapter tagAdapter;
    private UserAdapter userAdapter;
    private SiteAdapter siteAdapter;
    private SearchTitleAdapter searchTitleAdapter;
    private int page = 1;
    private int headerID = 0;
    Retrofit retrofit;

    HomeFragmentPresenter presenter;

    public static HomeScreenFragment newInstance(String siteApiParam, String siteName, boolean isSite) {
        HomeScreenFragment homeFragment = new HomeScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARG_SITE, siteApiParam);
        bundle.putString(Constants.ARG_NAME, siteName);
        bundle.putBoolean(Constants.ARG_IS_SITE, isSite);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home_screen;
    }

    @Override
    protected void initView(View view) {
        init();
        showActionBarHome(view, siteName, getString(R.string.active_questions));
        initPresenter();
        checkSite();
        onBottomList();
        customSearch();
        presenter.addList(headerArrayList);
        presenter.addSpinnerList(spinnerArrayList, headerID, isSite);
        presenter.callQuestions(retrofit, homeService, order, "activity", page, siteApi);
    }

    @OnClick(R.id.homeFrag_ivClose)
    void onClick(View view) {
        if (view.getId() == R.id.homeFrag_ivClose) {
            clearSearch();
        }
    }

    private void customSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.setSearch(s);
            }
        });

        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (headerID == 0) {
                    presenter.callSearch(retrofit, homeService, order, "activity", search.getText().toString(), siteApi);
                    return true;
                } else if (headerID == 1) {
                    tagItems.clear();
                    presenter.callTags(retrofit, homeService, order, sortTags, page, siteApi, search.getText().toString());
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void updateSearch(Editable s) {
        close.setVisibility(View.VISIBLE);
        search.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvTag.setText(getContext().getResources().getString(R.string.search_for, s));
    }

    @Override
    public void showSearch(List<SearchTitleItem> searchTitles) {
        searchTitleItem = searchTitles;
        searchTitleAdapter = new SearchTitleAdapter(searchTitleItem);
        recyclerView.setAdapter(searchTitleAdapter);
        searchTitleAdapter.notifyDataSetChanged();
        searchTitleAdapter.setRecyclerViewItemClickListener((view, position) -> addFragment(new DetailScreenFragment()));
    }

    private void onBottomList() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    page++;
                    selectedHeader(headerID);
                }
            }
        });
    }

    private void selectedHeader(int headerID) {
        switch (headerID) {
            case 0:
                presenter.callQuestions(retrofit, homeService, order, "activity", page, siteApi);
                presenter.addSpinnerList(spinnerArrayList, headerID, false);
                search.setHint(getString(R.string.search_questions));
                break;
            case 1:
                presenter.callTags(retrofit, homeService, order, sortTags, page, siteApi, "");
                presenter.addSpinnerList(spinnerArrayList, headerID, false);
                search.setHint(getString(R.string.search_tags));
                break;
            case 2:
                presenter.callUsers(retrofit, homeService, page, order, "reputation", siteApi);
                presenter.addSpinnerList(spinnerArrayList, headerID, false);
                search.setHint(getString(R.string.search_users));
                break;
        }
    }

    private void checkSite() {
        if (isSite) {
            mainLayout.setVisibility(View.GONE);
            mainLayoutSite.setVisibility(View.VISIBLE);
            tvSpinner.setOnClickListener(v -> AppUtils.showPopUpTagListSite(getContext(), mainLayoutSite, R.layout.popup_tag_list, spinnerArrayList, this));
            presenter.callSites(retrofit, homeService, page);
            tvSpinner.setText(getString(R.string.main_sites));
            search.setHint(getString(R.string.search_sites));
        }
    }

    private void initPresenter() {
        presenter = new HomeFragmentPresenter();
        presenter.setView(this);
    }

    private void init() {
        search.setHint(getString(R.string.search_questions));
        tvSpinner.setText("Active");
        //action bar
        if (getArguments() != null) {
            siteApi = getArguments().getString(Constants.ARG_SITE);
            siteName = getArguments().getString(Constants.ARG_NAME, "Stack Overflow");
            isSite = getArguments().getBoolean(Constants.ARG_IS_SITE);
        }
        swipeLayout.setOnRefreshListener(this::onRefresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        retrofit = RetrofitClient.getInstance();
        homeService = retrofit.create(HomeService.class);
    }

    @Override
    public void onItemClickListener(int position) {
        tvTag.setText(headerArrayList.get(position).getTitle());
        headerID = position;
        selectedHeader(headerID);
        tvSpinner.setText(spinnerArrayList.get(0).getTitle());
    }

    @Override
    public void onTagListItemClickListener(int position) {
        tvSpinner.setText(spinnerArrayList.get(position).getTitle());
        selectSpinner(position);
    }

    private void selectSpinner(int position) {
        questionItems.clear();
        tagItems.clear();
        userItems.clear();
        presenter.selectSpinner(retrofit, homeService, order, "", siteApi, page, headerID, position);
    }

    @Override
    public void showHeaderList(ArrayList<Header> headerArrayList) {
        llTitle.setOnClickListener(v -> AppUtils.showPopUp(getContext(), mainLayout, R.layout.popup_header, headerArrayList, this));
    }

    @Override
    public void showSpinnerList(ArrayList<SpinnerList> spinnerArrayList) {
        tvSpinner.setOnClickListener(v -> AppUtils.showPopUpTagList(getContext(), mainLayout, R.layout.popup_tag_list, spinnerArrayList, this));
    }

    @Override
    public void toast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showQuestions(List<QuestionItem> questions, int page) {
        if (page == 1) {
            questionItems = questions;
            questionsAdapter = new QuestionsAdapter(questionItems);
            recyclerView.setAdapter(questionsAdapter);
        } else {
            questionItems.addAll(questions);
            questionsAdapter.notifyDataSetChanged();
        }
        questionsAdapter.notifyItemInserted(questionItems.size() - 1);
        questionsAdapter.setRecyclerViewItemClickListener((view, position) -> addFragment(DetailScreenFragment.newInstance(questionItems.get(position).getQuestionId(), siteApi)));
    }

    @Override
    public void showTags(List<TagItem> tags, int page) {
        if (page == 1) {
            tagItems = tags;
            tagAdapter = new TagAdapter(tagItems);
            recyclerView.setAdapter(tagAdapter);
        } else {
            tagItems.addAll(tags);
            tagAdapter.notifyDataSetChanged();
        }
        tagAdapter.notifyItemInserted(tagItems.size() - 1);
        tagAdapter.setRecyclerViewItemClickListener((view, position) -> toast("Tag clicked"));

    }

    @Override
    public void showUsers(List<UserItem> users, int page) {
        if (page == 1) {
            userItems = users;
            userAdapter = new UserAdapter(userItems);
            recyclerView.setAdapter(userAdapter);
        } else {
            userItems.addAll(users);
            userAdapter.notifyDataSetChanged();
        }
        userAdapter.notifyItemInserted(userItems.size() - 1);
        userAdapter.setRecyclerViewItemClickListener(((view, position) -> toast("User clicked")));
    }

    @Override
    public void showSites(List<SiteItem> sites, int page) {
        if (page == 1) {
            siteItems = sites;
            siteAdapter = new SiteAdapter(siteItems);
            recyclerView.setAdapter(siteAdapter);
        } else {
            siteItems.addAll(sites);
            siteAdapter.notifyDataSetChanged();
        }
        siteAdapter.notifyItemInserted(siteItems.size() - 1);
        siteAdapter.setRecyclerViewItemClickListener((view, position) -> {
            replaceFragment(HomeScreenFragment.newInstance(siteItems.get(position).getApiSiteParameter(), siteItems.get(position).getName(), false));
        });
    }

    @Override
    public void clearSearch() {
        search.setText("");
        close.setVisibility(View.GONE);
        tvTag.setText(headerArrayList.get(headerID).getTitle());
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        if (isSite)
            presenter.callSites(retrofit, homeService, page);
        switch (headerID) {
            case 0:
                questionItems.clear();
                presenter.callQuestions(retrofit, homeService, order, "activity", page, siteApi);
                break;
            case 1:
                tagItems.clear();
                presenter.callTags(retrofit, homeService, order, sortTags, 1, siteApi, "");
                break;
            case 2:
                userItems.clear();
                presenter.callUsers(retrofit, homeService, 1, order, "reputation", siteApi);
                break;
        }
    }
}
