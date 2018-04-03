package ro.pub.cs.systems.eim.practicaltest01var01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var01SecondaryActivity extends Activity {
    private TextView receivedStringTextView = null;
    private Button registerButton = null;
    private Button cancelButton = null;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.register_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_secondary);

        registerButton = findViewById(R.id.register_button);
        cancelButton = findViewById(R.id.cancel_button);
        receivedStringTextView = findViewById(R.id.textView2);

        registerButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);


        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("crtButton")) {
            receivedStringTextView.setText(intent.getStringExtra("crtButton"));
        }
    }
}
