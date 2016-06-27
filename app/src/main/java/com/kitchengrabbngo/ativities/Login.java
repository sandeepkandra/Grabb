package com.kitchengrabbngo.ativities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kitchengrabbngo.R;
import com.kitchengrabbngo.utility.ConnectionDetector;
import com.kitchengrabbngo.utility.MyApp;
import com.kitchengrabbngo.validation.Validation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;


public class Login extends Activity implements View.OnClickListener {

    Button btnSignin;
    EditText editTextEmail, editTextPassword;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String email, passcode;

    // <code></code>
    EditText editText1, editText2, editText3, editText4, editText1Confirm, editText2Confirm, editText3Confirm, editText4Confirm;

    String pinCode, confirmPincode;

    //alert box to check internet...........
    @SuppressWarnings("deprecation")
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

    ProgressDialog progress;

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.edtTxtMerchantEmail);
        editTextPassword = (EditText) findViewById(R.id.edtTxtMerchantPassword);
        // View view = Activity.getCurrentFocus();

/*

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        finish();

        Intent i = new Intent(this, AlarmRing.class);
        stopService(i);
*/

/*

// pin code by sandy

        editText1=(EditText)findViewById(R.id.edittxt1);
        editText2=(EditText)findViewById(R.id.edittxt2);
        editText3=(EditText)findViewById(R.id.edittxt3);
        editText4=(EditText)findViewById(R.id.edittxt4);

        editText1Confirm=(EditText)findViewById(R.id.edittxt1confirm);
        editText2Confirm=(EditText)findViewById(R.id.edittxt2confirm);
        editText3Confirm=(EditText)findViewById(R.id.edittxt3confirm);
        editText4Confirm=(EditText)findViewById(R.id.edittxt4confirm);

        //pin <code></code>

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText1.getText().toString().length() == 0)
                    editText2.requestFocus();
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText2.getText().toString().length() == 0)
                    editText3.requestFocus();
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText3.getText().toString().length() == 0)
                    editText4.requestFocus();
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText4.getText().toString().length() == 0) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText4.getWindowToken(), 0);
                }
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        editText1Confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText1Confirm.getText().toString().length() == 0)
                    editText2Confirm.requestFocus();
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText2Confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText2Confirm.getText().toString().length() == 0)
                    editText3Confirm.requestFocus();
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText3Confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText3Confirm.getText().toString().length() == 0)
                    editText4Confirm.requestFocus();
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText4Confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText4Confirm.getText().toString().length() == 0) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText4Confirm.getWindowToken(), 0);
                }
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

*/

        progress = ProgressDialog.show(this, "Loading....",
                "Please wait", true);
        progress.dismiss();
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(Login.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }


        String checkLogin = MyApp.sharedPreferences.getString("LOGGEDIN", "default");


        if (checkLogin.equals("yes")) {

            Intent movetocreatedeal = new Intent(Login.this, MapsActivity.class);
            //finishActivity(TRIM_MEMORY_BACKGROUND);
            movetocreatedeal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(movetocreatedeal);
            Login.this.finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignin:

                cd = new ConnectionDetector(getApplicationContext());
                // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                if (!isInternetPresent) {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(Login.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }

                editTextEmail.setError(null);
                editTextPassword.setError(null);

                // TODO Auto-generated method stub
                //Email validation......................................
                if (checkValidation()) {
                    /*edtEmail.setCursorVisible(false);*/
                    //edtEmail.setFocusable(false);
                    email = editTextEmail.getText().toString().trim();

                    editTextEmail.setError(null);//removes error
                    //edtEmail.clearFocus();
                } else {
                    //edtEmail.setFocusable(true);
                    // prntplogin.setScrollY((int)edtEmail.getY());
                    editTextEmail.requestFocus();
                    String estring = "Enter valid email";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextEmail.setError(ssbuilder);

                    //edtEmail.setError( "Email is required!" );
                    return;
                }

				/*edtEmail.setCursorVisible(false);
				edtEmail.setFocusable(false);*/


                email = editTextEmail.getText().toString().trim();
                //password login......................

                if (editTextPassword.getText().toString().trim().equals("")) {
                    //edtPassword.setError( "Enter password" );
                    //You can Toast a message here that the Username is Empty
                    //edtPassword.setCursorVisible(true);
                    editTextPassword.setFocusable(true);
                    editTextPassword.requestFocus();
                    //  prntplogin.setScrollY((int)edtPassword.getY());
                    String estring = "Enter password";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextPassword.setError(ssbuilder);
                    //edtPassword.setFocusable(false);
                    //edtEmail.setFocusable(true);
                    return;
                }


                if (editTextPassword.getText().toString().startsWith(" ")) {
                    String estring = "Space is not allowed in first character";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextPassword.setError(ssbuilder);
                    //edtEmail.setFocusable(true);
                    editTextPassword.requestFocus();
                    return;
                } else {
                    passcode = editTextPassword.getText().toString();
                }

              /*  pinCode=editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString();

                confirmPincode=editText1Confirm.getText().toString()+editText2Confirm.getText().toString()+editText3Confirm.getText().toString()+editText4Confirm.getText().toString();
                if(pinCode.equalsIgnoreCase(confirmPincode)) {
                    //url
                    loginMerchant();
                }
                else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Login.this);

                    // set title
                    alertDialogBuilder.setTitle(" Sorry!! Pin Number Miss Matched");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Please enter correct Pin Number")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    return;
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }*/
                loginMerchant();
                break;

        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        // if (!Validation.hasText(etNormalText)) ret = false;
        if (!Validation.isEmailAddress(editTextEmail, true)) ret = false;
        //if (!Validation.isPhoneNumber(etPhoneNumber, false)) ret = false;

        return ret;
    }


    public void loginMerchant() {
        progress.show();

        String url = MyApp.url + "loginMerchant.php?merchant_email=" + URLEncoder.encode(email) + "&merchant_password=" + URLEncoder.encode(passcode);
        Log.e("url", url);
        JsonObjectRequest req = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jj = new JSONObject(response + "");
                    JSONArray ss = jj.getJSONArray("result");
                    progress.dismiss();
                    JSONObject oo = ss.getJSONObject(0);

                    if (oo.length() > 0) {

                        String message = oo.getString("record");
                        if (!message.equalsIgnoreCase("No Record")) {
                            int store_id = oo.getInt("store_id");
                            //String merchant_password =oo.getString("");
                            String id_merchant = oo.getString("id_merchant");
                            String store_pincode = oo.getString("store_pincode");
                            String store_status = oo.getString("store_status");
                            String lat = oo.getString("store_location_lat");
                            String lng = oo.getString("store_location_lng");
                            SharedPreferences settings = MyApp.sharedPreferences;
                            SharedPreferences.Editor editor = settings.edit();
                            String check = "yes";
                            //String check=chckLogin;
                            editor.putString("LOGGEDIN", check);
                            editor.putString("EMAIL", email);
                            editor.putString("lat", lat);
                            editor.putString("lng", lng);
                            editor.putString("MID", id_merchant);
                            editor.putString("store_pincode", store_pincode);
                            editor.putString("store_status", store_status);
                            editor.putInt("PID", store_id);
                            editor.commit();

                            Intent i = new Intent(Login.this, MapsActivity.class);

                            finishActivity(TRIM_MEMORY_BACKGROUND);

                            finish();
                            startActivity(i);

                        } else {
                            progress.dismiss();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    Login.this);

                            // set title
                            alertDialogBuilder.setTitle("Authentication Failed");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Please enter correct Username and Password")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            return;
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }

                    } else {
                        progress.dismiss();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                Login.this);

                        // set title
                        alertDialogBuilder.setTitle("Authentication Failed");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Please enter correct Username and Password")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        return;
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }


                } catch (Exception e) {
                    progress.dismiss();
                    Log.wtf("err", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Poor Internet Connection", Toast.LENGTH_LONG).show();
                Log.wtf("err", error.toString());
                // getOrders();
            }
        });
        MyApp.reqstQ.add(req);
    }


    public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = new Intent(context, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        return dismissIntent;
    }

}
