package com.smashingboxes.ga_wrapper;

import android.util.AttributeSet;

/**
 * Created by Tyler McCraw on 2/19/16.
 *
 * Interface for all UI components to add Analytics setup
 */
public interface IGaComponent {

    /**
     * Prepare the component's GA event, reusing custom attributes defined in the
     * resource layout xml for the component
     * @param attrs attribute set, typically passed in from the constructor
     */
    void initComponent(AttributeSet attrs);

    /**
     * Prepare the component's GA event before it is sent using categories, actions, labels
     * defined in our standardization document, if there are no developer-defined GA event attributes
     */
    void prepareEventForSend();
}
