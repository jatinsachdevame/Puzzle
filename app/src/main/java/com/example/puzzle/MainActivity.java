package com.example.puzzle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
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
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        bitmapArray = new Bitmap[3][3];
        bitmap = Bitmap.createBitmap(bitmap,0,(bitmap.getHeight()-bitmap.getWidth())/2,bitmap.getWidth(),bitmap.getWidth());
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                bitmapArray[i][j] = Bitmap.createBitmap(bitmap, j * bitmap.getWidth() / 3,
                        i * bitmap.getHeight() / 3, bitmap.getWidth() / 3, bitmap.getHeight() / 3);
            }
        }
        renderImages();
    }

    public void renderImages() {
        gridView.setColumnWidth(gridView.getWidth()/3);
        gridView.setNumColumns(3);
        gridView.setAdapter(new ImageAdapter(bitmapArray,this,3,gridView.getWidth(),gridView.getHeight()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d("position : ",Integer.toString(i));
            }
        });
    }
}
