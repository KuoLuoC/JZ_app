package com.wzq.jz_app.ui.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseActivity;

/**
 * 作者：wzq on 2018/12/14.
 * 邮箱：wang_love152@163.com
 */

public class NewsWebViewActivity extends BaseActivity {
    WebView mWebview;
    WebSettings mWebSettings;
    TextView loading, mtitle;
    private String newsId;
    /**
     * 内容类型 1.htmldata 2 url 3文档
     */
    private String contentType;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.main_home_webview;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mWebview = (WebView) findViewById(R.id.webView1);
//        loading = (TextView) findViewById(R.id.text_Loading);
        mtitle = (TextView) findViewById(R.id.title);
//        setTitle("详情");
        mWebSettings = mWebview.getSettings();
        contentType = getIntent().getStringExtra("contentType");

//        switch (contentType) {
//            case "1":// 本地内容
        // 接收数据
//                newsId = getIntent().getStringExtra("newsId");
//                getNewsDeatil();
//                break;
//            case "2":// 链接url
        url = getIntent().getStringExtra("url");
        mWebview.loadUrl(url);
//                break;
//        }
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!TextUtils.isEmpty(contentType)) {
                    if (TextUtils.equals("about:blank", title)) {
//                        setTitle("详情");
                    } else {
                        setTitle(title);
                    }
                }
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //支持javascript
                view.getSettings().setJavaScriptEnabled(true);
                 // 设置可以支持缩放
                view.getSettings().setSupportZoom(false);
                 // 设置出现缩放工具
                view.getSettings().setBuiltInZoomControls(false);
                //扩大比例的缩放
                view.getSettings().setUseWideViewPort(false);
                //自适应屏幕
                view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                view.getSettings().setLoadWithOverviewMode(true);
//                if (newProgress < 100) {
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                } else if (newProgress == 100) {
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                    loading.setVisibility(loading.INVISIBLE);//掩藏进度条
//
//                }
            }
        });


        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
//                Toast.makeText(NewsWebViewActivity.this, "开始加载", Toast.LENGTH_SHORT).show();

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
//                Toast.makeText(NewsWebViewActivity.this, "加载结束", Toast.LENGTH_SHORT).show();

            }
        });

        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
//                Toast.makeText(NewsWebViewActivity.this, "开始加载", Toast.LENGTH_SHORT).show();

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
//                Toast.makeText(NewsWebViewActivity.this, "加载结束", Toast.LENGTH_SHORT).show();

            }
        });


    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.main_home_webview);
//        super.onCreate(savedInstanceState);
//        mWebview = (WebView) findViewById(R.id.webView1);
//        loading = (TextView) findViewById(R.id.text_Loading);
//        mtitle = (TextView) findViewById(R.id.title);
//        setTitle("详情");
//        mWebSettings = mWebview.getSettings();
//        contentType = getIntent().getStringExtra("contentType");
//
////        switch (contentType) {
////            case "1":// 本地内容
//                // 接收数据
////                newsId = getIntent().getStringExtra("newsId");
////                getNewsDeatil();
////                break;
////            case "2":// 链接url
//                url = getIntent().getStringExtra("url");
//                mWebview.loadUrl(url);
////                break;
////        }
//        //设置不用系统浏览器打开,直接显示在当前Webview
//        mWebview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//
//        //设置WebChromeClient类
//        mWebview.setWebChromeClient(new WebChromeClient() {
//            //获取网站标题
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                if (!TextUtils.isEmpty(contentType)) {
//                    if (TextUtils.equals("about:blank", title)) {
//                        setTitle("详情");
//                    } else {
//                        setTitle(title);
//                    }
//                }
//            }
//
//            //获取加载进度
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress < 100) {
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                } else if (newProgress == 100) {
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                    loading.setVisibility(loading.INVISIBLE);//掩藏进度条
//
//                }
//            }
//        });

//
//        //设置WebViewClient类
//        mWebview.setWebViewClient(new WebViewClient() {
//            //设置加载前的函数
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                System.out.println("开始加载了");
////                Toast.makeText(NewsWebViewActivity.this, "开始加载", Toast.LENGTH_SHORT).show();
//
//            }
//
//            //设置结束加载函数
//            @Override
//            public void onPageFinished(WebView view, String url) {
////                Toast.makeText(NewsWebViewActivity.this, "加载结束", Toast.LENGTH_SHORT).show();
//
//            }
//        });

//    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }


    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
