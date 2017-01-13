package com.smashingboxes.ga_wrapper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by Tyler McCraw on 6/10/16.
 */
public class GaFloatingActionButton extends FloatingActionButton implements IGaComponent {

    private GaEvent gaEvent;

    public GaFloatingActionButton(Context context) {
        super(context);
    }

    public GaFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(attrs);
    }

    public GaFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
            gaEvent.setCategory(getContext().getString(R.string.ga_wrapper_category_fab));
        }
        if (TextUtils.isEmpty(gaEvent.getAction())) {
            gaEvent.setAction(getContext().getString(R.string.ga_wrapper_action_fab_click));
        }
        if (TextUtils.isEmpty(gaEvent.getLabel())) {
            gaEvent.setLabel(getContext().getString(R.string.ga_wrapper_label_fab_fallback));
        }
    }
}
