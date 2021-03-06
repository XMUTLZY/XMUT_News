package com.example.xmut_news.pager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xmut_news.JoinRecordActivity;
import com.example.xmut_news.LoginActivity;
import com.example.xmut_news.R;
import com.example.xmut_news.ShowMyReleaseActivity;
import com.example.xmut_news.base.BasePager;
import com.example.xmut_news.pojo.User;

public class UserPager extends BasePager {
    private Button logout;
    public UserPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        view = view.inflate(context,R.layout.user_pager,null);
        //实例化登录按钮
        Button login_button = getRootView().findViewById(R.id.user_pager_under_login);
        //查看活动申请记录
        Button record = getRootView().findViewById(R.id.join_record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,JoinRecordActivity.class);
                context.startActivity(intent);
            }
        });
        //查看我的发布并进行审核
        Button my_release = getRootView().findViewById(R.id.my_release);
        my_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowMyReleaseActivity.class);
                context.startActivity(intent);
            }
        });
        //添加登录按钮监听器
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到登陆页面
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });
        initData();
        //查看是否有数据
        logout = getRootView().findViewById(R.id.logout);
        SharedPreferences sp = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        if(sp.getString("phone","").isEmpty()){
                logout.setEnabled(false);
        }
        //退出登录
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getRootView().getContext())
                        .setTitle("注销提示")
                        .setMessage("确定退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                //1、清除保存在sp中的数据
                                SharedPreferences sp = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                sp.edit().clear().commit();
                                //2、设置登录模块显示
                                getRootView().findViewById(R.id.user_pager_under_under).setVisibility(View.VISIBLE);
                                //3、设置头像和手机号隐藏
                                getRootView().findViewById(R.id.user_pager_hide).setVisibility(View.GONE);
                                logout.setEnabled(false);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });
        return view;
    }

    @Override
    public void initData() {
        //查看是否有数据
        SharedPreferences sp = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        if(!sp.getString("phone","").isEmpty()){
            //获取用户数据
            User user = new User();
            String phone = sp.getString("phone","");
            String password = sp.getString("password","");
            String name = sp.getString("name","");
            String image = sp.getString("image","");
            String sex = sp.getString("sex","");
            String school = sp.getString("school","");
            String major = sp.getString("major","");
            user.setPhone(phone);
            user.setName(name);
            user.setPassword(password);
            user.setImage(image);
            user.setSchool(school);
            user.setSex(sex);
            user.setMajor(major);
            //设置登录模块隐藏
            getRootView().findViewById(R.id.user_pager_under_under).setVisibility(View.GONE);
            //设置头像和手机号显示
            getRootView().findViewById(R.id.user_pager_hide).setVisibility(View.VISIBLE);
            //实例化组件
            Button button = getRootView().findViewById(R.id.user_pager_hide_button);
            TextView textView = getRootView().findViewById(R.id.text_hide);
            //设置头像和手机号
            textView.setText("手机号:"+ user.getPhone());
        }
    }
}
