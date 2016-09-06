package com.example.rana.projectme;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.SocketHandler;

/**
 * Created by Rana on 10-Aug-16.
 */
public class ClientTask extends AsyncTask<String, String, String> implements
        MainActivity.OnListener {
    private static Socket socket = null;
    String dstAddress;
    int dstPort;
    PrintWriter out1, out2;
    private static boolean flag = true;
    Context context;

    public static synchronized Socket getSocket(){
        return socket;
    }

    public static synchronized void setSocket(Socket socket){
        ClientTask.socket = socket;
    }
    ClientTask(String addr, int port, Context context) {
        dstAddress = addr;
        dstPort = port;
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);

    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub

        try {
            socket = new Socket(dstAddress, dstPort);
            out1 = new PrintWriter(socket.getOutputStream(), true);
            out1.print(Build.MODEL+","+Build.VERSION.RELEASE+","+"Connected");
            out1.flush();

            /*BufferedReader in1 = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if (socket.isClosed()) {
                flag = false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi nhập!", Toast.LENGTH_LONG).show();
        }

        super.onPostExecute(result);
    }

    @Override
    public void listener(String text) {
        // TODO Auto-generated method stub
    }



}
