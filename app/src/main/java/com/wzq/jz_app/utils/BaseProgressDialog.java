package com.wzq.jz_app.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wzq.jz_app.R;


public class BaseProgressDialog {
    private Context context;
    private Dialog mDialog;
    private TextView mTextView;

    private Runnable cancelRunnable = new Runnable() {

        @Override
        public void run() {

            cancelImmediately();
        }
    };

    public BaseProgressDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_progress, null);
        mTextView = (TextView) view.findViewById(R.id.textview);
        setCancelable(false);
        mDialog.setContentView(view);
        mDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                mTextView.removeCallbacks(cancelRunnable);
            }
        });
        if (context instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            if (!appCompatActivity.isFinishing()) {
                this.mDialog.show();
            }
        }
    }

    public void setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            mTextView.setVisibility(View.GONE);
        }
        mTextView.setText(text);
    }

    public void setText(int textID) {
        if (textID == 0) {
            mTextView.setVisibility(View.GONE);
        }
        mTextView.setText(textID);
    }

    public void show() {
        mTextView.removeCallbacks(cancelRunnable);
        if(!this.mDialog.isShowing() && context != null) {
            if (context  instanceof AppCompatActivity) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                if (!appCompatActivity.isFinishing()) {
                    this.mDialog.show();
                }
            }
        }
    }

    public void cancelImmediately() {
        if(this.mDialog.isShowing() && context != null) {
            if (context  instanceof AppCompatActivity) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                if (!appCompatActivity.isFinishing()) {
                    if (mDialog.isShowing()) {
                        mDialog.cancel();
                    }
                }
            }
        }

    }

    public void cancel() {
//        mTextView.postDelayed(cancelRunnable, 500);
        cancelImmediately();
    }

}
