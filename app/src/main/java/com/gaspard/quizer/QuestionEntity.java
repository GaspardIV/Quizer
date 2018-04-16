package com.gaspard.quizer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "questions")
public class QuestionEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "quiz_id")
    private int quizId;

    @ColumnInfo(name = "quest_nr")
    private int questionNumber;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "image") // url to image
    private String image;

    @ColumnInfo(name = "answ1")
    private String answer1;

    @ColumnInfo(name = "answ2")
    private String answer2;

    @ColumnInfo(name = "answ3")
    private String answer3;

    @ColumnInfo(name = "answ4")
    private String answer4;

    @ColumnInfo(name = "right")
    private String rightAnswer;

    public QuestionEntity(@NonNull int id, int quizId, int questionNumber, String question, String image, String answer1, String answer2, String answer3, String answer4, String rightAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.questionNumber = questionNumber;
        this.question = question;
        this.image = image;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.rightAnswer = rightAnswer;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
