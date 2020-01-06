package com.example.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    Bitmap[] bitmap;
    int width, height;
    int size;
    private Context context;

    public ImageAdapter(Bitmap[] bitmap, Context context, int n, int width, int height) {
        this.bitmap = bitmap;

        this.context = context;
        this.width = width;
        this.height = height;
        size = n;
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
        return i;
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
        imageView.setPadding(3, 3, 3, 3);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setMinimumWidth(width / size);
        imageView.setMinimumHeight(width / size);
        imageView.setMaxWidth(width / size);
        imageView.setMaxHeight(width / size);
        return imageView;
    }
}