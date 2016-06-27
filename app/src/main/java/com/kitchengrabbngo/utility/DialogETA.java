package com.kitchengrabbngo.utility;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kitchengrabbngo.R;
import com.kitchengrabbngo.ativities.MapsActivity;
import com.kitchengrabbngo.gson.OrderParent;
import com.woosim.bt.WoosimPrinter;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandeep on 12-May-16.
 */
public class DialogETA extends Dialog implements View.OnClickListener {
    Context cntxt;
    int order_id, consumer_id, order_eta, postion, tokenId;
    String order_Details,status_Id;
    public int getorder_eta;
    MediaPlayer mediaPlayer;


    //  public DialogETA(Context context,int orderid, int consumerid,String orderDetails) {
    public DialogETA(Context context, int orderid, int consumerid, String orderDetails, int pos, int token_Id, int eta,String statusId) {
        super(context);
        cntxt = context;
        order_id = orderid;
        consumer_id = consumerid;
        tokenId = token_Id;
        order_Details = orderDetails;
        order_eta = eta;
        postion = pos;
        status_Id =statusId;

    }

    TextView txtVwTime1,txtVwReject, txtVwTime2, txtVwTime3, txtVwTime4, txtVwCheckTime, txVwOrderDetails, txtVwTokenId, txtVwOrderId;
    TextView btnAccept;
    Button btnComplete,btnPrint;

    // printer
    private final static String EUC_KR = "EUC-KR";
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothAdapter mBtAdapter;
    private WoosimPrinter woosim;
    private String address;
    //card data buffer
    private byte[] cardData;
    private byte[] extractdata = new byte[300];
    // add min to date
    static final long ONE_MINUTE_IN_MILLIS = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_pop_up_layout);

        woosim = new WoosimPrinter();
        woosim.setHandle(acthandler);

        PercentRelativeLayout percentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.relativePercentRelative2);
        txtVwReject= (TextView) findViewById(R.id.txtVwReject);
        txtVwTokenId = (TextView) findViewById(R.id.txtVwTokenId);
        txtVwOrderId = (TextView) findViewById(R.id.txtVwOrderId);
        txtVwTime1 = (TextView) findViewById(R.id.txtVwtime1);
        txtVwTime2 = (TextView) findViewById(R.id.txtVwtime2);
        txtVwTime3 = (TextView) findViewById(R.id.txtVwtime3);
        txtVwTime4 = (TextView) findViewById(R.id.txtVwtime4);
        txtVwCheckTime = (TextView) findViewById(R.id.txtVwCheckTime);
        txVwOrderDetails = (TextView) findViewById(R.id.txVwOrderDetails);

        btnPrint = (Button) findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(this);
        txtVwReject.setOnClickListener(this);
        btnComplete = (Button) findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(this);

        txVwOrderDetails.setText(Html.fromHtml(order_Details));
        txtVwOrderId.setText("OrderId: " + order_id + "");
        txtVwTokenId.setText("TokenId: " + tokenId + "");
        txVwOrderDetails.setMovementMethod(new ScrollingMovementMethod());

       if (order_eta == 0) {
           percentRelativeLayout.setVisibility(View.VISIBLE);
           btnComplete.setVisibility(View.GONE);
       }
        else
            percentRelativeLayout.setVisibility(View.GONE);
        if(status_Id.equalsIgnoreCase("1"))
        {
            btnComplete.setEnabled(true);
            btnComplete.setBackgroundColor(Color.YELLOW);

        }
        else if(status_Id.equalsIgnoreCase("3"))
        {
            btnComplete.setEnabled(true);
            btnComplete.setText("Completed");
            btnComplete.setBackgroundColor(Color.YELLOW);
        }
        else if(status_Id.equalsIgnoreCase("4"))
        {
            btnComplete.setEnabled(true);
            btnComplete.setText("Picked Up");
            btnComplete.setBackgroundColor(Color.YELLOW);
        }
        else if(status_Id.equalsIgnoreCase("5"))
        {
            btnComplete.setVisibility(View.GONE);

        }

                    txtVwTime1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (txtVwTime1.isSelected())

                    {
                        txtVwTime1.setSelected(false);


                        txtVwTime2.setTextColor(Color.RED);
                        txtVwTime3.setTextColor(Color.RED);
                        txtVwTime4.setTextColor(Color.RED);

                        txtVwTime1.setTextColor(Color.RED);
                    } else {
                        // txtVwTime1.setTextColor(Color.RED);
                        txtVwTime2.setSelected(false);
                        txtVwTime3.setSelected(false);
                        txtVwTime4.setSelected(false);

                        //txtVwTime1.setEnabled(false);

                        txtVwTime2.setTextColor(Color.RED);
                        txtVwTime3.setTextColor(Color.RED);
                        txtVwTime4.setTextColor(Color.RED);

                        txtVwTime1.setSelected(true);
                        txtVwTime1.setTextColor(Color.BLUE);
                    }

                }
            });

        txtVwTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtVwTime2.isSelected())

                {
                    txtVwTime2.setSelected(false);


                    txtVwTime2.setTextColor(Color.RED);
                    txtVwTime3.setTextColor(Color.RED);
                    txtVwTime4.setTextColor(Color.RED);

                    txtVwTime1.setTextColor(Color.RED);
                } else {
                    // txtVwTime1.setTextColor(Color.RED);
                    txtVwTime1.setSelected(false);
                    txtVwTime3.setSelected(false);
                    txtVwTime4.setSelected(false);

                    //txtVwTime1.setEnabled(false);

                    txtVwTime1.setTextColor(Color.RED);
                    txtVwTime3.setTextColor(Color.RED);
                    txtVwTime4.setTextColor(Color.RED);

                    txtVwTime2.setSelected(true);
                    txtVwTime2.setTextColor(Color.BLUE);
                }
            }
        });
        txtVwTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtVwTime3.isSelected())

                {
                    txtVwTime3.setSelected(false);


                    txtVwTime2.setTextColor(Color.RED);
                    txtVwTime3.setTextColor(Color.RED);
                    txtVwTime4.setTextColor(Color.RED);

                    txtVwTime1.setTextColor(Color.RED);
                } else {
                    // txtVwTime1.setTextColor(Color.RED);
                    txtVwTime1.setSelected(false);
                    txtVwTime2.setSelected(false);
                    txtVwTime4.setSelected(false);

                    //txtVwTime1.setEnabled(false);

                    txtVwTime1.setTextColor(Color.RED);
                    txtVwTime2.setTextColor(Color.RED);
                    txtVwTime4.setTextColor(Color.RED);

                    txtVwTime3.setSelected(true);
                    txtVwTime3.setTextColor(Color.BLUE);
                }


            }
        });
        txtVwTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtVwTime4.isSelected())

                {
                    txtVwTime4.setSelected(false);


                    txtVwTime2.setTextColor(Color.RED);
                    txtVwTime3.setTextColor(Color.RED);
                    txtVwTime4.setTextColor(Color.RED);

                    txtVwTime1.setTextColor(Color.RED);
                } else {
                    // txtVwTime1.setTextColor(Color.RED);
                    txtVwTime1.setSelected(false);
                    txtVwTime3.setSelected(false);
                    txtVwTime3.setSelected(false);

                    //txtVwTime1.setEnabled(false);

                    txtVwTime1.setTextColor(Color.RED);
                    txtVwTime2.setTextColor(Color.RED);
                    txtVwTime3.setTextColor(Color.RED);

                    txtVwTime4.setSelected(true);
                    txtVwTime4.setTextColor(Color.BLUE);
                }
            }
        });

        btnAccept = (TextView) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAccept:
                //Print_2inch();
                if (txtVwTime1.isSelected() || txtVwTime2.isSelected() || txtVwTime3.isSelected() || txtVwTime4.isSelected()) {
                    txtVwTime1.setError(null);
//                    mediaPlayer.stop();
                    if (txtVwTime1.isSelected()) {
                        order_eta = 5;
                    } else if (txtVwTime2.isSelected()) {
                        order_eta = 10;

                    } else if (txtVwTime3.isSelected()) {
                        order_eta = 15;
                    } else if (txtVwTime4.isSelected()) {
                        order_eta = 20;
                    }
                    if(status_Id.equalsIgnoreCase("1"))
                    {
                        status_Id="2";


                    }

                    ((MapsActivity) cntxt).setStatus(consumer_id+"",status_Id,order_eta+"");
                    try {
                        JSONObject props = new JSONObject();
                        props.put("OrderId", order_id);
                        props.put("TokenId", tokenId);
                       // props.put("Details", order_Details);
                        props.put("Accepted", true);
                        MyApp.mixpanel.track("OrderArrived", props);
                    } catch (JSONException e) {
                        Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                    }
                    orderStatusUpdate();

                } else {
                    //txtVwTime1.setError("Select");

                    txtVwCheckTime.setVisibility(View.VISIBLE);
                }


         /*       int reVal = woosim.BTConnection("00:15:0E:E3:4C:66", false);
                if(reVal== 1){
                    Toast t = Toast.makeText(cntxt,"SUCCESS CONNECTION!",Toast.LENGTH_SHORT);
                    t.show();
                }else if(reVal== -2){
                    Toast t = Toast.makeText(cntxt,"NOT CONNECTED",Toast.LENGTH_SHORT);
                    t.show();
                }else if(reVal== -5){
                    Toast t = Toast.makeText(cntxt,"DEVICE IS NOT BONDED",Toast.LENGTH_SHORT);
                    t.show();
                }else if(reVal== -6){
                    Toast t = Toast.makeText(cntxt,"ALREADY CONNECTED",Toast.LENGTH_SHORT);
                    t.show();
                }else if(reVal== -8){
                    Toast t = Toast.makeText(cntxt,"Please enable your Bluetooth and re-run this program!",Toast.LENGTH_LONG);
                    t.show();
                }else{
                    Toast t = Toast.makeText(cntxt,"ELSE",Toast.LENGTH_SHORT);
                    t.show();
                }
                Print_2inch();*/
                break;
            case R.id.btnComplete:
                //setorderStatus();
                //order_eta=e;

                if(status_Id.equalsIgnoreCase("1"))
                {
                    status_Id="2";


                }else  if(status_Id.equalsIgnoreCase("3"))
                {
                    status_Id="4";
                    try {
                        JSONObject props = new JSONObject();
                        props.put("OrderId", order_id);
                        //props.put("TokenId", tokenId);
                        // props.put("Details", order_Details)
                        MyApp.mixpanel.track("PreparationCompleted", props);
                    } catch (JSONException e) {
                        Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                    }

                }
                else  if(status_Id.equalsIgnoreCase("4"))
                {
                    status_Id="5";
                    try {
                        JSONObject props = new JSONObject();
                        props.put("OrderId", order_id);
                        //props.put("TokenId", tokenId);
                        // props.put("Details", order_Details)
                        MyApp.mixpanel.track("PickedUp", props);
                    } catch (JSONException e) {
                        Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                    }
                }

                ((MapsActivity) cntxt).setStatus(consumer_id+"",status_Id,order_eta+"");
                orderStatusUpdate();
                break;
            case R.id.txtVwReject:
                //setorderStatus();
                //order_eta=e;
                if(status_Id.equalsIgnoreCase("1"))
                {
                    status_Id="6";


                }

                ((MapsActivity) cntxt).setStatus(consumer_id+"",status_Id,order_eta+"");
                try {
                    JSONObject props = new JSONObject();
                    props.put("OrderId", order_id);
                    props.put("TokenId", tokenId);
                   // props.put("Details", order_Details);
                    props.put("Accepted", false);
                    MyApp.mixpanel.track("OrderArrived", props);
                } catch (JSONException e) {
                    Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                }
                orderStatusUpdate();
                break;
            case R.id.btnPrint:
                //setorderStatus();
                //order_eta=e;
                printer();
                break;

                //

        }
    }


    private void setOrderETA() {


       // String url = MyApp.url + "sendPusherConsumerETA.php?order_id=" + order_id + "&order_eta_min=" + order_eta + "&consumer_id=" + consumer_id;

        String url = MyApp.url + "order_status_update.php?order_id=" + order_id + "&order_eta_min=" + order_eta + "&consumer_id=" + consumer_id;
        Log.wtf("url", url);
        JsonObjectRequest req = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(cntxt, "Sucess", Toast.LENGTH_LONG).show();


                // int ss= ((Main) cntxt).orderParent.Orders.OrderArray.get(postion).Order_ETA;

                SharedPreferences sharedPreferences = MyApp.sharedPreferences;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("order_eta");
                editor.remove("post");
                editor.commit();
                editor.putInt("checkETA", 1);
                editor.putInt("post", postion);
                editor.putInt("order_eta", order_eta);
                editor.commit();
                //((Main) cntxt).handleMediaPlayer(false);
                dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cntxt, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        MyApp.reqstQ.add(req).setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS + 1500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void setorderStatus() {
        String url = MyApp.url+"order_status_update.php?order_id="+order_id+"&status=4&consumer_id="+consumer_id;
        Log.wtf("url",url);
        JsonObjectRequest req = new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                SharedPreferences sharedPreferences = MyApp.sharedPreferences;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("status");
                editor.commit();
                editor.putInt("status", 4);
                editor.commit();

                    Toast.makeText(cntxt,"Sucess",Toast.LENGTH_LONG).show();
                dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cntxt,"Poor Internet Connection",Toast.LENGTH_LONG).show();
            }
        });
        MyApp.reqstQ.add(req);
    }


    public  void orderStatusUpdate()
    {
        //MyApp.url + "order_status_update.php?order_id=" + order_id + "&order_eta_min=" + order_eta + "&consumer_id=" + consumer_id;

        final String URL = MyApp.url + "order_status_update.php";
// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("order_id", order_id+"");
        if(status_Id.equalsIgnoreCase("6") ) {
            order_eta=-1;
            params.put("order_eta_min", order_eta + "");
        }
        else {
            params.put("order_eta_min", order_eta + "");
        }
        params.put("consumer_id", consumer_id+"");


        params.put("status", status_Id);
        params.put("store_id", MyApp.sharedPreferences.getInt("PID",1)+"");
        Log.wtf("json", new JSONObject(params) +"");
        Log.wtf("json",URL +"");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.wtf("json", response +"\n"+order_id+order_eta+status_Id);


                          /*
                            SharedPreferences sharedPreferences = MyApp.sharedPreferences;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("order_eta");
                            editor.remove("post");
                            editor.commit();
                            editor.putInt("checkETA", 1);
                            editor.putInt("post", postion);
                            editor.putInt("status_Id", Integer.parseInt(status_Id));
                            editor.putInt("order_eta", order_eta);
                            editor.commit();*/
                            ((MapsActivity) cntxt).setETAAndStatus(order_id,order_eta,status_Id);
                            dismiss();
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.wtf("err eta",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Log.wtf("etime eta",error.toString());
            }
        });

// add the request object to the queue to be executed
        //ApplicationController.getInstance().addToRequestQueue(req);
        MyApp.reqstQ.add(req).setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS + 2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void printer() {

        int reVal = woosim.BTConnection("00:15:0E:E3:4C:66", false);

        Log.wtf("print", reVal+"");

        Log.wtf("intial", reVal + "");
        if (reVal == 1) {
          /*  Toast t = Toast.makeText(getApplicationContext(), "SUCCESS CONNECTION!", Toast.LENGTH_SHORT);
            t.show();*/
            Log.wtf("1", reVal + "");
            Print_2inch();

            //setOrderETA();
        } else if (reVal == -2) {
           /* Toast t = Toast.makeText(getApplicationContext(), "NOT CONNECTED", Toast.LENGTH_SHORT);
            t.show();*/
            // need to comment
            // setOrderETA();

            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
            printer();

            Log.wtf("-2", reVal + "");
        } else if (reVal == -5) {
          /*  Toast t = Toast.makeText(getApplicationContext(), "DEVICE IS NOT BONDED", Toast.LENGTH_SHORT);
            t.show();*/
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }

            printer();
            Log.wtf("-5", reVal + "");
        } else if (reVal == -6) {

         /*   Toast t = Toast.makeText(getApplicationContext(), "ALREADY CONNECTED", Toast.LENGTH_SHORT);
            t.show();*/

            Log.wtf("-6", reVal + "");
            Print_2inch();
            //}
            //  setOrderETA();
        } else if (reVal == -8) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }

            printer();

            Log.wtf("8", reVal + "");
           /* Toast t = Toast.makeText(getApplicationContext(), "Please enable your Bluetooth and re-run this program!", Toast.LENGTH_LONG);
            t.show();*/
        } else {
            Log.wtf("else", reVal + "");
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
            printer();

           /* Toast t = Toast.makeText(getApplicationContext(), "ELSE", Toast.LENGTH_SHORT);
            t.show();*/
        }
    }
    private void Print_2inch() {

        byte[] init = {0x1a, '@'};
        woosim.controlCommand(init, init.length);

        String regData = "";

        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
        Date dt = new Date();
        regData = dateFormat.format(dt);*/


        woosim.saveSpool(EUC_KR, " Grabb-n-Go\r\n", 0x11, true);
        woosim.saveSpool(EUC_KR, " Kitchen Receipt\r\n", 0, false);



        woosim.saveSpool(EUC_KR, "Token Id: " + tokenId + "\r\n", 0x11, true);

        // woosim.saveSpool(EUC_KR, "Order Id: " + getOrderId + "\r\n", 0x11, true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm:ss a");
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date afterAddingTenMins = new Date(t + (order_eta * ONE_MINUTE_IN_MILLIS));
        Log.wtf("time", dateFormat.format(afterAddingTenMins) + "");
        regData = dateFormat.format(afterAddingTenMins) + "";
        woosim.saveSpool(EUC_KR, "Ready By: " + regData + "\r\n", 0, false);
        //woosim.saveSpool(EUC_KR, ""+regData+"\r\n", 0, false);
        Log.wtf("true", Html.fromHtml(order_Details) + "");
        woosim.saveSpool(EUC_KR, Html.fromHtml(order_Details) + "\r\n", 0, false);

        woosim.saveSpool(EUC_KR, "--------------------------------\r\n", 0, false);

        byte[] lf = {0x0a};
        woosim.controlCommand(lf, lf.length);
        woosim.saveSpool(EUC_KR, "Thank you!\r\n\r\n\r\n", 0, true);

        woosim.controlCommand(lf, lf.length);
        byte[] ff = {0x0a};
        woosim.controlCommand(ff, 1);
        woosim.printSpool(true);
        cardData = null;
    }
    public Handler acthandler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == 0x01) {
                Log.e("+++Activity+++", "******0x01");
                Object obj1 = msg.obj;
                cardData = (byte[]) obj1;
                // ToastMessage();
            } else if (msg.what == 0x02) {
                //ardData[msg.arg1] = (byte) msg.arg2;
                Log.e("+++Activity+++", "MSRFAIL: [" + msg.arg1 + "]: ");
            } else if (msg.what == 0x03) {
                Log.e("+++Activity+++", "******EOT");
            } else if (msg.what == 0x04) {
                Log.e("+++Activity+++", "******ETX");
            } else if (msg.what == 0x05) {
                Log.e("+++Activity+++", "******NACK");
            }
        }
    };
}
