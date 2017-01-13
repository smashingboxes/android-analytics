# android-analytics
A library which extends Android Google Analytics so that developers can quickly and efficiently add Google Analytics event tracking within their Android application with minimal additional code.

##Why?
Sometimes analytics tracking is considered an afterthought during the development of a product. Developers get to the end of a project and scramble to add in Google Analytics tracking.
This project aims to reduce the time for GA setup, by allowing developers to automatically track basic user events and screen views with minimal code.

###Benefits
  - Can be added into new projects or existing projects
  - Super small library. ~50KB
  - Automatically handles all standard components (Buttons, Selection Controls, Dropdowns, Toggles, etc.) to send basic Google Analytics events with GA categories, actions, labels based on our GA Framework Event Standardization document https://docs.google.com/spreadsheets/d/1JoUbxMsEA5ZFS1AIEXDzJVbU-7BpQpWZOBALBXnydzw
  - For components that the library doesn’t handle automatically, there are helper functions for sending these events within the [TrackerManager](https://github.com/smashingboxes/android-analytics/blob/master/ga_wrapper/src/main/java/com/smashingboxes/ga_wrapper/TrackerManager.java) class
  - If an event isn’t handled automatically and there’s no available helper function for the event, developers still only need to write one line of code to send an event:

        mTracker.sendUserEvent(new GaEvent("Category", "Action", "Label", "Custom Dimension"));

  - Extensible so that developers can add custom categories, actions, labels, AND custom dimensions within the layout's xml (no code required) if the defaults need to be changed.

        <com.smashingboxes.ga_wrapper.GaButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Click me"
            ga_wrapper:category="Button"
            ga_wrapper:action="Button Click"
            ga_wrapper:label="Sign In"
            ga_wrapper:custom_dimension="User1234"/>


## Setup (~5 minutes)
1. Create your project's Google Analytics configuration file here: https://developers.google.com/mobile/add
   and copy the file to your project's `/app` directory

2. Add the latest Google Analytics dependencies by following Google's guide here: https://developers.google.com/analytics/devguides/collection/android/v4/ (currently you only need to add 2 permissions, 1 build.gradle plugin, 1 project-level build.gradle dependency, and 1 app-level build.gradle dependency)

3. Add the GA library AAR file to your `/app/libs` directory (create the libs directory if it doesn't exist) and add the GA library dependency to your app-level build.gradle file:
    `compile 'com.smashingboxes.ga_wrapper:gawrapper:1.0@aar'`

4. Initialize the TrackerManager singleton class in your custom Application class: (Don't forget to set this class in your Manifest!)

        public class MainApplication extends Application {
            @Override
            public void onCreate() {
                super.onCreate();
                initSingletons();
            }
            private void initSingletons() {
                TrackerManager.init(this, R.xml.global_tracker);
            }
        }

        <manifest xmlns:android="http://schemas.android.com/apk/res/android"
            package="com.smashingboxes.ga_wrapper_demo">
            <application
                android:name=".MainApplication"

5. Change/add your activities to extend the `GaAppCompatActivity` or `GaActivity` class rather than the `AppCompatActivity` or `Activity`

6. Change/Add your components to the corresponding GA library components. (e.g.:
Use ` <com.smashingboxes.ga_wrapper.GaButton...` instead of `<Button...`

#### Customization
- If you need to add custom GA Categories, Labels, Actions, or even custom dimensions to your component, you can do so in the layout resource file. Instead of adding the standard GaButton view in the layout file:

        <com.smashingboxes.ga_wrapper.GaButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@string/some_text"/>

  Add the custom attributes to the view, like so:

        <com.smashingboxes.ga_wrapper.GaButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@string/some_text"
            ga_wrapper:category="Button"
            ga_wrapper:action="Button Click"
            ga_wrapper:label="Raised Button"
            ga_wrapper:custom_dimension="User 1234"/>

- If you need to extend what would normally be the AppCompatActivity or Button classes, then simply extend the GaAppCompatActivity or GaButton (etc.) classes instead. This way, you can add your custom functionality to the class and keep automatic analytic reporting!
