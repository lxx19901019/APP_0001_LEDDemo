package com.smile.app_0001_leddemo;

import android.app.Service;
import android.bluetooth.IBluetoothInputDevice;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import android.os.ILedService;
//import android.os.ServiceManager;
public class MainActivity extends AppCompatActivity {

    private boolean ledon = false;
    private Button button = null;
    private CheckBox led1 = null;
    private CheckBox led2 = null;
    private CheckBox led3 = null;
    private CheckBox led4 = null;
    //private ILedService iLedService = null;
    private Method ledCtrl = null;
    private Object proxy = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        led1 = (CheckBox) findViewById(R.id.LED1);
        led2 = (CheckBox) findViewById(R.id.LED2);
        led3 = (CheckBox) findViewById(R.id.LED3);
        led4 = (CheckBox) findViewById(R.id.LED4);

        try {
            //  iLedService = ILedService.Stub.asInterface(ServiceManager.getService("led"));
            Method getService = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder ledService = (IBinder) getService.invoke(null, "led");

            Method asInterface = Class.forName("android.os.ILedService$Stub").getMethod("asInterface", IBinder.class);
            proxy = asInterface.invoke(null, ledService);

            ledCtrl = Class.forName("android.os.ILedService$Stub$Proxy").getMethod("ledCtrl", int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //ledCtrl.invoke(proxy, 0, 1);

        button = (Button) findViewById(R.id.BUTTON);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i;
                ledon = !ledon;
                if(ledon) {
                    button.setText("ALL OFF");

                    led1.setChecked(true);
                    led2.setChecked(true);
                    led3.setChecked(true);
                    led4.setChecked(true);
                    try {
                        for ( i =0 ; i<4;i++)
                         //   iLedService.ledCtrl(i,1);
                            ledCtrl.invoke(proxy, i, 1);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } else {
                    button.setText("ALL ON");

                    led1.setChecked(false);
                    led2.setChecked(false);
                    led3.setChecked(false);
                    led4.setChecked(false);
                    try {
                        for ( i =0 ; i<4;i++)
                           // iLedService.ledCtrl(i,0);
                            ledCtrl.invoke(proxy, i, 0);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked

        try {
            switch(view.getId()) {
                case R.id.LED1:
                    if (checked) {
                        Toast.makeText(getApplicationContext(),"LED1 on", Toast.LENGTH_SHORT).show();
                       // iLedService.ledCtrl(0,1);
                        ledCtrl.invoke(proxy,0, 1);
                    }
                    else {
                       // iLedService.ledCtrl(0,0);
                        ledCtrl.invoke(proxy, 0, 0);
                        Toast.makeText(getApplicationContext(),"LED1 off", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.LED2:
                    if (checked) {
                        //iLedService.ledCtrl(1, 1);
                        ledCtrl.invoke(proxy, 1, 1);
                        Toast.makeText(getApplicationContext(),"LED2 on", Toast.LENGTH_SHORT).show();
                    }
                    else {
                       // iLedService.ledCtrl(1, 0);
                        ledCtrl.invoke(proxy,1, 0);
                        Toast.makeText(getApplicationContext(),"LED2 off", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.LED3:
                    if (checked) {
                        //iLedService.ledCtrl(2, 1);
                        ledCtrl.invoke(proxy, 2, 1);
                        Toast.makeText(getApplicationContext(),"LED3 on", Toast.LENGTH_SHORT).show();
                    }
                    else {
                      //  iLedService.ledCtrl(2, 0);
                        ledCtrl.invoke(proxy, 2, 0);
                        Toast.makeText(getApplicationContext(),"LED3 off", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.LED4:
                    if (checked) {
                      //  iLedService.ledCtrl(3, 1);
                        ledCtrl.invoke(proxy, 3, 1);
                        Toast.makeText(getApplicationContext(),"LED4 on", Toast.LENGTH_SHORT).show();
                    }
                    else {
                       // iLedService.ledCtrl(3, 0);
                        ledCtrl.invoke(proxy, 3, 0);
                        Toast.makeText(getApplicationContext(),"LED4 off", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

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
