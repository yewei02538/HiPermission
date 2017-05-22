package me.weyye.hipermission;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class PermissionView extends FrameLayout {

    private TextView mTvTitle;
    private TextView mTvDesc;
    private Button mBtnNext;
    private GridView mGvPermission;
    private LinearLayout mLlRoot;

    public PermissionView(Context context) {
        this(context, null);
    }

    public PermissionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PermissionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View permissionView = View.inflate(getContext(), R.layout.dialog_request_permission, this);
        mTvTitle = (TextView) permissionView.findViewById(R.id.tvTitle);
        mLlRoot = (LinearLayout) permissionView.findViewById(R.id.llRoot);
        mTvDesc = (TextView) permissionView.findViewById(R.id.tvDesc);
        mBtnNext = (Button) permissionView.findViewById(R.id.goto_settings);
        mGvPermission = (GridView) permissionView.findViewById(R.id.gvPermission);
    }

    public void setGridViewColum(int colum) {
        mGvPermission.setNumColumns(colum);
    }

    public void setGridViewAdapter(ListAdapter adapter) {
        mGvPermission.setAdapter(adapter);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setMsg(String msg) {
        mTvDesc.setText(msg);
    }

    public void setBtnOnClickListener(OnClickListener listener) {
        mBtnNext.setOnClickListener(listener);
    }

    @SuppressWarnings("ResourceType")
    public void setStyleId(int styleId) {
        if (styleId <= 0)
            return;
        int[] ints = {
                R.attr.PermissionMsgColor,
                R.attr.PermissionTitleColor,
                R.attr.PermissionItemTextColor,
                R.attr.PermissionButtonTextColor,
                R.attr.PermissionBackround,
                R.attr.PermissionButtonBackground,
                R.attr.PermissionBgFilterColor,
                R.attr.PermissionIconFilterColor
        };
        Resources.Theme theme = getResources().newTheme();
        theme.applyStyle(styleId, true);

        TypedArray typedArray = theme.obtainStyledAttributes(ints);
        int msgColor = typedArray.getColor(0, 0);
        int titleColor = typedArray.getColor(1, 0);
        int itemTextColor = typedArray.getColor(2, 0);
        int btnTextColor = typedArray.getColor(3, 0);
        Drawable background = typedArray.getDrawable(4);
        Drawable Btnbackground = typedArray.getDrawable(5);
        int bgFilterColor = typedArray.getColor(6, 0);
        int iconFilterColor = typedArray.getColor(7, 0);

        if (titleColor != 0)
            mTvTitle.setTextColor(titleColor);
        if (background != null) {
            if (bgFilterColor != 0)
                background.setColorFilter(getColorFilter(bgFilterColor));
            mLlRoot.setBackgroundDrawable(background);
        }
        if (msgColor != 0)
            mTvDesc.setTextColor(msgColor);
        if (itemTextColor != 0)
            ((PermissionAdapter) mGvPermission.getAdapter()).setTextColor(itemTextColor);
        if (Btnbackground != null)
            mBtnNext.setBackgroundDrawable(Btnbackground);
        if (btnTextColor != 0)
            mBtnNext.setTextColor(btnTextColor);
        if (iconFilterColor != 0)
            setFilterColor(iconFilterColor);

        typedArray.recycle();

    }

    private ColorFilter getColorFilter(int bgFilterColor) {
        int blue = Color.blue(bgFilterColor);
        int green = Color.green(bgFilterColor);
        int red = Color.red(bgFilterColor);
        float[] cm = new float[]{
                1, 0, 0, 0, red,// 红色值
                0, 1, 0, 0, green,// 绿色值
                0, 0, 1, 0, blue,// 蓝色值
                0, 0, 0, 1, 1 // 透明度
        };
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
        return filter;
    }

    public void setFilterColor(int color) {
        if (color == 0)
            return;

        ((PermissionAdapter) mGvPermission.getAdapter()).setFilterColor(color);
    }
}
