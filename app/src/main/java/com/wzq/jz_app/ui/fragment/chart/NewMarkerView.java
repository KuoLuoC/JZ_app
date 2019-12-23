package com.wzq.jz_app.ui.fragment.chart;


import android.content.Context;
import android.widget.TextView;

import com.wzq.jz_app.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;


public class NewMarkerView extends MarkerView {

    private TextView tvContent;
    private CallBack mCallBack;
    private Boolean needFormat=true;

    public NewMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    /*部分数据不需要格式化处理*/
    public NewMarkerView(Context context, int layoutResource, boolean needFormat) {
        super(context, layoutResource);
        this.needFormat = needFormat;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        String values;
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            values = "" + Utils.formatNumber(ce.getHigh(), 0, true);
        } else {
            if(!needFormat){
                values=""+e.getY();
            }else{
                values = "" + Utils.formatNumber(e.getY(), 0, true);
            }
        }

        if (mCallBack != null) {
            mCallBack.onCallBack(e.getX(), values);
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        tvContent.setBackgroundResource(R.drawable.chart_popu);
        return new MPPointF(0, -getHeight());
    }

//    @Override
//    public MPPointF getOffsetRight() {
//        tvContent.setBackgroundResource(R.drawable.chart_popu_right);
//        return new MPPointF(-getWidth(), -getHeight());
//    }

    public void setCallBack (CallBack callBack) {
        this.mCallBack = callBack;
    }
    public interface CallBack {
        void onCallBack(float x, String value);
    }

    public TextView getTvContent() {
        return tvContent;
    }
}
