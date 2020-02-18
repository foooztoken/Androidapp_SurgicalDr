package lab.pak.com.app.Payment;

import android.content.Intent;
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

import com.google.gson.Gson;

import lab.pak.com.app.R;
import lab.pak.com.app.UserModule.Main;
import lab.pak.com.app.Models.bids;

public class PaymentMenu extends AppCompatActivity {
Button paypal,bank;
ProgressBar progress;
    private String value;
    private bids jobs;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_menu);
        Intent mIntent = getIntent();
        value = getIntent().getStringExtra("value");
        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        String studentDataObjectAsAString = getIntent().getStringExtra("ob");
        jobs = gson.fromJson(studentDataObjectAsAString, bids.class);

   init();

    paypal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent op = new Intent(getApplicationContext(), PaymentDetails.class);
            String vall= String.valueOf(value);
            op.putExtra("value",vall );
            bids o = jobs;
            Gson gson = new Gson();
            String json = gson.toJson(o);
            op.putExtra("ob", json);


            startActivity(op);
        }
    });
    bank.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent op = new Intent(getApplicationContext(), BankTransfer.class);
            bids o = jobs;
            Gson gson = new Gson();
            String json = gson.toJson(o);
            op.putExtra("ob", json);


            startActivity(op);
        }
    });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent o=new Intent(getApplicationContext(), Main.class);
                startActivity(o);
            }
        });
        //translation
        changelanguage();
}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
          //;// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    void changelanguage(){
        SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
        String language = pref.getString("language", null);
        String typee = pref.getString("type", null);
        if(language!=null&&language.equals("arabic")&&typee.equals("user")){
            Toolbar o=findViewById(R.id.header);
            o.setTitle("أختار طريقة الدفع");
            TextView mode=findViewById(R.id.mode);
            mode.setText("أختار طريقة الدفع");
 bank.setText("أيداع بنكي");
 paypal.setText("بطاقة الائتمان");

        }
    }
    void init(){
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
       b=findViewById(R.id.home);
       paypal=findViewById(R.id.paypal);
        bank=findViewById(R.id.bank);
        progress=findViewById(R.id.progress);
    }
}
