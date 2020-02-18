package lab.pak.com.app.MyJobsProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.R;
import lab.pak.com.app.Review.ReviewActivity;
import lab.pak.com.app.UserModule.Main;

public class JobDetailUserNew extends AppCompatActivity   {

    private Jobs jobs;
    RelativeLayout myjobs,findjobs;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ProgressBar progressBar;
TextView budget,attachment,mfattachment,status,description,age,bloodgroup,medicationtaken,history,chronicdisease;
    private TextView name,roomtype,bloodtransfer,icu,nodays;
    private Button clearorder;
//    private DrawerLayout mDrawerLayout;

    RelativeLayout bid;

    private ProgressBar progress;
TextView providername;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_newuser);
try {
    Gson gson = new Gson();
    String studentDataObjectAsAString = getIntent().getStringExtra("ob");
    jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
    init();

    status.setText(jobs.status);
    Toolbar toolbar = findViewById(R.id.header);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    Button b=findViewById(R.id.home);
int s=1;
    if(s==1){

}
    b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            Intent o=new Intent(getApplicationContext(), Main.class);
            startActivity(o);
        }
    });
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




       /*         Intent op = new Intent(getApplicationContext(), Profile.class);

                op.putExtra("id", jobs.providerid);
                op.putExtra("type", "provider");
                startActivity(op);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                finish();
                //finish();
       */



      /*
                Intent op=new Intent(JobDetailUserNew.this,JobDetailProvider.class);
                Gson gson = new Gson();
                String studentDataObjectAsAString = getIntent().getStringExtra("ob");
                jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
                Gson gso = new Gson();
                String json = gso.toJson(jobs);
                op.putExtra("ob", json);
                startActivity(op);
*/
        /*

  try {
      Intent i = new Intent(JobDetailUserNew.this, ProviderRequests.class);
      Gson gson = new Gson();
      String studentDataObjectAsAString = getIntent().getStringExtra("ob");
      jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
      Gson gso = new Gson();
      String json = gso.toJson(jobs);
      i.putExtra("ob", json);
      startActivity(i);
      mDrawerLayout.closeDrawer(GravityCompat.START);
  }catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG ).show();}     }
        });
*/
    name.setText(jobs.providername);
    budget.setText("$" + jobs.budget);
    status.setText(jobs.status);
    description.setText(jobs.description);
    medicationtaken.setText(jobs.medicationtaken);
    age.setText(jobs.age);
    history.setText(jobs.previoushistory);
    bloodgroup.setText(jobs.bloodgroup);
    chronicdisease.setText(jobs.chronicedisease);
roomtype.setText(jobs.roomtype);
    SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
    String type = prefs.getString("type", null);
    if (type.equals("user")) {
        if (jobs.status.equals("started")) {

        } else {
            clearorder.setVisibility(View.INVISIBLE);
        }

        clearorder.setText("Clear Order");
    } else {
        if (jobs.status.equals("complete")) {
            clearorder.setText("Review");
        } else {
            clearorder.setVisibility(View.INVISIBLE);
        }
    }


    clearorder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent op = new Intent(JobDetailUserNew.this, ReviewActivity.class);
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

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://surapi.surgicaldr.com/Models/uploading/" + newstring));
            startActivity(browserIntent);
        }
    });

    mfattachment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mains = jobs.attachmenturl;
            String newstring = mains.replace("uploading/", "");

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://surapi.surgicaldr.com/Models/uploading/" + newstring));
            startActivity(browserIntent);

        }
    });


}catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG ).show();}













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
finish();
return true;
           // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    private void init() {
        nodays=findViewById(R.id.nodaysname);
   image=findViewById(R.id.imagemain);
        bloodtransfer=findViewById(R.id.bloodtransfername);
        roomtype=findViewById(R.id.roomtypename);
        icu=findViewById(R.id.icuname);
        bid=findViewById(R.id.bids);
    //    attachment=findViewById(R.id.attachment);

        clearorder=findViewById(R.id.clearorder);
        attachment=findViewById(R.id.attachment);
        mfattachment=findViewById(R.id.mfattachment);
        age=findViewById(R.id.age);


        bloodgroup=findViewById(R.id.bloodgroup);
        medicationtaken=findViewById(R.id.medicationtaken);
        history=findViewById(R.id.history);
        chronicdisease=findViewById(R.id.chronicdisease);
        name=findViewById(R.id.patientname);
        budget=findViewById(R.id.budget);
        status=findViewById(R.id.budget);
        description=findViewById(R.id.description);





       status=findViewById(R.id.statustextt);

        attachment=findViewById(R.id.attachment);


        findjobs=findViewById(R.id.findjobs);
        myjobs=findViewById(R.id.myjobs);
        progress=findViewById(R.id.progress);

    }

  }
