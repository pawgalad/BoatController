package com.hfad.boatcontroller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ControlActivity extends Activity  {

    LinearLayout controllerLayout;
    Controller cn;
    Controller controller;
    private boolean sendOnUp = true;
    private boolean sendOnLeft = true;
    public boolean sendOnDown = false;
    private boolean sendOnRight = true;
    private boolean sendOnStop = true;
    private boolean sendOnRightUp1 = true;
    private boolean sendOnRightUp2 = true;
    private boolean sendOnRightDown1 = true;
    private boolean sendOnRightDown2 = true;
    private boolean sendOnLeftDown1 = true;
    private boolean sendOnLeftDown2 = true;
    private boolean sendOnLeftUp1 = true;
    private boolean sendOnLeftUp2 = true;
    private int direction = 0;
    private String boatIP = "";
    private int port = 5000;

    public static DataOutputStream dataOutputStream;
    public static Socket clientSocket;
    public ConnectionBySocket sendSignals;

    RadioButton firstGear, secondGear, thirdGear;
    ToggleButton  load;

    public ControlActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        controllerLayout = (LinearLayout) findViewById(R.id.layout_controller);
        controller = new Controller(controllerLayout, R.drawable.image_button, getApplicationContext());
        controllerLayout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                controller.setMotionEvent(motionEvent);
                controller.drawController();
                if((motionEvent.getAction() == MotionEvent.ACTION_DOWN)|| (motionEvent.getAction() == MotionEvent.ACTION_MOVE)) {

                    direction = controller.checkAngle();
                    if(direction == Controller.UP) {
                        if(sendOnUp == true) {
                            sendOnLeft = true;
                            sendOnStop = true;
                            sendOnRight = true;
                            sendOnDown = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 =true;
                            if (dataOutputStream != null) {
                                try {
                                    dataOutputStream.writeByte(1);
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnUp = false;
                        }

                    } else if(direction == Controller.RIGHTUP1) {
                        if(sendOnRightUp1 == true) {
                            sendOnLeft = true;
                            sendOnStop = true;
                            sendOnRight = true;
                            sendOnDown = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnRightUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 = true;
                            sendOnUp = true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(2);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnRightUp1 = false;
                        }


                    }

                    else if(direction == Controller.RIGHTUP2) {

                        if(sendOnRightUp2 == true) {
                            sendOnLeft = true;
                            sendOnStop = true;
                            sendOnRight = true;
                            sendOnDown = true;
                            sendOnUp = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 = true;

                            if (dataOutputStream != null) {
                                try {
                                    dataOutputStream.writeByte(3);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnRightUp2 = false;
                        }
                    }

                    else if(direction == Controller.RIGHT) {
                        if(sendOnRight == true) {
                            sendOnLeft = true;
                            sendOnStop = true;
                            sendOnDown = true;
                            sendOnUp = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 =true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(4);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnRight = false;
                        }

                    } else if(direction == Controller.RIGHTDOWN1) {
                        if(sendOnRightDown1 == true) {
                            sendOnLeft = true;
                            sendOnStop = true;
                            sendOnRight = true;
                            sendOnDown = true;
                            sendOnUp = true;
                            sendOnRightUp2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightDown2 = true;
                            sendOnLeftDown2 = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(5);

                                } catch (IOException e) {

                                    e.printStackTrace();
                                }
                            }
                            sendOnRightDown1 = false;
                        }
                    }


                    else if(direction == Controller.RIGHTDOWN2) {
                        if(sendOnRightDown2 == true) {

                            sendOnLeft = true;
                            sendOnStop = true;
                            sendOnRight = true;
                            sendOnDown = true;
                            sendOnUp = true;
                            sendOnRightUp2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(6);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnRightDown2 = false;
                        }

                    }else if(direction == Controller.DOWN) {

                        if (sendOnDown == true) {

                            sendOnLeft = true;
                            sendOnStop = true;
                            sendOnRight = true;
                            sendOnUp = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 = true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(7);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnDown = false;
                        }
                    }

                    else if(direction == Controller.LEFTDOWN2) {

                        if (sendOnLeftDown2 == true) {
                            sendOnUp = true;
                            sendOnLeft = true;
                            sendOnRight = true;
                            sendOnStop = true;
                            sendOnLeftDown1 = true;
                            sendOnDown = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(8);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnLeftDown2 = false;
                        }
                    }
                    else if(direction == Controller.LEFTDOWN1) {

                        if (sendOnLeftDown1 == true) {
                            sendOnUp = true;
                            sendOnLeft = true;
                            sendOnRight = true;
                            sendOnStop = true;
                            sendOnDown = true;
                            sendOnLeftDown2 = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(9);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnLeftDown1 = false;
                        }


                    }else if(direction == Controller.LEFT) {

                        if(sendOnLeft == true) {

                            sendOnUp = true;
                            sendOnStop = true;
                            sendOnRight = true;
                            sendOnDown = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 =true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(10);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnLeft = false;
                        }

                    } else if(direction == Controller.LEFTUP2) {

                        if (sendOnLeftUp2 == true) {

                            sendOnUp = true;
                            sendOnLeft = true;
                            sendOnRight = true;
                            sendOnStop = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnLeftUp1 = true;
                            sendOnDown = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(11);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnLeftUp2 = false;
                        }
                    }else if(direction == Controller.LEFTUP1) {

                        if (sendOnLeftUp1 == true) {
                            sendOnUp = true;
                            sendOnLeft = true;
                            sendOnRight = true;
                            sendOnStop = true;
                            sendOnDown = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnLeftUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;

                            if (dataOutputStream != null) {

                                try {

                                    dataOutputStream.writeByte(12);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnLeftUp1 = false;
                        }

                    }

                    else if(direction == Controller.STOP) {

                        if(sendOnStop == true) {

                            sendOnLeft = true;
                            sendOnUp = true;
                            sendOnRight = true;
                            sendOnDown = true;
                            sendOnLeftUp1 = true;
                            sendOnLeftUp2 = true;
                            sendOnLeftDown1 = true;
                            sendOnLeftDown2 = true;
                            sendOnRightUp1 = true;
                            sendOnRightUp2 = true;
                            sendOnRightDown1 = true;
                            sendOnRightDown2 =true;
                            if (dataOutputStream != null) {
                                try {

                                    dataOutputStream.writeByte(13);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            sendOnStop = false;
                        }

                    }
                }
                return true;
            }
        });

        Intent intent = getIntent();
        if (savedInstanceState != null){
            boatIP = savedInstanceState.getString("ip");
            port = savedInstanceState.getInt("port");

        }

        if ((intent.getStringExtra("ip") != null) && (savedInstanceState == null)) {

            boatIP = intent.getStringExtra("ip");

        }
        new Thread(new ConnectionBySocket(boatIP, port)).start();

        firstGear = (RadioButton) findViewById(R.id.firstGear);
        secondGear = (RadioButton) findViewById(R.id.secondGear);
        thirdGear= (RadioButton) findViewById(R.id.thirdGear);


        firstGear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (dataOutputStream != null) {
                    try {

                        dataOutputStream.writeByte(14);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        secondGear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (dataOutputStream != null) {
                    try {

                        dataOutputStream.writeByte(15);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                 }

            }
        });
        thirdGear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (dataOutputStream!= null) {
                    try {

                        dataOutputStream.writeByte(16);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        load = (ToggleButton) findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataOutputStream != null) {
                    try {

                        dataOutputStream.writeByte(17);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.control_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.author:

                return true;

            case R.id.connect:

                return true;

            case R.id.shut_rpi:
                if(dataOutputStream != null) {
                    try {
                        dataOutputStream.writeByte(18);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        if (dataOutputStream != null) {
            try {

                dataOutputStream.writeByte(13);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if (dataOutputStream != null) {
            try {

                dataOutputStream.writeByte(13);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onStart()
    {
        super.onStart();
        final String streamAddr = "http://169.254.156.56:8080/stream";
        final WebView webView =  findViewById(R.id.webView);
        if (dataOutputStream != null) {
            try {

                dataOutputStream.writeByte(13);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int zoomLevel = 100;
        webView.setInitialScale(zoomLevel);
        webView.post(new Runnable()
        {
            @Override
            public void run() {
                int width = webView.getWidth();
                int height = webView.getHeight();
                webView.loadUrl(streamAddr + "?width="+width+"&height="+height);
            }
        });

        webView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((WebView) v).reload();
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            if (clientSocket.isConnected() == true) {
                clientSocket.close();
                dataOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {

        super.onSaveInstanceState(savedState);
        savedState.putString("ip", boatIP);
        savedState.putInt("port", port);
    }


}
