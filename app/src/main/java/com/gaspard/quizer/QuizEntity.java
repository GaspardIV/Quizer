package com.gaspard.quizer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "quizzes")
public class QuizEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "qst_cnt")
    private int qstCnt;

    @ColumnInfo(name = "last_score")
    private int lastScore;

    @ColumnInfo(name = "last_question")
    private int lastQuestion;

    QuizEntity(int id) {
        this.id = id;
    }

    QuizEntity(int id, String url, String title, int qstCnt, int lastScore, int lastQuestion) {
        setValues(id, url, title, qstCnt, lastScore, lastQuestion);
    }

    public void setValues(int id, String url, String title, int qstCnt, int lastScore, int lastQuestion) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.qstCnt = qstCnt;
        this.lastScore = lastScore;
        this.lastQuestion = lastQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQstCnt() {
        return qstCnt;
    }

    public void setQstCnt(int qstCnt) {
        this.qstCnt = qstCnt;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public int getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(int lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

}
