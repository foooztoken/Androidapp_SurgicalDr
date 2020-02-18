package lab.pak.com.app.Notifications;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.MainActivity;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notifications extends AppCompatActivity {
static ProgressBar progress;
    private RecyclerView list;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        init();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Button b=findViewById(R.id.home);
        Button refresh=findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                final String providerid = prefs.getString("id", null);

                String type = prefs.getString("type", null);
                if(type.equals("provider")) {
                    login(providerid, "0");

                }else{
                    login("0", providerid);

                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent o=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(o);
            }
        });
        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        final String providerid = prefs.getString("id", null);

        String type = prefs.getString("type", null);
        if(type.equals("provider")) {
            login(providerid, "0");

        }else{
            login("0", providerid);

        }
    }

   public  void startprogress(){
        progress.setVisibility(View.VISIBLE);
    }
   public void stoprogress(){
        progress.setVisibility(View.INVISIBLE);

    }
    void login(String userid,String providerid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<notification>> callPet = service.getnotifications( "http://surapi.surgicaldr.com/Models/notification.php?providerid="+providerid+"&userid="+userid);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<notification>>() {
                @Override
                public void onResponse(Call<List<notification>> call, Response<List<notification>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<notification> ob=response.body();


                                ArrayList<notification> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){

                                        jobs.add(ob.get(i));

                                }
                                String size= String.valueOf(ob.size());
                                SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
                                editor.putString("notifications",size );
editor.apply();
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterNotifications customAdapter = new CustomAdapterNotifications(Notifications.this,jobs);// address,name,amount,idd,lat,lon,phone);
                                recyclerView.setAdapter(customAdapter);



                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                      //          Toast.makeText(Notificatio.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<notification>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                //        Toast.makeText(Notificatio.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
     ///       Toast.makeText(Notificatio.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }

    void init(){
        progress=findViewById(R.id.progress);
        list=findViewById(R.id.recycle);
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
}
