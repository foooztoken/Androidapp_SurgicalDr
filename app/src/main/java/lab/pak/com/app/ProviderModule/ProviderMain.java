package lab.pak.com.app.ProviderModule;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.NonNull;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.w3c.dom.Text;

        import java.util.ArrayList;
        import java.util.List;

        import lab.pak.com.app.AllConversation.ConversationActivity;
        import lab.pak.com.app.GeneralAdapters.CompleteOrdersProvider;
        import lab.pak.com.app.FindJobs.CustomAdapter;
        import lab.pak.com.app.Models.Jobs;
        import lab.pak.com.app.MainActivity;
        import lab.pak.com.app.MyJobsUser.MyJobsUser;
        import lab.pak.com.app.Notifications.NotificationService;
        import lab.pak.com.app.Notifications.Notifications;
        import lab.pak.com.app.PostJob.PostJob;
        import lab.pak.com.app.Profile.Profile;
        import lab.pak.com.app.R;
        import lab.pak.com.app.RetrofitInterface;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class ProviderMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //private String MY_PREFS_NAME="Logindata";
    private Retrofit retrofit;
    private EditText email,pass,user,phone;
    private ProgressBar progress;
    private Button save;
    private DrawerLayout mDrawerLayout;
    Button post;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout postjob,about,faq,contactus;
    private RelativeLayout signout;
    ImageView image;
    TextView namm,emai;
    private RelativeLayout postbut,aboutbut,signoutbut,orderbut,contactbut,faqbut;
    private RelativeLayout pickhosp,rateservice,change;
RelativeLayout closedorders,conversation;
    private boolean doubleBackToExitPressedOnce=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.providermainnew);
        getWindow().setBackgroundDrawableResource(R.mipmap.back);

        Intent op=new Intent(this, NotificationService.class);
        startService(op);
        try{
            Toolbar toolbar = findViewById(R.id.header);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);


            //    toolbar.setNavigationIcon(R.mipmap.lines);
            // getSupportActionBar().setIcon(R.mipmap.icona);
            init();

            //translation will be done if required

            changelanguage();

            closedorders.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(), CompleteOrdersProvider.class);
        startActivity(intent);
        mDrawerLayout.closeDrawer(GravityCompat.START);

    }
});
            pickhosp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MyJobsUser.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
/*
            orderbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
*/           /* faqbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });*/
            contactbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProviderMain.this, Notifications.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

           /* signoutbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
                    editor.putString("session", "no");
                    editor.apply();
                    Intent op=new Intent(ProviderMain.this, MainActivity.class);
                    startActivity(op);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    finish();
                }
            });
*/
            SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
            String nam = prefs.getString("name", null);
            String emailt = prefs.getString("email", null);

            namm.setText(nam);
            emai.setText(emailt);
            mDrawerLayout = findViewById(R.id.drawer_layout);
            ///***These are all navigational buttons**//

            postbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProviderMain.this, PostJob.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                    String id = prefs.getString("id", null);
                    Intent op=new Intent(ProviderMain.this, Profile.class);
                    op.putExtra("id", id);
                    op.putExtra("type","provider" );
                    startActivity(op);
                    mDrawerLayout.closeDrawer(GravityCompat.START);

                }
            });


            signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
                    editor.putString("session", "no");
                    editor.apply();
                    Intent op=new Intent(ProviderMain.this,MainActivity.class);
                    startActivity(op);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    finish();

                }
            });
rateservice.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(ProviderMain.this,RateService.class);
        startActivity(intent);
        mDrawerLayout.closeDrawer(GravityCompat.START);

    }
});

change.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(ProviderMain.this, ChangeLanguage.class);
        startActivity(intent);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
});
conversation.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(ProviderMain.this, ConversationActivity.class);
        startActivity(intent);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
});
            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProviderMain.this,Aboutprovider.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProviderMain.this, Faqprovider.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            contactus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProviderMain.this, Contactusprovider.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });




            ////**Navigational button ends**////


            mDrawerLayout.addDrawerListener(
                    new DrawerLayout.DrawerListener() {
                        @Override
                        public void onDrawerSlide(View drawerView, float slideOffset) {
                            // Respond when the drawer's position changes
                        }

                        @Override
                        public void onDrawerOpened(View drawerView) {
                            // Respond when the drawer is opened
                        }

                        @Override
                        public void onDrawerClosed(View drawerView) {
                            // Respond when the drawer is closed
                        }

                        @Override
                        public void onDrawerStateChanged(int newState) {
                            // Respond when the drawer motion state changes
                        }
                    }
            );
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.string.app_name, R.string.app_name) {

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);

                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }};
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        }catch (Exception e){
            //     Toast.makeText(ProviderMain.this,e.toString() ,Toast.LENGTH_LONG ).show();
        }}
void changelanguage(){
    SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
    String language = prefs.getString("language", null);
        if(language.equals("arabic")){
         Toolbar   toolbar=findViewById(R.id.header);
        toolbar.setTitle("عمليات");
        TextView one=findViewById(R.id.one);
        TextView two=findViewById(R.id.two);
            TextView three=findViewById(R.id.three);
            one.setText(" أطلب من المستشفيات عروض أسعار لعمليتك");
            two.setText("قارن بين العروض و اختار أفضل سعر");
            three.setText("بعد ما تعمل العمليه أعمل تقييم للخدمة");




        }
}
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.


        //return  true;
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    void init(){
        try{
            change=findViewById(R.id.changelanugage);
            conversation=findViewById(R.id.conversation);
            rateservice=findViewById(R.id.rateservice);
  //          orderbut=findViewById(R.id.orderbut);
    //        aboutbut=findViewById(R.id.aboutbut);
            postbut=findViewById(R.id.requestservice);
            pickhosp=findViewById(R.id.pickhospital);
            contactbut=findViewById(R.id.notification);
         //   faqbut=findViewById(R.id.faqbut);
           /// signoutbut=findViewById(R.id.signoutbut);
closedorders=findViewById(R.id.closedorder);
            namm=findViewById(R.id.namet);
            post=findViewById(R.id.post);
            emai=findViewById(R.id.balance);
            image=findViewById(R.id.imagemain);
            signout=findViewById(R.id.signout);
            signoutbut=findViewById(R.id.signoutbut);

            faq=(RelativeLayout)findViewById(R.id.faq);
            contactus=(RelativeLayout)findViewById(R.id.contactus);
            about=(RelativeLayout)findViewById(R.id.about);
            save=findViewById(R.id.save);
            progress=findViewById(R.id.progress);
        }catch (Exception e){
        } }
    void login(){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Jobs>> callPet = service.getjobs( "http://surapi.surgicaldr.com/Models/jo.php");
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Jobs>>() {
                @Override
                public void onResponse(Call<List<Jobs>> call, Response<List<Jobs>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Jobs> ob=response.body();


                                ArrayList<Jobs> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){

                                    jobs.add(ob.get(i));

                                }
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapter customAdapter = new CustomAdapter(ProviderMain.this,jobs);// address,name,amount,idd,lat,lon,phone);
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

                                Toast.makeText(ProviderMain.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Jobs>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                    //    Toast.makeText(ProviderMain.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(ProviderMain.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }

}
