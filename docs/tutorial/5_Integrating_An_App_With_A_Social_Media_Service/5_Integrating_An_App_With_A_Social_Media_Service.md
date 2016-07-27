<h1>5. Integrating An Application With A Social Media Service</h1>
<h6>Last updated: osheaanaya | Summer 2016</h6>
######[Back to index](../index.md)######

<h2 id="2_index">Index</h2>

- [Objective](#2_objective)
- [Create A Twitter Account/Create App In Twitter Console](#2_starting)
- [Setting Up App for the Save State](#2_usingAS)
- [Saving An Activity State](#2_testing)
- [Final Code](#2_files)
- [Sources & Further Reading](#2_sources)

---

<h2 id="2_objective">Objective</h2>

The objective for this tutorial is to integrate our app with a social media service. There are a number of popular socail media service but for this tutorial we will be using the Twitter social media service.

<h2 id="2_starting">Create A Twitter Account/Create App In Twitter Console</h2>

- **Step 1:** [Create a Twitter Account] (https://mobile.twitter.com/signup) for the App.
 	- *Note: You can use your personal twitter account but a phone number must be linked to the account to manage applications*
- **Step 2:** [Create new app] (https://dev.twitter.com/apps) in Twitter Console. Log in and click on "Create New App", then fill in the form and create the application.
- **Step 3:** Go to "Settings" and under Application type set the Access type to "Read and Write" and save the settings.
- **Step 4:** Go to "Keys and Access Tokens" and generate Access Tokens
- **Step 5:** We can now use these generated tokens and the "Consumer key" and "Consumer secret" to post tweets from your app

1. We must first [create a Twitter Account] (https://mobile.twitter.com/signup) for the App.

2.  Create New App in Twitter console

Android Studio is based of [IntelliJ IDEA](https://www.jetbrains.com/idea/). 

Managing the lifecycle of your activities by implementing callback methods is crucial to developing a strong and flexible application. The lifecycle of an activity is directly affected by its association with other activities, its task and back stack.

An activity can exist in essentially three states:

- Resumed:
    - The activity is in the foreground of the screen and has user focus. (This state is also sometimes referred to as "running".)
- Paused:
    - Another activity is in the foreground and has focus, but this one is still visible. That is, another activity is visible on top of this one and that activity is partially transparent or doesn't cover the entire screen. A paused activity is completely alive (the Activity object is retained in memory, it maintains all state and member information, and remains attached to the window manager), but can be killed by the system in extremely low memory situations.
- Stopped:
    - The activity is completely obscured by another activity (the activity is now in the "background"). A stopped activity is also still alive (the Activity object is retained in memory, it maintains all state and member information, but is not attached to the window manager). However, it is no longer visible to the user and it can be killed by the system when memory is needed elsewhere.

When an activity is paused or stopped, the state of the activity is retained because the Activity object is still held in memory and all information about its members and current state is still alive. Thus, any changes the user made within the activity are retained so that when the activity returns to the foreground (when it "resumes"), those changes are still there.

However, when the system destroys an activity in order to recover memory, the Activity object is destroyed, so the system cannot simply resume it with its state intact. Instead, the system must recreate the Activity object if the user navigates back to it. Yet, the user is unaware that the system destroyed the activity and recreated it and, thus, probably expects the activity to be exactly as it was. In this situation, you can ensure that important information about the activity state is preserved by implementing an additional callback method that allows you to save information about the state of your activity: onSaveInstanceState().

The system calls onSaveInstanceState() before making the activity vulnerable to destruction. The system passes this method a Bundle in which you can save state information about the activity as name-value pairs, using methods such as putString() and putInt(). Then, if the system kills your application process and the user navigates back to your activity, the system recreates the activity and passes the Bundle to both onCreate() and onRestoreInstanceState(). Using either of these methods, you can extract your saved state from the Bundle and restore the activity state. If there is no state information to restore, then the Bundle passed to you is null (which is the case when the activity is created for the first time).

![Taken from DeveloperAndroid](basic-lifecycle-savestate.png)

- **Note:** There's no guarantee that onSaveInstanceState() will be called before your activity is destroyed, because there are cases in which it won't be necessary to save the state (such as when the user leaves your activity using the Back button, because the user is explicitly closing the activity). If the system calls onSaveInstanceState(), it does so before onStop() and possibly before onPause().

The default implementation calls the corresponding onSaveInstanceState() method for every View in the layout, which allows each view to provide information about itself that should be saved. Almost every widget in the Android framework implements this method as appropriate, such that any visible changes to the UI are automatically saved and restored when your activity is recreated. For example, the EditText widget saves any text entered by the user and the CheckBox widget saves whether it's checked or not. 

Although the default implementation of onSaveInstanceState() saves useful information about your activity's UI, you still might need to override it to save additional information. For example, you might need to save member values that changed during the activity's life (which might correlate to values restored in the UI, but the members that hold those UI values are not restored, by default). Because the default implementation of onSaveInstanceState() helps save the state of the UI, if you override the method in order to save additional state information, you should always call the superclass implementation of onSaveInstanceState() before doing any work. Likewise, you should also call the superclass implementation of onRestoreInstanceState() if you override it, so the default implementation can restore view states.

- **Note:** Because onSaveInstanceState() is not guaranteed to be called, you should use it only to record the transient state of the activity (the state of the UI)—you should never use it to store persistent data. Instead, you should use onPause() to store persistent data (such as data that should be saved to a database) when the user leaves the activity. In this tutorial we will record the transient state of the activity.

A good way to test your application's ability to restore its state is to rotate the device so that the screen orientation changes. When the screen orientation changes, the system destroys and recreates the activity in order to apply alternative resources that might be available for the new screen configuration. 

<h2 id="2_usingAS">Setting Up the App for the Save State</h2>

We first modify the simple "Test Prime" application to count the number of prime integers a user has entered. We add a counter instance variable to MainActivity:

```Java
int counter;
```
We then add a Medium Text Widget to display the number of prime integers that the user has entered (the value of counter). Our layout should look something like:

![Taken from DeveloperAndroid](Counter_added.png)

We then add the following code to the test_prime method to increment counter everytime isPrime returns true and display it in the new Text Widget:
```Java
TextView textView3 = (TextView) findViewById(R.id.textView3); //added
if (prime) {
	counter++; //added
	textView2.setText(i + " is a prime");
	textView3.setText(_counter + " Prime Integers Entered"); //added
	}
```
When we build and run the app again, we see that the number of primes entered is now displayed. However, when we rotate the device the count is lost. As we previously stated, the Activity was paused, stopped, destroyed, recreated, restarted, then resumed during the rotation from portrait to landscape mode. Because the Activity is destroyed and recreated again when the device is rotated, its instance state is lost. Next, we will add code to save and restore the instance state.

<h2 id="2_testing">Saving An Activity State</h2>

We must add a method to MainActivity to save the instance state. Before the Activity is destroyed, Android automatically calls OnSaveInstanceState and passes in a Bundle that we can use to store our instance state. We will use it to save our count as an integer value:

```Java
protected override void OnSaveInstanceState (Bundle outState){
    outState.putInt("prime_count", counter);

    // always call the base implementation!
    super.OnSaveInstanceState(outState);    
}
```
When the Activity is recreated and resumed, Android passes this Bundle back into our OnCreate method. We must now add the following code to OnCreate to restore the counter value from the passed-in Bundle:

```Java
if (savedInstanceState != null){
    counter = savedInstanceState.getInt("prime_count");
}
```
Instead of restoring the state during onCreate() you may choose to implement onRestoreInstanceState(), which the system calls after the onStart() method. The system calls onRestoreInstanceState() only if there is a saved state to restore, so you do not need to check whether the Bundle is null:

```Java
protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    counter = savedInstanceState.getInt("prime_count");
}

```

**Caution:** Always call the superclass implementation of onRestoreInstanceState() so the default implementation can restore the state of the view hierarchy

Build and run the app again, then enter some prime numbers. When we rotate the device to landscape mode, the count is saved!

<h2 id="2_files">Final Code</h2>

Here are the complete manifest and class files for the updated project.

<h5>MainActivity.java</h5>

```Java
package intro.android.cs56.ucsb.edu.helloworld;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;	        //For our button callback function's parameters
import android.widget.TextView;     //For accessing our textViews
import android.widget.Toast; //For 'visual debugging' on phone
import android.util.Log;            //For actual debugging

public class MainActivity extends ActionBarActivity {

    int counter; //added

    @Override   //You MUST implement this
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            counter = savedInstanceState.getInt("prime_count");
        }
    }

    @Override //added
    protected override void OnSaveInstanceState (Bundle outState){
        outState.putInt("prime_count", counter);

        // always call the base implementation!
        super.OnSaveInstanceState(outState);    
    }
    
    @Override //added
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("prime_count");
    }

    @Override   //You MUST implement this
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override   //You MUST implement this
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

    //Button Callback Function
    public void test_prime(View view){

        //1. GET INPUT
        TextView textView = (TextView) findViewById(R.id.editText); //Obtain the TextView element where the user provides the input
        String str = textView.getText().toString(); //Get the text currently assigned to this input. (Since it is of inputType number we are assured that there will only be pos ints)

        //2. TEST INPUT
        if (!str.isEmpty()) { //Check that there is input
            Log.d("test_prime","Testing number"+str);
            int i = Integer.parseInt(str); //Turn the string into an int.

            //2.1 TEST PRIMENESS
            boolean prime = isPrime(i);

            //2.2 REPORT THE RESULT
            TextView textView2 = (TextView) findViewById(R.id.textView2);

            TextView textView3 = (TextView) findViewById(R.id.textView3); //added
            
            if (prime) {
	        counter++; //added
	        textView2.setText(i + " is a prime");
	        textView3.setText(_counter + " Prime Integers Entered"); //added
	        }
            else { textView2.setText(i + " is not a prime");}

            Log.d("test_prime",i+" -> prime:"+(boolean)prime);

        } else{//The user could have pressed the button without providing any number.
            Log.d("test_prime","No number was provided");
            Toast.makeText(this,"No number provided",Toast.LENGTH_LONG).show();
        }
    }

    //Tests if a number is prime
    private boolean isPrime(int i){
        boolean flag = true; //All inputs are considered prime until proven composite

        if (i < 2){ flag = false;} //0,1 are not primes
        else if (i == 2){ flag = true;} //2 is the only even prime
        else if ((i%2)==0){flag = false;} //Other multiple of 2 are not primes
        else {  //Odd numbers, we must check by bruteforce.
            for (int k = 3; k < Math.sqrt(i); k=k+2) {
                if (i%k==0){ flag = false;}    //if i is a multiple of some odd number; not a prime
            }
        }
        return flag;
    }
}

```
<h5>Manifest</h5>

```XML
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin" tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:text="Is this Number Prime?"
        android:id="@+id/textView"
        android:layout_centerHorizontal="true" />

    <EditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:inputType="number"
        android:ems="10"
        android:id="@+id/editText"
        android:layout_below="@+id/textView"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="52dp"
        android:hint="Input Integer" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/button"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true"
        android:text="Test!"
        android:nestedScrollingEnabled="false"
        android:onClick="test_prime" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceMedium"
        android:id="@+id/textView2"
        android:layout_below="@+id/editText"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="48dp"
        android:text="[Result goes here]" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceMedium"
        android:text="Prime Integers Entered"
        android:id="@+id/textView3"
        android:layout_below="@+id/button"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="59dp"
        android:hint="Prime Integers Entered" />

</RelativeLayout>

```

<h2 id="2_sources">Sources & Further Reading</h2>
- Posting To Twitter, Facebook...: https://www.git-tower.com/blog/automated-posting-to-social-media-platforms/
- How To Integrate Twitter In Android Application: http://stacktips.com/tutorials/android/how-to-integrate-twitter-in-android-application
- How To Post A Tweet in an Android App: https://stackoverflow.com/questions/10272355/how-to-post-a-tweet-from-an-android-app-to-one-specific-account

----------
######[Back to index](../index.md)######

<!--osheaanaya CS56 Summer 2016-->



