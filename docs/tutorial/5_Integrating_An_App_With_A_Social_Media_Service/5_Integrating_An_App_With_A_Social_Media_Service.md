<h1>5. Integrating An Application With A Social Media Service</h1>
<h6>Last updated: osheaanaya | Summer 2016</h6>
######[Back to index](../index.md)######

<h2 id="2_index">Index</h2>

- [Objective](#2_objective)
- [Create A Twitter Account/Create App In Twitter Console](#2_starting)
- [Integrate Twitter Library](#2_usingAS)
- [Add the Tweet Button](#2_testing)
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

<h2 id="2_testing">Add The Tweet Button</h2>

- **Step 1:** Add a new button to the application layout. This will be the button which "Tweets" the number of primes.
- **Step 2:** Add code for the tweet_primes method:
```Java
    public void tweet_primes(View v){
        String message = "" + counter + " Prime Integers Entered"
        String token ="<Your access token>";
        String secret = "<Your access token secret>";
        AccessToken a = new AccessToken(token,secret);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("<Your consumer key>", "<Your consumer secret>");
        twitter.setOAuthAccessToken(a);
        try {

           twitter.updateStatus(message);
        } catch (TwitterException e) {
            Log.d("Failed to post!", e.getMessage());
        }


    }
```
- *Note: You must enter your own Tokens, Keys, and Secrets*

- **Step 3:** Modify the onClick() property of the button to call tweet_primes method
- **Step 4:** Build and Run the Application!

<h2 id="2_sources">Sources & Further Reading</h2>
- Posting To Twitter, Facebook...: https://www.git-tower.com/blog/automated-posting-to-social-media-platforms/
- How To Integrate Twitter In Android Application: http://stacktips.com/tutorials/android/how-to-integrate-twitter-in-android-application
- How To Post A Tweet in an Android App: https://stackoverflow.com/questions/10272355/how-to-post-a-tweet-from-an-android-app-to-one-specific-account

----------
######[Back to index](../index.md)######

<!--osheaanaya CS56 Summer 2016-->



