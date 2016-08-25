package com.opklm78.momoda;

import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.opklm78.momoda.dao.DBManager;

public class MainActivity extends FragmentActivity {
    RadioGroup myTabRg = null;
    FragmentJoke joke = null;
    FragmentGirl girl = null;
    FragmentNovel novel = null;
    FragmentMine mine = null;
    ListView lv_joke = null;
    DBManager dbManager = null;
    public void initView() {
        if(joke == null){
            joke = new FragmentJoke();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, joke).commit();
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                Log.e("main","onclick ");
                switch (checkedId) {
                    case R.id.rbJoke:
                        Log.e("main","onclick joke");
                        if(joke==null){
                            joke = new FragmentJoke();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, joke)
                                .commit();
                        break;
                    case R.id.rbGirl:
                        Log.e("main","onclick girl");
//                        if (girl == null) {
//                            girl = new FragmentGirl();
//                        }
                        girl=null;
                        girl = new FragmentGirl();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, girl).commit();
                        break;
                    case R.id.rbNevol:
                        Log.e("main","onclick novel");
                        if(novel==null) {
                            novel = new FragmentNovel();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, novel)
                                .commit();
                        break;
                    case R.id.rbMine:
                        Log.e("main","onclick mine");
                        if(mine==null){
                            mine = new FragmentMine();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mine)
                                .commit();
                        break;
                    default:
                        break;
                }

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        dbManager = new DBManager(this);
        dbManager.openDatabase();
        dbManager.closeDatabase();

    }

}
