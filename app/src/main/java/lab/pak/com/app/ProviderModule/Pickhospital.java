package lab.pak.com.app.ProviderModule;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import lab.pak.com.app.GeneralAdapters.CompleteOrdersProvider;
import lab.pak.com.app.MyJobsUser.MyJobsUser;
import lab.pak.com.app.R;

public class Pickhospital extends AppCompatActivity {

    private Button b,open,close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickhospital);
        try{
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        ///Home button code below
        init();
      
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent o=new Intent(getApplicationContext(), ProviderMain.class);
                startActivity(o);
            }
        });
  
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MyJobsUser.class);
                startActivity(intent);
             //   mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CompleteOrdersProvider.class);
                startActivity(intent);
            }
        });
        
    }catch (Exception e){}}
    void init(){

         b=findViewById(R.id.home);
open=findViewById(R.id.open);
close=findViewById(R.id.close);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent o=new Intent(getApplicationContext(),ProviderMain.class);

            startActivity(o);// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
