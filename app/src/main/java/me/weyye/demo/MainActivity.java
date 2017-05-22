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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //default is normal style
                HiPermission.create(MainActivity.this)
                        .animStyle(R.style.PermissionAnimFade)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.i(TAG, "onClose");
                                showToast(getString(R.string.permission_on_close));
                            }

                            @Override
                            public void onFinish() {
                                showToast(getString(R.string.permission_completed));
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
                break;
            case R.id.btn2:
                //After you have set the theme, you must called filterColor () to set the color of the icon
                // ,otherwise the default is black
                List<PermissonItem> permissonItems = new ArrayList<PermissonItem>();
                permissonItems.add(new PermissonItem(Manifest.permission.CAMERA, getString(R.string.permission_cus_item_camera), R.drawable.permission_ic_camera));
                HiPermission.create(MainActivity.this)
                        .title(getString(R.string.permission_cus_title))
                        .permissions(permissonItems)
                        .msg(getString(R.string.permission_cus_msg))
                        .animStyle(R.style.PermissionAnimScale)
                        .style(R.style.PermissionDefaultBlueStyle)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.i(TAG, "onClose");
                                showToast(getString(R.string.permission_on_close));
                            }

                            @Override
                            public void onFinish() {
                                showToast(getString(R.string.permission_completed));
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
                break;
            case R.id.btn3:
                List<PermissonItem> permissons = new ArrayList<PermissonItem>();
                permissons.add(new PermissonItem(Manifest.permission.CALL_PHONE, getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
                HiPermission.create(MainActivity.this)
                        .title(getString(R.string.permission_cus_title))
                        .permissions(permissons)
                        .msg(getString(R.string.permission_cus_msg))
                        .animStyle(R.style.PermissionAnimModal)
                        .style(R.style.PermissionDefaultGreenStyle)
//                        .style(R.style.CusStyle)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.i(TAG, "onClose");
                                showToast(getString(R.string.permission_on_close));
                            }

                            @Override
                            public void onFinish() {
                                showToast(getString(R.string.permission_completed));
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
                break;
            case R.id.btn4:
                //request single permission only called onDeny or onGuarantee
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
                break;
        }
    }
}
