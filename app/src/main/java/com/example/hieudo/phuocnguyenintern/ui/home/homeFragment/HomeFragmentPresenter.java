package com.example.hieudo.phuocnguyenintern.ui.home.homeFragment;

import android.graphics.Color;
import android.text.Editable;
import android.util.Log;

import com.example.hieudo.phuocnguyenintern.others.models.localModels.Header;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.SpinnerList;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.Site;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.Question;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.searchInTitle.SearchTitle;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.tags.Tag;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.users.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter {
    private HomeFragmentContract.View view;

    public void setView(HomeFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void addList(ArrayList<Header> headerArrayList) {
        headerArrayList.add(new Header("Questions"));
        headerArrayList.add(new Header("Tags"));
        headerArrayList.add(new Header("Users"));
        view.showHeaderList(headerArrayList);
    }

    @Override
    public void addSpinnerList(ArrayList<SpinnerList> spinnerArrayList, int headerID, boolean isSite) {
        if (headerID == 0) {
            spinnerArrayList.clear();
            spinnerArrayList.add(new SpinnerList("Active")); //question activity
            spinnerArrayList.add(new SpinnerList("Newest"));
            spinnerArrayList.add(new SpinnerList("Hot"));// question hot
            spinnerArrayList.add(new SpinnerList("Votes")); // trong question param sort vote
        } else if (headerID == 1) {
            spinnerArrayList.clear();
            spinnerArrayList.add(new SpinnerList("Popular")); //question activity
            spinnerArrayList.add(new SpinnerList("Name"));
        } else if (headerID == 2) {
            spinnerArrayList.clear();
            spinnerArrayList.add(new SpinnerList("Reputation")); //question activity
            spinnerArrayList.add(new SpinnerList("Creation"));
            spinnerArrayList.add(new SpinnerList("Name"));
        }
        if (isSite) {
            spinnerArrayList.clear();
            spinnerArrayList.add(new SpinnerList("Main sites"));
            spinnerArrayList.add(new SpinnerList("Meta sites"));
            spinnerArrayList.add(new SpinnerList("All sites"));
        }
        view.showSpinnerList(spinnerArrayList);
    }

    @Override
    public void callQuestions(Retrofit retrofit, HomeService homeService, String order, String sort, int page, String siteApi) {
        Call<Question> call = homeService.getQuestions(page, order, sort, siteApi);
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    view.showQuestions(response.body().getItems(), page);
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });
    }

    @Override
    public void callTags(Retrofit retrofit, HomeService homeService, String order, String sort, int page, String siteApi, String inName) {
        Call<Tag> call = homeService.getTags(page, order, sort, inName, siteApi);
        call.enqueue(new Callback<Tag>() {
            @Override
            public void onResponse(Call<Tag> call, Response<Tag> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    view.showTags(response.body().getItems(), page);
                }
            }

            @Override
            public void onFailure(Call<Tag> call, Throwable t) {

            }
        });
    }

    @Override
    public void callUsers(Retrofit retrofit, HomeService homeService, int page, String order, String sortUser, String siteApi) {
        Call<User> call = homeService.getUsers(page, order, sortUser, siteApi);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    view.showUsers(response.body().getItems(), page);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void callSites(Retrofit retrofit, HomeService homeService, int page) {
        Call<Site> call = homeService.getSites(page);
        call.enqueue(new Callback<Site>() {
            @Override
            public void onResponse(Call<Site> call, Response<Site> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    view.showSites(response.body().getItems(), page);
                }
            }

            @Override
            public void onFailure(Call<Site> call, Throwable t) {

            }
        });
    }

    @Override
    public void setSearch(Editable s) {
        if (s.length() > 0) {
            view.updateSearch(s);
        }
    }

    @Override
    public void callSearch(Retrofit retrofit, HomeService homeService, String order, String sort, String intitle, String siteApi) {
        Call<SearchTitle> call = homeService.getSearchTitle(order, sort, intitle, siteApi);
        call.enqueue(new Callback<SearchTitle>() {
            @Override
            public void onResponse(Call<SearchTitle> call, Response<SearchTitle> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    view.showSearch(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<SearchTitle> call, Throwable t) {

            }
        });
    }

    @Override
    public void selectSpinner(Retrofit retrofit, HomeService homeService, String order, String intitle, String siteApi, int page, int headerID, int position) {
        String sort = "";
        if (headerID == 0) {

            switch (position) {
                case 0:
                case 1:
                    sort = "activity";
                    break;
                case 2:
                    sort = "hot";
                    break;
                case 3:
                    sort = "votes";
                    break;
            }
            callQuestions(retrofit, homeService, order, sort, page, siteApi);
        } else if (headerID == 1) {
            switch (position) {
                case 0:
                    sort = "popular";
                    break;
                case 1:
                    sort = "name";
                    break;
            }
            callTags(retrofit, homeService, order, sort, page, siteApi, "");
        } else if (headerID == 2) {
            switch (position) {
                case 0:
                    sort = "reputation";
                    break;
                case 1:
                    sort = "creation";
                    break;
                case 2:
                    sort = "name";
                    break;
            }
            callUsers(retrofit, homeService, page, order, sort, siteApi);
        }
    }


}
