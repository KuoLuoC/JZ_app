package com.wzq.jz_app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseFragment;
import com.wzq.jz_app.common.Constants;
import com.wzq.jz_app.model.bean.local.BBill;
import com.wzq.jz_app.model.bean.local.BSort;
import com.wzq.jz_app.model.bean.local.NoteBean;
import com.wzq.jz_app.model.bean.remote.MyUser;
import com.wzq.jz_app.model.repository.BmobRepository;
import com.wzq.jz_app.model.repository.LocalRepository;
import com.wzq.jz_app.ui.activity.AboutActivity;
import com.wzq.jz_app.ui.activity.LoginActivity;
import com.wzq.jz_app.ui.activity.SettingActivity;
import com.wzq.jz_app.ui.activity.SortActivity;
import com.wzq.jz_app.ui.activity.UserInfoActivity;
import com.wzq.jz_app.ui.adapter.MainFragmentPagerAdapter;
import com.wzq.jz_app.utils.ExcelUtil;
import com.wzq.jz_app.utils.ImageUtils;
import com.wzq.jz_app.utils.SelectphotoUtils;
import com.wzq.jz_app.utils.SharedPUtils;
import com.wzq.jz_app.utils.ThemeManager;
import com.wzq.jz_app.widget.ButtomDialogView1;
import com.wzq.jz_app.widget.CircleImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.wzq.jz_app.utils.SelectphotoUtils.getRealFilePathFromUri;

/**
 * 作者：wzq on 2019/4/2.
 * 邮箱：wang_love152@163.com
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView tOutcome;
    private TextView tIncome;
    private TextView tTotal;

    private View drawerHeader;
    private CircleImageView drawerIv;
    private TextView drawerTvAccount, drawerTvMail;

    protected static final int USERINFOACTIVITY_CODE = 0;
    protected static final int LOGINACTIVITY_CODE = 1;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //方形还是圆形截图
    private int type = 1;//"1"圆形 "2"方形
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //图片路径
    protected static Uri tempUri = null;
    public final int SIZE = 2 * 1024;


    // Tab
    private FragmentManager mFragmentManager;
    private MainFragmentPagerAdapter mFragmentPagerAdapter;
    private HomeFragment homeFragment;
    private ChartFragment chartFragment;

    private File selectFile;
    private ButtomDialogView1 dialogView1;
    private MyUser currentUser;
    private RelativeLayout editor;
    private RelativeLayout snyc;
    private RelativeLayout setting;
    private RelativeLayout theme;
    private RelativeLayout about;
    private RelativeLayout countClass;
    private RelativeLayout nav_outexcle;



    /***************************************************************************/
    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //注册 EventBus
        EventBus.getDefault().register(this);

        //第一次进入将默认账单分类添加到数据库
        if (SharedPUtils.isFirstStart(mContext)) {
            Log.i(TAG, "第一次进入将默认账单分类添加到数据库");
            NoteBean note = new Gson().fromJson(Constants.BILL_NOTE, NoteBean.class);
            List<BSort> sorts = note.getOutSortlis();
            sorts.addAll(note.getInSortlis());
            LocalRepository.getInstance().saveBsorts(sorts);
            LocalRepository.getInstance().saveBPays(note.getPayinfo());
        }

        homeFragment = new HomeFragment();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);


        drawerHeader = getViewById(R.id.drawer_header);
        drawerIv = getViewById(R.id.drawer_iv);
        drawerTvAccount = getViewById(R.id.drawer_tv_name);
        drawerTvMail = getViewById(R.id.drawer_tv_email);


        editor = getViewById(R.id.nav_edit);//编辑资料
        snyc = getViewById(R.id.nav_sync);//同步数据
        setting = getViewById(R.id.nav_setting);//设置
        theme = getViewById(R.id.nav_theme);//主题
        countClass = getViewById(R.id.nav_class);//账单分类
        nav_outexcle = getViewById(R.id.nav_outexcle);//导出账单
        about = getViewById(R.id.nav_about);//关于

        //设置头部账户
        setDrawerHeaderAccount();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initClick() {
        super.initClick();

        //监听菜单头部点击事件
        drawerHeader.setOnClickListener(v -> {
            if (currentUser == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("exit", "1");
                startActivity(intent);
                EventBus.getDefault().post("finish");
            } else {
                toChooseImg();
            }
        });

        //监听设置主题等
        editor.setOnClickListener(this);
        snyc.setOnClickListener(this);
        setting.setOnClickListener(this);
        theme.setOnClickListener(this);
        about.setOnClickListener(this);
        countClass.setOnClickListener(this);
        nav_outexcle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_edit://编辑资料
                currentUser = BmobUser.getCurrentUser(MyUser.class);
                //获取当前用户
                if (currentUser == null)
//                    SnackbarUtils.show(mContext, "请先登陆");
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                else
                    startActivityForResult(new Intent(mContext, UserInfoActivity.class), USERINFOACTIVITY_CODE);
                break;

            case R.id.nav_class://账单分类
                startActivity(new Intent(mContext, SortActivity.class));
                break;
            case R.id.nav_outexcle://导出账单
                ActivityCompat.requestPermissions(getActivity(), new String[]{android
                        .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                //创建文件夹
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && getAvailableStorage(getContext()) > 1000000) {
                    Toast.makeText(getContext(), "SD卡不可用", Toast.LENGTH_LONG).show();
                    return;
                }
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AndroidwzqExcelDemo/";
                File file = new File(filePath);
                if (!file.exists()) {
                    file.mkdirs();
                    if (file.mkdirs()) {
                        Toast.makeText(getApplicationContext(), "创建目录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "创建目录失败", Toast.LENGTH_SHORT).show();
                    }
                }
                String excleCountName = "/wzq.xls";
                String[] title = {"本地id", "服务器端id", "金额", "内容", "用户id", "支付方式", "图标", "账单分类", "分类图标", "创建时间", "收入支出", "版本"} ;
                String sheetName = "demoSheetName";
                List<BBill> bBills = new ArrayList<>();
                bBills = LocalRepository.getInstance().getBBills();
                filePath = file.getAbsolutePath() + "/" + excleCountName;
                ExcelUtil.initExcel(filePath, title);
                ExcelUtil.writeObjListToExcel(bBills, filePath, getApplicationContext());

                break;

            case R.id.nav_sync://同步数据
                if (currentUser == null)
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                else
                    BmobRepository.getInstance().syncBill(currentUser.getObjectId());
                break;
            case R.id.nav_setting://设置

                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.nav_theme://主题
                showUpdateThemeDialog();
                break;
            case R.id.nav_about://关于
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
        }
    }

    /**
     * 显示修改主题色 Dialog
     */
    private void showUpdateThemeDialog() {
        String[] themes = ThemeManager.getInstance().getThemes();
        new MaterialDialog.Builder(mContext)
                .title("选择主题")
                .titleGravity(GravityEnum.CENTER)
                .items(themes)
//                .titleColorRes(R.color.material_red_500)
                .contentColor(Color.BLACK) // notice no 'res' postfix for literal color
                .linkColorAttr(R.attr.aboutPageHeaderTextColor)  // notice attr is used instead of none or res for attribute resolving
                .dividerColorRes(R.color.colorMainDateBg)
//                .backgroundColorRes(R.drawable.dialog_backgroud)//背景色
//                .positiveColorRes(R.color.material_red_500)
                .neutralColorRes(R.color.colorControlNormal)
//                .negativeColorRes(R.color.material_red_500)
//                .widgetColorRes(R.color.colorControlNormal)//选中颜色
//                .buttonRippleColorRes(R.color.colorControlNormal)
                .negativeText("取消")
//                .customView(R.layout.activity_dialog,true)
                .itemsCallbackSingleChoice(0, (dialog, itemView, position, text) -> {
                    ThemeManager.getInstance().setTheme(mActivity, themes[position]);
                    dialog.dismiss();
                    return false;
                }).show();
    }


    /**
     * 显示选择头像来源对话框
     */
    private void toChooseImg() {
        if (dialogView1 == null) {
            dialogView1 = new ButtomDialogView1(getActivity());
            dialogView1.setOnDialogClickListener(new ButtomDialogView1.OnDialogClickListener() {
                @Override
                public void onclick1() {//相机
//                    selectFile = new File(Environment.getExternalStorageDirectory(), "WZQ/" +"head" + ".jpg");
                    selectFile = new File(SelectphotoUtils.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
                    SelectphotoUtils.takePicture(getActivity(), MineFragment.this, selectFile);
                    dialogView1.dismiss();
                }

                @Override
                public void onclick2() {//打开相册
                    SelectphotoUtils.selectPicture(getActivity(), MineFragment.this);
                    dialogView1.dismiss();
                }

                @Override
                public void onclick3() {
                    dialogView1.dismiss();
                }
            });
        }
        dialogView1.show();
    }


    /**
     * 获取SD可用容量
     */
    private static long getAvailableStorage(Context context) {
        String root = context.getExternalFilesDir(null).getPath();
        StatFs statFs = new StatFs(root);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long availableSize = blockSize * availableBlocks;
        // Formatter.formatFileSize(context, availableSize);
        return availableSize;
    }

    /**
     * 监听Activity返回值
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CHOOSE_PICTURE:// 直接从相册获取
                try {
                    SelectphotoUtils.gotoClipActivity(getActivity(), MineFragment.this, data.getData(), type);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case TAKE_PICTURE:// 调用相机拍照
                try {
                    if (isExistSd()) {
                        SelectphotoUtils.gotoClipActivity(getActivity(), MineFragment.this, Uri.fromFile(selectFile), type);
                    } else {
                        Toast.makeText(getActivity(), "图片保存失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                break;
            case REQUEST_CROP_PHOTO://取得裁剪后的图片
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    //此处后面可以将bitMap转为二进制上传后台网络
                    Toast.makeText(getActivity(),"上传中",Toast.LENGTH_SHORT).show();
                    //......
                    uploadPic(bitMap);
                }
                break;
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case USERINFOACTIVITY_CODE:
                    setDrawerHeaderAccount();
                    break;
                case LOGINACTIVITY_CODE:
                    setDrawerHeaderAccount();
                    break;
            }
        }
    }

    /**
     * 定义方法判断SD卡的存在性
     */
    private boolean isExistSd() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 设置DrawerHeader的用户信息
     */
    public void setDrawerHeaderAccount() {
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        //获取当前用户
        if (currentUser != null) {
            drawerTvAccount.setText(currentUser.getUsername());
            drawerTvMail.setText(currentUser.getEmail());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.icon_head_error);
            Glide.with(getActivity()).load(currentUser.getImage()).apply(requestOptions).into(drawerIv);

        } else {
            drawerTvAccount.setText("账号");
            drawerTvMail.setText("点我登录");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectFile = new File(SelectphotoUtils.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
                } else {
                    Toast.makeText(getActivity(), "你拒绝了权限申请，可能无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;

            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectphotoUtils.selectPicture(getActivity(), MineFragment.this);
                } else {
                    Toast.makeText(getActivity(), "你拒绝了权限申请，可能无法打开相册哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    /**
     * 保存头像并上传服务器
     *
     * @param bitmap
     */
    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        String imagename = currentUser.getObjectId() + "_" + String.valueOf(System.currentTimeMillis());
        String imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), imagename + ".png");
        if (imagePath != null) {
            final BmobFile bmobFile = new BmobFile(new File(imagePath));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        MyUser newUser = new MyUser();
                        newUser.setImage(bmobFile.getFileUrl());
                        newUser.update(currentUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                                    drawerIv.setImageBitmap(bitmap);
                                } else {
                                    Toast.makeText(getActivity(), "上传失败,"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "上传失败,"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //EvenBus
    @Subscribe
    public void eventBusListener(String tag) {
        if (TextUtils.equals("1", tag)) {
            processLogic();
        }
    }
}
