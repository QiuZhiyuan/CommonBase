package com.qiu.base.sample.ui.article.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArticleDataBaseHelper extends SQLiteOpenHelper {

    @NonNull
    private static final String DATA_BASE_NAME = "article_feed_data_base";
    private static int VERSION = 1;
    private static final String TABLE_NAME = "";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE ";

    public ArticleDataBaseHelper(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
