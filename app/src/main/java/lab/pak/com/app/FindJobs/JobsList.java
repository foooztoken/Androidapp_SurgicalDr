package lab.pak.com.app.FindJobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.FilterFragment;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.UserModule.Main;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobsList extends AppCompatActivity {
    private Retrofit retrofit;
    private EditText email,pass,user,phone;
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);
        init();
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        Button b=findViewById(R.id.home);
Button refresh=findViewById(R.id.refresh);
refresh.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        login();
    }
});
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//                mDrawerLayout.openDrawer(Gravity.END);



                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.frame, new FilterFragment());


                ft.commit();
            }
        });

        login();
    }
public  void intiatefilter(String city,String country){
        joblist(city,country );
}
    private void init() {
        progress=findViewById(R.id.progress);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent o=new Intent(JobsList.this, Main.class);

            startActivity(o);// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    void joblist(final String city, final String country){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Jobs>> callPet = service.getjobs( "http://surapi.surgicaldr.com/Models/jo.php");
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Jobs>>() {
                @Override
                public void onResponse(Call<List<Jobs>> call, Response<List<Jobs>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Jobs> ob=response.body();


                                ArrayList<Jobs> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){
                                    if(ob.get(i).status.equals("approve")){

                                        if(ob.get(i).city.equals(city)&&ob.get(i).country.equals(country)){
                                        jobs.add(ob.get(i));}}

                                }
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapter customAdapter = new CustomAdapter(JobsList.this,jobs);// address,name,amount,idd,lat,lon,phone);
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

                                Toast.makeText(JobsList.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Jobs>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(JobsList.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(JobsList.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void login(){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Jobs>> callPet = service.getjobs( "http://surapi.surgicaldr.com/Models/jo.php");
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Jobs>>() {
                @Override
                public void onResponse(Call<List<Jobs>> call, Response<List<Jobs>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Jobs> ob=response.body();

int ul;
                                ArrayList<Jobs> jobs=new ArrayList<>();

                                SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);

                                String city = prefs.getString("state", null);

                                String country = prefs.getString("country", null);


                                for(int i=0;i<ob.size();i++){
                                    if((ob.get(i).status.equals("approve")&&(city.equals(ob.get(i).city)&&(country.equals(ob.get(i).country
                                    ))))){

                                        //i//f(ob.get(i).status.equals("approve")){
                                        jobs.add(ob.get(i));}

                                }


                                int u;
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapter customAdapter = new CustomAdapter(JobsList.this,jobs);// address,name,amount,idd,lat,lon,phone);
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

                                Toast.makeText(JobsList.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Jobs>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(JobsList.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(JobsList.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }


}
