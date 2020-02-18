package lab.pak.com.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.Models.filter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity {



    //  ProgressBar progressBar;

    private String MY_PREFS_NAME="Logindata";
    private Retrofit retrofit;
    private EditText email,pass,user,phone;
    private ProgressBar progress;
    TextView signup;
   private Button save;
    Spinner citysp,countrysp;

TextView login;
    private EditText state,city,country;
    private String countrytext;
//    private BreakIterator countryt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        getWindow().setBackgroundDrawableResource(R.mipmap.back);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=check();
               if(i==1){
                   progress.setVisibility(View.VISIBLE);
                singup(email.getText().toString(), pass.getText().toString(),phone.getText().toString(),user.getText().toString(),state.getText().toString(),city.getText().toString(),country.getText().toString());
            }}});
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent op=new Intent(Signup.this,MainActivity.class);
        startActivity(op);
        finish();
    }
});
    }
    int check(){
        if(email.getText().toString().equals("")){
            Toast.makeText(Signup.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }

        if(user.getText().toString().equals("")){
            Toast.makeText(Signup.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(pass.getText().toString().equals("")){
            Toast.makeText(Signup.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(phone.getText().toString().equals("")){
            Toast.makeText(Signup.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(state.getText().toString().equals("")){
            Toast.makeText(Signup.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(country.getText().toString().equals("")){
            Toast.makeText(Signup.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(city.getText().toString().equals("")){
            Toast.makeText(Signup.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        else return 1;
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


/*}else{
                                for(int x=0;x<jobs.size();x++) {

                                    if (ob.get(i).country.equals(jobs.get(x).country)) {}else{
                                        jobs.add(ob.get(i));
                                        Toast.makeText(getApplicationContext(), ob.get(x).country,Toast.LENGTH_LONG ).show();
                                    }
                                }
  */
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

                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Problem occuring ",Toast.LENGTH_LONG ).show();

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

                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void initiatecountry(final List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).country);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        countrysp.setAdapter(adapter);
        countrysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                countrytext=adapter.getItem(position);
               TextView countryt=findViewById(R.id.countrytextt);
                countryt.setText(adapter.getItem(position));
//Toast.makeText(getApplicationContext(),countrytext ,Toast.LENGTH_LONG ).show();
                getstates(adapter.getItem(position));
            }
//wait ok thek ha bro? kia bana? working bro
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

    }
    void initiatestates(List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).state);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        citysp.setAdapter(adapter);
        citysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

              //  citytext=adapter.getItem(position);
             //   cityt.setText(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }}
    );
        }
    void init(){
        city=findViewById(R.id.cityedit);
        country=findViewById(R.id.countryedit);
        state=findViewById(R.id.stateedit);
login=findViewById(R.id.text);
        save=findViewById(R.id.save);
        phone=findViewById(R.id.phoneedit);
        user=findViewById(R.id.usernameedit);
        email=findViewById(R.id.emailedit);
        pass=findViewById(R.id.passwordedit);
        progress=findViewById(R.id.simpleProgressBar);
      //  signup=findViewById(R.id.text);
    }
    void singup(String user,String pass,String phonenum,String username,String state,String city,String country){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
String emai=email.getText().toString();
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            String json = "{\"email\":\""+emai+"\",\"password\":\""+pass+"\",\"city\":\""+city+"\",\"country\":\""+country+"\",\"state\":\""+state+"\",\"phone\":\""+phonenum+"\",\"name\":\""+username+"\"}";
            JSONObject obj = new JSONObject(json);

            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/signupprovider.php?var="+obj);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        //       progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Signup.this, response.body().toString(), Toast.LENGTH_LONG).show();

                        String resul = new Gson().toJson(response.body());

                        progress.setVisibility(View.INVISIBLE);
                        if (resul!=null) {
                            JSONObject result = new JSONObject(resul);

                            Toast.makeText(Signup.this, result.toString(), Toast.LENGTH_LONG).show();


                        if (response.isSuccessful()) {

                            try {

                           String ob=response.body().toString();
                             //   Toast.makeText(Signup.this, "Successfully signed up!", Toast.LENGTH_LONG).show();


                                Intent op=new Intent(Signup.this,MainActivity.class);
                                startActivity(op);
                                finish();




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

                                Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    }} catch (Exception e) {
                        //Toast.makeText(Signup.this, "Successfully signed up!", Toast.LENGTH_LONG).show();

                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);   }
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
            Toast.makeText(Signup.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }}

