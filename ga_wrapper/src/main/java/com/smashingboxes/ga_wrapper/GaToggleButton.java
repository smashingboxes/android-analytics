package com.smashingboxes.ga_wrapper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/**
 * Created by Tyler McCraw on 2/19/16.
 */
public class GaToggleButton extends ToggleButton implements IGaComponent {

    private GaEvent gaEvent;

    public GaToggleButton(Context context) {
        super(context);
    }

    public GaToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(attrs);
    }

    public GaToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GaToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponent(attrs);
    }

    @Override
    public boolean performClick() {
        boolean returnClick = super.performClick();
        prepareEventForSend();
        TrackerManager.getInstance().sendUserEvent(gaEvent, false);
        return returnClick;
    }

    @Override
    public void initComponent(AttributeSet attrs) {
        TypedArray attrArr = getContext().obtainStyledAttributes(attrs, R.styleable.ga_wrapper);
        gaEvent = new GaEvent(attrArr.getString(R.styleable.ga_wrapper_category),
                attrArr.getString(R.styleable.ga_wrapper_action),
                attrArr.getString(R.styleable.ga_wrapper_label),
                attrArr.getString(R.styleable.ga_wrapper_custom_dimension1),
                attrArr.getString(R.styleable.ga_wrapper_custom_dimension2));
        attrArr.recycle();
    }

    @Override
    public void prepareEventForSend() {
        if (TextUtils.isEmpty(gaEvent.getCategory())) {
            gaEvent.setCategory(getContext().getString(R.string.ga_wrapper_category_toggle_button));
        }
        if (TextUtils.isEmpty(gaEvent.getAction())) {
            gaEvent.setAction(getContext().getString(R.string.ga_wrapper_action_toggle_button_click));
        }
        if (TextUtils.isEmpty(gaEvent.getLabel())) {
            if (!TextUtils.isEmpty(getText().toString())) {
                gaEvent.setLabel(getText().toString());
            } else {
                gaEvent.setLabel(getContext().getString(R.string.ga_wrapper_label_toggle_button_fallback));
            }
        }
    }
}