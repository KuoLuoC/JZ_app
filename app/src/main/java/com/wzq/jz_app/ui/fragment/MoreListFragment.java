package com.wzq.jz_app.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wzq.jz_app.MyApplication;
import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseFragment;
import com.wzq.jz_app.net.IoMainScheduler;
import com.wzq.jz_app.ui.activity.NewsWebViewActivity;
import com.wzq.jz_app.ui.news.MoreAdapter;
import com.wzq.jz_app.ui.news.NewsBean;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.Context.MODE_PRIVATE;
import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * $desc$
 */
public class MoreListFragment extends BaseFragment {

    private SmartRefreshLayout messageSrl;
    private RecyclerView recyclerview2;
    private MoreAdapter adapter;//适配器
    private List<ClassT> tempList = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;
    private String type;
    private List<NewsBean.DataBean.DyBean> datas = new ArrayList<>();
    private List<NewsBean.DataBean.MoneyBean> data2 = new ArrayList<>();
    private List<NewsBean.DataBean.TechBean> data3 = new ArrayList<>();
    private List<NewsBean.DataBean.ToutiaoBean> data4 = new ArrayList<>();
    private List<NewsBean.DataBean.EntBean> data5 = new ArrayList<>();
    private List<NewsBean.DataBean.SportsBean> data6 = new ArrayList<>();
    private List<NewsBean.DataBean.WarBean> data7 = new ArrayList<>();
    private List<NewsBean.DataBean.AutoBean> data8 = new ArrayList<>();

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private String rankType;

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_more_tab;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
//        toolbar = getViewById(R.id.toolbar);
        messageSrl = getViewById(R.id.messageSrl);
        recyclerview2 = getViewById(R.id.recyclerview2);
//            //初始化Toolbar
//        toolbar.setTitle("财经新闻");
        rankType = getArguments().getString("rankType");
        initSrl();
        initRv();
        currentPage = 1;
//        test();
        getdate();
    }

    @Override
    protected void setListener() {

    }


    public static MoreListFragment getFragmentInstance(String type) {

        //复用fragment
        MoreListFragment fragment = new MoreListFragment();
        Bundle bundle = new Bundle();
        Log.e("123", "type = " + type);
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * 初始化SmartRefreshLayout
     */
    private void initSrl() {
        messageSrl.setEnableLoadMore(true);
        messageSrl.setEnableRefresh(true);
        messageSrl.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        messageSrl.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        //下拉刷新
        messageSrl.setOnRefreshListener(refreshlayout -> {
            currentPage = 1;
            messageSrl.finishRefresh(1000);
        });

//        //上拉加载
//        messageSrl.setOnLoadMoreListener(refreshLayout -> {
//            currentPage++;
//          messageSrl.finishRefresh(1000);
//        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRv() {

        recyclerview2.setHasFixedSize(true);
        recyclerview2.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview2.setLayoutManager(linearLayoutManager);
        if (adapter == null) {
            adapter = new MoreAdapter(getActivity());
        }
        adapter.setOnItemClickListener(new MoreAdapter.OnItemClickListener() {
            @Override
            public void onItem(ClassT bean) {
                Intent intent = new Intent(getActivity(), NewsWebViewActivity.class);
                if (tempList != null) {
                    if (bean != null) {
                        String type = bean.getType();
                        intent.putExtra("contentType", type);
                        // 链接url
//                        intent.putExtra("url", bean.getLink());
                        intent.putExtra("url","http://192.168.10.77:8080");
                        startActivity(intent);

                    }
                }
            }
        });
        recyclerview2.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 新闻列表
     */
    private void test() {
        MyApplication.application.getApiService().test().compose
                (new IoMainScheduler<>()).subscribe(new Observer<NewsBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                Toast toast = Toast.makeText(getApplicationContext(), "加载数据", Toast.LENGTH_SHORT);
                showProgressDialog("加载中");
            }

            @Override
            public void onNext(NewsBean responseBody) {
                cancelProgressDialog();
                if (responseBody != null) {
                    if (currentPage == 1) {
                        tempList.clear();
                        datas.clear();
                    }
                    String obj2 = "";
                    Gson gson2 = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                    if (rankType != null) {
                        switch (rankType) {
                            case "1":
                                datas = responseBody.getData().getDy();
                                //将Java实体类转换为json字符串
                                obj2 = gson2.toJson(datas);
                                break;

                            case "2":
                                data2 = responseBody.getData().getMoney();
                                obj2 = gson2.toJson(data2);
                                break;
                            case "3":
                                data3 = responseBody.getData().getTech();
                                obj2 = gson2.toJson(data3);
                                break;
                            case "4":
                                data4 = responseBody.getData().getToutiao();
                                obj2 = gson2.toJson(data4);
                                break;
                            case "5":
                                data5 = responseBody.getData().getEnt();
                                obj2 = gson2.toJson(data5);
                                break;
                            case "6":
                                data6 = responseBody.getData().getSports();
                                obj2 = gson2.toJson(data6);
                                break;
                            case "7":
                                data7 = responseBody.getData().getWar();
                                obj2 = gson2.toJson(data7);
                                break;
                            case "8":
                                data8 = responseBody.getData().getAuto();
                                obj2 = gson2.toJson(data8);
                                break;

                        }
                        //json数据转换为实体类(jsonString转list)
                        tempList = gson2.fromJson(obj2, new TypeToken<List<ClassT>>() {
                        }.getType());
                        if (tempList != null) {
                            adapter.setDatas(tempList);
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "数据异常", Toast.LENGTH_SHORT);
                    }
                }
                messageSrl.finishRefresh(2000);
                cancelProgressDialog();
            }


            @Override
            public void onError(Throwable e) {
                Toast toast = Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT);
                cancelProgressDialog();
                messageSrl.finishRefresh(2000);
            }

            @Override
            public void onComplete() {
                Toast toast = Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT);

            }
        });
    }

   public void getdate(){
       if (currentPage == 1) {
           tempList.clear();
           datas.clear();
       }
       NewsBean responseBody=new NewsBean();
       preferences = getActivity().getSharedPreferences("test", MODE_PRIVATE);
       String data=preferences.getString("mode","");
       if(data.length()>0){
           Gson gson=new Gson();
           Type type=new TypeToken<NewsBean>(){}.getType();
           responseBody=gson.fromJson(data,type);
       }
       String obj2 = "";
       Gson gson2 = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
       if (rankType != null) {
           switch (rankType) {
               case "1":
                   if(responseBody.getData()!=null) {
                       datas = responseBody.getData().getDy();
                   }
                   //将Java实体类转换为json字符串
                   obj2 = gson2.toJson(datas);
                   break;

               case "2":
                   if(responseBody.getData()!=null) {
                       data2 = responseBody.getData().getMoney();
                   }
                   obj2 = gson2.toJson(data2);
                   break;
               case "3":
                   if(responseBody.getData()!=null) {
                       data3 = responseBody.getData().getTech();
                   }
                   obj2 = gson2.toJson(data3);
                   break;
               case "4":
                   if(responseBody.getData()!=null) {
                       data4 = responseBody.getData().getToutiao();
                   }
                   obj2 = gson2.toJson(data4);
                   break;
               case "5":
                   if(responseBody.getData()!=null) {
                       data5 = responseBody.getData().getEnt();
                   }
                   obj2 = gson2.toJson(data5);
                   break;
               case "6":
                   if(responseBody.getData()!=null) {
                       data6 = responseBody.getData().getSports();
                   }
                   obj2 = gson2.toJson(data6);
                   break;
               case "7":
                   if(responseBody.getData()!=null) {
                       data7 = responseBody.getData().getWar();
                   }
                   obj2 = gson2.toJson(data7);
                   break;
               case "8":
                   if(responseBody.getData()!=null) {
                       data8 = responseBody.getData().getAuto();
                   }
                   obj2 = gson2.toJson(data8);
                   break;

           }
           //json数据转换为实体类(jsonString转list)
           tempList = gson2.fromJson(obj2, new TypeToken<List<ClassT>>() {
           }.getType());
           if (tempList != null) {
               adapter.setDatas(tempList);
               adapter.notifyDataSetChanged();
           }
   }}

}



