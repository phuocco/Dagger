package com.example.hieudo.phuocnguyenintern.ui.home.homeFragment;

import android.text.Editable;

import com.example.hieudo.phuocnguyenintern.others.models.localModels.Header;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.SpinnerList;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.SiteItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.searchInTitle.SearchTitle;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.searchInTitle.SearchTitleItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.tags.Tag;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.tags.TagItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.users.UserItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public interface HomeFragmentContract {
    interface View {
        void toast(String content);
        void showHeaderList(ArrayList<Header> headerArrayList);
        void showSpinnerList(ArrayList<SpinnerList> spinnerArrayList);
        void showQuestions(List<QuestionItem> questions, int page);
        void showTags(List<TagItem> tags, int page);
        void showUsers(List<UserItem> users, int page);
        void showSites(List<SiteItem> sites, int page);
        void clearSearch();
        void updateSearch(Editable s);
        void showSearch(List<SearchTitleItem> searchTitles);

    }
    interface Presenter {
        void addList(ArrayList<Header> headerArrayList);
        void addSpinnerList(ArrayList<SpinnerList> spinnerArrayList, int headerID, boolean isSite);
        void callQuestions(String order,String sort, int page, String siteApi);
        void callTags(Retrofit retrofit, HomeService homeService, String order,String sort, int page, String siteApi, String inName);
        void callUsers(Retrofit retrofit, HomeService homeService,int page, String order, String sortUser, String siteApi);
        void callSites(Retrofit retrofit, HomeService homeService, int page);
        void setSearch(Editable s);
        void callSearch(Retrofit retrofit, HomeService homeService, String order, String sort, String intitle, String siteApi);
        void selectSpinner(Retrofit retrofit, HomeService homeService, String order, String intitle, String siteApi, int page, int headerID, int position);
    }
}
