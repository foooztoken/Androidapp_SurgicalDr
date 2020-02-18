package lab.pak.com.app.BidsOrder;


import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
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

import java.text.SimpleDateFormat;
import java.util.List;

import lab.pak.com.app.MainActivity;
import lab.pak.com.app.Models.filter;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BidAcitivity extends AppCompatActivity {
EditText budget,description,roomtype;
    private ProgressBar progress;
  Spinner state,city;
    private Retrofit retrofit;
    Button bid;
    private static String countrytext;
    private static   String citytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_acitivity);
        final String pid= getIntent().getStringExtra("pid");
       final String bidt= getIntent().getStringExtra("bid");
        try{
            init();
            Toolbar toolbar = findViewById(R.id.header);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            Button b=findViewById(R.id.home);
getcountries();
Button refresh=findViewById(R.id.refresh);
refresh.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getcountries();
    }
});
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent o=new Intent(BidAcitivity.this, MainActivity.class);
                    startActivity(o);
                }
            });


        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        final String id = prefs.getString("id", null);

bid.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


int ch=check();
if(ch==1){
submit(bidt, pid, id);  }else{Toast.makeText(getApplicationContext(),"Enter data in all fields!" , Toast.LENGTH_SHORT).show();}


    }
    });
}catch (Exception e){Toast.makeText(BidAcitivity.this,e.toString() ,Toast.LENGTH_LONG ).show();}
    }





    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        long msTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy  hh:mm");
        return sdf.format(cal.getTime());
    }
    void submit(String bid,String providerid,String userid){
        try {
            progress.setVisibility(View.VISIBLE);
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            String date=giveDate();
String budge=budget.getText().toString();
String roomtyp=roomtype.getText().toString();


String des=description.getText().toString();
String cit=citytext;
String stat=countrytext;
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
       //     String json = "{\"bidid\":\""+bid+"\",\"providerid\":\""+providerid+"\",\"userid\":\""+userid+"\",\"budget\":\""+budge+"\",\"city\":\""+cit+"\",\"state\":\""+stat+"\",\"description\":\""+des+"\",\"specialization\":\""+des+"\",\"jobid\":\""+bid+"\",\"roomtype\":\""+roomtyp+"\",\"icuinclude\":\""+icu+"\",\"nodays\":\""+noday+"\",\"bloodtransfer\":\""+blood+"\"}";
         //   JSONObject obj = new JSONObject(json);
            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/bidupload.php?budget="+budge+"&userid="+userid+"&providerid="+providerid+"&bidid="+bid+"&city="+cit+"&state="+stat+"&description="+des+"&specialization="+des+"&jobid="+bid+"&time="+date+"&roomtype="+roomtyp+"&icuinclude= &nodays= &bloodtransfer= ");

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {

                        //       progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(BidAcitivity.this,response.body().toString() ,Toast.LENGTH_LONG ).show();
                    progress.setVisibility(View.INVISIBLE);


                    if (response.isSuccessful()) {
                     //       Toast.makeText(BidAcitivity.this ,response.toString(),Toast.LENGTH_LONG ).show();

                            try {


                                progress.setVisibility(View.INVISIBLE);



                    } catch (Exception e) {

                        progress.setVisibility(View.INVISIBLE);
                    }
                }}

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {


                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(BidAcitivity.this,t.toString() ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                     //   Toast.makeText(MainActivity.this,"Failure"+ a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        }

                catch (Exception a) {
           progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
          Toast.makeText(BidAcitivity.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }




    int check(){

        if(budget.equals("")){
            return 0;
        }
        if(description.equals("")){
            return 0;
        }


        if(roomtype.equals("")){
            return 0;
        }

        return 1;
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
        roomtype=findViewById(R.id.roomtypetext);
      bid=findViewById(R.id.insert);
        progress=findViewById(R.id.progress);
        city=findViewById(R.id.citytext);
        state=findViewById(R.id.statetext);
        budget=findViewById(R.id.budgettext);
        description=findViewById(R.id.descriptiontext);
}
















    void getstates(String country){

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<filter>> callPet = service.getcountries( "http://surapi.surgicaldr.com/Models/statefilter.php?country="+country);
            //progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<filter>>() {
                @Override
                public void onResponse(Call<List<filter>> call, Response<List<filter>> response) {
                    try {
                        //  progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<filter> ob=response.body();


                                ArrayList<filter> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){
//Toast.makeText(getApplicationContext(),ob.get(i).state ,Toast.LENGTH_LONG ).show();
                                    jobs.add(ob.get(i));

                                }



                                initiatestates(jobs);





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
                                //        progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                //         Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

                        //       Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                        //progressBar.setVisibility(View.INVISIBLE);
                        //    progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<filter>> call, Throwable t) {
                    try {
                        //progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Some Problem Occured Refresh Again!" ,Toast.LENGTH_LONG ).show();

                        //  progressBar.setVisibility(View.INVISIBLE);
                        //     Toast.makeText(getActivity(), call.toString(), Toast.LENGTH_LONG).show();

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        //       Toast.makeText(getActivity(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            //      Toast.makeText(getActivity(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void getcountries()
    {

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<filter>> callPet = service.getcountries( "http://surapi.surgicaldr.com/Models/countryfilter.php");
            //progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<filter>>() {
                @Override
                public void onResponse(Call<List<filter>> call, Response<List<filter>> response) {
                    try {
                        //  progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<filter> ob=response.body();


                                ArrayList<filter> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++) {

                                    jobs.add(ob.get(i));



/*}else{
                                for(int x=0;x<jobs.size();x++) {

                                    if (ob.get(i).country.equals(jobs.get(x).country)) {}else{
                                        jobs.add(ob.get(i));
                                        Toast.makeText(getApplicationContext(), ob.get(x).country,Toast.LENGTH_LONG ).show();
                                    }
                                }
  */                          }
                                initiatecountry(jobs);





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
                                //        progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                //                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

                        //         Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                        //progressBar.setVisibility(View.INVISIBLE);
                        //    progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<filter>> call, Throwable t) {
                    try {
                        //progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);
                        //            Toast.makeText(getActivity(), call.toString(), Toast.LENGTH_LONG).show();
//
                          Toast.makeText(getApplicationContext(),"Some Problem Occured Refresh Again!" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        ///        Toast.makeText(getActivity(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Some Problem Occured Refresh Again!" ,Toast.LENGTH_LONG ).show();

            //e.printStackTrace();
            ///      Toast.makeText(getActivity(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }

    void initiatestates(List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).state);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                citytext=adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

    }

    void initiatecountry(final List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).country);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                countrytext=adapter.getItem(position);
//Toast.makeText(getApplicationContext(),countrytext ,Toast.LENGTH_LONG ).show();
                getstates(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

    }


}
