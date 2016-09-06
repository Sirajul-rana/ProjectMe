package com.example.rana.projectme;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Rana on 25-Aug-16.
 */
public class FileChooser extends ListActivity {

    private File currentDir;
    private FileArrayAdapter fileAdapter;
    AlertDialog alertDialog;
    Option op = new Option("","","");
    Socket socket = null;
    CaptureScreen screen;
    File file;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File("/sdcard/");
        socket = ClientTask.getSocket();
        screen = new CaptureScreen(this);
        fill(currentDir);
    }

    private void fill(File file){

        this.setListAdapter(fileAdapter);
        File[] dirs = file.listFiles();
        this.setTitle("Current Dir: "+ file.getName());
        List<Option> dir = new ArrayList<>();
        List<Option> fls = new ArrayList<>();

        try {
            for(File f: dirs){
                if (f.isDirectory()){
                    dir.add(new Option(f.getName(), "Folder", f.getAbsolutePath()));
                }
                else{
                    fls.add(new Option(f.getName(),"File Size:"+f.length(), f.getAbsolutePath()));
                }

            }
        }
        catch (Exception e){

        }

        Collections.sort(dir);
        Collections.sort(fls);

        dir.addAll(fls);
        if(!file.getName().equalsIgnoreCase("sdcard")){
            dir.add(0,new Option("..","Parent Directory",file.getParent()));
        }
        fileAdapter = new FileArrayAdapter(FileChooser.this,R.layout.file_view, dir);
        this.setListAdapter(fileAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        screen = new CaptureScreen(this);
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Option o = fileAdapter.getItem(position);
        if (o.getData().equalsIgnoreCase("folder") || o.getData().equalsIgnoreCase("parent directory")) {
            currentDir = new File(o.getPath());
            fill(currentDir);

        } else {
            onFileClick(o.getName());
        }

        bitmap = screen.getScreen();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onFileClick(String o) {


        Toast.makeText(getParent(), o, Toast.LENGTH_SHORT).show();
    }

}
