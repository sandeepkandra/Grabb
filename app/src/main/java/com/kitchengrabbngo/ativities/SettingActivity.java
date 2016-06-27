package com.kitchengrabbngo.ativities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kitchengrabbngo.R;
import com.kitchengrabbngo.utility.PasswordDialog;

public class SettingActivity extends Activity implements View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */


    TextView textViewInventory, txtVwMerchantClose;
    ImageView imageViewBack;
    RelativeLayout relativeLayoutInventory, relativeLayoutNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification);

        relativeLayoutNotification = (RelativeLayout) findViewById(R.id.relativeLayoutNotification);
        relativeLayoutNotification.setOnClickListener(this);
        relativeLayoutInventory = (RelativeLayout) findViewById(R.id.relativeLayoutInventory);
        relativeLayoutInventory.setOnClickListener(this);
        imageViewBack = (ImageView) findViewById(R.id.imgVwBack);
        imageViewBack.setOnClickListener(this);
        textViewInventory = (TextView) findViewById(R.id.textViewInventory);
        textViewInventory.setOnClickListener(this);
        txtVwMerchantClose = (TextView) findViewById(R.id.txtVwMerchantClose);
        txtVwMerchantClose.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayoutInventory:
                Intent intent = new Intent(SettingActivity.this, Inventory.class);
                startActivity(intent);
                SettingActivity.this.finish();
                break;
            case R.id.txtVwMerchantClose:
                PasswordDialog d = new PasswordDialog(this, "N", "", "");

                d.show();
                break;
            case R.id.imgVwBack:
                Intent intent1 = new Intent(SettingActivity.this, MapsActivity.class);
                startActivity(intent1);
                SettingActivity.this.finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingActivity.this, MapsActivity.class);
        startActivity(intent);
        SettingActivity.this.finish();
    }
    /**
     * A placeholder fragment containing a simple view.
     */

}
