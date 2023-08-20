package com.pwn3rzs.crto.android.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.pwn3rzs.crto.android.R;

import io.noties.markwon.image.AsyncDrawable;
import io.noties.markwon.image.ImagesPlugin;

public class MyPlaceholderProvider implements ImagesPlugin.PlaceholderProvider {
    private final Context context;


    public MyPlaceholderProvider(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public Drawable providePlaceholder(@NonNull AsyncDrawable drawable) {
        // Возвращаем изображение-заглушку из ресурсов
        return ContextCompat.getDrawable(context, R.drawable.arrow_down);
    }
}

