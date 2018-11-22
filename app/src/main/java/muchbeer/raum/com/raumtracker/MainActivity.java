package muchbeer.raum.com.raumtracker;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import muchbeer.raum.com.raumtracker.utilitiy.SimUtil;
import muchbeer.raum.com.raumtracker.utilitiy.SmsHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private static final String PREF_USER_MOBILE_PHONE = "pref_user_mobile_phone";
    private static final int SMS_PERMISSION_CODE = 0;

    private EditText mNumberEditText;
    private String mUserMobilePhone;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        SMS_PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        initViews();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserMobilePhone = mSharedPreferences.getString(PREF_USER_MOBILE_PHONE, "");
        if (!TextUtils.isEmpty(mUserMobilePhone)) {
            mNumberEditText.setText(mUserMobilePhone);
        }
    }

    private void initViews() {
        mNumberEditText = (EditText) findViewById(R.id.et_number);
        findViewById(R.id.btn_normal_sms).setOnClickListener(this);
        findViewById(R.id.btn_conditional_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_conditional_sms:
                if (!hasValidPreConditions()) return;
                checkAndUpdateUserPrefNumber();


                SmsManager sms = SmsManager.getDefault();
                PendingIntent sentPI;
                String SENT = "SMS_SENT";

                sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
                sms.sendTextMessage("0755445126", null, "777", sentPI, null);

              //  sms.sendTextMessage(String.valueOf(mNumberEditText.getText()), null, "Tracking my car", sentPI, null);

                Toast.makeText(getApplicationContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_normal_sms:
       Intent startNewRaumTracker = new Intent(this, RaumActivity.class);
       startActivity(startNewRaumTracker);
                break;
        }
    }

    /**
     * Checks if stored SharedPreferences value needs updating and updates \o/
     */
    private void checkAndUpdateUserPrefNumber() {
        if (TextUtils.isEmpty(mUserMobilePhone) && !mUserMobilePhone.equals(mNumberEditText.getText().toString())) {
            mSharedPreferences
                    .edit()
                    .putString(PREF_USER_MOBILE_PHONE, mNumberEditText.getText().toString())
                    .apply();
        }
    }


    /**
     * Validates if the app has readSmsPermissions and the mobile phone is valid
     *
     * @return boolean validation value
     */
    private boolean hasValidPreConditions() {
        if (!hasReadSmsPermission()) {
            requestReadAndSendSmsPermission();
            return false;
        }

        if (!SmsHelper.isValidPhoneNumber(mNumberEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), R.string.error_invalid_phone_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
    }
}
