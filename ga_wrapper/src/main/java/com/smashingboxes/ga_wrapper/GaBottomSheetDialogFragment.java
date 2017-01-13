package com.smashingboxes.ga_wrapper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * Created by Tyler McCraw on 1/6/17.
 */

public class GaBottomSheetDialogFragment extends BottomSheetDialogFragment {

//    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
//        @Override
//        public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                dismiss();
//            }
//
//            track event
//
//        }
//
//        @Override
//        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//        }
//    };
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
//        CoordinatorLayout.Behavior behavior = params.getBehavior();
//
//        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
//            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//        }
//        return super.onCreateDialog(savedInstanceState);
//    }
}
