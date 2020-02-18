package lab.pak.com.app.BidsModule;

import android.content.Intent;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import lab.pak.com.app.MainActivity;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.Models.bids;
import lab.pak.com.app.MyJobsUser.JobDetailUser;
import lab.pak.com.app.Payment.PaymentMenu;
import lab.pak.com.app.Profile.Profile;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import lab.pak.com.app.UserModule.ConversationBidsUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BidDetails extends AppCompatActivity {
    private Retrofit retrofit;
    private bids jobs;
    private Button submit,profile,recommendation;
TextView des,budget,specialization,city,state,roomtype,icu,blood,nodays;
    private ProgressBar progress;
String value;
    private static String typestatic;
Button conversation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_details);
        try {
            Bundle bundle = getIntent().getExtras();
            Gson gson = new Gson();
            Intent mIntent = getIntent();
            value = mIntent.getStringExtra("value");
            String studentDataObjectAsAString = getIntent().getStringExtra("ob");
            jobs = gson.fromJson(studentDataObjectAsAString, bids.class);
            init();
            Toolbar toolbar = findViewById(R.id.header);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            Button bd=findViewById(R.id.home);
            SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
            typestatic = prefs.getString("type", null);
            bd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent o=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(o);
                }
            });

            SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
            String language = pref.getString("language", null);
            String typee = pref.getString("type", null);
            if(language!=null&&language.equals("arabic")&&typee.equals("user")){
                Toolbar toolb = findViewById(R.id.header);

                toolb.setTitle("عمليات");
                des.setText("وصف :" + jobs.description);


                budget.setText("التكلفة :" + jobs.budget);

                //if(!(jobs.city.equals(null))) {

                city.setText("المدينة:" + jobs.city);
                //}
                // if(!(jobs.state.equals(null))) {
                state.setText("الدولة: " + jobs.country);
                // }
                // if(!(jobs.nodays.equals(null))) {

                nodays.setText("الدولة:" + jobs.nodays);
                //}
                //if(!(jobs.roomtype.equals(null))) {

                roomtype.setText("درجة الأقامة :" + jobs.roomtype);
                // }
                // if(!(jobs.bloodtransfer.equals(null))) {

                blood.setText("نقل الدم :" + jobs.bloodtransfer);
                //}
                //if(!(jobs.icuincluded.equals(null))) {

                icu.setText("يشمل العلاج بالرعاية المركزة :" + jobs.icuinclude);
                // }
                conversation.setText("محادثة");
                recommendation.setText("توصية");
                profile.setText("مشاهدة صور و تقييمات المستشفي");
                submit.setText("موافقة");

            }else {

                des.setText("Description: " + jobs.description);


                budget.setText("Budget: " + jobs.budget);

                //if(!(jobs.city.equals(null))) {

                city.setText("City: " + jobs.city);
                //}
                // if(!(jobs.state.equals(null))) {
                state.setText("Country: " + jobs.country);
                // }
                // if(!(jobs.nodays.equals(null))) {

                nodays.setText("No of Days: " + jobs.nodays);
                //}
                //if(!(jobs.roomtype.equals(null))) {

                roomtype.setText("Room Type: " + jobs.roomtype);
                // }
                // if(!(jobs.bloodtransfer.equals(null))) {

                blood.setText("Blood Transfer: " + jobs.bloodtransfer);
                //}
                //if(!(jobs.icuincluded.equals(null))) {

                icu.setText("Icu Included: " + jobs.icuinclude);
                // }
            }
            float b= jobs.budget;


            if(typestatic.equals("provider")){
                submit.setVisibility(View.INVISIBLE);
profile.setVisibility(View.INVISIBLE);
                conversation.setVisibility(View.VISIBLE);

recommendation.setVisibility(View.VISIBLE);
            }else{
                conversation.setVisibility(View.VISIBLE);

            }
conversation.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent op=new Intent(getApplicationContext(), ConversationBidsUser.class);
        Gson gson = new Gson();
        String studentDataObjectAsAString = getIntent().getStringExtra("ob");
        jobs = gson.fromJson(studentDataObjectAsAString, bids.class);
        Gson gso = new Gson();
        String json = gso.toJson(jobs);
        op.putExtra("ob", json);
        startActivity(op);
    }
});
final float val= (float) (b*0.05);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(value.equals("a")) {









submit(jobs.bidid, jobs.providerid,jobs.userid ,jobs.jobid );
                    }else{
                        Intent op = new Intent(BidDetails.this, PaymentMenu.class);
                    String vall= String.valueOf(val);
                        op.putExtra("value",vall );
                        bids o = jobs;
                        Gson gson = new Gson();
                        String json = gson.toJson(o);
                       op.putExtra("ob", json);


                        startActivity(op);
                }}
            });
            recommendation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
Intent o=new Intent(getApplicationContext(),Recommendations.class);
o.putExtra("id", jobs.id);
o.putExtra("orderid",jobs.jobid );
                startActivity(o);}
            });

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(typestatic.equals("user")){
                    Intent op = new Intent(BidDetails.this, Profile.class);
                    op.putExtra("id", jobs.userid);
                    op.putExtra("type", "user");
                    op.putExtra("jobid",jobs.id );
                    startActivity(op);}
                else{
                    Intent op = new Intent(BidDetails.this, Profile.class);
                    op.putExtra("id", jobs.providerid);
                    op.putExtra("type", "provider");
                    op.putExtra("jobid",jobs.id );
                    startActivity(op);
                }
                }
            });
        }catch (Exception e){
           // Toast.makeText(BidDetails.this,e.toString() ,Toast.LENGTH_SHORT ).show();
        }
    }
void changelanguage(){


}
    void init() {try{
        recommendation=findViewById(R.id.recommendation);
        budget=findViewById(R.id.budget);
progress=findViewById(R.id.progress);
des=findViewById(R.id.description);
state=findViewById(R.id.state);
city=findViewById(R.id.city);
conversation=findViewById(R.id.conversation);
blood=findViewById(R.id.bloodtransfer);
roomtype=findViewById(R.id.roomtype);
icu=findViewById(R.id.icuinclude);
nodays=findViewById(R.id.nodays);
submit=findViewById(R.id.accept);
profile=findViewById(R.id.profile);
    }catch (Exception e){
      //  Toast.makeText(BidDetails.this,e.toString() ,Toast.LENGTH_SHORT ).show();
    }}
    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        long msTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy  hh:mm");
        return sdf.format(cal.getTime());
    }
        void submit(String bidid,String providerid,String userid,String jobid){
            try {
                progress.setVisibility(View.VISIBLE);
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://surapi.surgicaldr.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                String budge=budget.getText().toString();

                String cit=city.getText().toString();
                String stat=state.getText().toString();
                String date=giveDate();
                final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
                String json = "{\"bidid\":\""+bidid+"\",\"providerid\":\""+providerid+"\",\"userid\":\""+userid+"\",\"budget\":\""+budge+"\",\"city\":\""+cit+"\",\"jobid\":\""+jobs.jobid+"\",\"time\":\""+date+"\"}";
                JSONObject obj = new JSONObject(json);
                Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/bidaccept.php?var="+obj);

                callPet.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {

                        //       progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(BidDetails.this,response.body().toString() ,Toast.LENGTH_LONG ).show();
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

                            Toast.makeText(BidDetails.this,t.toString() ,Toast.LENGTH_LONG ).show();
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
                Toast.makeText(BidDetails.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

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
