package lab.pak.com.app.BidsModule;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import lab.pak.com.app.MainActivity;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import lab.pak.com.app.Signup;
import lab.pak.com.app.UserModule.About;
import lab.pak.com.app.UserModule.Main;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recommendations extends AppCompatActivity {
ProgressBar progress;
Button recommendation;
TextView recommendationtext;
EditText editrecommendation;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("id");
        String orderid = bundle.getString("orderid");
init();
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Button b=findViewById(R.id.home);

progress.setVisibility(View.VISIBLE);
getdata(id, orderid);
recommendation.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String budget=editrecommendation.getText().toString();
     if(budget!=null){
         progress.setVisibility(View.VISIBLE);
        rebid(budget,id);}
    }
});
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    void init(){
        progress=findViewById(R.id.progress);
        recommendation=findViewById(R.id.rebid);
        recommendationtext=findViewById(R.id.recommendationtext);
        editrecommendation=findViewById(R.id.amount);
    }


    void getdata(String id,String orderid){

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
           // String emai=email.getText().toString();
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
         //  JSONObject obj = new JSONObject(json);

            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/bidrecommendation.php?bidid="+id+"&orderid="+orderid);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        String resul = new Gson().toJson(response.body());
progress.setVisibility(View.INVISIBLE);
                        JSONObject jobj = new JSONObject(resul);


                        String status = jobj.getString("response");

recommendationtext.setText(status);
if(status.equals("No recommendations")){}else{
    recommendation.setVisibility(View.VISIBLE);
    editrecommendation.setVisibility(View.VISIBLE);

}
                        //       progressBar.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();

                      } catch (Exception e) {
                                    progress.setVisibility(View.INVISIBLE);
                                    //       progressBar.setVisibility(View.INVISIBLE);

                               //     Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }





                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
                        //  progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                        //e.printStackTrace();
                        //  Toast.makeText(Signup.this,"Failure"+ a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
          //  Toast.makeText(Signup.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }


    void rebid(String budget,String id){

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // String emai=email.getText().toString();
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            //  JSONObject obj = new JSONObject(json);

            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/rebid.php?id="+id+"&budget="+budget);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                                             String resul = new Gson().toJson(response.body());
                        progress.setVisibility(View.INVISIBLE);
                        JSONObject jobj = new JSONObject(resul);


                        String status = jobj.getString("response");


                        //       progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        progress.setVisibility(View.INVISIBLE);
                        //       progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }





                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
                        //  progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                        //e.printStackTrace();
                        //  Toast.makeText(Signup.this,"Failure"+ a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            //  Toast.makeText(Signup.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }

}

