package com.example.xmut_news.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.xmut_news.R;
import com.example.xmut_news.base.BasePager;
import com.example.xmut_news.pojo.UserRelease;
import com.example.xmut_news.utils.MyImageLoader;
import com.example.xmut_news.utils.MyRecyclerViewAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.List;

public class HomePager extends BasePager implements OnBannerListener {
    private String url = "http://7qvjkf.natappfree.cc/android_PlayAround_ssm/listUserRelease";
    private Banner mBanner;
    private MyImageLoader mMyImageLoader;
    private ArrayList<Integer> imagePath;
    private ArrayList<String> imageTitle;
    private RecyclerView release_list;
    private List<UserRelease> releases_datas = new ArrayList<>();//发布信息数据
    public HomePager(Context context) {
        super(context);
        initData1();
        initView1();
    }

    @Override
    public View initView() {
        view = view.inflate(context, R.layout.home_pager,null);
        release_list = getRootView().findViewById(R.id.release_list);
        return view;
    }

    /*
    * 准备数据
    * */
    @Override
    public void initData() {
    }
    private void initView1(){
        mMyImageLoader = new MyImageLoader();
        mBanner = (Banner)this.getRootView().findViewById(R.id.banner);
        //设置样式，里面有很多种样式可以自己都看看效果
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(mMyImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //轮播图片的文字
        mBanner.setBannerTitles(imageTitle);
        //设置轮播间隔时间
        mBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        mBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        mBanner.setImages(imagePath)
                //轮播图的监听
                .setOnBannerListener(this)
                //开始调用的方法，启动轮播图。
                .start();
    }
    /**
     * 轮播图的监听
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {

    }

    private void initData1() {
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        imagePath.add(R.drawable.guide1);
        imagePath.add(R.drawable.guide2);
        imagePath.add(R.drawable.guide3);
        imageTitle.add("我是海鸟一号");
        imageTitle.add("我是海鸟二号");
        imageTitle.add("我是海鸟三号");
        //从后台获取所有发布信息的数据
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context,url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                releases_datas = JSONObject.parseArray(s,UserRelease.class);
                Log.i("release_test", releases_datas.get(0).toString()+"2、"+releases_datas.get(1).toString());
                //添加RecyclerView的适配器
                release_list.setAdapter(new MyRecyclerViewAdapter(getRootView().getContext(),releases_datas));
                release_list.setLayoutManager(new LinearLayoutManager(getRootView().getContext(),LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Toast.makeText(context,"数据请求异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    /*
//    * RecyclerView的适配器
//    * */
//    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
//        private final Context context;
//        private List<UserRelease> data;
//        public MyRecyclerViewAdapter(Context context, List<UserRelease> data) {
//            this.context = context;
//            this.data = data;
//        }
//
//        /*
//        * 相当于getView方法中创建View和ViewHolder
//        * */
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View itemView = view.inflate(context,R.layout.item_recyclerview,null);
//            return new MyViewHolder(itemView);
//        }
//        /*
//        * 数据绑定
//        * */
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//            //绑定数据
//            String data1 = data.get(i).getTime();
//            myViewHolder.release_text.setText(data1);
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder{
//
//            private ImageView release_image;
//            private TextView release_text;
//            public MyViewHolder(@NonNull View itemView) {
//                super(itemView);
//                release_image = itemView.findViewById(R.id.release_image);
//                release_text = itemView.findViewById(R.id.release_text);
//            }
//        }
//    }
}
