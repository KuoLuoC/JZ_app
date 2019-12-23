package com.wzq.jz_app.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.wzq.jz_app.R;


/**
 * 自定义弹出对话框
 */

public class ButtomDialogView1 extends Dialog {

    private Context context;
    private ButtomDialogView1.OnDialogClickListener onDialogClickListener;

    public ButtomDialogView1(Context context) {
        super(context, R.style.dialog);

        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.module_layout_dialog_image2, null);
        RelativeLayout linear1 = (RelativeLayout) view.findViewById(R.id.dg_btn_1);
        RelativeLayout linear2 = (RelativeLayout) view.findViewById(R.id.dg_btn_2);
        RelativeLayout linear3 = (RelativeLayout) view.findViewById(R.id.dg_btn_3);
        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onclick1();
                }
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onclick2();
                }
            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onclick3();
                }
            }
        });


        setContentView(view);//这行一定要写在前面
        setCancelable(true);//点击外部不可dismiss
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    public void setOnDialogClickListener(ButtomDialogView1.OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public interface OnDialogClickListener{
        void onclick1();
        void onclick2();
        void onclick3();
    }
}
