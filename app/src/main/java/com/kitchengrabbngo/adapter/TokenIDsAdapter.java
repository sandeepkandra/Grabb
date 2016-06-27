package com.kitchengrabbngo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kitchengrabbngo.R;
import com.kitchengrabbngo.gson.OrderParent;
import com.kitchengrabbngo.models.Tokens;
import com.kitchengrabbngo.utility.DialogETA;
import com.kitchengrabbngo.utility.MyApp;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sandy on 1/23/2016.
 */
public class TokenIDsAdapter extends
        RecyclerView.Adapter<TokenIDsAdapter.ViewHolder> {

    Context mctxt;
    private static int focusedItem = 0;



    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.tokens_ids_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get the data model based on position
        //  Tokens contact = mContacts.get(position);
        final Tokens data = mkit.get(position);


        // Set item views based on the data model
        //Collections.sort(data);

        holder.txtVwTokenId.setText("" + data.get_token_id() + "");
    }
    @Override
    public int getItemCount() {
        return mkit.size();
    }

    // Store a member variable for the contacts
    private ArrayList<Tokens> mkit;
    // Store a member variable for the contacts


    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);


        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int tryFocusItem = focusedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
            notifyItemChanged(focusedItem);
            focusedItem = tryFocusItem;
            notifyItemChanged(focusedItem);
            lm.scrollToPosition(focusedItem);
            return true;
        }

        return false;
    }


    // Pass in the contact array into the constructor
    public TokenIDsAdapter(Context context, int layoutResourceId, ArrayList<Tokens> kit) {
        mkit = kit;
        mctxt = context;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView txtVwTokenId;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);

               txtVwTokenId = (TextView) itemView.findViewById(R.id.textViewTokensIDs);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //case R.id.parentTokens:


                  //  break;
            }
        }
    }
}
