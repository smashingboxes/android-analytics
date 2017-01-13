package com.smashingboxes.ga_wrapper;

/**
 * Created by Tyler McCraw on 5/19/16.
 *
 * Interface for enforcing GA setup for screen view events
 */
public interface IGaActivity {

    /**
     * Get the screen name of the activity
     * @return the name of the screen to be reported to GA for a screen view event
     * @throws UnsetScreenException if screen view is not set by overriding this method
     */
    String getScreenName() throws UnsetScreenException;

    /**
     * Get the TrackerManager. This should only be used if you need to send
     * explicitly defined custom GA events within your activity/fragment
     * @return TrackerManager - GA Manager object
     */
    TrackerManager getGaTracker();

    /**
     * Set a custom dimension for user events
     * @param customDimension1 first custom dimension
     */
    void setCustomDimension1(String customDimension1);

    /**
     * Set a custom dimension for user events
     * @param customDimension2 second custom dimension
     */
    void setCustomDimension2(String customDimension2);
}