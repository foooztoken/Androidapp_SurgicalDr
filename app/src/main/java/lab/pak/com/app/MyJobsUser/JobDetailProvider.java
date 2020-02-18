package lab.pak.com.app.MyJobsUser;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import java.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.GeneralAdapters.CustomAdapterConversation;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.Models.Messages;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import lab.pak.com.app.UploadMessage;
import lab.pak.com.app.UserModule.Main;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobDetailProvider extends AppCompatActivity {

   private Jobs jobs;
    private Retrofit retrofit;

RelativeLayout conversation,myjobs,findjobs;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
ProgressBar progressBar;
    private RelativeLayout jobdescr,jobrequests;
String de;
Button send;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final Context mContext = this;
    private final String API_URL_BASE = "http://surapi.surgicaldr.com/";
    private final String LOG_TAG = "BNK";
ProgressBar progress;
    private String doctyp,nam,amoun,jobtyp,roomtyp,degreeclas;
    private EditText space;
TextView provider,statuss;
    public  static Uri fileUri=null;
    private Button attachment;
    private TextView status,providername;
    private ImageView image;
    private final int PICK_FROM_FILE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detailprovider);

   try {
      Bundle bundle = getIntent().getExtras();


       Gson gson = new Gson();
       String studentDataObjectAsAString = getIntent().getStringExtra("ob");
       jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
       Toolbar toolbar = findViewById(R.id.header);
       setSupportActionBar(toolbar);
       ActionBar actionbar = getSupportActionBar();
       actionbar.setDisplayHomeAsUpEnabled(true);




       init();

       Button b=findViewById(R.id.home);

       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
               Intent o=new Intent(getApplicationContext(), Main.class);
               startActivity(o);
           }
       });

    //   provider.setText("Provider Name:"+jobs.providername);
     //  statuss.setText("Status:"+jobs.status);

   /*   Intent i=new Intent(JobDetailProvider.this,ProviderRequests.class);
        Gson gson = new Gson();
        String studentDataObjectAsAString = getIntent().getStringExtra("ob");
        jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
        Gson gso = new Gson();
        String json = gso.toJson(jobs);
        i.putExtra("ob", json);
        startActivity(i);
        mDrawerLayout.closeDrawer(GravityCompat.START);
 */

        /*    Intent i=new Intent(JobDetailProvider.this,JobDetailUserNew.class);
               Gson gson = new Gson();
               String studentDataObjectAsAString = getIntent().getStringExtra("ob");
               jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
               Gson gso = new Gson();
               String json = gso.toJson(jobs);
               i.putExtra("ob", json);
               startActivity(i);
               mDrawerLayout.closeDrawer(GravityCompat.START);
         */



attachment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openImageChooser();
    }
});
handlePermission();
send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
if(jobs.status.equals("started")){


            // Should we show an explanation?

            SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
            String name = prefs.getString("name", null);

            String message=space.getText().toString();
           /// String ss="2";
            if(fileUri!=null){
                uploadFile(fileUri, jobs.userid, jobs.providerid, jobs.id, name,message );

            }   else {
                sendmessage(jobs.providerid, jobs.userid, jobs.id, name, message);
            }}else{Toast.makeText(JobDetailProvider.this,"Order is "+jobs.status ,Toast.LENGTH_SHORT ).show();}}

        //    } uploadFile(fileUri,ss,ss , ss, name,message);










});

    showmessages(jobs.providerid,jobs.userid,jobs.id );


   }catch (Exception e){
       Toast.makeText(JobDetailProvider.this,e.toString() , Toast.LENGTH_LONG).show();
   }

    }


    private void handlePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PICK_FROM_FILE);
        }
    }




    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings(JobDetailProvider.this);
                    }
                });
        alertDialog.show();
    }
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_FROM_FILE);
    }
    public static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }public String giveDate() {
        Calendar cal = Calendar.getInstance();
        long msTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy  hh:mm");
        return sdf.format(cal.getTime());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
             fileUri = data.getData();
            if (fileUri != null) {




               // uploadFile(fileUri); // uploads the file to the web service
            }
        }
    }

    private void uploadFile(Uri fileUri,String userid,String providerid,String orderid,String name,String message) {
        try{
            final String filePath = getRealPathFromUri(fileUri);
            if (filePath != null && !filePath.isEmpty()) {
                File file = new File(filePath);
                if (file.exists()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL_BASE)
                            .build();

                    ///json object
message=filteration(message);


                    String date=giveDate();
                    String stats="pending";
                    String by="provider";
              //      String name ="8";
                 //   String json = "{\"name\":\""+name+"\",\"userid\":\""+userid+"\",\"providerid\":\""+providerid+"\",\"jobid\":\""+orderid+"\",\"dates\":\""+date+"\",\"status\":\""+stats+"\",\"bywho\":\""+by+"\",\"message\":\""+message+"\",\"jobid\":\""+orderid+"\"}";
                    String json = "{\"name\":\""+name+"\",\"userid\":\""+userid+"\",\"providerid\":\""+providerid+"\",\"jobid\":\""+orderid+"\",\"dates\":\""+date+"\",\"status\":\""+stats+"\",\"bywho\":\""+by+"\",\"message\":\""+message+"\",\"jobid\":\""+orderid+"\"}";
progress.setVisibility(View.VISIBLE);
                    JSONObject obj = new JSONObject(json);
                    Gson gson = new GsonBuilder().setLenient().create();
                    RequestBody modelBody = RequestBody.create(MediaType.parse("application/json"), json);



                    UploadMessage.WebAPIService service = retrofit.create(UploadMessage.WebAPIService.class);

                    // creates RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    // MultipartBody.Part is used to send also the actual filename
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    // adds another part within the multipart request
                    String descriptionString = "Sample description";
                    RequestBody jsonobject = RequestBody.create(MediaType.parse("multipart/form-data"),json);

                    RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
                    // executes the request
                    Call<ResponseBody> call = service.postFile(body, description,modelBody);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                               Response<ResponseBody> response) {
                            progress.setVisibility(View.INVISIBLE);
nullifyuri();
                            space.setText(" ");
                    //        Toast.makeText(getApplicationContext(),"Sent!", Toast.LENGTH_LONG).show();
//                            showmessages(jobs.providerid,jobs.userid,jobs.id );
                            showmessagesrefresh(jobs.providerid, jobs.userid,jobs.id );

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_SHORT).show();
                            showmessages(jobs.providerid,jobs.userid,jobs.id );

                        }
                    });
                }
            }}catch (Exception e){}
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }
String filteration(String a){
        a=a.replace("01", "");
    a=a.replace("02", "*");
    a=a.replace("03", "*");
    a=a.replace("23", "*");
    a=a.replace("33", "*");
    a=a.replace("34", "*");
    a=a.replace("35", "*");
    a=a.replace("36", "*");
    a=a.replace("010", "*");
    a=a.replace("011", "*");
    a=a.replace("014", "*");
    a=a.replace("012", "*");

    a=a.replace("013", "*");

    a=a.replace("015", "*");
    a=a.replace("016", "*");
    a=a.replace("017", "*");
    a=a.replace("018", "*");
    a=a.replace("019", "*");
    a=a.replace("Hospital", "*");
    a=a.replace("Center", "*");
    a=a.replace("Clinic", "*");
    a=a.replace("Polyclinic", "*");
    a=a.replace("Call me", "*");
    a=a.replace("Callme", "*");
    a=a.replace("Skype", "*");
    a=a.replace("Imo", "*");
    a=a.replace("Whatsapp", "*");
    a=a.replace("Whtsapp", "*");
    a=a.replace("@", "*");
    a=a.replace("Email", "*");
    a=a.replace("vezeta", "*");
    a=a.replace("AT", "*");
    a=a.replace("(at)", "*");
    a=a.replace("$", "*");
    a=a.replace("Dollar", "*");
    a=a.replace("www.", "*");
    a=a.replace("www", "*");

    a=a.replace(".com", "*");
    a=a.replace(".net", "*");
    a=a.replace(".org", "*");
    a=a.replace(".eg", "*");
    a=a.replace("Facebook", "*");
    a=a.replace("https", "*");
    a=a.replace("دولار", "*");
    a=a.replace("جنيه", "*");
    a=a.replace("جنية", "*");
    a=a.replace("مستشفي", "*");
    a=a.replace("مجمع طبي", "*");
    a=a.replace("عيادات تخصصيه", "*");
    a=a.replace("فيزيتا", "*");




    return  a;
}
    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(mContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];            if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(mContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
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
    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    @Override
    public void onBackPressed() {

        finish();

    }
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_FILE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //  Show your own message here
                        } else {
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



void nullifyuri(){
        fileUri=null;
}





  void sendmessage(String providerid,String userid,String jobid,String name,String message){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            message=filteration(message);

            String date=giveDate();
            String stats="pending";
            String by="provider";
            String json = "{\"name\":\""+name+"\",\"userid\":\""+userid+"\",\"providerid\":\""+providerid+"\",\"jobid\":\""+jobid+"\",\"dates\":\""+date+"\",\"status\":\""+stats+"\",\"bywho\":\""+by+"\",\"message\":\""+message+"\"}";

            JSONObject obj = new JSONObject(json);

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call callPet = service.signup( "http://surapi.surgicaldr.com/Models/sendmessage.php?var="+obj);
            progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {space.setText(" ");
                        progress.setVisibility(View.INVISIBLE);
                    //    Toast.makeText(getApplicationContext(),"Message has been sent for approval", Toast.LENGTH_LONG).show();
//                        showmessages(jobs.providerid,jobs.userid,jobs.id );
                        showmessagesrefresh(jobs.providerid, jobs.userid,jobs.id );

                        if (response.isSuccessful()) {

                            try {


                             Object ob=response.body();





                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(JobDetailProvider.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    try {
                        //showmessages(jobs.providerid,jobs.userid,jobs.id );
                        showmessagesrefresh(jobs.providerid, jobs.userid,jobs.id );
                        space.setText(" ");
                        progress.setVisibility(View.INVISIBLE);
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(JobDetailProvider.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(JobDetailProvider.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void showmessages(String providerid,String userid,String jobid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Messages>> callPet = service.getmessage( "http://surapi.surgicaldr.com/Models/messagesorder.php?providerid="+providerid+"&employeeid="+userid+"&jobid="+jobid);
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
                                for(int i=0;i<ob.size();i++){

                                    jobs.add(ob.get(i));

                                }
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterConversation customAdapter = new CustomAdapterConversation(JobDetailProvider.this,jobs);// address,name,amount,idd,lat,lon,phone);
                                recyclerView.setAdapter(customAdapter);






                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(JobDetailProvider.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Messages>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
     } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(JobDetailProvider.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(JobDetailProvider.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void showmessagesrefresh(String providerid,String userid,String jobid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Messages>> callPet = service.getmessage( "http://surapi.surgicaldr.com/Models/messagesorder.php?providerid="+providerid+"&employeeid="+userid+"&jobid="+jobid);
          //  progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Messages>>() {
                @Override
                public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                    try {
                        progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<Messages> ob=response.body();


                                ArrayList<Messages> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){

                                    jobs.add(ob.get(i));

                                }
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                CustomAdapterConversation customAdapter = new CustomAdapterConversation(JobDetailProvider.this,jobs);// address,name,amount,idd,lat,lon,phone);
                                recyclerView.setAdapter(customAdapter);






                            } catch (Exception e) {
                                progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(JobDetailProvider.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Messages>> call, Throwable t) {
                    try {
                        progress.setVisibility(View.INVISIBLE);
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(JobDetailProvider.this, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(JobDetailProvider.this,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void init(){
       //providername=findViewById(R.id.provider);
     try {
         status = findViewById(R.id.statuss);

         attachment = findViewById(R.id.attachment);


         findjobs = findViewById(R.id.findjobs);
         myjobs = findViewById(R.id.myjobs);
         progress = findViewById(R.id.progress);
         send = findViewById(R.id.send);
         space = findViewById(R.id.text);
         statuss = findViewById(R.id.statuss);
         provider = findViewById(R.id.provider);
     }catch (Exception e){Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG ).show();}   }
}
