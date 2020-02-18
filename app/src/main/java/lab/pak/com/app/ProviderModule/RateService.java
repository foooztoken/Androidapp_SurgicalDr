package lab.pak.com.app.ProviderModule;

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
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.MyJobsUser.CustomAdapterJobsFeedback;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RateService extends AppCompatActivity {

    private ProgressBar progress;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myjobs);
        init();

        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        final String id = prefs.getString("id", null);

        login(id);
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Button b=findViewById(R.id.home);
        changelanguage();
Button refresh=findViewById(R.id.refresh);
refresh.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        login(id);
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

    }
//void changelanguage(){
    private void changelanguage() {
        SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
        String language = pref.getString("language", null);
        String typee = pref.getString("type", null);
        if(language!=null&&language.equals("arabic")&&typee.equals("user")){
            Toolbar toolb = findViewById(R.id.header);
            toolb.setTitle("عملياتي");

        }
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent o=new Intent(getApplicationContext(), ProviderMain.class);

            startActivity(o);// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    void login(String id){
        try {
         //   Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Jobs>> callPet = service.getjobs( "http://surapi.surgicaldr.com/Models/rateservice.php?userid="+id);
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
                                SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                                String id = prefs.getString("id", null);
                                //    Toast.makeText(Myjobs.this, id, Toast.LENGTH_SHORT).show();if (id != null) {

                                    for(int i=0;i<ob.size();i++){



                                            jobs.add(ob.get(i));


                                    }


                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterJobsFeedback customAdapter = new CustomAdapterJobsFeedback(RateService.this,jobs);// address,name,amount,idd,lat,lon,phone);
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

                                Toast.makeText(RateService.this, "Problem Connecting", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(RateService.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(RateService.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }


    void init(){
        progress=findViewById(R.id.progress);
    }
}