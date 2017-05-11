package me.weyye.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissonItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<PermissonItem> permissonItems = new ArrayList<PermissonItem>();
                permissonItems.add(new PermissonItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_memory));
                HiPermission.create(MainActivity.this)
//                        .title("亲爱的上帝")
//                        .permissions(permissonItems)
//                        .filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()))
//                        .msg("为了保护世界的和平，开启这些权限吧！\n你我一起拯救世界！")
//                        .style(R.style.PermissionBlueStyle)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.i(TAG, "onClose");
                                showToast("用户关闭权限申请");
                            }

                            @Override
                            public void onFinish() {
                                showToast("所有权限申请完成");
                            }

                            @Override
                            public void onDeny(String permisson, int position) {
                                Log.i(TAG, "onDeny");
                            }

                            @Override
                            public void onGuarantee(String permisson, int position) {
                                Log.i(TAG, "onGuarantee");
                            }
                        });

            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单个权限申请只会回调onDeny和onGuarantee
                HiPermission.create(MainActivity.this).checkSinglePermission(Manifest.permission.CAMERA, new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDeny(String permisson, int position) {
                        showToast("onDeny");
                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
                        showToast("onGuarantee");
                    }
                });
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
