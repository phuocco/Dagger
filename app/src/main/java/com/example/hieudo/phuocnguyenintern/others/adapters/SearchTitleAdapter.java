package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.RecyclerViewItemClickListener;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.searchInTitle.SearchTitleItem;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTitleAdapter extends RecyclerView.Adapter<SearchTitleAdapter.CustomViewHolder> {
    private Context context;

    public SearchTitleAdapter(List<SearchTitleItem> searchTitleItemList) {
        this.searchTitleItemList = searchTitleItemList;
    }

    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    private List<SearchTitleItem> searchTitleItemList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_questions,parent,false);
        context = parent.getContext();
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SearchTitleItem searchTitleItem = searchTitleItemList.get(position);
        holder.title.setText(searchTitleItem.getTitle());
        holder.votes.setText(String.valueOf(searchTitleItem.getScore()));
        holder.answers.setText(String.valueOf(searchTitleItem.getAnswerCount()));
        if(searchTitleItem.getIsAnswered()){
            holder.answers.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.answers_with_accepted,0);
            holder.llLeft.setBackgroundResource(R.drawable.bg_answered);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String tag : searchTitleItem.getTags()){
            stringBuilder.append(tag + ", ");
        }
        holder.tags.setText(stringBuilder);
        holder.time.setText(String.valueOf(searchTitleItem.getLastActivityDate()));

        int time = (int) (new Date().getTime()/1000) - searchTitleItem.getLastActivityDate();
        if(time < 60){
            holder.time.setText(context.getResources().getString(R.string.sec_ago,time));
        } else if (time <3600 && time >60){
            holder.time.setText(context.getResources().getString(R.string.min_ago,time/60));
        } else if (time<7200 && time >3600) {
            holder.time.setText(context.getResources().getString(R.string.hour_ago,time/3600));
        } else if(time<86400 && time >7200){
            holder.time.setText(context.getResources().getString(R.string.hours_ago,time/3600));
        } else {
            holder.time.setText(context.getResources().getString(R.string.day_ago,time/86400));
        }
    }

    @Override
    public int getItemCount() {
        return searchTitleItemList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemQuestions_tvTitle)
        TextView title;
        @BindView(R.id.itemQuestions_tvAnswers)
        TextView answers;
        @BindView(R.id.itemQuestions_tvVotes)
        TextView votes;
        @BindView(R.id.itemQuestions_tvTags)
        TextView tags;
        @BindView(R.id.itemQuestions_tvTime)
        TextView time;
        @BindView(R.id.itemQuestions_llLeft)
        LinearLayout llLeft;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
