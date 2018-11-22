package muchbeer.raum.com.raumtracker.utilitiy;

/**
 * Created by muchbeer on 20/11/2018.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";
    private static final String TAG_COORDINATE = "SUBSTRING";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            String get_coordinate = smsBody;
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
            }

            if (smsBody.startsWith("Hexagonal")) {
                Log.d(TAG, "Sms with condition detected");
                Toast.makeText(context, "BroadcastReceiver caught conditional SMS: " + smsBody, Toast.LENGTH_LONG).show();
             String raum_coordinate   =smsBody.substring(60,77);

                Log.d(TAG, "The live coordinate " + raum_coordinate );

            }
            Log.d(TAG, "SMS detected: From " + smsSender + " With text " + smsBody);
        }
    }
}