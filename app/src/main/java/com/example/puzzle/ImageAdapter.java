package com.example.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    Bitmap[] bitmap;
    int width, height;
    private Context context;

    public ImageAdapter(Bitmap[][] bitmap, Context context, int n, int width, int height) {
        this.bitmap = new Bitmap[n * n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                this.bitmap[i * n + j] = bitmap[i][j];
                Log.d("index", Integer.toString(i * n + j));
            }
        }
        this.context = context;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getCount() {
        return bitmap.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            imageView.setImageBitmap(bitmap[i]);
        } else {
            imageView = (ImageView) view;
        }
        imageView.setPadding(1, 1, 1, 1);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setMinimumWidth(width / 3);
        imageView.setMinimumHeight(height / 3);

        return imageView;
    }
}