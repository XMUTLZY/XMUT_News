package com.example.xmut_news;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.example.xmut_news.pojo.UserJoin;
import com.example.xmut_news.utils.ReleaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowMyReleaseActivity extends AppCompatActivity {

    private List<UserJoin> userJoins_datas = new ArrayList<>();//所有申请数据
    private List<UserJoin> userJoins_data = new ArrayList<>();//指定用户的申请数据
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_release);
        initData();
        getSupportActionBar().hide();
    }
    private void initData(){
        recyclerView = findViewById(R.id.my_release);
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        String jsonlist = sp.getString("jsonList","");
        //json转化成list
        userJoins_datas = JSONObject.parseArray(jsonlist, UserJoin.class);
        //获取当前用户的phone
        String phone = sp.getString("phone","");
        for(int i = 0;i<userJoins_datas.size();i++){
            if(userJoins_datas.get(i).getRelease_phone().equals(phone)&&userJoins_datas.get(i).getState().equals("申请中")){
                userJoins_data.add(userJoins_datas.get(i));
            }
        }
        //添加适配器
        recyclerView.setAdapter(new ReleaseRecyclerViewAdapter(ShowMyReleaseActivity.this,userJoins_data));
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowMyReleaseActivity.this,LinearLayoutManager.VERTICAL,false));

    }
}
