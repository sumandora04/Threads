package com.notepoint4u.threads;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int[] memo = new int[1024];

    Handler mainHandler = new Handler();
    private volatile boolean stopThread = false;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        System.out.println(fibinacciSeries(20));

        String string = "ABC";
        StringBuffer buffer = new StringBuffer();
        buffer.append(string);
        buffer = buffer.reverse();

        System.out.println(buffer);

        System.out.println(reverseString("SHILPA"));

        System.out.println("Factorial: "+factorialOf(5));
    }

    public int fibinacciSeries(int num){
        if (num<=0)
            return 0;

        else if (num==1)
            return 1;
        else if (memo[num]==0){
            memo[num] = fibinacciSeries(num-1) + fibinacciSeries(num-2);
        }

        return memo[num];
    }


    private String reverseString(String string){
        StringBuilder reverse = new StringBuilder();
        char[] array = string.toCharArray();
        for (int i = array.length-1; i >=0; i--) {
            reverse.append(array[i]);
        }

        return reverse.toString();
    }


    private int factorialOf(int number){
        if (number==1)
            return 1;

        return number* factorialOf(number-1);
    }
    public void startThread(View view) {

        stopThread = false;
        // 1:
       /* new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: "+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/


       // 2:
       ExampleRunnable runnable = new ExampleRunnable(10);
       new Thread(runnable).start();

       // 3:
        /*ExampleThread thread = new ExampleThread(10);
        thread.start();*/
    }


    public void stopThread(View view) {
        stopThread = true;
    }


    class ExampleRunnable implements Runnable{

        int seconds;

        public ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 1; i <=seconds; i++) {
                Log.d(TAG, "run: "+i);
                if (stopThread){
                    return;
                }

                final int finalI = i;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(""+(finalI *10)+"%");
                    }
                });

                if (i==5){
                    // 1:
                   /* Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("50%");
                        }
                    });*/

                   // 2:

                    /*textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("50%");
                        }
                    });*/

                    // 3:
                   /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("50%");
                        }
                    });*/

                   // 4:
                   /* mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("50%");
                        }
                    });*/

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleThread extends Thread{
        int seconds;
        ExampleThread(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "run: "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
