package com.example.rana.projectme;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Rana on 31-Aug-16.
 */
public class CaptureScreen {

    ListActivity listActivity2;

    public CaptureScreen(ListActivity list)
    {
        listActivity2 = list;
    }
    public Bitmap getScreen(){

        View v1 = listActivity2.getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);

        return bitmap;
    }
}
