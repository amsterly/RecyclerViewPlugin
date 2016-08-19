package com.hallo.fahionaly.recycleviewplugin.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hallo.fahionaly.recycleviewplugin.swipe.SwipeLayout;


/**
 * Created by lwb on 16/7/19.
 * 基本参照hyman的ViewHolder 增加网络图片加载功能
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static ViewHolder createViewHolder(Context context, View itemView) {
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context context,
                                              ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

//    //--新增 设置spinnerIndex
//    public ViewHolder setSpinnerIndex(int viewID, DeviceParameterBean deviceParameterBean, int arrayID) {
//        int index;
//        if (deviceParameterBean.getValue() == null)
//            index = 0;
//        else
//            index = Integer.parseInt(deviceParameterBean.getValue());
//
//
//        Spinner view = getView(viewID);
//
//        if (view.getAdapter() == null) {
//            String[] items = mContext.getResources().getStringArray(arrayID);
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.row_spn, items);
//            adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
//            view.setAdapter(adapter);
//        }
//        view.setSelection(index);
//
//        return this;
//    }

    public ViewHolder setSpinnerIndexChangedListener(int viewID, AdapterView.OnItemSelectedListener listener) {
        Spinner view = getView(viewID);
        view.setOnItemSelectedListener(listener);
        return this;
    }

//    //--新增 设置switch 开关
//    public ViewHolder setSwitch(int viewID, boolean isOn) {
//        Switch view = getView(viewID);
//        view.setChecked(isOn);
//        return this;
//    }
//
//    //--新增 设置 EditText 范围检查
//    public ViewHolder setEditTextCheck(int viewID, Object object) {
//        final MaterialEditText editText = getView(viewID);
//        final DeviceParameterBean deviceParameterBean = (DeviceParameterBean) object;
//        ;
//        final int[] scope = deviceParameterBean.getScope();
//        final String atype = deviceParameterBean.getType();
//        final String num;
//        if (atype != null && atype.equals("int32"))
//            num = "的整数";
//        else num = "的数";
//        if (deviceParameterBean != null) {
//            editText.setText(deviceParameterBean.getValue());
//            editText.setFloatingLabelText("");
//            if (scope != null && scope.length == 2)
//                editText.setHelperText(String.format("请输入范围在[%s,%s]%s", scope[0], scope[1], num));
//        }
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                try {
//                    float tempValue = Float.parseFloat(editText.getText().toString());
//
//                    if (scope != null && scope.length == 2) {
//                        if (tempValue > scope[1] || tempValue < scope[0]) {
//                            editText.setError(String.format("非法输入！请输入范围:[%s,%s]%s", scope[0], scope[1], num));
//                            deviceParameterBean.setLegal(false);
//                            return;
////                            if(type=="float")
//                        } else if (atype != null && atype.equals("int32")) {
//                            if (editText.getText().toString().contains(".")) {
//                                editText.setError(String.format("非法输入！请输入范围:[%s,%s]%s", scope[0], scope[1], num));
//                                deviceParameterBean.setLegal(false);
//                                return;
//                            }
//                        }
//                    }
//                    deviceParameterBean.setLegal(true);
//
//                } catch (Exception e) {
//                    if (scope != null && scope.length == 2) {
//                        editText.setError(String.format("非法输入！" +
//                                "请输入范围:" +
//                                "[%s,%s]%s", scope[0], scope[1], num));
//                        deviceParameterBean.setLegal(false);
//                    }
//
//                }
//            }
//        });
//        return this;
//    }
//
//    public ViewHolder setEditTextTextWatcherListener(int viewID, TextWatcher listener) {
//        final MaterialEditText editText = getView(viewID);
//        editText.addTextChangedListener(listener);
//        return this;
//    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setSwipeLayout(int viewId, View.OnTouchListener listener) {
        SwipeLayout swipeLayout = getView(viewId);
        swipeLayout.setOnTouchListener(listener);
        SwipeLayout.addSwipeView(swipeLayout);
        return this;
    }


    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


    public ViewHolder removeSwipeLayout(int viewId) {
        SwipeLayout swipeLayout = getView(viewId);
        SwipeLayout.removeSwipeView(swipeLayout);
        return this;
    }
}
