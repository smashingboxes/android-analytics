package com.smashingboxes.ga_wrapper_demo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainFragment extends Fragment implements RecyclerAdapter.OnRecyclerInteractionListener{

    private static final Integer[] VIEW_TYPES = {
            R.layout.card_bottom_sheet,
            R.layout.card_button,
            R.layout.card_floating_action_button,
            R.layout.card_dialog,
            R.layout.card_floating_context_menu,
            R.layout.card_contextual_action,
            R.layout.card_date_picker,
            R.layout.card_time_picker,
            R.layout.card_selection,
            R.layout.card_seekbar,
            R.layout.card_snackbar,
            R.layout.card_text_field,
            R.layout.card_unhandled };

    private ViewGroup mRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ActionMode mActionMode;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        // Set up screen UI (recycler, FAB, nav drawer, etc.)
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.demo_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(getContext(), new ArrayList<>(Arrays.asList(VIEW_TYPES)), this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.main_fragment_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullData();
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshDrawableState();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return mRootView;
    }

    /**
     * Test retrieving data from a server
     * so that we can send GA events on start and finish of data pull
     */
    private void pullData() {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_item_one:
                return true;
            case R.id.menu_item_two:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onRecyclerInteraction(int viewType, View viewClicked) {
        switch (viewType) {
            case R.layout.card_bottom_sheet:
                openBottomSheet();
                break;
            case R.layout.card_dialog:
                openGenericDialog();
                break;
            case R.layout.card_floating_context_menu:
                // TODO registerForContextMenu(viewClicked);
                break;
            case R.layout.card_contextual_action:
                contextualActionClicked(viewClicked);
                break;
            case R.layout.card_date_picker:
                showDatePickerDialog(viewClicked);
                break;
            case R.layout.card_time_picker:
                showTimePickerDialog(viewClicked);
                break;
            case R.layout.card_snackbar:
                showSnackbar();
            default:
                break;
        }
    }

    private void openBottomSheet() {
        BottomSheetDialogFragment bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    private void openGenericDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setNegativeButton(R.string.component_dialog_negative, null)
                .setPositiveButton(R.string.component_dialog_positive, null)
                .setCancelable(true)
                .setTitle(R.string.component_dialog_title)
                .setSingleChoiceItems(R.array.dialog, 0, null)
                .setOnItemSelectedListener(null);
        builder.show();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_one:
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public void contextualActionClicked(View v) {
        if (mActionMode != null) {
            return;
        }

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = getActivity().startActionMode(mActionModeCallback);
//        v.setSelected(true);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

    private void showSnackbar() {
        Snackbar.make(mRootView, R.string.component_snackbar_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.component_snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { }
                })
                .show();
    }
}
