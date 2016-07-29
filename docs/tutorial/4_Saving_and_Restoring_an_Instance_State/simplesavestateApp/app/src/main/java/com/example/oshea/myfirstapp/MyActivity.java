package com.example.oshea.myfirstapp;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;     //For accessing our textViews
import android.widget.Toast; //For 'visual debugging' on phone
import android.util.Log;




public class MyActivity extends AppCompatActivity {


    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("prime_count");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("prime_count", counter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("prime_count");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    public void test_prime(View view) {
        //1. GET INPUT
        TextView textView = (TextView) findViewById(R.id.editText); //Obtain the TextView element where the user provides the input
        String str = textView.getText().toString(); //Get the text currently assigned to this input. (Since it is of inputType number we are assured that there will only be pos ints)
        //2. TEST INPUT
        if (!str.isEmpty()) { //Check that there is input
            Log.d("test_guess", "Testing guess" + str);
            int i = Integer.parseInt(str); //Turn the string into an int
            //2.1 TEST PRIMENESS
            boolean prime = isPrime(i);
            //2.2 REPORT THE RESULT
            TextView textView2 = (TextView) findViewById(R.id.textView2);
            TextView textView3 = (TextView) findViewById(R.id.textView3);

            if (prime) {
                counter++;
                textView2.setText(i + " is Prime!");
                textView3.setText(counter + " Primes Integers Entered");
            } else {
                textView2.setText(i + " is not Prime");
            }

            Log.d("test_prime", i + " -> prime:" + prime);
        } else {//The user could have pressed the button without providing any number.
            Log.d("test_prime", "No number was provided");
            Toast.makeText(this, "No number provided", Toast.LENGTH_LONG).show();
        }
    }

    //Tests if a number is prime
    private boolean isPrime(int i) {
        boolean flag = true; //All inputs are considered prime until proven composite

        if (i < 2) {
            flag = false;
        } //0,1 are not primes
        else if (i == 2) {
            flag = true;
        } //2 is the only even prime
        else if ((i % 2) == 0) {
            flag = false;
        } //Other multiple of 2 are not primes
        else {  //Odd numbers, we must check by bruteforce.
            for (int k = 3; k < Math.sqrt(i); k = k + 2) {
                if (i % k == 0) {
                    flag = false;
                }    //if i is a multiple of some odd number; not a prime
            }
        }
        return flag;
    }


}