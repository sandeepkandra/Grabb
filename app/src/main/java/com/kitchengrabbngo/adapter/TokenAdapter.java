package com.kitchengrabbngo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.kitchengrabbngo.R;
import com.kitchengrabbngo.ativities.MapsActivity;
import com.kitchengrabbngo.gson.OrderParent;
import com.kitchengrabbngo.utility.DialogETA;
import com.kitchengrabbngo.utility.MyApp;

import java.util.ArrayList;


/**
 * Created by sandy on 1/23/2016.
 */
public class TokenAdapter extends
        RecyclerView.Adapter<TokenAdapter.ViewHolder> {

    Context mctxt;
    private static int focusedItem = 0;
    private static int lastCheckedPos = 0;
    private String mItem;


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_vw_tokens, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get the data model based on position
        //  Tokens contact = mContacts.get(position);
        final OrderParent.OrderArrayDetails data = mkit.get(position);


        // Set item views based on the data model


        holder.txtVwTokenNo.setText("Order: " + data.Order_ID + "");

        holder.txtVwTokenId.setText("Token: " + data.Token + "");
         if (data.Status_id.equalsIgnoreCase("3"))

        {
            holder.parentTokens.setBackgroundColor(Color.WHITE);
            holder.relativePercentRelative1.setVisibility(View.VISIBLE);
            holder.txVwOrderDetails.setVisibility(View.VISIBLE);
            holder.btnPopUP.setVisibility(View.VISIBLE);
            holder.imageViewProgress.setVisibility(View.GONE);

        }
         else if (data.Status_id.equalsIgnoreCase("4"))

        {
            holder.parentTokens.setBackgroundColor(Color.BLUE);
            holder.relativePercentRelative1.setVisibility(View.VISIBLE);
            holder.txVwOrderDetails.setVisibility(View.VISIBLE);
            holder.btnPopUP.setVisibility(View.VISIBLE);
            holder.imageViewProgress.setVisibility(View.GONE);
        }
        else if (data.Status_id.equalsIgnoreCase("5"))

        {
            holder.parentTokens.setBackgroundColor(Color.LTGRAY);

            holder.relativePercentRelative1.setVisibility(View.VISIBLE);
            holder.txVwOrderDetails.setVisibility(View.VISIBLE);
            holder.btnPopUP.setVisibility(View.VISIBLE);
            holder.imageViewProgress.setVisibility(View.GONE);

        }
        else if(data.Status_id.equalsIgnoreCase("6"))
        {
            try {
                //holder.parentTokens.setVisibility(View.GONE);
                delete(position);
            }catch (Exception e)
            {
                Log.wtf("errremove",e.toString());
            }
            //notifyItemRemoved(position);
          /*  data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, data.size());*/

        }
        else if (data.Status_id.equalsIgnoreCase("2")) {
             Bitmap bmp;
             holder.parentTokens.setBackgroundColor(Color.WHITE);
             holder.relativePercentRelative1.setVisibility(View.VISIBLE);
             holder.txVwOrderDetails.setVisibility(View.GONE);
             holder.btnPopUP.setVisibility(View.GONE);
             holder.imageViewProgress.setVisibility(View.VISIBLE);
           /* BitmapDrawable abmp = (BitmapDrawable) holder.imageViewProgress.getDrawable();
//second parametre is radius
            bmp = abmp.getBitmap();
            Bitmap blurred = blurRenderScript(mctxt, bmp, 25);
            holder.imageViewProgress.setImageBitmap(blurred);*/


         }
        //holder.parentTokens.setBackgroundResource(data.colorIsGreen ? R.drawable.backgroundgreen : R.drawable.backgroundgreengray);
        // holder.txtVwTokenId.setBackgroundResource(data.colorIsGreen ? R.drawable.backgroundgreen : R.drawable.backgroundgreengray);
        // holder.txtVwTokenNo.setBackgroundResource(data.colorIsGreen ? R.drawable.backgroundgreen : R.drawable.backgroundgreengray);

        String fVal1 = "";
        for (int i = 0; i < data.Products.size(); i++) {
            String output = "";

            output = "<b>" + "(" + data.Products.get(i).Quantity + ") " + data.Products.get(i).Product_Name + "" + data.Products.get(i).Quantity + "</b> <br /> " + "\n-----------------------\n";

            String customized = "";
            for (int j = 0; j < data.Products.get(i).Customization.size(); j++) {

                customized += data.Products.get(i).Customization.get(j).category + ": " + data.Products.get(i).Customization.get(j).category_value + "<br /> ";

            }
            fVal1 += "<br /> " + output + "<br /> " + customized;


        }

        holder.txVwOrderDetails.setText(Html.fromHtml(fVal1));
        holder.txVwOrderDetails.setMovementMethod(new ScrollingMovementMethod());
        //  holder.parentTokens.setBackgroundColor(Color.parseColor());
       /* if(data.Order_ETA ==0)
        {
            String fVal = "";
            for (int i = 0; i < data.Products.size(); i++) {
                String output = "";

                output = "<b>" + "(" + data.Products.get(i).Quantity + ") " + data.Products.get(i).Product_Name + "" + data.Products.get(i).Quantity + "</b> <br /> " + "\n----------------------------------------------------\n";

                String customized = "";
                for (int j = 0; j < data.Products.get(i).Customization.size(); j++) {

                    customized += data.Products.get(i).Customization.get(j).category + ": " + data.Products.get(i).Customization.get(j).category_value + "<br /> ";

                }
                fVal += "<br /> " + output + "<br /> " + customized;


            }


            final DialogETA dialogETA = new DialogETA(mctxt, Integer.parseInt(data.Order_ID + ""), data.consumerid, fVal, position,data.Token,data.Order_ETA);
            dialogETA.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    data.Order_ETA = MyApp.sharedPreferences.getInt("order_eta", 0);
                notifyDataSetChanged();
                    ((MapsActivity) mctxt).handleMediaPlayer(false);

                }
            });
            //dialogETA.setCanceledOnTouchOutside(false);
            if( data.Order_ETA == 0)
                dialogETA.setCanceledOnTouchOutside(false);
            else
                dialogETA.setCanceledOnTouchOutside(true);
            dialogETA.show();


        }*/

        holder.btnPopUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fVal = "";
                for (int i = 0; i < data.Products.size(); i++) {
                    String output = "";

                    output = "<b>" + "(" + data.Products.get(i).Quantity + ") " + data.Products.get(i).Product_Name + "" + "</b> <br /> " ;

                    String customized = "";
                    for (int j = 0; j < data.Products.get(i).Customization.size(); j++) {

                        customized += data.Products.get(i).Customization.get(j).category + ": " + data.Products.get(i).Customization.get(j).category_value + "<br /> ";

                    }
                    if (customized.equalsIgnoreCase(""))
                        //fVal += "<br /> " + output + "<br /> " + customized;
                        fVal += output + "<br /> " + customized;
                    else
                        fVal += output + "<br /> " + "\n--------------------------------\n" + "<br /> " + customized;


                }


                final DialogETA dialogETA = new DialogETA(mctxt, Integer.parseInt(data.Order_ID + ""), data.consumerid, fVal, position, data.Token, data.Order_ETA, data.Status_id);
                dialogETA.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        //data.Order_ETA = MyApp.sharedPreferences.getInt("order_eta", 0);
                        //data.Order_ETA = MyApp.sharedPreferences.getInt("status_Id", );

                        notifyDataSetChanged();
//                        ((MapsActivity) mctxt).handleMediaPlayer(false);

                    }
                });
                if (data.Order_ETA == 0)
                    dialogETA.setCanceledOnTouchOutside(false);
                else
                    dialogETA.setCanceledOnTouchOutside(true);
                dialogETA.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mkit.size();
    }

    // Store a member variable for the contacts
    private ArrayList<OrderParent.OrderArrayDetails> mkit;
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
    public TokenAdapter(Context context, int layoutResourceId, ArrayList<OrderParent.OrderArrayDetails> kit) {
        mkit = kit;
        mctxt = context;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtVwTokenNo;
        public TextView txtVwTokenId, txVwOrderDetails;
        PercentRelativeLayout parentTokens, relativePercentRelative1;
        public Button btnPopUP;
        private String mItem;
        public ImageView imageViewProgress;

        public void setItem(String item) {
            mItem = item;
            txtVwTokenId.setText(item);
        }

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);

            //    txtVwTokenNo = (TextView) itemView.findViewById(R.id.txtVwTokenNoHori);
            //    txtVwTokenId = (TextView) itemView.findViewById(R.id.txtVwTokenIdHori);
            //    parentTokens = (LinearLayout) itemView.findViewById(R.id.parentTokens);
            imageViewProgress = (ImageView) itemView.findViewById(R.id.imageViewProgress);
            txVwOrderDetails = (TextView) itemView.findViewById(R.id.txVwOrderDetails);
            txtVwTokenNo = (TextView) itemView.findViewById(R.id.txtVwOrderId);
            txtVwTokenId = (TextView) itemView.findViewById(R.id.txtVwTokenId);
            relativePercentRelative1 = (PercentRelativeLayout) itemView.findViewById(R.id.relativePercentRelative1);

            parentTokens = (PercentRelativeLayout) itemView.findViewById(R.id.relativePercentRelativeParent);
            btnPopUP = (Button) itemView.findViewById(R.id.btnPopUP);

// Handle item click and set the selection
           /* parentTokens.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redraw the old selection and the new
                    notifyItemChanged(focusedItem);
                    focusedItem = getLayoutPosition();
                    notifyItemChanged(focusedItem);


                }
            });*/

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //case R.id.parentTokens:


                //  break;
            }
        }
    }


    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }
    public void delete(final int position) {
        try {

            Handler handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    mkit.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mkit.size());
                }
            };

            handler.post(r);


        }
        catch (Exception e)
        {
            Log.wtf("erritemremove",e.toString());
        }
    }


    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

}
