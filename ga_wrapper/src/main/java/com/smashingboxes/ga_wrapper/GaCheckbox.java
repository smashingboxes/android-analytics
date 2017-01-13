package com.smashingboxes.ga_wrapper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by Tyler McCraw on 6/17/16.
 */
public class GaCheckbox extends AppCompatCheckBox implements IGaComponent {

    private GaEvent gaEvent;
    private boolean overrideEventAction;

    public GaCheckbox(Context context) {
        super(context);
    }

    public GaCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(attrs);
    }

    public GaCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent(attrs);
    }

    @Override
    public boolean performClick() {
        boolean returnClick = super.performClick();
        prepareEventForSend();
        if (!overrideEventAction) {
            if (isSelected()) {
                gaEvent.setAction(getContext().getString(R.string.ga_wrapper_action_checkbox_unselect));
            } else {
                gaEvent.setAction(getContext().getString(R.string.ga_wrapper_action_checkbox_unselect));
            }
        }
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
            gaEvent.setCategory(getContext().getString(R.string.ga_wrapper_category_checkbox));
        }

        overrideEventAction = !TextUtils.isEmpty(gaEvent.getAction());

        if (TextUtils.isEmpty(gaEvent.getLabel())) {
            if (!TextUtils.isEmpty(getText().toString())) {
                gaEvent.setLabel(getText().toString());
            } else {
                gaEvent.setLabel(getContext().getString(R.string.ga_wrapper_label_checkbox_fallback));
            }
        }
    }
}
