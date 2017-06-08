package me.weyye.hipermission;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.List;
import java.util.ListIterator;


/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class PermissionActivity extends AppCompatActivity {

    public static int PERMISSION_TYPE_SINGLE = 1;
    public static int PERMISSION_TYPE_MUTI = 2;
    private int mPermissionType;
    private String mTitle;
    private String mMsg;
    private static PermissionCallback mCallback;
    private List<PermissionItem> mCheckPermissions;
    private Dialog mDialog;

    private static final int REQUEST_CODE_SINGLE = 1;
    private static final int REQUEST_CODE_MUTI = 2;
    public static final int REQUEST_CODE_MUTI_SINGLE = 3;
    private static final int REQUEST_SETTING = 110;

    private static final String TAG = "PermissionActivity";
    private CharSequence mAppName;
    private int mStyleId;
    private int mFilterColor;
    private int mAnimStyleId;

    public static void setCallBack(PermissionCallback callBack) {
        PermissionActivity.mCallback = callBack;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallback = null;
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDatas();
        if (mPermissionType == PERMISSION_TYPE_SINGLE) {
            //单个权限申请
            if (mCheckPermissions == null || mCheckPermissions.size() == 0)
                return;

            requestPermission(new String[]{mCheckPermissions.get(0).Permission}, REQUEST_CODE_SINGLE);
        } else {
            mAppName = getApplicationInfo().loadLabel(getPackageManager());
            //多个权限
            showPermissionDialog();
        }
    }


    private String getPermissionTitle() {
        return TextUtils.isEmpty(mTitle) ? String.format(getString(R.string.permission_dialog_title), mAppName) : mTitle;
    }

    private void showPermissionDialog() {

        String title = getPermissionTitle();
        String msg = TextUtils.isEmpty(mMsg) ? String.format(getString(R.string.permission_dialog_msg), mAppName) : mMsg;

        PermissionView contentView = new PermissionView(this);
        contentView.setGridViewColum(mCheckPermissions.size() < 3 ? mCheckPermissions.size() : 3);
        contentView.setTitle(title);
        contentView.setMsg(msg);
        //这里没有使用RecyclerView，可以少引入一个库
        contentView.setGridViewAdapter(new PermissionAdapter(mCheckPermissions));
        if (mStyleId == -1) {
            //用户没有设置，使用默认绿色主题
            mStyleId = R.style.PermissionDefaultNormalStyle;
            mFilterColor = getResources().getColor(R.color.permissionColorGreen);
        }

        contentView.setStyleId(mStyleId);
        contentView.setFilterColor(mFilterColor);
        contentView.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
                String[] strs = getPermissionStrArray();
                ActivityCompat.requestPermissions(PermissionActivity.this, strs, REQUEST_CODE_MUTI);
            }
        });
        mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(contentView);
        if (mAnimStyleId != -1)
            mDialog.getWindow().setWindowAnimations(mAnimStyleId);

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                if (mCallback != null)
                    mCallback.onClose();
                finish();
            }
        });
        mDialog.show();
    }


    private void reRequestPermission(final String permission) {
        String permissionName = getPermissionItem(permission).PermissionName;
        String alertTitle = String.format(getString(R.string.permission_title), permissionName);
        String msg = String.format(getString(R.string.permission_denied), permissionName, mAppName);
        showAlertDialog(alertTitle, msg, getString(R.string.permission_cancel), getString(R.string.permission_ensure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestPermission(new String[]{permission}, REQUEST_CODE_MUTI_SINGLE);
            }
        });
    }

    private void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(PermissionActivity.this, permissions, requestCode);
    }

    private void showAlertDialog(String title, String msg, String cancelTxt, String PosTxt, DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(cancelTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onClose();
                    }
                })
                .setPositiveButton(PosTxt, onClickListener).create();
        alertDialog.show();
    }

    private String[] getPermissionStrArray() {
        String[] str = new String[mCheckPermissions.size()];
        for (int i = 0; i < mCheckPermissions.size(); i++) {
            str[i] = mCheckPermissions.get(i).Permission;
        }
        return str;
    }


    private void getDatas() {
        Intent intent = getIntent();
        mPermissionType = intent.getIntExtra(ConstantValue.DATA_PERMISSION_TYPE, PERMISSION_TYPE_SINGLE);
        mTitle = intent.getStringExtra(ConstantValue.DATA_TITLE);
        mMsg = intent.getStringExtra(ConstantValue.DATA_MSG);
        mFilterColor = intent.getIntExtra(ConstantValue.DATA_FILTER_COLOR, 0);
        mStyleId = intent.getIntExtra(ConstantValue.DATA_STYLE_ID, -1);
        mAnimStyleId = intent.getIntExtra(ConstantValue.DATA_ANIM_STYLE, -1);
        mCheckPermissions = (List<PermissionItem>) intent.getSerializableExtra(ConstantValue.DATA_PERMISSIONS);
    }

    /**
     * 重新申请权限数组的索引
     */
    private int mRePermissionIndex;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_SINGLE:
                String permission = getPermissionItem(permissions[0]).Permission;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onGuarantee(permission, 0);
                } else {
                    onDeny(permission, 0);
                }
                finish();
                break;
            case REQUEST_CODE_MUTI:
                for (int i = 0; i < grantResults.length; i++) {
                    //权限允许后，删除需要检查的权限
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        PermissionItem item = getPermissionItem(permissions[i]);
                        mCheckPermissions.remove(item);
                        onGuarantee(permissions[i], i);
                    } else {
                        //权限拒绝
                        onDeny(permissions[i], i);
                    }
                }
                if (mCheckPermissions.size() > 0) {
                    //用户拒绝了某个或多个权限，重新申请
                    reRequestPermission(mCheckPermissions.get(mRePermissionIndex).Permission);
                } else {
                    onFinish();
                }
                break;
            case REQUEST_CODE_MUTI_SINGLE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    //重新申请后再次拒绝
                    //弹框警告! haha
                    try {
                        //permissions可能返回空数组，所以try-catch
                        String name = getPermissionItem(permissions[0]).PermissionName;
                        String title = String.format(getString(R.string.permission_title), name);
                        String msg = String.format(getString(R.string.permission_denied_with_naac), mAppName, name, mAppName);
                        showAlertDialog(title, msg, getString(R.string.permission_reject), getString(R.string.permission_go_to_setting), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Uri packageURI = Uri.parse("package:" + getPackageName());
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                    startActivityForResult(intent, REQUEST_SETTING);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    onClose();
                                }
                            }
                        });
                        onDeny(permissions[0], 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onClose();
                    }
                } else {
                    onGuarantee(permissions[0], 0);
                    if (mRePermissionIndex < mCheckPermissions.size() - 1) {
                        //继续申请下一个被拒绝的权限
                        reRequestPermission(mCheckPermissions.get(++mRePermissionIndex).Permission);
                    } else {
                        //全部允许了
                        onFinish();
                    }
                }
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult--requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (requestCode == REQUEST_SETTING) {
            if (mDialog != null && mDialog.isShowing())
                mDialog.dismiss();
            checkPermission();
            if (mCheckPermissions.size() > 0) {
                mRePermissionIndex = 0;
                reRequestPermission(mCheckPermissions.get(mRePermissionIndex).Permission);
            } else {
                onFinish();
            }
        }

    }

    private void checkPermission() {

        ListIterator<PermissionItem> iterator = mCheckPermissions.listIterator();
        while (iterator.hasNext()) {
            int checkPermission = ContextCompat.checkSelfPermission(getApplicationContext(), iterator.next().Permission);
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                iterator.remove();
            }
        }
    }

    private void onFinish() {
        if (mCallback != null)
            mCallback.onFinish();
        finish();
    }

    private void onClose() {
        if (mCallback != null)
            mCallback.onClose();
        finish();
    }

    private void onDeny(String permission, int position) {
        if (mCallback != null)
            mCallback.onDeny(permission, position);
    }

    private void onGuarantee(String permission, int position) {
        if (mCallback != null)
            mCallback.onGuarantee(permission, position);
    }

    private PermissionItem getPermissionItem(String permission) {
        for (PermissionItem permissionItem : mCheckPermissions) {
            if (permissionItem.Permission.equals(permission))
                return permissionItem;
        }
        return null;
    }
}
