package com.wzq.jz_app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseActivity;
import com.wzq.jz_app.base.BaseFragment;
import com.wzq.jz_app.widget.photo.ClipImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author wzq
 * @data 2019/7/10
 * @email wang_love152@163.com
 * @for
 */
public class SelectphotoUtils {

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;//申请相机权限
    protected static final int CROP_SMALL_PICTURE = 2;
    //请求外部访问存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //图片路径
    protected static Uri tempUri = null;


    /**
     * 显示选择头像来源对话框
     */
    @SuppressLint("ResourceAsColor")
    public static void showIconDialog(final BaseFragment ac, final Activity activity) {
        new MaterialDialog.Builder(activity)
                .title("选择图片来源")
                .titleGravity(GravityEnum.CENTER)
                .items(new String[]{"相册", "相机"})
                .positiveText("确定")
                .positiveColor(R.color.colorPrimary1)
                .widgetColorRes(R.color.colorPrimary1)//选中颜色
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            case CHOOSE_PICTURE: // 选择本地照片
                                Intent openAlbumIntent = new Intent(Intent.ACTION_PICK, null);
                                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                ac.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                break;
                            case TAKE_PICTURE: // 拍照
//                                takePicture(activity);
                                break;
                        }
                        dialog.dismiss();
                        return false;
                    }
                }).show();

    }

    /**
     * 拍照(fragment中调用此方法)
     */
    public static void takePicture(Context context, BaseFragment baseFragment, File file) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 进入这儿表示没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(baseFragment.getActivity(), Manifest.permission.CAMERA)) {
                // 提示已经禁止
//                ToastUtil.longToast(mContext, getString(R.string.you_have_cut_down_the_permission));
            } else {
                ActivityCompat.requestPermissions(baseFragment.getActivity(), new String[]{Manifest.permission.CAMERA}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        } else {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

// 判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= 24) {
                //android 7.0系统解决拍照的问题android.os.FileUriExposedException:file:///storage/emulated/0/test.txt
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                tempUri = FileProvider.getUriForFile(context,
                        "com.wzq.jz_app.fileProvider", file);
            } else {
                tempUri = Uri.fromFile(file);
            }

            // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            if (isExistSd()) {
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            }
//            openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            baseFragment.startActivityForResult(openCameraIntent, TAKE_PICTURE);
        }
    }

    /**
     * 相册选择（fragment）
     */
    public static void selectPicture(Context context, BaseFragment baseFragment) {
        //权限判断
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            //回调到相册--跳转到相册
            Intent openAlbumIntent = new Intent(Intent.ACTION_PICK, null);
            openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            baseFragment.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

        }
    }

    private static final int REQUEST_CROP_PHOTO = 102;

    /**
     * 打开截图界面
     */
    public static void gotoClipActivity(Context context, BaseFragment baseFragment, Uri uri, int type) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        baseFragment.startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    /**
     * 裁剪图片方法实现（fragment中调用此方法）
     *
     * @param uri
     */
    public static void startPhotoZoom(Uri uri, final BaseFragment activity) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    /**
     * 裁剪图片方法实现（Activity中调用此方法）
     *
     * @param uri
     */
    public static void startPhotoZoom1(Uri uri, final BaseActivity activity) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    /**
     * 定义方法判断SD卡的存在性
     */
    public static boolean isExistSd() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
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
     * 采样率压缩图片
     */
    public static Bitmap getBitmap(Bitmap bitmap, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        Log.i("info", "图片大小：" + bit.getByteCount());//2665296  10661184
        return bit;
    }

    /// *
//    * 根据Uri返回文件绝对路径
//     * 兼容了file:///开头的 和 content://开头的情况
//     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }


}
