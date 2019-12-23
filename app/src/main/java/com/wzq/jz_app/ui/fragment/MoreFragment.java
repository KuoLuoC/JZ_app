package com.wzq.jz_app.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseFragment;
import com.wzq.jz_app.ui.adapter.ContactInfoAdapter;
import com.wzq.jz_app.ui.news.NewsBean;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wzq on 2019/4/19.
 * 邮箱：wang_love152@163.com
 */

public class MoreFragment extends BaseFragment  {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    ViewPager mCvp_zone;
    TabLayout tabLayout;
    private NewsBean responseBody;
    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_more;
    }


    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        tabLayout=getViewById(R.id.tab_bar);
        mCvp_zone=getViewById(R.id.cvp_zone);
        //
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        initFragments();
    }



    @Override
    protected void setListener() {


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //获取本地实体类数据  （因为接口不稳定，选择存入本地）
//         responseBody=new NewsBean();
//        SharedPreferences preferences = getActivity().getSharedPreferences("test", MODE_PRIVATE);
//        String data=preferences.getString("mode","");
//        if(data.length()>0){
//            Gson gson=new Gson();
//            Type type=new TypeToken<NewsBean>(){}.getType();
//            responseBody=gson.fromJson(data,type);
//        }

    }

    public void initFragments() {
        Gson gson=new Gson();
        //推荐/财经/教育/头条/娱乐/体育/军事/汽车
        mFragments.clear();
        List<String> typeList = new ArrayList<>();
        typeList.add("推荐");
        MoreListFragment fragment1 = new MoreListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("rankType","1");
//        bundle.putString("data", gson.toJson(responseBody.getData().getDy()));
        fragment1.setArguments(bundle);
        mFragments.add(fragment1);

        typeList.add("财经");
        MoreListFragment fragment2 = new MoreListFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("rankType","2");
//        bundle2.putString("data", gson.toJson(responseBody.getData().getMoney()));
        fragment2.setArguments(bundle2);
        mFragments.add(fragment2);

        typeList.add("教育");
        MoreListFragment fragment3 = new MoreListFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("rankType","3");
//        bundle3.putString("data", gson.toJson(responseBody.getData().getTech()));
        fragment3.setArguments(bundle3);
        mFragments.add(fragment3);

        typeList.add("头条");
        MoreListFragment fragment4 = new MoreListFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("rankType","4");
//        bundle4.putString("data", gson.toJson(responseBody.getData().getToutiao()));
        fragment4.setArguments(bundle4);
        mFragments.add(fragment4);

        typeList.add("娱乐");
        MoreListFragment fragment5 = new MoreListFragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("rankType","5");
//        bundle5.putString("data", gson.toJson(responseBody.getData().getEnt()));
        fragment5.setArguments(bundle5);
        mFragments.add(fragment5);

        typeList.add("体育");
        MoreListFragment fragment6 = new MoreListFragment();
        Bundle bundle6 = new Bundle();
        bundle6.putString("rankType","6");
//        bundle6.putString("data", gson.toJson(responseBody.getData().getSports()));
        fragment6.setArguments(bundle6);
        mFragments.add(fragment6);

        typeList.add("军事");
        MoreListFragment fragment7 = new MoreListFragment();
        Bundle bundle7 = new Bundle();
        bundle7.putString("rankType","7");
//        bundle7.putString("data", gson.toJson(responseBody.getData().getWar()));
        fragment7.setArguments(bundle7);
        mFragments.add(fragment7);

        typeList.add("汽车");
        MoreListFragment fragment8 = new MoreListFragment();
        Bundle bundle8 = new Bundle();
        bundle8.putString("rankType","8");
//        bundle8.putString("data", gson.toJson(responseBody.getData().()));
        fragment8.setArguments(bundle8);
        mFragments.add(fragment8);




        ContactInfoAdapter mGrowAdapter = new ContactInfoAdapter(getChildFragmentManager(), mFragments, typeList);
        mCvp_zone.setAdapter(mGrowAdapter);
        tabLayout.setupWithViewPager(mCvp_zone);
    }

}
