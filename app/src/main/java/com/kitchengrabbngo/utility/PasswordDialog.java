package com.kitchengrabbngo.utility;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kitchengrabbngo.R;
import com.kitchengrabbngo.ativities.Inventory;
import com.kitchengrabbngo.ativities.SettingActivity;
import com.woosim.bt.WoosimPrinter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sandeep on 06-May-16.
 */
public class PasswordDialog extends Dialog {
    Context cntxt;
    String checkInventry,stock_check,store_products_id;



    public PasswordDialog(Context context, String Check, String stock_chek, String store_productsid) {
        super(context);
        cntxt=context;
        checkInventry =Check;
        stock_check =stock_chek;
        store_products_id = store_productsid;
    }
    public String pinCode,MerchantStatus;
   public EditText editText, editText1,editText2,editText3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_password);



         editText1=(EditText)findViewById(R.id.txt2);
        editText2=(EditText)findViewById(R.id.txt3);
        editText3=(EditText)findViewById(R.id.txt4);
         editText=(EditText)findViewById(R.id.txt1);
        Button button0=(Button)findViewById(R.id.btn0);
        Button button1=(Button)findViewById(R.id.btn1);
        Button button2=(Button)findViewById(R.id.btn2);
        Button button3=(Button)findViewById(R.id.btn3);
        Button button4=(Button)findViewById(R.id.btn4);
        Button button5=(Button)findViewById(R.id.btn5);
        Button button6=(Button)findViewById(R.id.btn6);
        Button button7=(Button)findViewById(R.id.btn7);
        Button button8=(Button)findViewById(R.id.btn8);
        Button button9=(Button)findViewById(R.id.btn9);
        Button buttonclr=(Button)findViewById(R.id.btncncl);
        Button buttonnxt=(Button)findViewById(R.id.nxtbtn);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("0");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append("9");
            }
        });


        buttonclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.txt1);
                text.setText("");
            }
        });
        buttonnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(M,"",Toast.LENGTH_SHORT).show();

                if( editText.getText().toString().trim().equals(""))
                {
                    //edtPassword.setError( "Enter password" );
                    //You can Toast a message here that the Username is Empty
                    //edtPassword.setCursorVisible(true);
                    editText.setFocusable(true);
                    editText.requestFocus();
                    //  prntplogin.setScrollY((int)edtPassword.getY());
                    String estring = "Enter pin code";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText.setError(ssbuilder);
                    //edtPassword.setFocusable(false);
                    //edtEmail.setFocusable(true);
                    return;
                }


                if ( editText.getText().toString().startsWith(" ") )
                {
                    String estring = "Space is not allowed in first character";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText.setError(ssbuilder);
                    //edtEmail.setFocusable(true);
                    editText.requestFocus();
                    return;
                }


                else
                {
                    editText.setError(null);
                    //passcode=editText1.getText().toString();
                }

                if( editText1.getText().toString().trim().equals(""))
                {
                    //edtPassword.setError( "Enter password" );
                    //You can Toast a message here that the Username is Empty
                    //edtPassword.setCursorVisible(true);
                    editText1.setFocusable(true);
                    editText1.requestFocus();
                    //  prntplogin.setScrollY((int)edtPassword.getY());
                    String estring = "Enter enter pin code";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText1.setError(ssbuilder);
                    //edtPassword.setFocusable(false);
                    //edtEmail.setFocusable(true);
                    return;
                }


                if ( editText1.getText().toString().startsWith(" ") )
                {
                    String estring = "Space is not allowed in first character";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText1.setError(ssbuilder);
                    //edtEmail.setFocusable(true);
                    editText1.requestFocus();
                    return;
                }


                else
                {
                    editText1.setError(null);
                    //passcode=editText1.getText().toString();
                }
                if( editText2.getText().toString().trim().equals(""))
                {
                    //edtPassword.setError( "Enter password" );
                    //You can Toast a message here that the Username is Empty
                    //edtPassword.setCursorVisible(true);
                    editText2.setFocusable(true);
                    editText2.requestFocus();
                    //  prntplogin.setScrollY((int)edtPassword.getY());
                    String estring = "Enter enter pin code";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText2.setError(ssbuilder);
                    //edtPassword.setFocusable(false);
                    //edtEmail.setFocusable(true);
                    return;
                }


                if ( editText2.getText().toString().startsWith(" ") )
                {
                    String estring = "Space is not allowed in first character";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText2.setError(ssbuilder);
                    //edtEmail.setFocusable(true);
                    editText2.requestFocus();
                    return;
                }


                else
                {
                    editText2.setError(null);
                    //passcode=editText1.getText().toString();
                }
                if( editText3.getText().toString().trim().equals(""))
                {
                    //edtPassword.setError( "Enter password" );
                    //You can Toast a message here that the Username is Empty
                    //edtPassword.setCursorVisible(true);
                    editText3.setFocusable(true);
                    editText3.requestFocus();
                    //  prntplogin.setScrollY((int)edtPassword.getY());
                    String estring = "Enter enter pin code";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText3.setError(ssbuilder);
                    //edtPassword.setFocusable(false);
                    //edtEmail.setFocusable(true);
                    return;
                }


                if ( editText3.getText().toString().startsWith(" ") )
                {
                    String estring = "Space is not allowed in first character";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editText3.setError(ssbuilder);
                    //edtEmail.setFocusable(true);
                    editText3.requestFocus();
                    return;
                }


                else
                {
                    editText3.setError(null);
                    //passcode=editText1.getText().toString();
                }

                pinCode =editText.getText().toString()+editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString();
                Log.wtf("pin",pinCode);

                updateMerchantStatus();

            }
        });
        /*
editText.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (editText.getText().toString().length() == 1)
            editText2.requestFocus();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});



        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText1.getText().toString().length()==1)
                    editText2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText2.getText().toString().length()==1)
                    editText3.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        */


        Button button01=(Button)findViewById(R.id.btn0);
        Button button11=(Button)findViewById(R.id.btn1);
        Button button21=(Button)findViewById(R.id.btn2);
        Button button31=(Button)findViewById(R.id.btn3);
        Button button41=(Button)findViewById(R.id.btn4);
        Button button51=(Button)findViewById(R.id.btn5);
        Button button61=(Button)findViewById(R.id.btn6);
        Button button71=(Button)findViewById(R.id.btn7);
        Button button81=(Button)findViewById(R.id.btn8);
        Button button91=(Button)findViewById(R.id.btn9);
        Button buttonclr1=(Button)findViewById(R.id.btncncl);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("0");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("0");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("0");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("0");
                    editText3.requestFocus();
                }
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equalsIgnoreCase(""))
                {
                    editText.append("1");
                    editText.requestFocus();
                }
                else if(editText1.getText().toString().equalsIgnoreCase(""))
                {
                    editText1.append("1");
                    editText1.requestFocus();
                }
                else if(editText2.getText().toString().equalsIgnoreCase(""))
                {
                    editText2.append("1");
                    editText2.requestFocus();
                }
                else if(editText3.getText().toString().equalsIgnoreCase(""))
                {
                    editText3.append("1");
                    editText3.requestFocus();
                }


            }
        });
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("2");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("2");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("2");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("2");
                    editText3.requestFocus();
                }
            }
        });
        button31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("3");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("3");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("3");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("3");
                    editText3.requestFocus();
                }
            }
        });
        button41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("4");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("4");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("4");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("4");
                    editText3.requestFocus();
                }
            }
        });
        button51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("5");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("5");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("5");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("5");
                    editText3.requestFocus();
                }
            }
        });
        button61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("6");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("6");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("6");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("6");
                    editText3.requestFocus();
                }
            }
        });
        button71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("7");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("7");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("7");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("7");
                    editText3.requestFocus();
                }
            }
        });
        button81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("8");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("8");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("8");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("8");
                    editText3.requestFocus();
                }
            }
        });
        button91.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    editText.append("9");
                    editText.requestFocus();
                } else if (editText1.getText().toString().equalsIgnoreCase("")) {
                    editText1.append("9");
                    editText1.requestFocus();
                } else if (editText2.getText().toString().equalsIgnoreCase("")) {
                    editText2.append("9");
                    editText2.requestFocus();
                } else if (editText3.getText().toString().equalsIgnoreCase("")) {
                    editText3.append("9");
                    editText3.requestFocus();
                }
            }
        });
        buttonclr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.txt2);
                EditText text1=(EditText) findViewById(R.id.txt1);
                EditText text2=(EditText) findViewById(R.id.txt3);
                EditText text3=(EditText) findViewById(R.id.txt4);
                text.setText("");
                text1.setText("");
                text2.setText("");
                text3.setText("");
            }
        });


/*
        Button button02=(Button)findViewById(R.id.btn0);
        Button button12=(Button)findViewById(R.id.btn1);
        Button button22=(Button)findViewById(R.id.btn2);
        Button button32=(Button)findViewById(R.id.btn3);
        Button button42=(Button)findViewById(R.id.btn4);
        Button button52=(Button)findViewById(R.id.btn5);
        Button button62=(Button)findViewById(R.id.btn6);
        Button button72=(Button)findViewById(R.id.btn7);
        Button button82=(Button)findViewById(R.id.btn8);
        Button button92=(Button)findViewById(R.id.btn9);
        Button buttonclr2=(Button)findViewById(R.id.btncncl);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("0");
            }
        });
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("1");
            }
        });
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("2");
            }
        });
        button32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("3");
            }
        });
        button42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("4");
            }
        });
        button52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("5");
            }
        });
        button62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("6");
            }
        });
        button72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("7");
            }
        });
        button82.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("8");
            }
        });
        button92.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.append("9");
            }
        });
        buttonclr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.txt3);
                text.setText("");
            }
        });



        Button button03=(Button)findViewById(R.id.btn0);
        Button button13=(Button)findViewById(R.id.btn1);
        Button button23=(Button)findViewById(R.id.btn2);
        Button button33=(Button)findViewById(R.id.btn3);
        Button button43=(Button)findViewById(R.id.btn4);
        Button button53=(Button)findViewById(R.id.btn5);
        Button button63=(Button)findViewById(R.id.btn6);
        Button button73=(Button)findViewById(R.id.btn7);
        Button button83=(Button)findViewById(R.id.btn8);
        Button button93=(Button)findViewById(R.id.btn9);
        Button buttonclr3=(Button)findViewById(R.id.btncncl);
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("0");
            }
        });
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("1");
            }
        });
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("2");
            }
        });
        button33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("3");
            }
        });
        button43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("4");
            }
        });
        button53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("5");
            }
        });
        button63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("6");
            }
        });
        button73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("7");
            }
        });
        button83.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("8");
            }
        });
        button93.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.append("9");
            }
        });
        buttonclr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text=(EditText)findViewById(R.id.txt4);
                text.setText("");
            }
        });
*/




    }


    public void updateMerchantStatus() {

        if(!checkInventry.equalsIgnoreCase("YES")) {
            if (MyApp.sharedPreferences.getString("store_status", "OPEN").equalsIgnoreCase("OPEN"))

            {

                MerchantStatus = "CLOSE";

                SharedPreferences settings = MyApp.sharedPreferences;
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("store_status", MerchantStatus);
                editor.commit();

            } else {
                //  txtVwMerchantClose.setBackgroundResource(R.drawable.backgroundgreen);
                //  txtVwMerchantClose.setText("OPEN");

                MerchantStatus = "OPEN";
                SharedPreferences settings = MyApp.sharedPreferences;
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("store_status", MerchantStatus);
                editor.commit();
            }
        }
        else {

            MerchantStatus= MyApp.sharedPreferences.getString("store_status","OPEN");


        }

        // http://www.dishem.com/DriveThru/checkMerchantStatus.php?merchant_id=1&merchant_status=OPEN&CheckPincode=N&CheckPincodeNumber=2345
        String url = MyApp.url+"checkMerchantStatus.php?merchant_id="+MyApp.sharedPreferences.getString("store_id",1+"")+"&merchant_status="+MerchantStatus+"&CheckPincode=N&CheckPincodeNumber="+pinCode;
        Log.wtf("url",url);
        JsonObjectRequest req = new JsonObjectRequest(url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jj = new JSONObject(response + "");
                    JSONArray ss=jj.getJSONArray("result");

                    JSONObject oo=ss.getJSONObject(0);
                    String message=oo.getString("message");

                    if(message.equalsIgnoreCase("CorrectPinNumber")) {

                        if(!checkInventry.equalsIgnoreCase("YES")) {
                            SharedPreferences settings = MyApp.sharedPreferences;
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("store_status", MerchantStatus);
                            editor.commit();


                            Toast.makeText(cntxt, "Succesfully Done :)", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(cntxt, SettingActivity.class);
                            cntxt.startActivity(intent);
                            dismiss();
                        }
                        else {
                            setProductStatus();

                        }

                    }
                    else {
                        Toast.makeText(cntxt, "Please Enter Vaild PinCode", Toast.LENGTH_LONG).show();

                    }

                }
                catch (Exception e)
                {

                    Log.wtf("err",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cntxt, "Poor Internet Connection ", Toast.LENGTH_LONG).show();
                // getOrders();
            }
        });
        MyApp.reqstQ.add(req);
    }


    private void setProductStatus() {

        if(stock_check.equalsIgnoreCase("Y"))
        {
            stock_check ="N";

        }
        else {
            stock_check ="Y";

        }
        String url = MyApp.url+"update_store_product_stock.php?store_products_id="+store_products_id+"&stock_check="+stock_check;
        Log.wtf("url",url);
        JsonObjectRequest req = new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(cntxt,"Sucess",Toast.LENGTH_LONG).show();
                dismiss();
                Intent i=new Intent(cntxt,Inventory.class);

                cntxt.startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cntxt,"Poor Internet Connection",Toast.LENGTH_LONG).show();
            }
        });
        MyApp.reqstQ.add(req);
    }


}

