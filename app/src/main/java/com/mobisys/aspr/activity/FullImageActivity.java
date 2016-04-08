package com.mobisys.aspr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobisys.aspr.imageloadingutil.ImageLoader;
import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.widgets.ZoomableImageView;
import com.pktworld.aspr.R;

/**
 * Created by Prabhat on 09/04/16.
 */
public class FullImageActivity extends AppCompatActivity {
    private static final String TAG = FullImageActivity.class.getSimpleName();
    private ZoomableImageView imageZoom;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.txtToolbar);
        getSupportActionBar().setTitle("");
        mTitle.setText("Image");

        String imageUrl = getIntent().getStringExtra(ApplicationConstant.FLAG1);
        imageLoader = ImageLoader.getInstance(this);
        imageZoom = (ZoomableImageView)findViewById(R.id.imageZoom);
        imageLoader.DisplayImage(imageUrl, imageZoom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
