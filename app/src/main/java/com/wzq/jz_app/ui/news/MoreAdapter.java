package com.wzq.jz_app.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wzq.jz_app.R;
import com.wzq.jz_app.ui.fragment.ClassT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wangzhiqiang on 2018/12/11.
 */

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MyViewHolder> {

    //数据
    private List<ClassT> datas = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    //重写MyViewHolder onCreateViewHolder()方法，在该方法中实现获取列表中，每行item的布局文件
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取列表中，每行的布局文件
        View view = LayoutInflater.from(lcontext).inflate(R.layout.main_fragment_more_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    //重写onBindViewHolder()方法，在该方法中设置列表菜单中item（子项）所显示的内容
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(datas.get(position).getTitle()!=null){

            //设置图片
            if(datas.get(position).getPicInfo().size()>0){
                Glide.with(lcontext).load(datas.get(position).getPicInfo().get(0).getUrl()).into(holder.img);
            }else {
                holder.img.setVisibility(View.GONE);
            }
            //设置标题
            holder.name.setText(datas.get(position).getTitle());
            //设置时间
            holder.info.setText(datas.get(position).getSource()+"|"+datas.get(position).getCategory()+"|"+datas.get(position).getPtime());
        }



        holder.honorRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItem(datas.get(position));
                }
            }
        });
    }

    //该方法中实现返回数据集中的项目总数
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, info, time;//编号 文字 时间
        public ImageView img;
        public LinearLayout honorRl;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.news_name);
            info = (TextView) itemView.findViewById(R.id.news_time);
            img = (ImageView) itemView.findViewById(R.id.news_img);
            honorRl=itemView.findViewById(R.id.R1);

        }
    }
    private Context lcontext;//上下文

    public MoreAdapter(Context context) {
        lcontext = context;
    }

    //获取数据
    public void setDatas(List<ClassT> datas) {
        this.datas = datas;
        if (datas == null) {
            return;
        }
    }

    public interface OnItemClickListener {
        void onItem(ClassT bean);
    }

}

