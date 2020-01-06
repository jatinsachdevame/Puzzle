package com.example.puzzle;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    Bitmap bitmap;
    int N = 5;

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
        renderImages();
    }



    public void renderImages() {
        Bitmap[] bitmapArray = new Bitmap[N*N];
        int k=0;
        bitmap = Bitmap.createBitmap(bitmap,0,(bitmap.getHeight()-bitmap.getWidth())/2,bitmap.getWidth(),bitmap.getWidth());
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                bitmapArray[k++] = Bitmap.createBitmap(bitmap, j * bitmap.getWidth() / N,
                        i * bitmap.getHeight() / N, bitmap.getWidth() / N, bitmap.getHeight() / N);
            }
        }
        Bitmap temp;
        Random random = new Random();
        for (int i=0;i<N*N;++i) {
            int randomIndex1 = random.nextInt(N*N);
            int randomIndex2 = random.nextInt(N*N);
            temp = bitmapArray[randomIndex1];
            bitmapArray[randomIndex1] = bitmapArray[randomIndex2];
            bitmapArray[randomIndex2] = temp;
        }
        gridView.setColumnWidth(gridView.getWidth()/N);
        gridView.setNumColumns(N);
        gridView.setAdapter(new ImageAdapter(bitmapArray,this,N,gridView.getWidth(),gridView.getHeight()));
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dragAndDrop();
                return true;
            }
        });
    }



    public void takeSizeInput(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_edittext, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.sizeInput);

        dialogBuilder.setTitle("Enter size of the grid");
        //dialogBuilder.setMessage("");
        dialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!editText.getText().toString().isEmpty()) {
                    N = Integer.valueOf(editText.getText().toString());
                    renderImages();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }



    public void dragAndDrop() {
        //For drag
        for (int i=0;i<N*N;++i) {
            final View imageView = gridView.getChildAt(i);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipData data = ClipData.newPlainText("","");
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                    v.startDrag(data,myShadow,v,0);
                    return true;
                }
            });
        }

        //For drop
        View target;

        for (int i=0;i<N*N;++i) {
            target = gridView.getChildAt(i);
            if (true) {
                target.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        if (event.getAction() == DragEvent.ACTION_DROP) {
                            if (event.getLocalState() == v) {
                                return false;
                            } else {
                                if(event.getLocalState() != null) {
                                    View dropped = (View) event.getLocalState();
                                    ImageView sourceImage = (ImageView) dropped;
                                    ImageView targetImage = (ImageView) v;
                                    Drawable d1,d2;
                                    d1 = sourceImage.getDrawable();
                                    d2 = targetImage.getDrawable();

                                    sourceImage.setImageDrawable(d2);
                                    targetImage.setImageDrawable(d1);
                                } else {
                                    Log.d("error", "It is still showing null");
                                }
                            }
                        }
                        return true;
                    }
                });
            }
        }

    }

}
