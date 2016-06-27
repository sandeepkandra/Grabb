package com.kitchengrabbngo.utility;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kitchengrabbngo.R;
import com.kitchengrabbngo.ativities.MapsActivity;
import com.woosim.bt.WoosimPrinter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by sandeep on 12-May-16.
 */
public class DialogSOS extends Dialog implements View.OnClickListener {
    Context cntxt;
    String sos,order_id,tokenId;
    MediaPlayer mediaPlayer;


    //  public DialogETA(Context context,int orderid, int consumerid,String orderDetails) {
    public DialogSOS(Context context, String sosm, String token, String order_id) {
        super(context);
        cntxt = context;
        this.order_id = order_id;
        tokenId = token;
        sos = sosm;


    }

    TextView txtVwTime1,txtVwReject, txtVwTime2, txtVwTime3, txtVwTime4, txtVwCheckTime, txVwOrderDetails, txtVwTokenId, txtVwOrderId;
    TextView btnAccept;
    Button btnComplete,btnPrint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_pop_up_layout);


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
        //txVwOrderDetails.setVisibility(View.GONE);
        btnPrint.setVisibility(View.GONE);
        txVwOrderDetails.setText(sos);
        txtVwOrderId.setText("OrderId: " + order_id + "");
        txtVwTokenId.setText("TokenId: " + tokenId + "");
        txVwOrderDetails.setMovementMethod(new ScrollingMovementMethod());

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();


        }


        Uri alert = Uri.parse("android.resource://" + cntxt.getPackageName() + "/" + R.raw.sound);

        try {


            final AudioManager audioManager = (AudioManager) cntxt.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
                mediaPlayer.setDataSource(cntxt, alert);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
            }
        } catch (Exception e) {
            Log.wtf("err2play", e.toString());
        }

        mediaPlayer.start();


           percentRelativeLayout.setVisibility(View.GONE);

        btnComplete.setText("Calling");


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

            case R.id.btnComplete:
                mediaPlayer.reset();
            dismiss();

                break;

                //

        }
    }



}
