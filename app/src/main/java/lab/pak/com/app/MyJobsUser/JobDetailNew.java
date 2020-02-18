package lab.pak.com.app.MyJobsUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.BidsModule.CustomAdapterbids;
import lab.pak.com.app.BidsOrder.MybidsOrder;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.Models.bids;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import lab.pak.com.app.Review.ReviewActivity;
import lab.pak.com.app.UserModule.Main;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class JobDetailNew extends AppCompatActivity   {

    private Jobs jobs;

TextView budget,attachment,mfattachment,status,description,age,bloodgroup,medicationtaken,history,chronicdisease;
    private TextView patientname,name,roomtype,bloodtransfer,icu,nodays;
    private Button clearorder;
    TextView stat,ag,blood,hist,chronicdiseas,ur,desc,roomtyp,mybid;

    RelativeLayout myjobs,findjobs;
    private Retrofit retrofit;
    ProgressBar progress;
    private TextView medicationtake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_new);
try{
        Gson gson = new Gson();
        String studentDataObjectAsAString = getIntent().getStringExtra("ob");
        jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
init();
      //  status.setText(jobs.status);

      //  providername.setText("Order "+jobs.status);
    SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
    final String id = prefs.getString("id", null);
    login(id,jobs.id);
    Button refresh=findViewById(R.id.refresh);

    refresh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login(id,jobs.id);
        }
    });
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
      /*  Button b=findViewById(R.id.home);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent o=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(o);
            }
        });
*/


        /*
*/




/*
                if(jobs.status.equals("pending")){
                    Intent intent=new Intent(JobDetailNew.this,MybidsOrder.class);
                    intent.putExtra("jobid",jobs.id );
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);


                }else{





                    String o="order has been "+jobs.status;
                    Toast.makeText(getApplicationContext(),o ,Toast.LENGTH_SHORT ).show();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }}
*/


    Button b=findViewById(R.id.home);

    b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            Intent o=new Intent(getApplicationContext(), Main.class);
            startActivity(o);
        }
    });
    SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
    String language = pref.getString("language", null);
    String typee = pref.getString("type", null);
    if(language!=null&&language.equals("arabic")&&typee.equals("user")){
        Toolbar   toolb=findViewById(R.id.header);

        toolb.setTitle("عمليات");
        ag.setText("العمر");
        blood.setText("فصيلة الدم");
        medicationtake.setText("الأدوية التي تتناولها بأستمرار");
        hist.setText("التاريخ الطبي");
        chronicdiseas.setText("الأمراض المزمنة");
        stat.setText("الحالة");
        desc.setText("وصف");
        roomtyp.setText("درجة الأقامة");
        attachment.setText("المرفق");
     TextView mbid=findViewById(R.id.mybids);
           mbid.setText("عروض الأسعار");
        //   mybid.setText("عروض الأسعار");
        TextView pleasecheck=findViewById(R.id.attachment);
        pleasecheck.setText("أستعراض التقارير");


        budget.setText("$" + jobs.budget);
        status.setText(jobs.status);
        description.setText(jobs.description);
        medicationtaken.setText(jobs.medicationtaken);
        age.setText(jobs.age);
        history.setText(jobs.previoushistory);
        bloodgroup.setText(jobs.bloodgroup);
        chronicdisease.setText(jobs.chronicedisease);
        roomtype.setText(jobs.roomtype);
    }else {
      //  patientname.setText(jobs.country);
        budget.setText("$" + jobs.budget);
        status.setText(jobs.status);
        description.setText(jobs.description);
        medicationtaken.setText(jobs.medicationtaken);
        age.setText(jobs.age);
        history.setText(jobs.previoushistory);
        bloodgroup.setText(jobs.bloodgroup);
        chronicdisease.setText(jobs.chronicedisease);
        roomtype.setText(jobs.roomtype);
    }
        SharedPreferences prefd = getSharedPreferences("userdata", MODE_PRIVATE);
        String type = prefd.getString("type", null);
        if(type.equals("user")){
            if(jobs.status.equals("started")){

            }else{
                clearorder.setVisibility(View.INVISIBLE);
            }

            clearorder.setText("Clear Order");
        }else{
            if(jobs.status.equals("complete")){
                clearorder.setText("Review");
            }else{
                clearorder.setVisibility(View.INVISIBLE);
            }
        }



        clearorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent op=new Intent(JobDetailNew.this, ReviewActivity.class);
                Gson gson = new Gson();
                String studentDataObjectAsAString = getIntent().getStringExtra("ob");
                jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
                Gson gso = new Gson();
                String json = gso.toJson(jobs);
                op.putExtra("ob", json);
startActivity(op);
            }
        });
attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mains = jobs.attachmenturl;
                String newstring = mains.replace("uploading/", "");

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://surapi.surgicaldr.com/Models/uploading/" + newstring ));
                startActivity(browserIntent);
            }
        });

        mfattachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mains = jobs.attachmenturl;
                String newstring = mains.replace("uploading/", "");

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://surapi.surgicaldr.com/Models/uploading/" + newstring ));
                startActivity(browserIntent);

    }});










    //    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
     //   navigationView.setNavigationItemSelectedListener(this);




    }catch (Exception e){
        Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG ).show();
    }

    }


    void login(String id,String jobid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<bids>> callPet = service.getbids( "http://surapi.surgicaldr.com/Models/bidlistprovider.php?providerid="+id+"&jobid="+jobid);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<bids>>() {
                @Override
                public void onResponse(Call<List<bids>> call, Response<List<bids>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<bids> ob=response.body();


                                ArrayList<bids> jobs=new ArrayList<>();
                                SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                                String id = prefs.getString("id", null);
                                //    Toast.makeText(Myjobs.this, id, Toast.LENGTH_SHORT).show();
                                if (id != null) {

                                    for(int i=0;i<ob.size();i++){

                                        jobs.add(ob.get(i));

                                    }}


                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterbids customAdapter = new CustomAdapterbids(JobDetailNew.this,jobs);// address,name,amount,idd,lat,lon,phone);
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

                                //           Toast.makeText(Mybids.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //Toast.makeText(
                            //MybidsOrder.this, e.toString(), Toast.LENGTH_LONG).show();

                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<bids>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        //         Toast.makeText(Mybids.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
        finish(); // OPEN DRAWER
            return true;
           // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    private void init() {
try {
    stat=findViewById(R.id.stat);
    medicationtake=findViewById(R.id.md);

    ag=findViewById(R.id.sta);
    blood=findViewById(R.id.st);
    hist=findViewById(R.id.m);
    chronicdiseas=findViewById(R.id.ma);
    ur=findViewById(R.id.mfattachment);
    desc=findViewById(R.id.des);
    roomtyp=findViewById(R.id.roomtype);

    nodays = findViewById(R.id.nodaysname);
    bloodtransfer = findViewById(R.id.bloodtransfername);
    roomtype = findViewById(R.id.roomtypename);
    icu = findViewById(R.id.icuname);
patientname=findViewById(R.id.patientname);
progress=findViewById(R.id.progress);
    clearorder = findViewById(R.id.clearorder);
    attachment = findViewById(R.id.attachment);
    mfattachment = findViewById(R.id.mfattachment);
    age = findViewById(R.id.age);
    bloodgroup = findViewById(R.id.bloodgroup);
    medicationtaken = findViewById(R.id.medicationtaken);
    history = findViewById(R.id.history);
    chronicdisease = findViewById(R.id.chronicdisease);
    name = findViewById(R.id.patientname);
    budget = findViewById(R.id.budget);
    status = findViewById(R.id.statustextt);
description=findViewById(R.id.description);
}catch (Exception e){
    Toast.makeText(getApplicationContext(),"here"+e.toString() ,Toast.LENGTH_LONG ).show();
}
    }
}
