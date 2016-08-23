package com.opklm78.momoda.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojr on 2016/8/20.
 */
public class JokeDao {
    public JokeDao(Context context) {
        cdb = new CommondDB(context);
    }

    private static CommondDB cdb;
    public List<String> queryJoke(int start, int end) {
        SQLiteDatabase rdb = cdb.getReadableDatabase();
        String sql = "select content from t_ci_jokes where id>? and id<? order by asc";
        Cursor result = rdb.rawQuery(sql, new String[]{"" + start, "" + end});
        List<String> jokes = new ArrayList<String>();
        for (int i = 0, size = result.getCount(); i < size; i++){
            jokes.add(result.getString(i));
        }
    return jokes;
    }
}
