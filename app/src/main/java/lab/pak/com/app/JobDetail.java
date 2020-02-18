package lab.pak.com.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.BidsOrder.BidAcitivity;
import lab.pak.com.app.BidsOrder.CustomAdapterbidsorder;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.Models.bids;
import lab.pak.com.app.UserModule.Main;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class JobDetail extends AppCompatActivity {
TextView name,des,amount,roomtype,jobtype,degreeclass,doctype;
    private Jobs jobs;
    private Retrofit retrofit;
Button insert;
ProgressBar progressBar;
TextView age,history,chronicdisease,bloodgroup,medicationtaken,bloodtype,roomtyp;
RelativeLayout attachment;
    private TextView mfattachment,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

   try {
       Bundle bundle = getIntent().getExtras();
       String nam = bundle.getString("name");
       String de = bundle.getString("des");
       String amoun = bundle.getString("amount");
       String roomtyp = bundle.getString("roomtype");
       String jobtyp = bundle.getString("jobtype");
       String degreeclas = bundle.getString("degreeclass");
        String doctyp = bundle.getString("doctortype");
       String aget = bundle.getString("age");
       String historyt = bundle.getString("history");
       String diseaset = bundle.getString("disease");
       String medt = bundle.getString("med");
       String bloodgroupt = bundle.getString("bloodgroup");

       String roomtypet = bundle.getString("roomtype");
       String bloodtransfert = bundle.getString("bloodtransfer");
       String nodayst = bundle.getString("nodays");
       String icuincludet = bundle.getString("icuinclude");

       Gson gson = new Gson();
       String studentDataObjectAsAString = getIntent().getStringExtra("ob");
       jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
       Toolbar toolbar = findViewById(R.id.header);
       setSupportActionBar(toolbar);
       ActionBar actionbar = getSupportActionBar();
       actionbar.setDisplayHomeAsUpEnabled(true);
       Button b=findViewById(R.id.home);

       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
               Intent o=new Intent(getApplicationContext(),MainActivity.class);
               startActivity(o);
           }
       });

       init();
      // Toast.makeText(getApplicationContext(), bloodgroupt + " " + historyt + " " + diseaset + " " + medt, Toast.LENGTH_LONG).show();

       if(doctyp.equals(null)||doctyp.equals("")){
           doctyp="Not available";}
       if(nam.equals(null)||nam.equals("")){
           nam="Not available";}
       name.setText( nam);
       if(de.equals(null)||de.equals("")){
           de="Not available";}
       des.setText( de);
       if(amoun.equals(null)||amoun.equals("")){
           amoun="Not available";}
       amount.setText("$"+ amoun);
       if(roomtypet.equals(null)||roomtypet.equals("")){
           roomtypet="Not available";}
       roomtype.setText( roomtypet);
       if(jobtyp.equals(null)||jobtyp.equals("")){
           jobtyp="Not available";}

       jobtype.setText(jobtyp);




       if(!(aget.equals(null))){
           age.setText(aget);
       }else{
           age.setText("Not specified");
       }

       if(!(jobs.bloodgroup.equals(null))){
           bloodgroup.setText(jobs.bloodgroup);
       }else{
          bloodgroup.setText("Not specified");
       }

       if(!(jobs.city.equals(null))){
           city.setText(jobs.city);
       }else{
           city.setText("Not specified");
       }


       if(!(diseaset.equals(null))){
           chronicdisease.setText(diseaset);
       }else{
           chronicdisease.setText("Not specified");
       }
       if(!(medt.equals(null))){
          medicationtaken.setText(medt);
       }else{
           medicationtaken.setText("Not specified");
       }
       if(!(historyt.equals(null))){
           history.setText(historyt);
       }else{
           history.setText("Not specified");
       }
       if(!(bloodgroupt==null)){
           bloodgroup.setText(bloodgroupt);
       }else{
           bloodgroup.setText("Not specified");
       }

       if(!(roomtypet==null)){
           roomtype.setText(roomtypet);
       }else{
           roomtype.setText("Not specified");
       }

       SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);

       String id = prefs.getString("id", null);
       login(jobs.providerid,jobs.id);

   }catch (Exception e){
 //    Toast.makeText(JobDetail.this,e.toString() , Toast.LENGTH_LONG).show();
   }
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
            }
        });
   insert.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
Intent op=new Intent(JobDetail.this, BidAcitivity.class);
String pidd=jobs.providerid;
op.putExtra("bid",jobs.id );
op.putExtra("pid",jobs.providerid );
startActivity(op);
       }
   });
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent o=new Intent(JobDetail.this, Main.class);

            startActivity(o);// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    void login(String id,String jobid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<bids>> callPet = service.getbids( "http://surapi.surgicaldr.com/Models/bidlistprovider.php?providerid="+id+"&jobid="+jobid);
        //    progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<bids>>() {
                @Override
                public void onResponse(Call<List<bids>> call, Response<List<bids>> response) {
                    try {
          //              progress.setVisibility(View.INVISIBLE);

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
                                CustomAdapterbidsorder customAdapter = new CustomAdapterbidsorder(getApplicationContext(),jobs);// address,name,amount,idd,lat,lon,phone);
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
            //                    progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                //           Toast.makeText(Mybids.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                        //progressBar.setVisibility(View.INVISIBLE);
              //          progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<bids>> call, Throwable t) {
                    try {
                //        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        //         Toast.makeText(Mybids.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                  //      progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
          //  progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void init(){
        attachment=findViewById(R.id.attachment);
        age=findViewById(R.id.age);
        bloodgroup=findViewById(R.id.bloodgroup);
        medicationtaken=findViewById(R.id.medicationtaken);
        history=findViewById(R.id.history);
        chronicdisease=findViewById(R.id.chronicdisease);
mfattachment=findViewById(R.id.attachmenturl);
     insert=findViewById(R.id.insert);
     city=findViewById(R.id.city);
        name=findViewById(R.id.name);
        des=findViewById(R.id.description);
        amount=findViewById(R.id.providertype);
        roomtype=findViewById(R.id.roomtype);
        jobtype=findViewById(R.id.jobtype);
progressBar=findViewById(R.id.progress);

roomtype=findViewById(R.id.roomtype);

        doctype=findViewById(R.id.doctortype);
    }
}
