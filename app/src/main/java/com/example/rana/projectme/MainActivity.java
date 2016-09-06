package com.example.rana.projectme;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Socket;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView test1, test2;
    private EditText editTextAddress, editTextPort;
    private Button buttonConnect;
    private ClientTask myClientTask;
    private OnListener listener;
    private static boolean flag = true;
    final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public interface OnListener {
        void listener(String text);
    }

    public void addListener(OnListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getFilePermission(this)== true){
            test1 = (TextView)findViewById(R.id.textViewTest1);
            test2 = (TextView)findViewById(R.id.textViewTest2);
            editTextAddress = (EditText) findViewById(R.id.editTextAddress);
            editTextPort = (EditText) findViewById(R.id.editTextPort);
            buttonConnect = (Button) findViewById(R.id.buttonConnect);

            buttonConnect.setOnClickListener(this);
        }
        else{
            test1 = (TextView)findViewById(R.id.textViewTest1);
            test2 = (TextView)findViewById(R.id.textViewTest2);
            editTextAddress = (EditText) findViewById(R.id.editTextAddress);
            editTextPort = (EditText) findViewById(R.id.editTextPort);
            buttonConnect = (Button) findViewById(R.id.buttonConnect);

            buttonConnect.setOnClickListener(this);

        }
    }


    public Boolean getFilePermission(Context context)
    {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage("External storage permission is necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getParent(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);}});

                AlertDialog alert = alertBuilder.create();
                alert.show();
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);}
            return false;}
        else {
            return true;
        }
    }
    public void onClick(View v) {
        Button connect = (Button) v;

        if (connect == buttonConnect)
        {
            myClientTask = new ClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText()
                    .toString()), this.getApplicationContext());
            myClientTask.execute();
            Intent intent = new Intent(this, FileChooser.class);
            startActivity(intent);

        }
        // TODO Auto-generated method stub

    }
}
