package com.hfad.boatcontroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
    public Context context = null;
    Button connectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Connecting with the Boat (RPI)
        if(savedInstanceState == null){
            context = null;
            AlertDialog.Builder startWindow = new AlertDialog.Builder(this);
            startWindow.setCancelable(true);
            String message = "This app requires a working Wi-Fi. Do you want to turn it on?";
            startWindow.setMessage(message);
            startWindow.setTitle("Connect with the boat");

            startWindow.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });
            startWindow.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            startWindow.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    return;
                }
            });

            startWindow.show();
        }

        connectButton = (Button) findViewById(R.id.connectButton);
        // add a click effect to connectButton
        buttonClickMethod(connectButton);
        //when the button was clicked and the ip is correct, it starts the ControlActivity
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControlActivity.class);
                EditText editText = (EditText) findViewById(R.id.IPBoat);
                editText.getText().toString();
                if(editText.getText().toString().contentEquals("169.254.156.56")) {
                    intent.putExtra("ip", editText.getText().toString());
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(v.getContext().getApplicationContext(),"Wrong ip adress. Please try again!" , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.author_info:
                return true;
            case R.id.connect:
                return true;
            case R.id.exit:
                System.exit(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //method which add a click effect to button
    void buttonClickMethod(View view)
    {
        Drawable normalButton = view.getBackground();
        Drawable pressedButton = view.getBackground().getConstantState().newDrawable();
        pressedButton.mutate();
        pressedButton.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
        StateListDrawable statesList = new StateListDrawable();
        statesList.addState(new int[] {android.R.attr.state_pressed}, pressedButton);
        statesList.addState(new int[] {}, normalButton);
        view.setBackground(statesList);
    }
}
