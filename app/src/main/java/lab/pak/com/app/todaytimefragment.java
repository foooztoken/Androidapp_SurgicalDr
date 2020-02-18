package lab.pak.com.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;





import org.json.JSONObject;


import java.util.List;


import lab.pak.com.app.Models.Providerinformation;
import lab.pak.com.app.ProviderModule.ProviderMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static android.content.Context.MODE_PRIVATE;

public class todaytimefragment  extends Fragment {


        private View rootview;
    private ProgressBar progress;
    //private AdView add;


    //  ProgressBar progressBar;

    private Retrofit retrofit;
    private EditText email,pass;

    TextView signup;

    private Button save;
    static String val;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    @Override
        public void onResume() {

            super.onResume();

            getView().setFocusableInTouchMode(true);
            getView().requestFocus();

        }

        @Override
        public View onCreateView(LayoutInflater inflater,

                                 ViewGroup container, Bundle savedInstanceState) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            rootview=inflater.inflate(R.layout.signinuser, container, false);
            progress=(ProgressBar)rootview.findViewById(R.id.progress);
            ImageView apple=rootview.findViewById(R.id.apple);
            apple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            getActivity().getWindow().setBackgroundDrawableResource(R.mipmap.back);
            save=rootview.findViewById(R.id.save);

            email=rootview.findViewById(R.id.emailedit);
            pass=rootview.findViewById(R.id.passwordedit);
            progress=rootview.findViewById(R.id.simpleProgressBar);
            signup=rootview.findViewById(R.id.text);
try{
Button provider=rootview.findViewById(R.id.provider);
    provider.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            getActivity().onBackPressed();




        }});
    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {






                String emaill=email.getText().toString();
                String passs=pass.getText().toString();
                loginprovider(emaill,passs);



            //  login(email.getText().toString(), pass.getText().toString());
        }});
    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                Intent op=new Intent(getActivity(),Signup.class);
                startActivity(op);



        }
    });

            }catch (Exception e){}
            return rootview; }

    void loginprovider(String user,String pass){
        try {
            progress.setVisibility(View.VISIBLE);
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            String json = "{\"email\":\""+user+"\",\"password\":\""+pass+"\"}";
            JSONObject obj = new JSONObject(json);
            Call<List<Providerinformation>> callPet = service.loginprovider( "http://surapi.surgicaldr.com/Models/loginprovider.php?var="+obj);

            callPet.enqueue(new Callback<List<Providerinformation>>() {
                @Override
                public void onResponse(Call<List<Providerinformation>> call, Response<List<Providerinformation>> response) {
                    try {
                        //       progressBar.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Providerinformation> ob=response.body();
                                progress.setVisibility(View.INVISIBLE);
                                for (int x = 0; x < ob.size(); x++) {

                                    //                                                  Toast.makeText(MainActivity.this,ob.get(x).name ,Toast.LENGTH_LONG ).show();
                                    //                                                Toast.makeText(MainActivity.this,ob.get(x).city ,Toast.LENGTH_LONG ).show();
                                    //                                              Toast.makeText(MainActivity.this,ob.get(x).country ,Toast.LENGTH_LONG ).show();


                                }


                                Toast.makeText(getActivity(),"Signed in !" ,Toast.LENGTH_LONG ).show();



                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userdata", MODE_PRIVATE).edit();
                                editor.putString("id", ob.get(0).id);
                                editor.putString("image",ob.get(0).image);
                                editor.putString("name", ob.get(0).name);
                                editor.putString("email", ob.get(0).email);
                                editor.putString("session","active");
                                editor.putString("type","user");
                                editor.putString("email", ob.get(0).email);
                                editor.putString("country",ob.get(0).country );
                                editor.putString("state",ob.get(0).state );
                                editor.putString("phone",ob.get(0).phone );

                                editor.putString("language", "arabic");

                                editor.putString("password",ob.get(0).PASSWORD );
                                editor.apply();
//                                SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
  //                              editor.apply();


                                //      Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();
                                Intent op=new Intent(getActivity(), ProviderMain.class);
                                startActivity(op);
                                getActivity().finish();

                                //             progressBar.setVisibility(View.INVISIBLE);
                                // progressBar.setVisibility(View.INVISIBLE);

                            } catch (Exception e) {

                                //       progressBar.setVisibility(View.INVISIBLE);
                                progress.setVisibility(View.INVISIBLE);

                                // Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Providerinformation>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getActivity(),"Sign in error Please try again!",Toast.LENGTH_SHORT ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(getActivity(),"Sign in error Please try again!",Toast.LENGTH_SHORT ).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(getContext(),"Sign in error Please try again!",Toast.LENGTH_SHORT ).show();

        }


    }

}