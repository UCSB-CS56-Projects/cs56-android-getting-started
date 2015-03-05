package intro.android.cs56.ucsb.edu.simpleservice;

import android.app.Service;
import android.util.Log;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.content.IntentFilter;

public class MyService extends Service{

    //Debuggin' Purpouses
    private final static String TAG="MyService";


    //Broadcast Reciever//
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
                Log.d(TAG,"SMS Received!");
                Toast.makeText(context, "SMS Received!", Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");

        //Setup an intent filter which we'll use to tell the system that our particular broadcast reciever is expecting to be triggered when SMS are recieved.
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

        registerReceiver(receiver, filter); //Register the Receiver

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();

        //Unregister the Receiver.
        unregisterReceiver(receiver);

        Log.d(TAG, "onDestroy");

    }

}

