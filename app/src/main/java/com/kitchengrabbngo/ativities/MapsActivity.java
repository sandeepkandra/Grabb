package com.kitchengrabbngo.ativities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kitchengrabbngo.R;
import com.kitchengrabbngo.adapter.TokenAdapter;
import com.kitchengrabbngo.adapter.TokenIDsAdapter;
import com.kitchengrabbngo.gson.OrderParent;
import com.kitchengrabbngo.models.Eta;
import com.kitchengrabbngo.models.Products;
import com.kitchengrabbngo.models.Tokens;
import com.kitchengrabbngo.sqlitedb.Printer;
import com.kitchengrabbngo.sqlitedb.PrinterSqlDetails;
import com.kitchengrabbngo.sqlitedb.Token;
import com.kitchengrabbngo.sqlitedb.TokenSqlDetails;
import com.kitchengrabbngo.utility.ConnectionDetector;
import com.kitchengrabbngo.utility.DialogETA;
import com.kitchengrabbngo.utility.DialogSOS;
import com.kitchengrabbngo.utility.MyApp;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.woosim.bt.WoosimPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    RecyclerView recycle_tokens, recycle_tokenIDs;
    TokenAdapter token_adapter;
    TokenIDsAdapter tokenIDsAdapter;
    OrderParent orderParent;
    GsonBuilder gsonBuilder;
    Gson gson1;
    String getOrderId, getOrderIdDelivery;
    static NotificationCompat.Builder builder;
    MediaPlayer mMediaPlayer = null;
    GeoFire geoFire;
    protected PowerManager.WakeLock mWakeLock;
    Handler handler;

    HashMap<String, Marker> hashMap;
    private static final int PERMISSION_REQUEST_CODE = 1;
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

    String order_eta;
    String consumer_id;
    Firebase rootRef;
    String baseurl = "https://blistering-torch-3715.firebaseio.com/usertrack/store";

    ArrayList<Token> tokenArrayList;
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        // alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    Boolean isInternetPresent = false;
    ImageView imageViewSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        handler = new Handler();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        hashMap = new HashMap<String, Marker>();
        PercentRelativeLayout percentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.relativeRecycleView);
        percentRelativeLayout.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        woosim = new WoosimPrinter();
        woosim.setHandle(acthandler);
        tokenArrayList = new ArrayList<Token>();

        imageViewSetting = (ImageView) findViewById(R.id.imgVwSetting);
        imageViewSetting.setOnClickListener(this);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(MapsActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
// check location permission
        if (!checkPermission()) {

            requestPermission();

        } else {

            Snackbar.make(this.findViewById(android.R.id.content), "Permission already granted.", Snackbar.LENGTH_LONG).show();

        }
        // for Order details

        recycle_tokens = (RecyclerView) findViewById(R.id.recycle_token);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
// Control orientation of the items
// also supports LinearLayoutManager.HORIZONTAL
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

// Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
        //  layoutManager.scrollToPositionWithOffset(2, 20);

// Attach layout manager to the RecyclerView
        recycle_tokens.setLayoutManager(layoutManager);


        // for token Number

        recycle_tokenIDs = (RecyclerView) findViewById(R.id.recycle_tokenIDs);
        LinearLayoutManager layoutManager_recycle_tokenIDs = new LinearLayoutManager(this);
// Control orientation of the items
// also supports LinearLayoutManager.HORIZONTAL
        layoutManager_recycle_tokenIDs.setOrientation(LinearLayoutManager.HORIZONTAL);
// Optionally customize the position you want to default scroll to
        layoutManager_recycle_tokenIDs.scrollToPosition(0);
        //  layoutManager.scrollToPositionWithOffset(2, 20);

// Attach layout manager to the RecyclerView
        recycle_tokenIDs.setLayoutManager(layoutManager_recycle_tokenIDs);

       // tokenArrayList.add(new Tokens("",0));
       // recycle_tokenIDs.setAdapter(new TokenIDsAdapter(MapsActivity.this, R.layout.tokens_ids_layout,tokenArrayList));
       // recyclerView.setLayoutManager(new LinearLayoutManager(getCurrentActivity()));
        //recyclerView.setHasFixedSize(true);
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

        }
       /* String projectToken = "769c50e8ab7abe030e310e15adac6e07"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        try {
            JSONObject props = new JSONObject();
            props.put("Gender", "Female");
            props.put("Logged in", false);
            mixpanel.track("MainActivity - onCreate called", props);
        } catch (JSONException e) {
            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
        }
*/
        gsonBuilder = new GsonBuilder();
        gson1 = gsonBuilder.create();

        getOrders();


        Firebase.setAndroidContext(this);


       /* Pusher pusher = new Pusher("1f006f9bd40000fbe5e8");

        Channel channel = pusher.subscribe("k_channel_" + MyApp.sharedPreferences.getInt("PID", 1) + "");

        channel.bind("k_event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.wtf("data", data + "");
                Log.i("channel", channelName + "");
                //
                try {

                    JSONObject jsonObj = new JSONObject(data);
                    //String message = jsonObj.getString("message");
                    // String order_status = jsonObj.getString("order_status");
                    // {"message":"New Order!!","order_id":539,"token":"62","consumer_id":25,"order_status":"placed"}


                    String order_status = jsonObj.getString("order_status");
                    Log.wtf("order_stat", order_status);
                    if (order_status.equalsIgnoreCase("3")) {
                        int or = jsonObj.getInt("order_id");
                        getOrderId = or + "";
                        Log.wtf("order_status", order_status);

                        // Create Inner Thread Class
                        Thread background = new Thread(new Runnable() {


                            // After call for background.start this run method call
                            public void run() {
                                try {

                                    for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
                                        if (det.Order_ID == Integer.parseInt(getOrderId)) {
                                            //token_adapter.notifyDataSetChanged();
                                            det.Status_id = "3";
                                            token_adapter.notifyDataSetChanged();
                                            printer();
                                            generateNotification(MapsActivity.this, "Kitchen" + "");
                                            break;
                                        }


                                    }
                                } catch (Throwable t) {
                                    // just end the background thread
                                    Log.i("Animation", "Thread  exception " + t);
                                }
                            }
                        });
                        // Start Thread
                        background.start();  //After call start method thread called run Method

                        Log.wtf("ostatus", order_status);
                    } else if (order_status.equalsIgnoreCase("1")) {
                        int or = jsonObj.getInt("order_id");
                        getOrderId = or + "";
                        consumer_id = jsonObj.getString("consumer_id");
                        generateNotification(MapsActivity.this, "Kitchen" + "");
                        getOrderIDDetails();
                    }

                    //// getOrderidDetails();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.wtf("err3", e.toString());
                }


            }
        });

        pusher.connect();
*/

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //fire base11
        String lat = MyApp.sharedPreferences.getString("lat", 12.9199142 + "");
        String lng = MyApp.sharedPreferences.getString("lng", 77.5897483 + "");
        LatLng latLng = new LatLng(Double.parseDouble(lat) , Double.parseDouble(lng));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.storeicon)));
        //marker.setTitle("hello");
        marker.showInfoWindow();
       /* latLng = new LatLng(12.919914, 77.9743);
        final Marker marker1= mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.near_marker)));
        marker1.setTitle("hai");
        marker1.showInfoWindow();*/
        mMap.addCircle(new CircleOptions()
                .center(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                // .center(new LatLng(12.9565323, 77.32156555))
                .radius(2000)
                .strokeColor(Color.RED)
                .fillColor(Color.parseColor("#00FFFFFF")));

        rootRef = new Firebase(baseurl + MyApp.sharedPreferences.getInt("PID", 1));
        Log.wtf("url", "https://blistering-torch-3715.firebaseio.com/usertrack/store" + MyApp.sharedPreferences.getInt("PID", 1));
        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> m = (Map<String, Object>) dataSnapshot.getValue();

                final String key = dataSnapshot.getKey();
                String order_status = null;
                if (m.containsKey("order_status")) {
                    order_status = m.get("order_status") + "";
                    if (order_status.equalsIgnoreCase("3")) {
                        if (m.containsKey("order_id")) {
                            getOrderId = m.get("order_id") + "";
                            Log.wtf("co", getOrderId);
                        }

                        // Create Inner Thread Class
                        Thread background = new Thread(new Runnable() {


                            // After call for background.start this run method call
                            public void run() {
                                try {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

//stuff that updates ui{
                                            try {
                                                for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
                                                    if (det.Order_ID == Integer.parseInt(getOrderId)) {
                                                        //token_adapter.notifyDataSetChanged();
                                                        det.Status_id = "3";
                                                        Log.wtf("co1", getOrderId);


                                                        token_adapter.notifyDataSetChanged();

                                                        //generateNotification(MapsActivity.this, "Kitchen" + "");
                                                        break;
                                                    }


                                                }
                                            }catch (Exception e)
                                            {
                                                Log.wtf("notready",e.toString());
                                            }



                                        }
                                    });


                                    printer();

                                } catch (Throwable t) {
                                    // just end the background thread
                                    Log.wtf("exp", "Thread  exception " + t);
                                }
                            }
                        });
                        // Start Thread
                        background.start();  //After call start method thread called run Method


                    } else if (order_status.equalsIgnoreCase("1")) {

                        if (m.containsKey("order_id")) {
                            getOrderId = m.get("order_id") + "";
                            //getOrderId = m.get("orderid") + "";
                            consumer_id = key;
                            generateNotification(MapsActivity.this, "Kitchen" + "");
                            getOrderIDDetails();
                        }
                    }


                }
                String tokenid = null;
                if (m.containsKey("token_id")) {
                    tokenid = m.get("token_id") + "";
                }

/*

                if (tokenid.equalsIgnoreCase("null") ||tokenid.equalsIgnoreCase(null) ) {
                    tokenid = "";

                }
*/

                if (m.containsKey("lat") && m.containsKey("lng")) {


                    double lat = Double.parseDouble(m.get("lat") + "");
                    double lng = Double.parseDouble(m.get("lng") + "");
                    String s1 = m.get("eta") + "";
                    int val = Integer.parseInt(s1);
                    String eta = null;
                    if (val > 59) {
                        final int min = val / 60;

                        if (min < 3) {
                            if (min == 1) {

                                eta = tokenid + " Arrived ETA (" + min + " Minute)";
                            } else if (min >= 2) {
                                eta = tokenid + " Arrived ETA (" + min + " Minutes)";
                            }
                        } else {
                            if (min >= 3 && min <= 7)
                                eta = tokenid + " Near ETA (" + min + " Minutes)";
                            else if (min > 7)
                                eta = tokenid + " Far ETA (" + min + " Minutes)";

                        }
                    } else {

                        eta = tokenid + " Arrived";


                    }

                    LatLng latLng = new LatLng(lat, lng);
                    //final String finalEta = eta;
                    //icon(BitmapDescriptorFactory.fromBitmap(bmp))

                    if (eta.contains("Near")) {

                        final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f, 1));

                        marker.setTitle(eta);

                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {

                                //Do what you want on obtained latLng
                                marker.showInfoWindow();
                            }
                        });


                        marker.showInfoWindow();

                        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        hashMap.put(key, marker);
                    }
                    if (eta.contains("Far")) {

                        final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)).anchor(0.5f, 1));
                        marker.setTitle(eta);

                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {

                                //Do what you want on obtained latLng
                                marker.showInfoWindow();
                            }
                        });


                        marker.showInfoWindow();

                        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        hashMap.put(key, marker);
                    }
                    if (eta.contains("Arrived")) {

                        final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).anchor(0.5f, 1));
                        marker.setTitle(eta);

                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {

                                //Do what you want on obtained latLng
                                marker.showInfoWindow();
                            }
                        });


                        marker.showInfoWindow();

                        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        hashMap.put(key, marker);
                    }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> m = (Map<String, Object>) dataSnapshot.getValue();
                final String key = dataSnapshot.getKey();
                // Log.wtf("datasnap",key+" sss "+m.get("lat"));
                rootRef = new Firebase(baseurl + MyApp.sharedPreferences.getInt("PID", 1));

                // rootRef.update({ name: { first: 'Fred', last: 'Flintstone' }});
                // rootRef.child(key).set({ first: 'Fred', last: 'Flintstone' });
                String tokenid = null;
                if (m.containsKey("token_id")) {
                    tokenid = m.get("token_id") + "";
                    Log.wtf("tim1tokenid",tokenid);


                }

                String sosmessage;
                if (m.containsKey("sos_message")) {
                    sosmessage = m.get("sos_message") + "";
                    Log.wtf("tim1tokenid",sosmessage);
                    String tokenid1 = null,order_id = null;
                    if (m.containsKey("token_id")) {
                        tokenid1 = m.get("token_id") + "";
                        Log.wtf("tim1tokenid",tokenid);


                    }
                    if (m.containsKey("order_id")) {
                        order_id = m.get("order_id") + "";
                        Log.wtf("tim1tokenid",tokenid);


                    }
                    if(!sosmessage.equalsIgnoreCase("done")) {
                        final DialogSOS dialogETA = new DialogSOS(MapsActivity.this, sosmessage, tokenid1, order_id);
                        dialogETA.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                rootRef = new Firebase(baseurl + MyApp.sharedPreferences.getInt("PID", 1));
                                Firebase alanRef = rootRef.child(key);
                                Map<String, Object> nickname = new HashMap<String, Object>();

                                nickname.put("sos_message", "done");
                                alanRef.updateChildren(nickname);
                                //alanRef.child(sla).removeValue();
                                //firebase.child(id).removeValue();
                            }
                        });
                        dialogETA.setCanceledOnTouchOutside(false);
                        dialogETA.show();
                    }
                }

                String order_status;

                if (m.containsKey("order_status")) {

                    order_status = m.get("order_status") + "";
                    Log.wtf("order_status", order_status);
                    if (order_status.equalsIgnoreCase("3")) {
                        if (m.containsKey("order_id"))
                            getOrderId = m.get("order_id") + "";
                        //Printer emp = new Printer(getApplicationContext());
                        PrinterSqlDetails empSql = new PrinterSqlDetails(getApplicationContext());
                        SQLiteDatabase db = empSql.getWritableDatabase();

                        // for cuisine
                        int cPos; // for cursor position
                        Cursor c = db.rawQuery("SELECT checkorder FROM priDetails where orderid=" + getOrderId, null);


                        if (c.getCount() > 0) { // If cursor has atleast one row
                            String checkorder;
                            Log.wtf("true", c.getCount() + "");

                            // Dynamic string array
                            c.moveToFirst();
                            do { // always prefer do while loop while you deal with database
                                cPos = c.getPosition();
                                checkorder = c.getString(c.getColumnIndex("checkorder"));

                                c.moveToNext();
                            } while (!c.isAfterLast());


                        } else {
                            Log.wtf("false", c.getCount() + "");


                            // Create Inner Thread Class
                            Thread background = new Thread(new Runnable() {


                                // After call for background.start this run method call
                                public void run() {
                                    try {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

//stuff that updates ui
                                                for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
                                                    if (det.Order_ID == Integer.parseInt(getOrderId)) {
                                                        //token_adapter.notifyDataSetChanged();
                                                        det.Status_id = "3";
                                                        Log.wtf("co1", getOrderId);


                                                        token_adapter.notifyDataSetChanged();
                                                        Printer pri = new Printer(getApplicationContext());
                                                        pri.orderid = getOrderId + "";
                                                        pri.check = "true";
                                                        PrinterSqlDetails printerSqlDetails = new PrinterSqlDetails(getApplicationContext());
                                                        SQLiteDatabase db1 = printerSqlDetails.getWritableDatabase();
                                                        db1 = printerSqlDetails.getWritableDatabase();
                                                        printerSqlDetails.addPrinterData(pri);
                                                        printerSqlDetails.close();
                                                        db1.close();
                                                        Log.wtf("bb", getOrderId);
                                                        //generateNotification(MapsActivity.this, "Kitchen" + "");
                                                        break;
                                                    }


                                                }


                                            }
                                        });
                                        try {
                                            JSONObject props = new JSONObject();
                                            props.put("OrderId", getOrderId);
                                            //props.put("TokenId", tokenId);
                                            // props.put("Details", order_Details)
                                            MyApp.mixpanel.track("PaymentConfirm", props);
                                        } catch (JSONException e) {
                                            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                                        }
                                        printer();

                                    } catch (Throwable t) {
                                        // just end the background thread
                                        Log.wtf("exception ", t.toString());
                                    }
                                }
                            });
                            // Start Thread
                            background.start();  //After call start method thread called run Method

                            Log.wtf("ostatus", order_status);
                        }

                    } else if (order_status.equalsIgnoreCase("1")) {

                        getOrderId = m.get("order_id") + "";
                        consumer_id = key;
                        generateNotification(MapsActivity.this, "Kitchen" + "");
                        getOrderIDDetails();
                    }
                }


                //Marker marker = hashMap.get(key);

                if (m.containsKey("lat") && m.containsKey("lng")) {
                    LatLng latLng = new LatLng(Double.parseDouble(m.get("lat") + ""), Double.parseDouble(m.get("lng") + ""));
                    //String eta=key+" ETA ("+m.get("eta")+" seconds)";
                    String s1 = "0";
                    /*if (m.containsKey("SLA")) {
                        s1 = m.get("SLA") + "";
                    }*/

                    double lat = Double.parseDouble(m.get("lat") + "");
                    double lng = Double.parseDouble(m.get("lng") + "");
                    s1 = m.get("eta") + "";
                    int val = Integer.parseInt(s1);
                    String eta = null;
                    if (val > 59) {
                        final int min = val / 60;

                        if (min < 3) {
                            if (min == 1) {

                                eta = tokenid + " Arrived ETA (" + min + " Minute)";
                            } else if (min >= 2) {
                                eta = tokenid + " Arrived ETA (" + min + " Minutes)";
                            }
                        } else {
                            if (min >= 3 && min <= 7)
                                eta = tokenid + " Near ETA (" + min + " Minutes)";
                            else if (min > 7)
                                eta = tokenid + " Far ETA (" + min + " Minutes)";

                        }
                    } else {

                        eta = tokenid + " Arrived";


                    }

                    //LatLng latLng = new LatLng(lat, lng);
                    //final String finalEta = eta;
                    //icon(BitmapDescriptorFactory.fromBitmap(bmp))

                    if (eta.contains("Near")) {


                        final Marker hashmarker = hashMap.get(key);
                        if(!hashMap.containsKey(key)) {
                            final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f, 1));

                            marker.setTitle(eta);


                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {

                                    //Do what you want on obtained latLng
                                    marker.showInfoWindow();
                                }
                            });


                            marker.showInfoWindow();

                            // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            hashMap.put(key, marker);

                        }
                        else {

                            Log.wtf("firec", eta);

                            hashmarker.remove();
                            final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f, 1));

                            marker.setTitle(eta);


                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {

                                    //Do what you want on obtained latLng
                                    hashmarker.showInfoWindow();
                                }
                            });


                            marker.showInfoWindow();

                            // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            hashMap.put(key, marker);
                        }
                    }
                    if (eta.contains("Far")) {


                        final Marker hashmarker = hashMap.get(key);
                        if(!hashMap.containsKey(key)) {
                            final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f, 1));

                            marker.setTitle(eta);


                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {

                                    //Do what you want on obtained latLng
                                    marker.showInfoWindow();
                                }
                            });


                            marker.showInfoWindow();

                            // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            hashMap.put(key, marker);

                        }
                        else {

                            hashmarker.remove();
                            final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f, 1));

                            marker.setTitle(eta);


                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {

                                    //Do what you want on obtained latLng
                                    hashmarker.showInfoWindow();
                                }
                            });

                            Log.wtf("fire2", eta);
                            marker.showInfoWindow();

                            // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            hashMap.put(key, marker);
                        }
/*
                        final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)).anchor(0.5f, 1));
                        marker.setTitle(eta);

                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {

                                //Do what you want on obtained latLng
                                marker.showInfoWindow();
                            }
                        });


                        marker.showInfoWindow();

                        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        hashMap.put(key, marker);*/
                    }
                    if (eta.contains("Arrived")) {


                        final Marker hashmarker = hashMap.get(key);
                        if(!hashMap.containsKey(key)) {
                            final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f, 1));

                            marker.setTitle(eta);


                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {

                                    //Do what you want on obtained latLng
                                    marker.showInfoWindow();
                                }
                            });


                            marker.showInfoWindow();

                            // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            hashMap.put(key, marker);

                        }
                        else {

                            hashmarker.remove();
                            final Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f, 1));


                            marker.setTitle(eta);


                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {

                                    //Do what you want on obtained latLng
                                    hashmarker.showInfoWindow();
                                }
                            });


                            marker.showInfoWindow();
                            Log.wtf("fire3", eta);
                            // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            hashMap.put(key, marker);
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                try {
                    Map<String, Object> m = (Map<String, Object>) dataSnapshot.getValue();
                    String key = dataSnapshot.getKey();
                    Log.wtf("datasnap", key + " sss " + m.get("lat"));

                    Marker marker = hashMap.get(key);
                    hashMap.remove(marker);
                    marker.remove();
                } catch (Exception e) {
                    Log.wtf("eremovemarker", e.toString() + "");
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


/*

// geofire
        geoFire = new GeoFire( new Firebase("https://blistering-torch-3715.firebaseio.com/storegeo/store1"));
        // GeoQuery geoQuery = geoFire.queryAtLocation(currentUserLocation, 1.6);0  12.908365
        String lat=MyApp.sharedPreferences.getString("lat",12.9198672+"");
        String lng=MyApp.sharedPreferences.getString("lng",77.5898793+"");
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(Double.parseDouble(lat),Double.parseDouble(lng)), 2000);



        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {

            String conCheck="";
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                try {
                    Log.wtf("enter", key + location.latitude + location.longitude);
                    LatLng latLng = new LatLng( location.latitude, location.longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("eta"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                }
                catch (Exception e)
                {
                    Log.wtf("ee",e.toString());
                }
            }

            @Override
            public void onKeyExited(String key) {
                Log.wtf("exited", key);
                try{


            Log.wtf("key",key);


                }
                catch (Exception e)
                {
                    Log.wtf("ee",e.toString());
                }
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                Log.wtf("moved", key+ location.latitude+ location.longitude);
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                Log.wtf("enter", "All initial data has been loaded and events have been fired!");
                System.out.println("All initial data has been loaded and events have been fired!");
            }

            @Override
            public void onGeoQueryError(FirebaseError error) {
                Log.wtf("There was an error with this query:",error.toString());
                System.err.println("There was an error with this query: " + error);
            }
        });*/


    }


    private void getOrders() {
        //
        String url = MyApp.url + "Get_OrderDetails_allrecent.php?store_id=" + MyApp.sharedPreferences.getInt("PID", 1) + "&recent=true";
        //String url = "http://www.dishem.com/DriveThru/Get_OrderDetail.php?order_id=489";
        Log.wtf("url", url);

        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    orderParent = gson1.fromJson(response, OrderParent.class);

                    token_adapter = new TokenAdapter(MapsActivity.this, R.layout.list_vw_tokens, orderParent.Orders.OrderArray);
                    recycle_tokens.setAdapter(token_adapter);

                    //  tokenIDsAdapter = new TokenIDsAdapter(MapsActivity.this, R.layout.tokens_ids_layout, orderParent.Orders.OrderArray);
                    //  recycle_tokenIDs.setAdapter(tokenIDsAdapter);

                    for (OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
                        if (det.Order_ETA == 0) {
                            if (!mMediaPlayer.isPlaying())
                                handleMediaPlayer(true);
                            break;
                        }


                    }


                } catch (Exception e) {

                    Log.e("eorderid", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        MyApp.reqstQ.add(req).setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS + 2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void generateNotification(Context context, String message) {

        builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logoround)
                        .setContentTitle("On The Go")
                        .setContentText("This is a On The Go notification" + "\n" + message);

        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("CheckNotification", "YES");
        //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("OrderId", message);
        i.putExtra("EXIT", true);


        //Intent notificationIntent = new Intent(context, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);


        builder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        // builder.setVibrate(pattern);
        builder.setStyle(new NotificationCompat.InboxStyle());
       /* Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);*/
// Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());

        // notificationSound();

    }

    private void getOrderIDDetails() {
        //http://www.dishem.com/DriveThru/Get_OrderDetail.php?order_id=489

        String url = MyApp.url + "Get_OrderDetail.php?order_id=" + getOrderId;

        Log.wtf("url", url);
        // String url = "http://sqweezy.com/DriveThru/getOrderDetailsGrouped.php?merchant_id=20";
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    /*JSONObject jsonObject = new JSONObject(response);
                     JSONArray jsonArray = jsonObject.getJSONArray("OrderArray");

                    // if (jsonArray.length() >= 0) {*/
                    OrderParent op = gson1.fromJson(response, OrderParent.class);

                    for (OrderParent.OrderArrayDetails det : op.Orders.OrderArray) {

                        orderParent.Orders.OrderArray.add(det);
                    }
                    // if(orderParent.Orders.OrderArray.size()>=0) {
                    token_adapter.notifyItemInserted(orderParent.Orders.OrderArray.size() - 1);
                    // tokenIDsAdapter.notifyItemInserted(orderParent.Orders.OrderArray.size() - 1);

                    // recycle_tokenId.smoothScrollToPosition(orderParent.Orders.OrderArray.size()-1);

                    handleMediaPlayer(true);

                  /* } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        getOrders();
                    }*/

                } catch (Exception e) {


                    Log.e("egeord", e.toString());
                    //getOrders();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                //getOrders();

            }
        });
        MyApp.reqstQ.add(req).setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS + 2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public void handleMediaPlayer(boolean play) {

        if (play) {

            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();

            }
            if (mMediaPlayer.isPlaying()) {
                // token_adapter.notifyDataSetChanged();
                // return;


                for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
                    if (det.Order_ETA == 0) {
                        //token_adapter.notifyDataSetChanged();
                        if(!det.Status_id.equalsIgnoreCase("6"))
                        {
                        String fVal = "";
                        for (int i = 0; i < det.Products.size(); i++) {
                            String output = "";

                            // output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b> <br /> " ;
                            output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b>  ";
                            String customized = "";
                            for (int j = 0; j < det.Products.get(i).Customization.size(); j++) {

                                customized += det.Products.get(i).Customization.get(j).category + ": " + det.Products.get(i).Customization.get(j).category_value + "<br /> ";

                            }
                            if (customized.equalsIgnoreCase(""))
                                //fVal += "<br /> " + output + "<br /> " + customized;
                                fVal += output + "<br /> " + customized;
                            else
                                fVal += output + "<br /> " + "\n--------------------------------\n" + "<br /> " + customized;


                        }


                        final DialogETA dialogETA = new DialogETA(MapsActivity.this, Integer.parseInt(det.Order_ID + ""), det.consumerid, fVal, 1, det.Token, det.Order_ETA, det.Status_id);
                        dialogETA.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                                // det.Order_ETA = MyApp.sharedPreferences.getInt("order_eta", 0);
                                token_adapter.notifyDataSetChanged();
                                handleMediaPlayer(false);

                            }
                        });
                        //dialogETA.setCanceledOnTouchOutside(false);
                        if (det.Order_ETA == 0)
                            dialogETA.setCanceledOnTouchOutside(false);
                        else
                            dialogETA.setCanceledOnTouchOutside(true);
                        dialogETA.show();
                    }
                    else {
                        token_adapter.notifyDataSetChanged();
                        setETAAndStatus(det.Order_ID,-1,det.Status_id);
                        token_adapter.notifyDataSetChanged();
                    }
                        return;
                    }
                }
            } else {

                Uri alert = Uri.parse("android.resource://" + MapsActivity.this.getPackageName() + "/" + R.raw.sound);

                try {


                    final AudioManager audioManager = (AudioManager) MapsActivity.this.getSystemService(Context.AUDIO_SERVICE);
                    if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
                        mMediaPlayer.setDataSource(MapsActivity.this, alert);
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                        mMediaPlayer.setLooping(true);
                        mMediaPlayer.prepare();
                    }
                } catch (Exception e) {
                    Log.wtf("err2play", e.toString());
                }

                mMediaPlayer.start();

                for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {

                    if (det.Order_ETA == 0) {

                        if(!det.Status_id.equalsIgnoreCase("6"))
                        {
                            token_adapter.notifyDataSetChanged();

                            String fVal = "";
                            for (int i = 0; i < det.Products.size(); i++) {
                                String output = "";

                                // output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b> <br /> " ;
                                output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b>  ";
                                String customized = "";
                                for (int j = 0; j < det.Products.get(i).Customization.size(); j++) {

                                    customized += det.Products.get(i).Customization.get(j).category + ": " + det.Products.get(i).Customization.get(j).category_value + "<br /> ";

                                }
                                if (customized.equalsIgnoreCase(""))
                                    //fVal += "<br /> " + output + "<br /> " + customized;
                                    fVal += output + "<br /> " + customized;
                                else
                                    fVal += output + "<br /> " + "\n--------------------------------\n" + "<br /> " + customized;


                            }


                            final DialogETA dialogETA = new DialogETA(MapsActivity.this, Integer.parseInt(det.Order_ID + ""), det.consumerid, fVal, 1, det.Token, det.Order_ETA, det.Status_id);
                            dialogETA.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                    // det.Order_ETA = MyApp.sharedPreferences.getInt("order_eta", 0);


                                    token_adapter.notifyDataSetChanged();
                                    handleMediaPlayer(false);

                                }
                            });
                            //dialogETA.setCanceledOnTouchOutside(false);
                            if (det.Order_ETA == 0)
                                dialogETA.setCanceledOnTouchOutside(false);
                            else {
                                dialogETA.setCanceledOnTouchOutside(true);


                            }
                            dialogETA.show();
                        }
                        else {
                            token_adapter.notifyDataSetChanged();
                            setETAAndStatus(det.Order_ID,-1,det.Status_id);
                            token_adapter.notifyDataSetChanged();
                        }
                        return;
                    }
                }
            }

        } else {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();

            }
            if (!mMediaPlayer.isPlaying())
                return;
            else {

                for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
                    if (det.Order_ETA == 0) {
                        if(!det.Status_id.equalsIgnoreCase("6"))
                        {

                        token_adapter.notifyDataSetChanged();

                        String fVal = "";
                        for (int i = 0; i < det.Products.size(); i++) {
                            String output = "";
                            // output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b> <br /> " ;
                            output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b>  ";
                            String customized = "";
                            for (int j = 0; j < det.Products.get(i).Customization.size(); j++) {

                                customized += det.Products.get(i).Customization.get(j).category + ": " + det.Products.get(i).Customization.get(j).category_value + "<br /> ";

                            }
                            if (customized.equalsIgnoreCase(""))
                                //fVal += "<br /> " + output + "<br /> " + customized;
                                fVal += output + "<br /> " + customized;
                            else
                                fVal += output + "<br /> " + "\n--------------------------------\n" + "<br /> " + customized;

                        }


                        final DialogETA dialogETA = new DialogETA(MapsActivity.this, Integer.parseInt(det.Order_ID + ""), det.consumerid, fVal, 1, det.Token, det.Order_ETA, det.Status_id);
                        dialogETA.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                                //det.Order_ETA = MyApp.sharedPreferences.getInt("order_eta", 0);
                                token_adapter.notifyDataSetChanged();
                                handleMediaPlayer(false);

                            }
                        });
                        //dialogETA.setCanceledOnTouchOutside(false);
                        if (det.Order_ETA == 0)
                            dialogETA.setCanceledOnTouchOutside(false);
                        else
                            dialogETA.setCanceledOnTouchOutside(true);
                        dialogETA.show();
                        }
                        else {
                            token_adapter.notifyDataSetChanged();
                            setETAAndStatus(det.Order_ID,-1,det.Status_id);
                            token_adapter.notifyDataSetChanged();
                        }
                        return;
                    }
                }
                // mMediaPlayer.stop();
                mMediaPlayer.reset();
                //mMediaPlayer.release();
            }
        }


    }


// loaction permission for marshmellow

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            Toast.makeText(MapsActivity.this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Snackbar.make(this.findViewById(android.R.id.content), "Permission Granted, Now you can access location data.", Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(this.findViewById(android.R.id.content), "Permission Denied, You cannot access location data.", Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case  R.id.textViewInventory:
                Intent intent=new Intent(MapsActivity.this,Inventory.class);
                startActivity(intent);
                break;
            case  R.id.txtVwMerchantClose:
                PasswordDialog d=new PasswordDialog(this,"N","","");

                d.show();
                break;*/
            case R.id.imgVwSetting:

                // getOrderIDDetails1();


                Intent intent = new Intent(MapsActivity.this, SettingActivity.class);
                MapsActivity.this.finish();
                startActivity(intent);
               /* PopupMenu popup = new PopupMenu(MapsActivity.this, imageViewSetting);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_layout, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MapsActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu

                setForceShowIcon(popup);
                */
                break;
        }
    }

    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public void printer() {

        int reVal = woosim.BTConnection("00:15:0E:E3:4C:66", false);
/*handler.post(new Runnable() {
    @Override
    public void run() {
        Log.wtf("getOrderId", getOrderId);
    }
});*/


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


        int tokenId = 0;
        int eta = 0;
        String fVal = "";
        for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
            if (det.Order_ID == Integer.parseInt(getOrderId)) {
                //token_adapter.notifyDataSetChanged();
                tokenId = det.Token;
                eta = det.Order_ETA;
                for (int i = 0; i < det.Products.size(); i++) {
                    String output = "";

                    // output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b> <br /> " ;
                    output = "<b>" + "(" + det.Products.get(i).Quantity + ") " + det.Products.get(i).Product_Name + "" + "</b>  ";
                    String customized = "";
                    for (int j = 0; j < det.Products.get(i).Customization.size(); j++) {

                        customized += det.Products.get(i).Customization.get(j).category + ": " + det.Products.get(i).Customization.get(j).category_value + "<br /> ";

                    }
                    if (customized.equalsIgnoreCase(""))
                        //fVal += "<br /> " + output + "<br /> " + customized;
                        fVal += output + "<br /> " + customized;
                    else
                        fVal += output + "<br /> " + "\n--------------------------------\n" + "<br /> " + customized;


                }
                break;
            }
        }
        woosim.saveSpool(EUC_KR, "Token Id: " + tokenId + "\r\n", 0x11, true);

        // woosim.saveSpool(EUC_KR, "Order Id: " + getOrderId + "\r\n", 0x11, true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm:ss a");
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date afterAddingTenMins = new Date(t + (eta * ONE_MINUTE_IN_MILLIS));
        Log.wtf("time", dateFormat.format(afterAddingTenMins) + "");
        regData = dateFormat.format(afterAddingTenMins) + "";
        woosim.saveSpool(EUC_KR, "Ready By: " + regData + "\r\n", 0, false);
        //woosim.saveSpool(EUC_KR, ""+regData+"\r\n", 0, false);
        Log.wtf("true", Html.fromHtml(fVal) + "");
        woosim.saveSpool(EUC_KR, Html.fromHtml(fVal) + "\r\n", 0, false);

        woosim.saveSpool(EUC_KR, "--------------------------------\r\n", 0, false);

        byte[] lf = {0x0a};
        woosim.controlCommand(lf, lf.length);
        woosim.saveSpool(EUC_KR, "Thank you!\r\n\r\n\r\n", 0, true);
if(tokenId !=0) {
    woosim.controlCommand(lf, lf.length);
    byte[] ff = {0x0a};
    woosim.controlCommand(ff, 1);
    woosim.printSpool(true);
    cardData = null;
}
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


    public void setETAAndStatus(int orderid, int eta, String status) {

        for (final OrderParent.OrderArrayDetails det : orderParent.Orders.OrderArray) {
            if (det.Order_ID == orderid) {
                det.Status_id = status;
                det.Order_ETA = eta;
                token_adapter.notifyDataSetChanged();
                Log.wtf("setlog", det.Status_id);
                //tokenIDsAdapter.notifyDataSetChanged();
                break;
            }

        }

    }

    public void setStatus(String key, String status, String sla) {
        rootRef = new Firebase(baseurl + MyApp.sharedPreferences.getInt("PID", 1));
        Firebase alanRef = rootRef.child(key);
        Map<String, Object> nickname = new HashMap<String, Object>();
        nickname.put("order_status", status + "");
        nickname.put("sla", sla + "");
        alanRef.updateChildren(nickname);

    }


    public void conSqlite() {
        Printer pri = new Printer(getApplicationContext());
        pri.orderid = order_eta + "";
        pri.check = "true";
        PrinterSqlDetails printerSqlDetails = new PrinterSqlDetails(getApplicationContext());
        SQLiteDatabase db = printerSqlDetails.getWritableDatabase();
        printerSqlDetails.addPrinterData(pri);
        printerSqlDetails.close();
        db.close();

    }

}
