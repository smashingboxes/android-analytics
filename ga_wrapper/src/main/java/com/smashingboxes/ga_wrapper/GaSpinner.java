package com.smashingboxes.ga_wrapper;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Tyler McCraw on 2/19/16.
 */
public class GaSpinner extends AppCompatSpinner implements IGaComponent {

    private GaEvent gaClickEvent;
    private GaEvent gaItemClickEvent;

    public GaSpinner(Context context) {
        super(context);
    }

    public GaSpinner(Context context, int mode) {
        super(context, mode);
    }

    public GaSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(attrs);
    }

    public GaSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent(attrs);
    }

    public GaSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        initComponent(attrs);
    }

    public GaSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
        initComponent(attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            prepareEventForSend();
            TrackerManager.getInstance().sendUserEvent(gaClickEvent, false);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setSelection(int position) {
        prepareEventForSend();
        TrackerManager.getInstance().sendUserEvent(gaItemClickEvent, false);
        super.setSelection(position);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        prepareEventForSend();
        TrackerManager.getInstance().sendUserEvent(gaItemClickEvent, false);
        super.setSelection(position, animate);
    }

    @Override
    public void initComponent(AttributeSet attrs) {
        TypedArray attrArr = getContext().obtainStyledAttributes(attrs, R.styleable.ga_wrapper);
        gaClickEvent = new GaEvent(attrArr.getString(R.styleable.ga_wrapper_category),
                attrArr.getString(R.styleable.ga_wrapper_action),
                attrArr.getString(R.styleable.ga_wrapper_label),
                attrArr.getString(R.styleable.ga_wrapper_custom_dimension1),
                attrArr.getString(R.styleable.ga_wrapper_custom_dimension2));
        gaItemClickEvent = new GaEvent(attrArr.getString(R.styleable.ga_wrapper_category),
                attrArr.getString(R.styleable.ga_wrapper_action),
                attrArr.getString(R.styleable.ga_wrapper_label),
                attrArr.getString(R.styleable.ga_wrapper_custom_dimension1),
                attrArr.getString(R.styleable.ga_wrapper_custom_dimension2));
        attrArr.recycle();
    }

    @Override
    public void prepareEventForSend() {
        if (TextUtils.isEmpty(gaClickEvent.getCategory())) {
            gaClickEvent.setCategory(getContext().getString(R.string.ga_wrapper_category_spinner));
        }
        if (TextUtils.isEmpty(gaClickEvent.getAction())) {
            gaClickEvent.setAction(getContext().getString(R.string.ga_wrapper_action_spinner_click));
        }
        if (TextUtils.isEmpty(gaClickEvent.getLabel())) {
            if (getAdapter() != null && getAdapter().getCount() > 0 && !TextUtils.isEmpty(getItemAtPosition(0).toString())) {
                gaClickEvent.setLabel(getItemAtPosition(0).toString());
            } else {
                gaClickEvent.setLabel(getContext().getString(R.string.ga_wrapper_label_spinner_fallback));
            }
        }

        if (TextUtils.isEmpty(gaItemClickEvent.getCategory())) {
            if (getAdapter() != null && getAdapter().getCount() > 0 && !TextUtils.isEmpty(getItemAtPosition(0).toString())) {
                gaItemClickEvent.setCategory(getItemAtPosition(0).toString()
                        + " " + getContext().getString(R.string.ga_wrapper_category_spinner));
            } else {
                gaItemClickEvent.setCategory(getContext().getString(R.string.ga_wrapper_category_spinner));
            }
        }
        if (TextUtils.isEmpty(gaItemClickEvent.getAction())) {
            gaItemClickEvent.setAction(getContext().getString(R.string.ga_wrapper_action_spinner_select));
        }
        if (TextUtils.isEmpty(gaItemClickEvent.getLabel())) {
            if (!TextUtils.isEmpty(getSelectedItem().toString())) {
                gaItemClickEvent.setLabel(getSelectedItem().toString());
            } else {
                gaItemClickEvent.setLabel(getContext().getString(R.string.ga_wrapper_label_spinner_item_fallback));
            }
        }
    }
}
