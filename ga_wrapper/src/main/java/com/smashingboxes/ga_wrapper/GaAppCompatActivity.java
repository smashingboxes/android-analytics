package com.smashingboxes.ga_wrapper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Tyler McCraw on 5/19/16.
 *
 * Abstract appcompat activity for enforcing GA setup for screen view events
 */
public abstract class GaAppCompatActivity extends AppCompatActivity implements IGaActivity {

    private TrackerManager mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTracker = TrackerManager.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            TrackerManager.getInstance().sendScreenView(getScreenName());
        } catch (UnsetScreenException e) {
            e.printStackTrace();
        }
    }

    //TODO need to define a configuration to better handle auto submission of these events
//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        getGaTracker().sendContextMenuClick("");
//        return super.onMenuOpened(featureId, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        getGaTracker().sendContextMenuItemClick(item.getTitle().toString(), "");
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * Get the screen name of the activity
     * @return the name of the screen to be reported to GA for a screen view event
     * @throws UnsetScreenException if screen view is not set by overriding this method
     */
    @Override
    public abstract String getScreenName() throws UnsetScreenException;

    @Override
    public TrackerManager getGaTracker() {
        return mTracker;
    }

    @Override
    public void setCustomDimension1(String customDimension1) {
        getGaTracker().setCustomDimension1(customDimension1);
    }

    @Override
    public void setCustomDimension2(String customDimension2) {
        getGaTracker().setCustomDimension2(customDimension2);
    }
}
