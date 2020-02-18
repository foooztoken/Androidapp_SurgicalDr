package lab.pak.com.app.Review;

import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.MainActivity;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends AppCompatActivity {

    private Jobs jobs;
    private Retrofit retrofit;
    private ProgressBar progress;
    private RatingBar ratingbar;
EditText reviewtext;
    private Button insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Gson gson = new Gson();
        String studentDataObjectAsAString = getIntent().getStringExtra("ob");
        jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        final String type = prefs.getString("type", null);
        final String name = prefs.getString("name", null);

init();
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Button b=findViewById(R.id.home);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent o=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(o);
            }
        });
insert.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(type.equals("user")){
            clearorder(name);
        }else{
            rating(name);
        }
    }
});


    }
    void init(){
        insert=findViewById(R.id.insert);
        reviewtext=findViewById(R.id.reviewtext);
        ratingbar=findViewById(R.id.ratingbar);
        progress=findViewById(R.id.progress);
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
          // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        long msTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy  hh:mm");
        return sdf.format(cal.getTime());
    }

    void clearorder(String name){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            String date=giveDate();
            progress.setVisibility(View.VISIBLE);
String stars= String.valueOf(ratingbar.getNumStars());
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call callPet = service.submitbid( "http://surapi.surgicaldr.com/Models/rating.php?providerid="+jobs.providerid+"&userid="+jobs.userid+"&jobid="+jobs.orderid+"&name="+name+"&review="+reviewtext.getText().toString()+"&stars="+stars+"&time="+date+"&jobid="+jobs.id);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(ReviewActivity.this,response.body().toString() ,Toast.LENGTH_SHORT ).show();

                        if (response.isSuccessful()) {

                            try {
                                Object ob=response.body();
                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(ReviewActivity.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(ReviewActivity.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });

        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(ReviewActivity.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void rating(String name){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            String date=giveDate();
            progress.setVisibility(View.VISIBLE);
            String stars= String.valueOf(ratingbar.getRating());
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call callPet = service.submitbid( "http://surapi.surgicaldr.com/Models/providerrating.php?providerid="+jobs.providerid+"&userid="+jobs.userid+"&jobid="+jobs.orderid+"&name="+name+"&review="+reviewtext.getText().toString()+"&stars="+stars+"&time="+date+"&jobid="+jobs.id);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(ReviewActivity.this,response.body().toString() ,Toast.LENGTH_SHORT ).show();

                        if (response.isSuccessful()) {

                            try {
                                Object ob=response.body();
                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(ReviewActivity.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(ReviewActivity.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });

        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(ReviewActivity.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }


}
