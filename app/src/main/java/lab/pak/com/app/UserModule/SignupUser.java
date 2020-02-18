package lab.pak.com.app.UserModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import lab.pak.com.app.MainActivity;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupUser extends AppCompatActivity {



    //  ProgressBar progressBar;

    private String MY_PREFS_NAME="Logindata";
    private Retrofit retrofit;
    private EditText email,pass,user,phone;
    private ProgressBar progress;
    TextView signup;
   private Button save;

TextView login;
    private EditText state,city,country,degree,specification,license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupuser);
        init();
        getWindow().setBackgroundDrawableResource(R.mipmap.back);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=check();
               if(i==1){
                   progress.setVisibility(View.VISIBLE);
                singup(email.getText().toString(), pass.getText().toString(),phone.getText().toString(),user.getText().toString(),state.getText().toString(),city.getText().toString(),country.getText().toString(),degree.getText().toString(),license.getText().toString(),specification.getText().toString());
            }}});
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent op=new Intent(SignupUser.this, MainActivity.class);
        startActivity(op);
        finish();
    }
});
    }
    int check(){
        if(email.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }

        if(user.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(pass.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(phone.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(degree.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(specification.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(license.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(state.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(country.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        if(city.getText().toString().equals("")){
            Toast.makeText(SignupUser.this,"Enter data in all fields" ,Toast.LENGTH_SHORT ).show();
            return 0;
        }
        else return 1;
    }
    void init(){
        degree=findViewById(R.id.degreeedit);
        specification=findViewById(R.id.specificationedit);
        license=findViewById(R.id.licenseedit);
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
    void singup(String user,String pass,String phonenum,String username,String state,String city,String country,String degree,String license,String specification){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
String emai=email.getText().toString();
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            String json = "{\"email\":\""+emai+"\",\"password\":\""+pass+"\",\"city\":\""+city+"\",\"country\":\""+country+"\",\"state\":\""+state+"\",\"phone\":\""+phonenum+"\",\"name\":\""+username+"\",\"degree\":\""+degree+"\",\"specification\":\""+specification+"\",\"license\":\""+license+"\"}";
            JSONObject obj = new JSONObject(json);

            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/signup.php?var="+obj);

            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        Toast.makeText(SignupUser.this, response.body().toString(), Toast.LENGTH_LONG).show();
                        progress.setVisibility(View.INVISIBLE);


                        //   Toast.makeText(Main.this, resul.toString(), Toast.LENGTH_LONG).show();


                        if (response.isSuccessful()) {

                            try {
                                progress.setVisibility(View.INVISIBLE);
                                String ob = response.body().toString();


                                Intent op = new Intent(SignupUser.this, MainActivity.class);
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

                                //       Toast.makeText(SignupUser.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        Toast.makeText(SignupUser.this, e.toString(), Toast.LENGTH_LONG).show();

                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
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
                     //   Toast.makeText(SignupUser.this,"Failure"+ a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
        //    Toast.makeText(SignupUser.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }


    }}

