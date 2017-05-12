package me.weyye.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
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
                //Green style is default style
                HiPermission.create(MainActivity.this)
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

            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //After you have set the theme, you must called filterColor () to set the color of the icon
                // ,otherwise the default is black
                List<PermissonItem> permissonItems = new ArrayList<PermissonItem>();
                permissonItems.add(new PermissonItem(Manifest.permission.CAMERA, getString(R.string.permission_cus_item_camera), R.drawable.permission_ic_camera));
                HiPermission.create(MainActivity.this)
                        .title(getString(R.string.permission_cus_title))
                        .permissions(permissonItems)
                        .filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()))
                        .msg(getString(R.string.permission_cus_msg))
                        .style(R.style.PermissionBlueStyle)
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
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Single permission only called onDeny and onGuarantee
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
