package com.wzq.jz_app.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;

import android.util.Log;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseActivity;
import com.wzq.jz_app.base.BaseFragment;
import com.wzq.jz_app.ui.activity.MainActivity1;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static com.wzq.jz_app.utils.UiUtils.getString;

/**
 * @author wzq
 * @data 2019/7/10
 * @email wang_love152@163.com
 * @for
 */
public class SelectphotoUtils {

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 101;
    //图片路径
    protected static Uri tempUri = null;


//    /**
//     * 显示选择头像来源对话框
//     */
//    @SuppressLint("ResourceAsColor")
//    public static void showIconDialog(final BaseFragment ac, final Activity activity) {
//        new MaterialDialog.Builder(activity)
//                .title("选择图片来源")
//                .titleGravity(GravityEnum.CENTER)
//                .items(new String[]{"相册", "相机"})
//                .positiveText("确定")
//                .positiveColor(R.color.colorPrimary1)
//                .widgetColorRes(R.color.colorPrimary1)//选中颜色
//                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
//                        switch (which) {
//                            case CHOOSE_PICTURE: // 选择本地照片
//                                Intent openAlbumIntent = new Intent(Intent.ACTION_PICK,null);
//                                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                                ac.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
//                                break;
//                            case TAKE_PICTURE: // 拍照
////                                takePicture(activity);
//                                break;
//                        }
//                        dialog.dismiss();
//                        return false;
//                    }
//                }).show();
//
//    }

    /**
     * 拍照(fragment中调用此方法)
     */
    public static void takePicture(Context context, BaseFragment baseFragment, File file) {
        if (ContextCompat.checkSelfPermission(baseFragment.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 进入这儿表示没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(baseFragment.getActivity(), Manifest.permission.CAMERA)) {
                // 提示已经禁止
                displayFrameworkBugMessageAndExit(context);
            } else {
                requestPermissions(baseFragment.getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
            }
        } else {
            //获取存储权限
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (ActivityCompat.checkSelfPermission(baseFragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(baseFragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // 提示已经禁止
                        displayFrameworkBugMessageAndExit(context);
                    } else {
                       requestPermissions(baseFragment.getActivity(), PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                    }
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                        Uri fileUri = FileProvider7.getUriForFile(context, file);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        baseFragment.startActivityForResult(takePictureIntent, TAKE_PICTURE);
                    }
                }
            } else {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(baseFragment.getActivity().getPackageManager()) != null) {
                    Uri fileUri = FileProvider7.getUriForFile(baseFragment.getActivity(), file);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    baseFragment.startActivityForResult(takePictureIntent, TAKE_PICTURE);
                }
            }
        }
    }

    /**
     * 拍照（Activity中调用此方法）
     */
    public static void takePicture1(BaseActivity baseActivity, File file) {
        if (ContextCompat.checkSelfPermission(baseActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 进入这儿表示没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(baseActivity, Manifest.permission.CAMERA)) {
                // 提示已经禁止
//                ToastUtil.longToast(mContext, getString(R.string.you_have_cut_down_the_permission));
            } else {
                requestPermissions(baseActivity, new String[]{Manifest.permission.CAMERA}, 100);
            }
        } else {
            //获取存储权限
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (ActivityCompat.checkSelfPermission(baseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(baseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // 提示已经禁止
                    } else {
                        requestPermissions(baseActivity, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                    }
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(baseActivity.getPackageManager()) != null) {
                        Uri fileUri = FileProvider7.getUriForFile(baseActivity, file);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        baseActivity.startActivityForResult(takePictureIntent, TAKE_PICTURE);
                    }
                }
            } else {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(baseActivity.getPackageManager()) != null) {
                    Uri fileUri = FileProvider7.getUriForFile(baseActivity, file);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    baseActivity.startActivityForResult(takePictureIntent, TAKE_PICTURE);
                }
            }

        }
    }

    /**
     * 裁剪图片方法实现（fragment中调用此方法）
     *
     * @param uri
     */
    public static void startPhotoZoom(Uri uri, final BaseFragment activity, File file) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        if (Build.MANUFACTURER.equals("HUAWEI")) {//华为手机的裁剪框为圆形，需要如下判断
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        }else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//       activity.startActivityForResult(intent, CROP_SMALL_PICTURE);

        if (OSUtil.isMIUI()) {
            Uri uritempFile = Uri.parse("file://" + "/" + file);
//            Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg"
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        } else {
            intent.putExtra("return-data", true);
        }
        activity.startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    /**
     * 裁剪图片方法实现（Activity中调用此方法）
     *
     * @param uri
     */
    public static void startPhotoZoom1(Uri uri, final BaseActivity activity, File file) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        if (Build.MANUFACTURER.equals("HUAWEI")) {//华为手机的裁剪框为圆形，需要如下判断
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        }else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        if (OSUtil.isMIUI()) {
            Uri uritempFile = Uri.parse("file://" + "/" + file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        } else {
            intent.putExtra("return-data", true);
        }
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


    private static void displayFrameworkBugMessageAndExit(Context context) {
        String per = String.format(getString(R.string.permission), getString(R.string.camera), getString(R.string.camera));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.qr_name));
        builder.setMessage(per);
        builder.setPositiveButton(getString(R.string.i_know), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private static void displayFrameworkBugMessageAndExit1(Context context) {
        String per = String.format(getString(R.string.permission), getString(R.string.sdcar), getString(R.string.sdcar));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.qr_name));
        builder.setMessage(per);
        builder.setPositiveButton(getString(R.string.i_know), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

}
