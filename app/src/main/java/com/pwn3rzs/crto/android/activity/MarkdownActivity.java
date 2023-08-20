package com.pwn3rzs.crto.android.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Spanned;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pwn3rzs.crto.android.R;

import java.io.IOException;
import java.io.InputStream;

import io.noties.markwon.Markwon;
import io.noties.markwon.image.ImagesPlugin;
import io.noties.markwon.image.file.FileSchemeHandler;


public class MarkdownActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle bundle0) {
        super.onCreate(bundle0);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        //toolBarLayout.setTitle(getTitle());
//
//        FloatingActionButton fab = binding.fab;
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("asset_file_name")) {
            String assetFileName = bundle.getString("asset_file_name");
            setTitle(assetFileName);
            displayMarkdown(assetFileName);
        } else {

//            final SpannableConfiguration conf = SpannableConfiguration.builder(getBaseContext())
//                    .asyncDrawableLoader(AsyncDrawableLoader.create())
//                    .build();

            TextView markdownTextView = findViewById(R.id.markdownTextView);
            String markdownText = new String("![img](fisets/20220831113655.png)\"");

            Markwon markwon = Markwon.builder(this)
                    .usePlugin(ImagesPlugin.create().addSchemeHandler(FileSchemeHandler.createWithAssets(getAssets())))
                    .build();
            markwon.setMarkdown(markdownTextView, markdownText);
        }
    }

    private void displayMarkdown(String assetFileName) {
        TextView markdownTextView = findViewById(R.id.markdownTextView);

        try {
            // Открываем файл из assets
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(assetFileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String markdownText = new String(buffer);

//            Markwon markwon = Markwon.builder(this)
////                    .usePlugin(ImagesPlugin.create(new ImagesPlugin.ImagesConfigure() {
////                        @Override
////                        public void configureImages(@NonNull ImagesPlugin plugin) {
////
////                        }
////                    }))
//                    //.usePlugin(new AbstractMarkwonPlugin() {}
//                    .usePlugin(ImagesPlugin.create(
//                            new CustomGlideImagesPlugin()
//                                    .placeholderProvider(new MyPlaceholderProvider(this))
//                    ))
//                    .build();
//            Spanned spanned = markwon.toMarkdown(markdownText);
//            markdownTextView.setText(spanned);

            Markwon markwon = Markwon.builder(this)
                    .usePlugin(ImagesPlugin.create()
                            .addSchemeHandler(FileSchemeHandler.createWithAssets(getAssets())))
                    .build();
            markwon.setMarkdown(markdownTextView, markdownText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}