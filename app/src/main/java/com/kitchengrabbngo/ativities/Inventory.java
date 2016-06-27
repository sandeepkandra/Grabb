package com.kitchengrabbngo.ativities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kitchengrabbngo.R;
import com.kitchengrabbngo.adapter.GridViewAdapter;
import com.kitchengrabbngo.models.Products;
import com.kitchengrabbngo.utility.MyApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inventory extends Activity implements View.OnClickListener {

    GridView gridView;
    ProgressDialog progressDialog;
    ArrayList<Products> productconsumerArray = new ArrayList<Products>();
    ;
    GridViewAdapter gridViewAdapter;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventry);
        gridView = (GridView) findViewById(R.id.gridview);
        imageViewBack = (ImageView) findViewById(R.id.imgVwBack);
        imageViewBack.setOnClickListener(this);
        progressDialog = ProgressDialog.show(Inventory.this, "Loading....",
                "Please wait", true);
        progressDialog.dismiss();
        getStoreMenu();
    }

    public void getStoreMenu() {
        progressDialog.show();

       String url = MyApp.url + "get_store_menu.php?store_id=" + MyApp.sharedPreferences.getInt("PID", 1)+"&merchant_id="+MyApp.sharedPreferences.getString("MID", "1");
        Log.e("url", url);
        JsonObjectRequest req = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jj = new JSONObject(response + "");
                    JSONArray ss = jj.getJSONArray("store_menu");
                    progressDialog.dismiss();
                    //JSONObject oo=ss.getJSONObject(0);

                    if (ss.length() > 0) {

                        for (int i = 0; i < ss.length(); i++) {
                            JSONObject oo = ss.getJSONObject(i);

                            String store_products_id = oo.getString("store_products_id");

                            int product_id = oo.getInt("product_id");
                            //String merchant_password =oo.getString("");
                            String product_name = oo.getString("product_name");
                            String product_image = oo.getString("product_image");
                            String stock_check = oo.getString("stock_check");

                            productconsumerArray.add(new Products(product_id, product_name, store_products_id, stock_check, product_image));


                        }

                        gridViewAdapter = new GridViewAdapter(Inventory.this, R.layout.grid_item, productconsumerArray);

                        gridView.setAdapter(gridViewAdapter);

                    }


                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.wtf("err", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Poor Internet Connection", Toast.LENGTH_LONG).show();
                Log.wtf("err", error.toString());
                // getOrders();
            }
        });
        MyApp.reqstQ.add(req);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Inventory.this, MapsActivity.class);
        startActivity(intent);
        Inventory.this.finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgVwBack:
                Intent intent = new Intent(Inventory.this, SettingActivity.class);
                startActivity(intent);
                Inventory.this.finish();
                break;

        }
    }
}
