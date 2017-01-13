package com.smashingboxes.ga_wrapper_demo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.widget.FrameLayout;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.fragment_bottom_sheet);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }
}