package lab.pak.com.app;

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
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.MainActivity;
import lab.pak.com.app.Notifications.CustomAdapterNotifications;
import lab.pak.com.app.Notifications.notification;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Notificatio extends AppCompatActivity {
static ProgressBar progress;

    private Retrofit retrofit;
    private ArrayList<notification> jobs;
    private static RecyclerView recyclerView;
    private boolean userScrolled;
    static int pagenumber=0;
  static   int pastVisiblesItems, visibleItemCount, totalItemCount;
    private GridLayoutManager linearLayoutManager;
    private CustomAdapterNotifications customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
       // Toolbar toolbar = findViewById(R.id.header);
        //setSupportActionBar(toolbar);
      //  ActionBar actionbar = getSupportActionBar();
       try {
           init();
           //populatRecyclerView();

           jobs = new ArrayList<>();
           login("4", "0");
           implementScrollListener();
       }catch (Exception e){
           Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();}

    }

   public  void startprogress(){
        progress.setVisibility(View.VISIBLE);
    }
   public void stoprogress(){
        progress.setVisibility(View.INVISIBLE);

    }
    void login(String userid, String providerid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            pagenumber=pagenumber+1;
            String url="http://surapi.surgicaldr.com/Models/notificationnew.php?providerid=4&userid=0&pagenumber=1&count=5";
            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            Call<List<notification>> callPet = service.getnotifications( "http://surapi.surgicaldr.com/Models/notificationnew.php?providerid=0&userid=18"+"&pagenumber="+pagenumber+"&count=5");
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<notification>>() {
                @Override
                public void onResponse(Call<List<notification>> call, Response<List<notification>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<notification> ob=response.body();



                                for(int i=0;i<ob.size();i++){
try{
                                        jobs.add(ob.get(i));
                                } catch (Exception e) {
                                    progress.setVisibility(View.INVISIBLE);

                                    //       progressBar.setVisibility(View.INVISIBLE);

                                    Toast.makeText(Notificatio.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                                }
                                String size= String.valueOf(ob.size());
if(pagenumber==1) {
    try{
        recyclerView = (RecyclerView) findViewById(R.id.recycle);


        // set a LinearLayoutManager with default vertical orientaion
    linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
    recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
     customAdapter = new CustomAdapterNotifications(Notificatio.this, jobs);// address,name,amount,idd,lat,lon,phone);
    recyclerView.setAdapter(customAdapter);

} catch (Exception e) {
                                    progress.setVisibility(View.INVISIBLE);

                                    //       progressBar.setVisibility(View.INVISIBLE);

                                    Toast.makeText(Notificatio.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
}else{
    customAdapter.notifyDataSetChanged();
}
                                Toast.makeText(Notificatio.this, response.toString(), Toast.LENGTH_LONG).show();


                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                               Toast.makeText(Notificatio.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<notification>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(Notificatio.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                //        Toast.makeText(Notificatio.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
     ///       Toast.makeText(Notificatio.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }

    private void implementScrollListener() {try{
        recyclerView = (RecyclerView) findViewById(R.id.recycle);

        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);
try{
                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;
                        }} catch (Exception e) {
        progress.setVisibility(View.INVISIBLE);

        //       progressBar.setVisibility(View.INVISIBLE);

        Toast.makeText(Notificatio.this, e.toString(), Toast.LENGTH_LONG).show();
    }


                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager
try{
                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisiblesItems = linearLayoutManager
                                .findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;

                            login("4", "0");
                        }
} catch (Exception e) {
    progress.setVisibility(View.INVISIBLE);

    //       progressBar.setVisibility(View.INVISIBLE);

    Toast.makeText(Notificatio.this, e.toString(), Toast.LENGTH_LONG).show();
}
                    }

                });
    } catch (Exception a) {
        //progressBar.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.INVISIBLE);

        //e.printStackTrace();
           Toast.makeText(Notificatio.this,a.toString(),Toast.LENGTH_LONG ).show();

    }
    }

    // Method for repopulating recycler view



    void init(){
         recyclerView = (RecyclerView) findViewById(R.id.recycle);

        progress= (ProgressBar) findViewById(R.id.progress);
    }



}
