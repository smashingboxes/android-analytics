package com.smashingboxes.ga_wrapper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by Tyler McCraw on 6/17/16.
 */
public class GaRadioButton extends AppCompatRadioButton implements IGaComponent {

    private GaEvent gaEvent;

    public GaRadioButton(Context context) {
        super(context);
    }

    public GaRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(attrs);
    }

    public GaRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            gaEvent.setCategory(getContext().getString(R.string.ga_wrapper_category_radio_button));
        }
        if (TextUtils.isEmpty(gaEvent.getAction())) {
            gaEvent.setAction(getContext().getString(R.string.ga_wrapper_action_radio_button_select));
        }
        if (TextUtils.isEmpty(gaEvent.getLabel())) {
            if (!TextUtils.isEmpty(getText().toString())) {
                gaEvent.setLabel(getText().toString());
            } else {
                gaEvent.setLabel(getContext().getString(R.string.ga_wrapper_label_radio_button_fallback));
            }
        }
    }
}
