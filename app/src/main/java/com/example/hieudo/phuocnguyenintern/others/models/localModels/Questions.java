package com.example.hieudo.phuocnguyenintern.others.models.localModels;

public class Questions {
    private String title;
    private int vote;
    private int answer;
    private String time;
    private String tag;

    public String getTitle() {
        return title;
    }

    public int getVote() {
        return vote;
    }

    public int getAnswer() {
        return answer;
    }

    public String getTime() {
        return time;
    }

    public String getTag() {
        return tag;
    }

    public Questions(String title, int vote, int answer, String time, String tag) {
        this.title = title;
        this.vote = vote;
        this.answer = answer;
        this.time = time;
        this.tag = tag;
    }
}
