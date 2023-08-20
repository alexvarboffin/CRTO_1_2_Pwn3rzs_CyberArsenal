package com.pwn3rzs.crto.android.activity;

import io.noties.markwon.image.ImagesPlugin;

import androidx.annotation.NonNull;

import io.noties.markwon.AbstractMarkwonPlugin;

public class CustomGlideImagesPlugin extends AbstractMarkwonPlugin implements ImagesPlugin.ImagesConfigure {




    public static CustomGlideImagesPlugin create() {
        return new CustomGlideImagesPlugin();
    }

//    @NonNull
//    public static GlideImagesPlugin create(@NonNull ImagesPlugin.ImagesConfigure configure) {
//        return new GlideImagesPlugin(configure);
//    }

    public CustomGlideImagesPlugin() {
        // Default constructor
    }

//    private GlideImagesPlugin(@NonNull ImagesPlugin.ImagesConfigure configure) {
//        configure.configureImages(this);
//    }

    @NonNull
    public CustomGlideImagesPlugin placeholderProvider(@NonNull ImagesPlugin.PlaceholderProvider placeholderProvider) {
        return this;
    }

    @NonNull
    public CustomGlideImagesPlugin errorHandler(@NonNull ImagesPlugin.ErrorHandler errorHandler) {
        // Implement your errorHandler logic here
        return this;
    }

    // Implement other methods if needed

    @Override
    public void configureImages(@NonNull ImagesPlugin plugin) {
        // Here you can configure your ImagesPlugin instance
        // Use the provided plugin instance to set your custom placeholderProvider and errorHandler
    }

}