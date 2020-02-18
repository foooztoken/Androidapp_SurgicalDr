package lab.pak.com.app.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.MainActivity;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.Models.Providerinformation;
import lab.pak.com.app.Models.Userinformation;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import lab.pak.com.app.Review.Review;
import lab.pak.com.app.Review.ReviewActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFeedback extends AppCompatActivity {
static String provider,user;
    private Retrofit retrofit;
    private ProgressBar progress;
static String urldata;
    private TextView phone,id,name,email;
    private TextView idtext;
    private TextView info;
    private Jobs jobs;
Button review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilenew);
        try{
            Gson gson = new Gson();
            String studentDataObjectAsAString = getIntent().getStringExtra("ob");
            jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);

            final String id= getIntent().getStringExtra("id");
        String type= getIntent().getStringExtra("type");

        init();
review.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Intent op=new Intent(ProfileFeedback.this, ReviewActivity.class);
        Jobs ob = jobs;
        Gson gson = new Gson();
        String json = gson.toJson(ob);
        op.putExtra("ob", json);
    startActivity(op);
    }
});
        if(type.equals("provider"))
        {user="0";
            provider=id;
            urldata="providerinformation";
            info.setText("User Information:");
        }else{changelanguage();
            urldata="userinformation";
            provider="0";
            user=id;
            info.setText("Provider Information:");

        }

///*Retrieve Reviews///
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Button b=findViewById(R.id.home);
            Button refresh=findViewById(R.id.refresh);

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getreviews(provider, user);

                    if(provider.equals("0"))
                    {


                        try{
                            getuserdata(id);}catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() , Toast.LENGTH_LONG).show();}}else {

                        try {
                            getproviderdata(id);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                    }


            }
            });        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent o=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(o);
            }
        });
try {
    getreviews(provider, user);
}catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() , Toast.LENGTH_LONG).show();}
        //*Retrieve data of user or provider///
      if(provider.equals("0"))
      {


          try{
          getuserdata(id);}catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() , Toast.LENGTH_LONG).show();}}else{

          try{
          getproviderdata(id);
              }catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() , Toast.LENGTH_LONG).show();}


          }}catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG ).show();}

    }
    private void changelanguage() {
        SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
        String language = pref.getString("language", null);
        if(language!=null&&language.equals("arabic")) {
            Toolbar toolb = findViewById(R.id.header);
            toolb.setTitle("صفحة مقدم الخدمة");

            TextView images=findViewById(R.id.images);
            images.setText("صور المستشفي");
            TextView review =findViewById(R.id.rev);
            review.setText("تقييمات العملاء الأخرين");

        }
    }
    private void init() {
        try {
            review=findViewById(R.id.review);
            progress = findViewById(R.id.progress);
            phone = findViewById(R.id.phonetext);
            name = findViewById(R.id.nametext);
            idtext = findViewById(R.id.idtext);
            email = findViewById(R.id.emailtext);
        info=findViewById(R.id.info);
        }catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG ).show();}}


    void getreviews(String provider,String user){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Review>> callPet = service.getreview( "http://surapi.surgicaldr.com/Models/reviews.php?providerid="+provider+"&employeeid="+user);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Review>>() {
                @Override
                public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Review> ob=response.body();


                                ArrayList<Review> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){

                                    jobs.add(ob.get(i));

                                }
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterReview customAdapter = new CustomAdapterReview(ProfileFeedback.this,jobs);// address,name,amount,idd,lat,lon,phone);
                                recyclerView.setAdapter(customAdapter);


                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                Toast.makeText(ProfileFeedback.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Review>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
      } catch (Exception a) {
                       progress.setVisibility(View.INVISIBLE);

                        Toast.makeText(ProfileFeedback.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });

        } catch (Exception a) {

            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(ProfileFeedback.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void getuserdata(String id){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Userinformation>> callPet = service.login( "http://surapi.surgicaldr.com/Models/userinformation.php?id="+id);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Userinformation>>() {
                @Override
                public void onResponse(Call<List<Userinformation>> call, Response<List<Userinformation>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Userinformation> ob=response.body();

name.setText(ob.get(0).name);
email.setText(ob.get(0).email);
idtext.setText(ob.get(0).id);
phone.setText(ob.get(0).phone);


                                ArrayList<String> images = new ArrayList<>();
                                images.add("http://surapi.surgicaldr.com/Models/uploading/" + ob.get(0).imageone);
                                images.add("http://surapi.surgicaldr.com/Models/uploading/" + ob.get(0).imagetwo );
                                images.add("http://surapi.surgicaldr.com/Models/uploading/" + ob.get(0).imagethree);
                                images.add("http://surapi.surgicaldr.com/Models/uploading/" + ob.get(0).imagefour);
                                images.add("http://surapi.surgicaldr.com/Models/uploading/" + ob.get(0).imagefive);
                                images.add("http://surapi.surgicaldr.com/Models/uploading/" + ob.get(0).imagesix);
                             //   images.add("http://surapi.surgicaldr.com/Models/uploading/" + ob.get(0).imagesix);

                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleimages);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterImages customAdapter = new CustomAdapterImages(ProfileFeedback.this, images);// address,name,amount,idd,lat,lon,phone);
                                recyclerView.setAdapter(customAdapter);


                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                Toast.makeText(ProfileFeedback.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {         Toast.makeText(ProfileFeedback.this, e.toString(), Toast.LENGTH_LONG).show();

                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Userinformation>> call, Throwable t) {
                    try {
                        Toast.makeText(ProfileFeedback.this, t.toString(), Toast.LENGTH_LONG).show();

                        progress.setVisibility(View.INVISIBLE);
                    } catch (Exception a) {
                        progress.setVisibility(View.INVISIBLE);

                        Toast.makeText(ProfileFeedback.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });

        } catch (Exception a) {
            Toast.makeText(ProfileFeedback.this, a.toString(), Toast.LENGTH_LONG).show();

            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(ProfileFeedback.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void getproviderdata(String id){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Providerinformation>> callPet = service.loginprovider( "http://surapi.surgicaldr.com/Models/providerinformation.php?id="+id);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Providerinformation>>() {
                @Override
                public void onResponse(Call<List<Providerinformation>> call, Response<List<Providerinformation>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Providerinformation> ob=response.body();
                                name.setText(ob.get(0).name);
                                email.setText(ob.get(0).email);
                                idtext.setText(ob.get(0).id);
                                phone.setText(ob.get(0).phone);

      } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                Toast.makeText(ProfileFeedback.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        Toast.makeText(ProfileFeedback.this, e.toString(), Toast.LENGTH_LONG).show();

                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Providerinformation>> call, Throwable t) {
                    try {
                        Toast.makeText(ProfileFeedback.this, t.toString(), Toast.LENGTH_LONG).show();

                        progress.setVisibility(View.INVISIBLE);
                    } catch (Exception a) {
                        progress.setVisibility(View.INVISIBLE);

                        Toast.makeText(ProfileFeedback.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });

        } catch (Exception a) {
            Toast.makeText(ProfileFeedback.this, a.toString(), Toast.LENGTH_LONG).show();

            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(ProfileFeedback.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


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
