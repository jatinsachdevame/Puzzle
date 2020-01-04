package com.example.puzzle;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    Bitmap bitmap;
    Bitmap[][] bitmapArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
    }

    public void clickImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        bitmapArray = new Bitmap[3][3];
        bitmap = Bitmap.createBitmap(bitmap,0,(bitmap.getHeight()-bitmap.getWidth())/2,bitmap.getWidth(),bitmap.getWidth());
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                bitmapArray[i][j] = Bitmap.createBitmap(bitmap, j * bitmap.getWidth() / 3,
                        i * bitmap.getHeight() / 3, bitmap.getWidth() / 3, bitmap.getHeight() / 3);
            }
        }
        bitmapArray[2][2] = Bitmap.createBitmap(bitmap.getWidth()/3, bitmap.getHeight()/3, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas (bitmapArray[2][2]);
        int HEX=0xFFEEEFF1;
        canvas.drawColor (HEX);
        gridView.setColumnWidth(gridView.getWidth()/3);
        gridView.setNumColumns(3);
        gridView.setAdapter(new ImageAdapter(bitmapArray,this,3,gridView.getWidth(),gridView.getHeight()));
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dragAndDrop();
                return true;
            }
        });
    }



    public void dragAndDrop() {
        final View imageView = gridView.getChildAt(8);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                v.startDrag(data,myShadow,v,0);
                return true;
            }
        });

        View target;

        for (int i=0;i<9;++i) {
            target = gridView.getChildAt(i);
            if (!target.equals(imageView)) {
                target.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        if (event.getAction() == DragEvent.ACTION_DROP) {
                            ImageView temp = (ImageView) v;
                            ImageView temp2 = (ImageView) gridView.getChildAt(8);
                            temp2.setImageDrawable(temp.getDrawable());
                            temp.setImageBitmap(bitmap);
                        }
                        return true;
                    }
                });
            }
        }

    }

   /* private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ((event.getAction() == MotionEvent.ACTION_DOWN) && ((ImageView)v).getDrawable() != null) {
                Toast.makeText(MainActivity.this, "Good going", Toast.LENGTH_SHORT).show();
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data,shadowBuilder,v,0);
                return true;
            }else {
                return false;
            }
        }
    }*/
}
