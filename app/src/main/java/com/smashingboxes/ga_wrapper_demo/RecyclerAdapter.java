package com.smashingboxes.ga_wrapper_demo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Integer> mDataset;
    protected List<Integer> viewsForPositions = new ArrayList<>();

    private OnRecyclerInteractionListener mListener;

    public interface OnRecyclerInteractionListener {
        void onRecyclerInteraction(int viewType, View viewClicked);
    }

    public RecyclerAdapter(Context context, ArrayList<Integer> myDataset, OnRecyclerInteractionListener recyclerListener) {
        this.mContext = context;
        this.mDataset = myDataset;
        this.mListener = recyclerListener;
        setUpDisplayedViewsList();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        switch (viewType) {
            case R.layout.card_bottom_sheet:
            case R.layout.card_dialog:
            case R.layout.card_contextual_action:
            case R.layout.card_date_picker:
            case R.layout.card_time_picker:
            case R.layout.card_snackbar:
                View.OnClickListener clickListener = null;
                if (mListener != null) {
                    clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onRecyclerInteraction(viewType, v);
                        }
                    };
                }
                return new ViewHolderWithCallback(rootView, clickListener);
            default:
                return new ViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        int viewType = getItemViewType(position);
//        switch (viewType) {
//            case R.layout.card_contextual_action:
//                break;
//            case R.layout.card_date_picker:
//                break;
//            case R.layout.card_time_picker:
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewsForPositions.get(position);
    }

    protected void setUpDisplayedViewsList() {
        for (Integer layout : mDataset) {
            viewsForPositions.add(layout);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public RelativeLayout mRelLayout;

        public ViewHolder(View view) {
            super(view);
            mCardView = (CardView) itemView.findViewById(R.id.demo_card_view);
            mTextView = (TextView) itemView.findViewById(R.id.card_header_text);
            mRelLayout = (RelativeLayout) itemView.findViewById(R.id.demo_card_view_layout);
        }
    }

    public static class ViewHolderWithCallback extends ViewHolder{
        public Button callbackButton;

        public ViewHolderWithCallback(View view, View.OnClickListener clickListener) {
            super(view);
            callbackButton = (Button) itemView.findViewById(R.id.callback_button);
            callbackButton.setOnClickListener(clickListener);
        }
    }
}