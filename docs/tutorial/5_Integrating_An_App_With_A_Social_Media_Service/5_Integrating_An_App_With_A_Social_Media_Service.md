<h1>5. Integrating An Application With A Social Media Service</h1>
<h6>Last updated: osheaanaya | Summer 2016</h6>
######[Back to index](../index.md)######

<h2 id="2_index">Index</h2>

- [Objective](#2_objective)
- [Create A Twitter Account/Create App In Twitter Console](#2_starting)
- [Integrate Twitter Library](#2_usingAS)
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

<h2 id="2_usingAS">Integrate Twitter Library</h2>
- **Step 1:** [Download the Twitter4J Library Here.] (twitter4j.org/en/) Twitter4J is an unofficial Java library for the Twitter API. With Twitter4J, you can easily integrate your Java application with the Twitter service. 
	- *Note: Twitter4j is an unofficial library*
- **Step 2:** Unzip the Twitter4J Library and add twitter4j-core-4.0.2.jar to libs folder of your application.
- **Step 3:** Import the Twitter4J library. Add the following lines to your MainActivity.java Class:

```Java
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
```

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



<h2 id="2_sources">Sources & Further Reading</h2>
- Posting To Twitter, Facebook...: https://www.git-tower.com/blog/automated-posting-to-social-media-platforms/
- How To Integrate Twitter In Android Application: http://stacktips.com/tutorials/android/how-to-integrate-twitter-in-android-application
- How To Post A Tweet in an Android App: https://stackoverflow.com/questions/10272355/how-to-post-a-tweet-from-an-android-app-to-one-specific-account

----------
######[Back to index](../index.md)######

<!--osheaanaya CS56 Summer 2016-->



