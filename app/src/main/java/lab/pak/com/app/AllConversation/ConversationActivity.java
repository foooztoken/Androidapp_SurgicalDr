package lab.pak.com.app.AllConversation;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.BidsModule.CustomAdapterbids;
import lab.pak.com.app.BidsOrder.MybidsOrder;
import lab.pak.com.app.MainActivity;
import lab.pak.com.app.Models.Messages;
import lab.pak.com.app.Models.bids;
import lab.pak.com.app.ProviderModule.ProviderMain;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import lab.pak.com.app.UserModule.About;
import lab.pak.com.app.UserModule.Main;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConversationActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        init();
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Button b=findViewById(R.id.home);
        Button refresh=findViewById(R.id.refresh);

        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        //  String id = prefs.getString("session", null);
        final String type = prefs.getString("type", null);
        final String id = prefs.getString("id", null);
        if(type.equals("provider")){
            getconversation(0,Integer.parseInt(id));
        }
            else{
            getconversation( Integer.parseInt(id),0);
            }
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("provider")){
                    getconversation(0,Integer.parseInt(id));
                }
                else{
                    getconversation( Integer.parseInt(id),0);
                }            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("provider")){
                 //   getconversation(id,"0");
              Intent o=new Intent(ConversationActivity.this,MainActivity.class);
              startActivity(o);
                }
                else{
                    Intent o=new Intent(ConversationActivity.this, ProviderMain.class);
                    startActivity(o);
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
           // Intent o=new Intent(getApplicationContext(), Main.class);

         //   startActivity(o);// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
void init(){
        progress=findViewById(R.id.progress);
}

    void getconversation(int providerid,int userid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
String url="http://surapi.surgicaldr.com/Models/conversation.php?providerid="+providerid+"&userid="+userid;
///Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Messages>> callPet = service.getconversations( "http://surapi.surgicaldr.com/Models/conversation.php?providerid="+providerid+"&userid="+userid);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Messages>>() {
                @Override
                public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Messages> ob=response.body();


                                ArrayList<Messages> jobs=new ArrayList<>();

                                //    Toast.makeText(Myjobs.this, id, Toast.LENGTH_SHORT).show();


                                    for(int i=0;i<ob.size();i++){

                                        jobs.add(ob.get(i));

                                    }


                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterConversation customAdapter = new CustomAdapterConversation(ConversationActivity.this,jobs);// address,name,amount,idd,lat,lon,phone);
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

                                    Toast.makeText(getApplicationContext(), "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //Toast.makeText(MybidsOrder.this, e.toString(), Toast.LENGTH_LONG).show();

                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Messages>> call, Throwable t) {
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
    
    
    
}
