package com.gaspard.quizer;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Locale;


public class QuizzesDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "quizzes.db";
    private static final int DATABASE_VERSION = 1;

    public QuizzesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getQuizzes() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id", "url", "title", "qst_cnt", "last_score", "last_question"};
        String sqlTables = "quizzes";
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getQuiz(int quizId) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"id", "url", "title", "qst_cnt", "last_score", "last_question"};
        String sqlTables = "quizzes";
        String WHERE = String.format(new Locale("pl"), "id=%d", quizId);
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, WHERE, null,
                null, null, null);
        c.moveToFirst();
        return c;
    }


    public void setExistingQuiz(QuizEntity quizEntity) {
        ContentValues cv = new ContentValues();
        cv.put("qst_cnt", quizEntity.getQstCnt());
        cv.put("last_score", quizEntity.getLastScore());
        cv.put("last_question", quizEntity.getLastQuestion());
        String sqlTables = "quizzes";
        SQLiteDatabase db = getWritableDatabase();
        db.update(sqlTables, cv, "id=" + quizEntity.getId(), null);
    }

    public Cursor getQuestions(int quizId) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"id", "quiz_id", "quest_nr", "question", "image", "answ1", "answ2", "answ3", "answ4", "right"};
        String sqlTables = "questions";
        String WHERE = String.format(new Locale("pl"), "quiz_id=%d", quizId);
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, WHERE, null,
                null, null, "quest_nr");
        c.moveToFirst();
        return c;
    }
}

