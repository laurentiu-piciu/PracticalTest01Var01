package ro.pub.cs.systems.eim.practicaltest01var01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var01MainActivity extends AppCompatActivity {

    private Button northButton = null;
    private Button southButton = null;
    private Button westButton = null;
    private Button eastButton = null;
    private Button navigateButton = null;
    private EditText desctiptionText = null;
    private int nr_clicks = 0;

    private int serviceStatus = Constants.SERVICE_STOPPED;

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;



    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String current_value = String.valueOf(desctiptionText.getText());
            switch (view.getId()) {
                case R.id.north_button:
                    current_value += ",";
                    current_value += String.valueOf(northButton.getText().toString());
                    desctiptionText.setText(current_value);
                    nr_clicks += 1;
                    break;
                case R.id.south_button:
                    current_value += ",";
                    current_value += String.valueOf(southButton.getText().toString());
                    desctiptionText.setText(current_value);
                    nr_clicks += 1;
                    break;
                case R.id.west_button:
                    current_value += ",";
                    current_value += String.valueOf(westButton.getText().toString());
                    desctiptionText.setText(current_value);
                    nr_clicks += 1;
                    break;
                case R.id.east_button:
                    current_value += ",";
                    current_value += String.valueOf(eastButton.getText().toString());
                    desctiptionText.setText(current_value);
                    nr_clicks += 1;
                    break;
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01SecondaryActivity.class);
                    intent.putExtra("crtButton", String.valueOf(desctiptionText.getText()));
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    desctiptionText.setText("");
                    nr_clicks = 0;
                    break;
            }

            if (nr_clicks == Constants.CLICK_THRESH && serviceStatus == Constants.SERVICE_STOPPED) {
                Log.d(Constants.TAG, "Sent to service " + String.valueOf(desctiptionText.getText()));
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01Service.class);
                intent.putExtra("instruction", String.valueOf(desctiptionText.getText()));
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_main);

        northButton = findViewById(R.id.north_button);
        southButton = findViewById(R.id.south_button);
        westButton = findViewById(R.id.east_button);
        eastButton = findViewById(R.id.west_button);
        navigateButton = findViewById(R.id.navigate_to_secondary_activity_button);
        desctiptionText = findViewById(R.id.textView1);

        northButton.setOnClickListener(buttonClickListener);
        southButton.setOnClickListener(buttonClickListener);
        westButton.setOnClickListener(buttonClickListener);
        eastButton.setOnClickListener(buttonClickListener);
        navigateButton.setOnClickListener(buttonClickListener);

        if (savedInstanceState == null) {
            Log.d(Constants.TAG, "onCreate() method was invoked without a previous state");
        } else {
            if (savedInstanceState.containsKey("nrClicks")) {
                nr_clicks = savedInstanceState.getInt("nrClicks");
            } else {
                nr_clicks = 0;
            }

            Log.d(Constants.TAG, "onCreate() method was invoked WITH a previous state: " + nr_clicks);
            Toast.makeText(this, "Nr_clicks: " + nr_clicks, Toast.LENGTH_LONG).show();
        }

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG, "onRestart() method was invoked");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constants.TAG, "onStart() method was invoked");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Constants.TAG, "onResume() method was invoked");
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        Intent intent = new Intent(this, PracticalTest01Var01Service.class);
        stopService(intent);
        unregisterReceiver(messageBroadcastReceiver);
        Log.d(Constants.TAG, "onPause() method was invoked");
        super.onPause();
    }



    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop() method was invoked");
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var01Service.class);
        stopService(intent);
        Log.d(Constants.TAG, "onDestroy() method was invoked");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Constants.TAG, "onSaveInstanceState()");
        outState.putInt("nrClicks", nr_clicks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        if (savedInstanceState.containsKey("nrClicks")) {
            nr_clicks = savedInstanceState.getInt("nrClicks");
        } else {
            nr_clicks = 0;
        }

        Log.d(Constants.TAG, "onRestoreInstanceState(): " + nr_clicks);
        Toast.makeText(this, "Nr_clicks: " + nr_clicks, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(Constants.TAG, "onActivityResult()");
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.TAG, "Message received: " + intent.getStringExtra("message"));
        }
    }
    private IntentFilter intentFilter = new IntentFilter();
}
