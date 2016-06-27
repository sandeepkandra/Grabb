package com.kitchengrabbngo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.kitchengrabbngo.R;
import com.kitchengrabbngo.models.Products;
import com.kitchengrabbngo.utility.MyApp;
import com.kitchengrabbngo.utility.PasswordDialog;

import org.json.JSONObject;

import java.util.ArrayList;

public class GridViewAdapter extends BaseSwipeAdapter {

    private Context mContext ;
    // List<Order> mkit;

    private ArrayList<Products> mkit;

String stock_check,store_products_id;

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Products> kit) {
        // mkit = kit;
        this.mContext = mContext;
        this.mkit = kit;


    }




    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        final View v =LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);

        return v;
    }

    @Override
    public void fillValues(final int position, final View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.txtVwProductName);
LinearLayout lnrStock=(LinearLayout)convertView.findViewById(R.id.lnrtrash);
        final ImageView imgVwStock = (ImageView)convertView.findViewById(R.id.imgVwSwipeStock);
        final ImageView imgVwStockAvailable = (ImageView)convertView.findViewById(R.id.imgVwStockAvailable);

        final Products prodcuts = mkit
                .get(position);

            t.setText(prodcuts.get_product_name());
        if(prodcuts.get_stock_check().equalsIgnoreCase("Y"))
        {
            imgVwStock.setBackgroundResource(R.drawable.inactive_product);
            imgVwStockAvailable.setBackgroundResource(R.drawable.active_product);
        }
        else {
            imgVwStock.setBackgroundResource(R.drawable.active_product);

            imgVwStockAvailable.setBackgroundResource(R.drawable.inactive_product);
        }
       /* try {
            Picasso.with(mContext).load(prodcuts.get_product_name()).into(imgVwStock);
        } catch (Exception e) {

        }*/

        convertView.findViewById(R.id.lnrtrash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stock_check=prodcuts.get_stock_check();
                store_products_id=prodcuts.get_store_products_id()+"";

              /*  PasswordDialog passwordDialog =new PasswordDialog(mContext,"YES",stock_check,store_products_id);
                passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
*//*
                        if(stock_check.equalsIgnoreCase("Y"))
                        {
                            stock_check ="N";
                            imgVwStock.setBackgroundResource(R.drawable.inactive_product);
                            imgVwStockAvailable.setBackgroundResource(R.drawable.active_product);
                        }
                        else {
                            stock_check ="Y";
                            imgVwStock.setBackgroundResource(R.drawable.active_product);

                            imgVwStockAvailable.setBackgroundResource(R.drawable.inactive_product);
                        }

                        prodcuts.set_stock_check(stock_check);
                       setProductStatus();*//*
                        notifyDataSetChanged();
                    }
                });
                passwordDialog.show();
*/
            }
        });

      /*  imgVwStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stock_check=prodcuts.get_stock_check();
                store_products_id=prodcuts.get_store_products_id()+"";
                setProductStatus();
            }
        });*/

        //}
    }




    //for search of deals



    @Override
    public int getCount() {
        return mkit.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




}
