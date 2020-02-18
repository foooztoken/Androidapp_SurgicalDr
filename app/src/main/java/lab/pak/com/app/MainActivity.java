package lab.pak.com.app;



import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


import java.util.List;


import lab.pak.com.app.Models.Userinformation;
import lab.pak.com.app.ProviderModule.ProviderMain;
import lab.pak.com.app.UserModule.Main;
import lab.pak.com.app.UserModule.SignupUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



  //  ProgressBar progressBar;

    private String MY_PREFS_NAME="Logindata";
   private Retrofit retrofit;
    private EditText email,pass;
    private ProgressBar progress;
TextView signup;
    private RadioButton employee,owner;
    private Button save;
TextView signu;
static String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getWindow().setBackgroundDrawableResource(R.mipmap.back);
        val="provider";
        try{

        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        String id = prefs.getString("session", null);
            String type = prefs.getString("type", null);
     if(id!=null&type.equals("provider")){
        if(id.equals("active")) {
            Intent op = new Intent(MainActivity.this, Main.class);
            startActivity(op);
            finish();
        } }else if(id!=null&type.equals("user")){
         if(id.equals("active")) {
             Intent op = new Intent(MainActivity.this, ProviderMain.class);
             startActivity(op);
             finish();
         }
     }}catch (Exception e){


            //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
            Button user=findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//                mDrawerLayout.openDrawer(Gravity.END);



                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.frame, new todaytimefragment());


                    ft.commit();
                }



            });
     save.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {



             String emaill=email.getText().toString();
             String passs=pass.getText().toString();
             login(emaill,passs);


       //  login(email.getText().toString(), pass.getText().toString());
     }});
signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    Intent op=new Intent(MainActivity.this, SignupUser.class);
    startActivity(op);

    }
});
    }

    void init(){

        save=findViewById(R.id.save);

        email=findViewById(R.id.emailedit);
        pass=findViewById(R.id.passwordedit);
        progress=findViewById(R.id.simpleProgressBar);
        signup=findViewById(R.id.text);
    }
    void login(String user,String pass){
        try {
progress.setVisibility(View.VISIBLE);
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            String json = "{\"email\":\""+user+"\",\"password\":\""+pass+"\"}";
            JSONObject obj = new JSONObject(json);
            Call<List<Userinformation>> callPet = service.login( "http://surapi.surgicaldr.com/Models/login.php?var="+obj);

            callPet.enqueue(new Callback<List<Userinformation>>() {
                                @Override
                                public void onResponse(Call<List<Userinformation>> call, Response<List<Userinformation>> response) {
                                    try {
                                        //       progressBar.setVisibility(View.INVISIBLE);

                                        if (response.isSuccessful()) {

                                            try {

                                              List<Userinformation> ob=response.body();
progress.setVisibility(View.INVISIBLE);
                                                for (int x = 0; x < ob.size(); x++) {
//Toast.makeText(MainActivity.this,ob.get(x).id ,Toast.LENGTH_LONG ).show();
  //                                                  Toast.makeText(MainActivity.this,ob.get(x).name ,Toast.LENGTH_LONG ).show();
    //                                                Toast.makeText(MainActivity.this,ob.get(x).city ,Toast.LENGTH_LONG ).show();
      //                                              Toast.makeText(MainActivity.this,ob.get(x).country ,Toast.LENGTH_LONG ).show();


                                                }






                                SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
                                    editor.putString("id", ob.get(0).id);
                                    editor.putString("image",ob.get(0).image);
                                    editor.putString("name", ob.get(0).name);
                                    editor.putString("phone", ob.get(0).phone);
editor.putString("session","active");
                                                editor.putString("type","provider");
                                    editor.putString("detail", ob.get(0).city);
                                    editor.putString("email", ob.get(0).email);
                                    editor.putString("country",ob.get(0).country );
                                                editor.putString("state",ob.get(0).state );
                                                editor.putString("degree",ob.get(0).degree );
                                                editor.putString("license", ob.get(0).license);
                                                editor.putString("specification",ob.get(0).specification );
                                                editor.putString("password",ob.get(0).password );
                                                editor.apply();



                                      Toast.makeText(getApplicationContext(), "Signed in!", Toast.LENGTH_SHORT).show();
                                              Intent op=new Intent(MainActivity.this, Main.class);
                                              startActivity(op);
                                             finish();

                                                //             progressBar.setVisibility(View.INVISIBLE);
                                                // progressBar.setVisibility(View.INVISIBLE);

                                            } catch (Exception e) {

                                                //       progressBar.setVisibility(View.INVISIBLE);
                                                progress.setVisibility(View.INVISIBLE);

                                //                Toast.makeText(MainActivity.this, "User doesnt Exist", Toast.LENGTH_LONG).show();
                                            }
                                        }


                                    } catch (Exception e) {
                                       progress.setVisibility(View.INVISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Userinformation>> call, Throwable t) {
                                    try {
                                        progress.setVisibility(View.INVISIBLE);

                                        //  progressBar.setVisibility(View.INVISIBLE);

                                           Toast.makeText(MainActivity.this,"Sign in error Please try again!",Toast.LENGTH_SHORT ).show();
                                    } catch (Exception a) {
                                        //progressBar.setVisibility(View.INVISIBLE);

                                        //e.printStackTrace();
                                       // Toast.makeText(MainActivity.this,"Failure"+ a.toString(), Toast.LENGTH_LONG).show();

                                    }
                                }

                            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
         //   Toast.makeText(MainActivity.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }





}

