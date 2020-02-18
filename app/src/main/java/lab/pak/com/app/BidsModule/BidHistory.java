package lab.pak.com.app.BidsModule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.FindJobs.CustomAdapter;
import lab.pak.com.app.FindJobs.JobsList;
import lab.pak.com.app.MainActivity;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.Models.bids;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BidHistory extends AppCompatActivity {
    private String MY_PREFS_NAME="Logindata";
    private Retrofit retrofit;
    private EditText email,pass,user,phone;
    private ProgressBar progress;
    TextView signup;
    private Button save;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout findjob,myjobs,about,faq,contactus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybids);
       // Intent i = getIntent();
     //   String jobid = i.getStringExtra("jobid");
        init();
        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        final String id = prefs.getString("id", null);
        login(id);
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
        Button refresh=findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(id);
            }
        });
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
    void init(){


        progress=findViewById(R.id.progress);
    }
    void login(String id){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<bids>> callPet = service.getbids( "http://surapi.surgicaldr.com/Models/bidhistory.php?userid="+id);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<bids>>() {
                @Override
                public void onResponse(Call<List<bids>> call, Response<List<bids>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<bids> ob=response.body();


                                ArrayList<bids> jobs=new ArrayList<>();
                                SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                                String id = prefs.getString("id", null);
                                String city = prefs.getString("state", null);

                                String country = prefs.getString("country", null);
                                if (id != null) {

                                    for(int i=0;i<ob.size();i++){

                                            jobs.add(ob.get(i));}

                                    }


                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterbids customAdapter = new CustomAdapterbids(BidHistory.this,jobs,"provider");// address,name,amount,idd,lat,lon,phone);
                                recyclerView.setAdapter(customAdapter);





                             /*    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("id", id);
                                    editor.putString("image", driverimage);
                                    editor.putString("name", name);
                                    editor.putString("phone", phone);

                                    editor.putString("detail", details);
                                    editor.putString("email", email);
                                    editor.apply();


                                    SharedPreferences.Editor editorr = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("details", "123");
                                    editor.apply();
                               */     //      Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();


                                //             progressBar.setVisibility(View.INVISIBLE);
                                // progressBar.setVisibility(View.INVISIBLE);

                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                     //           Toast.makeText(Mybids.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {        Toast.makeText(BidHistory.this, e.toString(), Toast.LENGTH_LONG).show();

                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<bids>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                   //         Toast.makeText(Mybids.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(BidHistory.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(BidHistory.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    public  void intiatefilter(String city,String country){
        joblist(city,country );
    }






    void joblist(final String city, final String country) {
        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        final String id = prefs.getString("id", null);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://surapi.surgicaldr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<List<bids>> callPet = service.getbids("http://surapi.surgicaldr.com/Models/bidhistory.php?userid=" + id);
        progress.setVisibility(View.VISIBLE);
        callPet.enqueue(new Callback<List<bids>>() {
            @Override
            public void onResponse(Call<List<bids>> call, Response<List<bids>> response) {
                try {
                    progress.setVisibility(View.INVISIBLE);

                    if (response.isSuccessful()) {

                        try {

                            List<bids> ob = response.body();


                            ArrayList<bids> jobs = new ArrayList<>();
                            SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                            String id = prefs.getString("id", null);
                        //    String city = prefs.getString("state", null);

                          //  String country = prefs.getString("country", null);
                            if (id != null) {

                                for (int i = 0; i < ob.size(); i++) {
                                    if (city.equals(jobs.get(i).city) && country.equals(jobs.get(i).country)) {
                                        jobs.add(ob.get(i));
                                    }

                                }
                            }


                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                            GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                            recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                            CustomAdapterbids customAdapter = new CustomAdapterbids(BidHistory.this, jobs, "provider");// address,name,amount,idd,lat,lon,phone);
                            recyclerView.setAdapter(customAdapter);





                             /*    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("id", id);
                                    editor.putString("image", driverimage);
                                    editor.putString("name", name);
                                    editor.putString("phone", phone);

                                    editor.putString("detail", details);
                                    editor.putString("email", email);
                                    editor.apply();


                                    SharedPreferences.Editor editorr = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("details", "123");
                                    editor.apply();
                               */     //      Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();


                            //             progressBar.setVisibility(View.INVISIBLE);
                            // progressBar.setVisibility(View.INVISIBLE);

                        } catch (Exception e) {
                            progress.setVisibility(View.INVISIBLE);

                            //       progressBar.setVisibility(View.INVISIBLE);

                            //           Toast.makeText(Mybids.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (Exception e) {
                    Toast.makeText(BidHistory.this, e.toString(), Toast.LENGTH_LONG).show();

                    //progressBar.setVisibility(View.INVISIBLE);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<bids>> call, Throwable t) {
                try {
                    progress.setVisibility(View.INVISIBLE);

                    //  progressBar.setVisibility(View.INVISIBLE);

                    //         Toast.makeText(Mybids.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                } catch (Exception a) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    progress.setVisibility(View.INVISIBLE);

                    //e.printStackTrace();
                    Toast.makeText(BidHistory.this, a.toString(), Toast.LENGTH_LONG).show();

                }
            }

        });


//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
    }

}
