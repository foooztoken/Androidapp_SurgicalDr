package lab.pak.com.app.MyJobsProvider;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.MyJobsUser.JobDetailProvider;
import lab.pak.com.app.R;
import lab.pak.com.app.UserModule.Main;

public class MenuProvider extends AppCompatActivity {
RelativeLayout orderconversation,orderdescription,orderrequests;
    private Jobs jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_provider);
        Gson gson = new Gson();
        String studentDataObjectAsAString = getIntent().getStringExtra("ob");
        jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);

        init();
        orderrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProviderRequests.class);
                Gson gson = new Gson();
                String studentDataObjectAsAString = getIntent().getStringExtra("ob");
                jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
                Gson gso = new Gson();
                String json = gso.toJson(jobs);
                i.putExtra("ob", json);
                startActivity(i);
            }
        });
        orderdescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), JobDetailUserNew.class);
                Gson gson = new Gson();
                String studentDataObjectAsAString = getIntent().getStringExtra("ob");
                jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
                Gson gso = new Gson();
                String json = gso.toJson(jobs);
                i.putExtra("ob", json);
                startActivity(i);
            }
        });
        orderconversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent op=new Intent(getApplicationContext(), JobDetailProvider.class);
                Gson gson = new Gson();
                String studentDataObjectAsAString = getIntent().getStringExtra("ob");
                jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
                Gson gso = new Gson();
                String json = gso.toJson(jobs);
                op.putExtra("ob", json);
                startActivity(op);
            }
        });
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Button b=findViewById(R.id.home);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent o=new Intent(getApplicationContext(), Main.class);
                startActivity(o);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent o=new Intent(getApplicationContext(), Main.class);

            startActivity(o);// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    void init(){
orderconversation=findViewById(R.id.orderconversation);
orderdescription=findViewById(R.id.orderdescription);
orderrequests=findViewById(R.id.orderrequests);
    }
}
