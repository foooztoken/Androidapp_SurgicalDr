package lab.pak.com.app.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.IOException;

import lab.pak.com.app.R;
import lab.pak.com.app.UserModule.Main;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PictureDetail extends AppCompatActivity {
ProgressBar progressBar;
    private SubsamplingScaleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturedetail);
        try {

            String url =  getIntent().getStringExtra("url");
           // Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
            OkHttpClient client = new OkHttpClient();
            progressBar = findViewById(R.id.progress);
           image = findViewById(R.id.images);


            Request request = new Request.Builder()
                    .url(url)
                    .build();
          //  progressBar.setVisibility(View.VISIBLE);
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                 try{
                  //   Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();



                   //  progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception ee) {
               //     Toast.makeText(getApplicationContext(), ee.toString(), Toast.LENGTH_LONG).show();
                }  }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {

                        //progressBar.setVisibility(View.INVISIBLE);

                        response.body().byteStream(); // Read the data from the stream
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
               
                        image.setImage(ImageSource.bitmap(bmp));
                    } catch (Exception e) {
                    //    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            Toolbar toolbar = findViewById(R.id.header);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            Button b = findViewById(R.id.home);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent o = new Intent(PictureDetail.this, Main.class);
                    startActivity(o);
                }
            });
        }catch (Exception e){

            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();}
    }}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
