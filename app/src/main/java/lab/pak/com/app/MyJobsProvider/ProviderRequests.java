package lab.pak.com.app.MyJobsProvider;

import android.content.Intent;

import java.util.Calendar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;



import java.text.DateFormat;
import java.text.SimpleDateFormat;


import lab.pak.com.app.MainActivity;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProviderRequests extends AppCompatActivity {

    private Spinner spinner;
    EditText purpose;
    Button submit;
    ProgressBar progress;
static String positionstr="Cancel";
    private Retrofit retrofit;
    private Jobs jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_requests);
      try {
          Bundle bundle = getIntent().getExtras();


          Gson gson = new Gson();
          String studentDataObjectAsAString = getIntent().getStringExtra("ob");
          jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
init();
          Toolbar toolbar = findViewById(R.id.header);
          setSupportActionBar(toolbar);
          ActionBar actionbar = getSupportActionBar();
          actionbar.setDisplayHomeAsUpEnabled(true);
          Button b = findViewById(R.id.home);

          b.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  finish();
                  Intent o = new Intent(getApplicationContext(), MainActivity.class);
                  startActivity(o);
              }
          });
          submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  if(jobs.status.equals("complete")){

                      Toast.makeText(getApplicationContext(), "Order has been marked as Complete!", Toast.LENGTH_SHORT).show();

                  }else{
                  if (positionstr.equals("Cancel")) {
                      submit();
                  } else if (positionstr.equals("Clear")) {
                      request();
                  }}
              }
          });

          spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                  // your code here

                  if (position == 0) {
                      positionstr = "Cancel";
                  } else if (position == 1) {
                      positionstr = "Clear";
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> parentView) {
                  // your code here
              }

          });
      }catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG ).show();}
    }

    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        long msTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy  hh:mm");
        return sdf.format(cal.getTime());
    }
    void submit(){
        try {
            progress.setVisibility(View.VISIBLE);
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            String userid=jobs.userid;
            String providerid=jobs.providerid;
            String jobid=jobs.id;
            String status="open";



            String time=giveDate();
            String type=positionstr;
            String pur=purpose.getText().toString();
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);



            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/cancelrequest.php?time="+time+"&providerid="+providerid+"&status="+status+"&purpose="+pur+"&jobid="+jobid+"&date="+time+"&userid="+userid+"&type="+type);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Toast.makeText(ProviderRequests.this,response.body().toString() ,Toast.LENGTH_SHORT ).show();

                    progress.setVisibility(View.INVISIBLE);

                    if (response.isSuccessful()) {

                        try {

                            progress.setVisibility(View.INVISIBLE);



                        } catch (Exception e) {Toast.makeText(ProviderRequests.this,e.toString() ,Toast.LENGTH_SHORT ).show();
                            progress.setVisibility(View.INVISIBLE);
                        }
                    }}

                @Override
                public void onFailure(Call<Object> call, Throwable t) {


                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(ProviderRequests.this,t.toString() ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(ProviderRequests.this,a.toString() ,Toast.LENGTH_SHORT ).show();
                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        }

        catch (Exception a) {
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(ProviderRequests.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }

    void request(){
        try {
            progress.setVisibility(View.VISIBLE);
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            String userid=jobs.userid;
            String providerid=jobs.providerid;
            String jobid=jobs.id;
            String status="open";
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");



            String time=giveDate();
            String type=positionstr;
            String pur=purpose.getText().toString();
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);



            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/orderclearrequest.php?time="+time+"&providerid="+providerid+"&status="+status+"&purpose="+pur+"&jobid="+jobid+"&date="+time+"&userid="+userid+"&type="+type);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Toast.makeText(ProviderRequests.this,response.body().toString() ,Toast.LENGTH_SHORT ).show();

                    progress.setVisibility(View.INVISIBLE);

                    if (response.isSuccessful()) {

                        try {
//Toast.makeText(PostJob.this,response.toString() ,Toast.LENGTH_SHORT ).show();

                            progress.setVisibility(View.INVISIBLE);



                        } catch (Exception e) {Toast.makeText(ProviderRequests.this,e.toString() ,Toast.LENGTH_SHORT ).show();
                            progress.setVisibility(View.INVISIBLE);
                        }
                    }}

                @Override
                public void onFailure(Call<Object> call, Throwable t) {


                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(ProviderRequests.this,t.toString() ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(ProviderRequests.this,a.toString() ,Toast.LENGTH_SHORT ).show();
                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        }

        catch (Exception a) {
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(ProviderRequests.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }
    void   initspinner(){
        String[] items = new String[]{"Cancellation", "Clearance"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);
    }


    void init(){
        submit=findViewById(R.id.insert);
        spinner=findViewById(R.id.type);
        initspinner();
        purpose=findViewById(R.id.purposetext);
        progress=findViewById(R.id.progress);
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
