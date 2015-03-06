<h1>3. Intro To Services and Broadcast Receivers</h1>
<h6>Last updated: pmsosa | Winter 2015</h6>
######[Back to index](../index.md)######

<h2 id="3_index">Index</h2>

- [Objective](#3_objective)
- [Services Basics](#3_basics)
- [Broadcast Receivers Basics](#3_broadcast)
- [Coding the App](#3_coding)
- [Final Code](#3_files)
- [Sources & Further Reading](#3_sources)

---

<h2 id="3_objective">Objective</h2>

The objective for this tutorial will be to build a very simple *Unbound* service that will simply display a Toast when we get an SMS message.


<h2 id="3_basics">Services Basics</h2>

<h5>Basic Concepts</h5>

A Service is a component which:
- Runs in the background without direct interaction with the user. 
- As the service has no user interface, it is not bound to the life cycle of an activity. (meaning you can change activities or even destroy the activity and the service will continue running *(if you so desire)*
- They are usually great for taking care of long running background tasks, such as Internet Downloads, checking for new data from some source, data processing, etc.

Services can be of two types:
- **Unbound:** A service started from an application component calling `startService()`. This service will continue to run in the background even if the original component is destroyed.
- **Bound:** A service started and bound by an application component using `bindService()`. A bound service lives as long as the application component lives. As soon as they unbind (or the component gets destroyed), the service destroys itself.

<h5>Lifecylce of an Android App</h5>
![Taken from https://developer.android.com/guide/components/services.html#Lifecycle](3_Service_Life.png)

<h5>Callback of Services</h5>
- **onStartCommand():** The system calls this method when another component requests the service to be started.
- **onBind():** The system calls this method when another component wants to bind to this method
- **onUnbind():** The system calls this method when all clients have disconnected from this method.
- **onRebind():** The system calls this method when new clients have connected to this method.
- **onCreate():** The system calls this method to preform one time setup.
- **onDestroy():** The system calls this method to destroy the service.



<h2 id="#3_broadcast">BroadcastReceiver Basics</h2>

Before we can proceed to build our app, we need to have a simple background on BroadcastReceivers.

<h5>Basic Concept</h5>
A broadcast receiver (short receiver) is an Android component which allows you to register for system or application events. All registered receivers for an event are notified by the Android runtime once this event happens.

In our case, we want our application to register a BroadcastReceiver for the android.provider.Telephony.SMS_RECEIVED action which will be fired once the phone receives a new SMS.

<h5>Lifecycle</h5>

- First the Receiver has to be registered in one of two ways:
	- **Static:** Add the receiver to the AndroidManifest.xml.
	- **Dynamically: ** by calling `Context.registerReceiver()` method.
- Once the receiver is registered. The `onReceiver()` callback of the receiver will be called by the system.
- After `onReceiver()` has finished the Android system is allowed to recycle the receiver.

**Note:** While this is a very basic overview of what a Broadcast Receiver does, you will most likely encounter them more in the future. I would highly recommend checking out Vogella's amazing tutorial on Receivers at: http://www.vogella.com/tutorials/AndroidBroadcastReceiver/article.html#broadcastreceiver_definition

<h2 id="3_coding">Coding the App</h2>

<h5>Basic Setup</h5> 

- First set up a new project, with a blank activity as your base template.
- Next, create a new class (MyService.java). This is going to be the actual service class implementation.
- Much like you declare your Activities in the Manifest.xml, it is important to remember to declare your services in the Manifest file. To do so add `<service android:enabled="true" android:name=".MyService" />` inside the application tags.
- While we are in the Manifest.xml we will need to add the following permission `<uses-permission android:name="android.permission.RECEIVE_SMS" />` inside the manifest tags. As you can imagine, this permission will allow us to receive information of incoming SMS messages. 
- Finally lets take a moment to add some import statements to our classes.
	- In MainActivity.java add:
		```Java
		import android.content.Intent;
		``` 
	- In MyService.java add:
		```Java
		import android.app.Service;
		import android.util.Log;
		import android.widget.Toast;
		import android.content.BroadcastReceiver;
		import android.content.Context;
		import android.content.Intent;
		import android.os.IBinder;
		import android.content.IntentFilter;
		```

<h5>Coding the Service</h5>

To write a service, the first thing you must actually do is extend from the class Service. Depending on what you are coding, you are welcome to override the onBind, onCreate,onStart,and onDestroy methods. For the sake of this simple tutorial we will override these to add log statements and Toasts to be able to see the changes in the service lifestyles.

So the basic structure of our Service will look something like:
```Java
public class MyService extends Service{

    //Debuggin' Purpouses
    private final static String TAG="MyService";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");

    }

}
```

<h5>Reading Incoming SMS Messages</h5>
To receive Incoming SMS we will need to setup a **Broadcast Receivers**.
To do so we will create a new instance variable BroadcastReciever right on the spot. We will Override the onReciever method to check that the broadcast we are recieving is that of an incoming SMS. If it is we will simply toast to it!

```Java
//Broadcast Receiver
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

```

With the broadcaster already declared, we now need to register the broadcast listener on our Service's onCreate method.

```Java

@Override
public void onCreate() {
    Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
    Log.d(TAG, "onCreate");

	
    //Setup an intent filter which we'll use to tell the system that our particular broadcast reciever is expecting to be triggered when SMS are recieved.
    IntentFilter filter = new IntentFilter();
    filter.addAction("android.provider.Telephony.SMS_RECEIVED");

    registerReceiver(receiver, filter); //Register the reciever

}
```

And we can't forget to unregister the receiver once our service is destroyed.

```Java

public void onDestroy() {
    Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();

    //Unregister the Receiver.
    unregisterReceiver(receiver);

    Log.d(TAG, "onDestroy");

}
```

<h5>Starting the Service</h5>

In our MainActivity's OnCreate method we will start our service. This will essentially start our service as soon as our app starts.

```Java
@Override
protected void onCreate(Bundle savedInstanceState) {

    //start the service from here (MyService)
    startService(new Intent(this, MyService.class));

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
}
```

<h2 id="3_files">Final Code</h2>

Here are the complete manifest and class files for this project.
You can find the complete project [here](SimpleService).

<h5>MainActivity.java</h5>

```Java
package intro.android.cs56.ucsb.edu.simpleservice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //start the service from here (MyService)
        startService(new Intent(this, MyService.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
```

<h5>MyService.java</h5>

```Java
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

```

<h5>AndroidManifest.xml</h5>

```XML
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="intro.android.cs56.ucsb.edu.simpleservice" >

    <uses-permission android:name="android.permission.RECEIVE_SMS" />

    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <service android:enabled="true" android:name=".MyService" />
    </application>

</manifest>

```

<h2 id="3_sources">Sources & Further Reading</h2>
- Services Android Developer: https://developer.android.com/guide/components/services.html#Lifecycle
- Vogella's BroadcastReceivers Tutorial: http://www.vogella.com/tutorials/AndroidBroadcastReceiver/article.html#broadcastreceiver_definition
- BroadcastReceivers Android Developer: https://developer.android.com/reference/android/content/BroadcastReceiver.html
- BroadcastReceivers Tutorial: http://www.tutorialspoint.com/android/android_broadcast_receivers.htm

----------------
######[Back to index](../index.md)######

<!--pmsosa CS56 Winter 2015-->
