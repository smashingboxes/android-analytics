package com.smashingboxes.ga_wrapper;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * Created by Tyler McCraw on 1/15/16.
 *
 * Manager class to handle sending of all screen and user events
 */
public class TrackerManager {

    private static final String TAG = TrackerManager.class.getSimpleName();
    private static TrackerManager instance;
    private Tracker mTracker;
    private Resources resources;
    private int mGaConfigLocation;
    private String currentScreenName;
    /**
     * This will be used as a custom dimension which will be sent with user events for each component
     */
    private String customDimension1;
    private String customDimension2;

    /**
     * Get a new instance of TrackerManager w/ the GA config file location set
     * @param application Application context
     * @param analyticsConfigLocation location of google analytics google-services.json file
     */
    public static void init(Application application, @XmlRes int analyticsConfigLocation) {
        instance = new TrackerManager(application, analyticsConfigLocation);
    }

    private TrackerManager(Application application, int analyticsConfigLocation) {
        resources = application.getResources();
        mGaConfigLocation = analyticsConfigLocation;
        mTracker = getDefaultTracker(application);
    }

    public static TrackerManager getInstance() {
        return instance;
    }

    synchronized private Tracker getDefaultTracker(Context context) {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            mTracker = analytics.newTracker(mGaConfigLocation);
        }
        return mTracker;
    }

    public void setCustomDimension1(String customDimension1) {
        this.customDimension1 = customDimension1;
    }

    public void setCustomDimension2(String customDimension2) {
        this.customDimension2 = customDimension2;
    }

    /**
     * Send a screen view event to Google Analytics
     * This will also set the screen name for user event category titles
     * @param screenName name of screen to send
     */
    public void sendScreenView(String screenName) {
        currentScreenName = screenName;
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * Send a user event to Google Analytics
     * Optionally, send a custom dimension
     * See @link https://support.google.com/analytics/answer/1033068?hl=en
     * @param gaEvent analytics event object with category, action, label, custom dimension, etc.
     * @param prependScreenName true if we should prepend the current screen name to the event category
     */
    public void sendUserEvent(GaEvent gaEvent, boolean prependScreenName) {
        String eventCategory = gaEvent.getCategory();
        if (prependScreenName) {
            eventCategory = prependScreenName(eventCategory);
        }

        // If user has explicitly set the custom dimension for the tracker manager
        // and the component didn't have an explicit custom dimension (from the resource layout), then set it here
        // This will always prefer the individual control's set custom dimension over a globally set dimension via setCustomDimension1()
        if (!TextUtils.isEmpty(customDimension1) && TextUtils.isEmpty(gaEvent.getCustomDimension1())) {
            gaEvent.setCustomDimension1(customDimension1);
        }
        if (!TextUtils.isEmpty(customDimension2) && TextUtils.isEmpty(gaEvent.getCustomDimension2())) {
            gaEvent.setCustomDimension2(customDimension2);
        }

        Map<String, String> eventMap = buildUserEvent(eventCategory, gaEvent.getAction(),
                gaEvent.getLabel(), gaEvent.getCustomDimension1(), gaEvent.getCustomDimension2()).build();
        if (BuildConfig.DEBUG) {
            // To enable better debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            String events = "";
            for (String eventValue : eventMap.values()) {
                events += " " + eventValue;
            }
            Log.d(TAG, "buildUserEvent:" + events);
        }
        mTracker.send(eventMap);
    }

    /**
     * Prepend the current screen's name to the user event category
     * @param gaEventCategory event category
     * @return prepended screen name + category
     */
    private String prependScreenName(String gaEventCategory) {
        return currentScreenName + " " + gaEventCategory;
    }

    /**
     * Will create an event builder based on the passed variables.
     * @param category - category of event
     * @param action - action of event
     * @param label - label of event
     * @param customDimension1 - custom dimension of event
     * @param customDimension2 - custom dimension of event
     * @return - the created Event Builder for analytics
     */
    private static HitBuilders.EventBuilder buildUserEvent(String category, String action, String label,
                                                           @Nullable String customDimension1, @Nullable String customDimension2) {

        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .setValue(1);

        // Set custom dimensions
        if (!TextUtils.isEmpty(customDimension1)) {
            eventBuilder.setCustomDimension(1, customDimension1);
        }
        if (!TextUtils.isEmpty(customDimension2)) {
            eventBuilder.setCustomDimension(2, customDimension2);
        }

        return eventBuilder;
    }

    ///////////////////////////////////////////////////////////////////////////
    // AUTOMATIC CUSTOM USER EVENTS
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Send a context menu click (i.e., overflow menu button click)
     * @param customDimension - custom dimension of event
     */
    public void sendContextMenuClick(String customDimension) {
        sendUserEvent(new GaEvent(resources.getString(R.string.ga_wrapper_category_overflow_menu_button),
                resources.getString(R.string.ga_wrapper_action_button_click),
                resources.getString(R.string.ga_wrapper_label_overflow_menu),
                customDimension), false);
    }

    /**
     * Send a context menu item click (i.e., overflow menu item click)
     * @param label - label of event
     * @param customDimension - custom dimension of event
     */
    public void sendContextMenuItemClick(String label, String customDimension) {
        sendUserEvent(new GaEvent(resources.getString(R.string.ga_wrapper_category_overflow_menu_item),
                resources.getString(R.string.ga_wrapper_action_overflow_menu_item_select),
                label, customDimension), false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // MANUALLY-HANDLED CUSTOM USER EVENTS
    // The following events must be called explicitly within your own code
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Send a navigation drawer click (i.e., navigation drawer button click which opens a NavigationView)
     * @param customDimension - custom dimension of event
     * @param prependScreenName true if we should prepend the current screen name to the event category
     */
    public void sendNavigationDrawerClick(String customDimension, boolean prependScreenName) {
        sendUserEvent(new GaEvent(resources.getString(R.string.ga_wrapper_category_nav_drawer_button),
                resources.getString(R.string.ga_wrapper_action_nav_drawer_open),
                resources.getString(R.string.ga_wrapper_label_nav_drawer),
                customDimension), prependScreenName);
    }

    /**
     * Send a cnavigation drawer item click (i.e., navigation drawer item click, while nav drawer is open)
     * @param label - label of event
     * @param customDimension - custom dimension of event
     * @param prependScreenName true if we should prepend the current screen name to the event category
     */
    public void sendNavigationDrawerItemClick(String label, String customDimension, boolean prependScreenName) {
        sendUserEvent(new GaEvent(resources.getString(R.string.ga_wrapper_category_nav_drawer_item),
                resources.getString(R.string.ga_wrapper_action_nav_drawer_item_select),
                label, customDimension), prependScreenName);
    }
}
