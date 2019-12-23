package com.wzq.jz_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wzq.jz_app.R;
import com.wzq.jz_app.model.bean.local.BSort;
import com.wzq.jz_app.utils.ImageUtils;

import java.util.List;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public class SortAdapter extends RecyclerView.Adapter<SortAdapter.MyItemViewHolder> {

    private Context mContext;
    private List<BSort> mData;

    public SortAdapter(Context mContext, List<BSort> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setItems(List<BSort> items){
        this.mData=items;
    }

    @Override
    public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_note_sort, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyItemViewHolder holder, int position) {
        holder.item_name.setText(mData.get(position).getSortName());
        holder.item_img.setImageDrawable(ImageUtils.getDrawable(mData.get(position).getSortImg()));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_name;
        ImageView item_img;
        View vItem;

        MyItemViewHolder(View view) {
            super(view);
            item_img = view.findViewById(R.id.item_note_edit_iv);
            vItem = view.findViewById(R.id.ll_item);
            item_name = view.findViewById(R.id.item_note_edit_tv);
        }
    }


}
