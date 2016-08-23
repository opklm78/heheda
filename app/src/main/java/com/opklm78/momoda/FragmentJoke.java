package com.opklm78.momoda;


import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.opklm78.momoda.dao.DBManager;
import com.opklm78.momoda.dao.JokeDao;

import java.util.ArrayList;
import java.util.List;

public class FragmentJoke extends Fragment {
    ListView lv_joke = null;
    private SQLiteDatabase database;
    List<String> db_joke = null;
    int jokeStart = 0;
    int jokeEnd = 10;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }
    private void changeDBJoke(){
        List<String> addLs = null;
        if(db_joke==null){
            db_joke = new ArrayList<String>();
            addLs = getJokeDB(0,10);
            jokeStart=0;
            jokeEnd=10;
        }else{
            addLs = getJokeDB(jokeStart,jokeEnd);
        }
        if(addLs!=null&&addLs.size()>0){
            db_joke.addAll(addLs);
            ArrayAdapter arrAdapter = (ArrayAdapter) lv_joke.getAdapter();
            arrAdapter.notifyDataSetChanged();
        }else{
            Toast toast = Toast.makeText(this.getContext(),"已到最后",Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_joke,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private List<String> getJokeDB(int pStart, int pEnd){
        String start = String.valueOf(pStart);
        String end = String.valueOf(pEnd);
        if(database==null||(database!=null&&(!database.isOpen()))){
            database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH+"/"+DBManager.DB_NAME,null);
        }
        Cursor cursor = database.rawQuery("select content from t_ci_jokes where id>? and id<=? order by content asc",new String[]{start,end});
        List<String> ls = new ArrayList<String>();
        if(cursor != null){
            int resultNum = cursor.getCount();
            if (cursor.moveToFirst()) {
                do {
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    ls.add(content);
                } while (cursor.moveToNext());
            }
            return ls;
        }else{
            return null;
        }

    }
    //listview state listener
    public void scrollStateListener(){
        lv_joke.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            jokeStart+=10;
                            jokeEnd+=10;
                            changeDBJoke();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(database!=null){
            database.close();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH+"/"+DBManager.DB_NAME,null);
        if(db_joke==null){
            db_joke = getJokeDB(0,10);
        }
        database.close();
        lv_joke = (ListView) getActivity().findViewById(R.id.lv_joke);
        lv_joke.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.tv_item_joke,db_joke));
        scrollStateListener();
    }
}
