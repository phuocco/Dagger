package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.RecyclerViewItemClickListener;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.Questions;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.questions.QuestionItem;
import com.example.hieudo.phuocnguyenintern.utils.AppUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private Context context;
    private List<QuestionItem> questionsList;

    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    public QuestionsAdapter(List<QuestionItem> questionsList){
        this.questionsList = questionsList;
    }
    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_list_questions,parent,false);
        context = parent.getContext();
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        QuestionItem question = questionsList.get(position);
        holder.title.setText(question.getTitle());

        holder.answers.setText(String.valueOf(question.getAnswerCount()));
        if(question.getIsAnswered()){
            holder.answers.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.answers_with_accepted,0);
            holder.llLeft.setBackgroundResource(R.drawable.bg_answered);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String tag : question.getTags()){
            stringBuilder.append(tag + ", ");
        }
        holder.tags.setText(stringBuilder);
        holder.time.setText(String.valueOf(question.getLastActivityDate()));

        int time = (int) (new Date().getTime()/1000) - question.getLastActivityDate();
       // holder.votes.setText(String.valueOf(question.getScore()));
        holder.votes.setText(AppUtils.showVote(context,question.getScore()));
        holder.time.setText(AppUtils.showTime(context,time));

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
        QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(recyclerViewItemClickListener != null){
                recyclerViewItemClickListener.onItemClickListener(v,getLayoutPosition());
            }
        }
    }
}
