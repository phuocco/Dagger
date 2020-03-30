package com.example.hieudo.phuocnguyenintern.ui.home.homeScreen;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Navigation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.SiteItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.NavigationService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public interface HomeScreenContract {
    interface View {
        void Toast(String content);
        void toMapActivity();
        void toAuthScreen();
        void replaceFragment(String position, String name, boolean isSite);
        void showListSite(List<SiteItem> items);
        void deleteData();
    }
    interface Presenter {
      //  void addRecyclerView(RecyclerView recyclerViewNav, ArrayList<Navigation> navigationArrayList, Context mContext);
     //   void addListSite(RecyclerView recyclerViewNav,Context mContext, int pos);
        void callListSite(Retrofit retrofit, NavigationService navigationService);
        void signOutFacebook();
        void signOutGoogle();

    }
}
