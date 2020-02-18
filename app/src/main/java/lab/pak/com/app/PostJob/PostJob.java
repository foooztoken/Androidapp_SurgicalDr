package lab.pak.com.app.PostJob;

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

        import java.util.ArrayList;
        import java.util.Calendar;

        import android.net.Uri;
        import android.os.Build;
        import android.os.Environment;
        import android.provider.DocumentsContract;
        import android.provider.MediaStore;
        import android.provider.Settings;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONObject;

        import java.io.File;
        import java.text.SimpleDateFormat;
        import java.util.List;

        import lab.pak.com.app.MainActivity;
        import lab.pak.com.app.Models.filter;
        import lab.pak.com.app.R;
        import lab.pak.com.app.RetrofitInterface;
        import okhttp3.MediaType;
        import okhttp3.MultipartBody;
        import okhttp3.RequestBody;
        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class PostJob extends AppCompatActivity {
    RelativeLayout attachment;
    EditText state,description,age,bloodgroup,history,chronicdisease,medicationtaken;
    private Retrofit retrofit;
static     String countrytext,citytext;
    Button bid;
    private static Uri fileUri=null;
    Spinner city,country;
    TextView roomt,countryt,cityt;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private TextView attachmenttext;
    Spinner roomtype;
    private EditText bloodtransfer,nodays;
    private final int PICK_FROM_FILE=1;
ProgressBar progress;
    private static String roomtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        try{
            SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
            final String idd = prefs.getString("id", null);
            final String name = prefs.getString("name", null);
            init();
            getcountries();
            initiateroomt();
            getroom();
            Toolbar toolbar = findViewById(R.id.header);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            Button b=findViewById(R.id.home);

            Button refresh=findViewById(R.id.refresh);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  try {
                      getcountries();
                  }catch (Exception e){}}
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent o=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(o);
                }
            });
            handlePermission();
            attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageChooser();
                }
            });


            bid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int ch = check();
                    if (ch == 1) {
                        try {

                            submit(idd, name);
                        } catch (Exception e) {
                            Toast.makeText(PostJob.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(PostJob.this, "Enter data in all fields!", Toast.LENGTH_LONG).show();

                    }

                }
            });
            //for translation of languages
changelanguage();

        }catch (Exception e){

        //    Toast.makeText(PostJob.this,e.toString() ,Toast.LENGTH_LONG ).show();
        }
    }



    void changelanguage(){
        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        String language = prefs.getString("language", null);
        String typee = prefs.getString("type", null);
        if(language!=null&&language.equals("arabic")&&typee.equals("user")){
            try {
            Toolbar toolbar = findViewById(R.id.header);
            toolbar.setTitle("طلب خدمه جديده");
            ///    description.setText("قم بتعبئة البيانات");
            TextView roomtype = findViewById(R.id.roomtyp);
            roomtype.setText("درجة الأقامة");
            TextView submit=findViewById(R.id.submit);
            submit.setText("قم بتعبئة البيانات");
            TextView desc = findViewById(R.id.desc);
            desc.setText("العملية الجراحية المطلوب أجرائها");
            TextView country = findViewById(R.id.countr);
            country.setText("بلد");
            TextView city = findViewById(R.id.cit);
            city.setText("المدينه");
            TextView ag = findViewById(R.id.ag);
            ag.setText("العمر");
            TextView blood = findViewById(R.id.blood);
            blood.setText("فصيلة الدم");
            TextView med = findViewById(R.id.med);
            med.setText("التاريخ الطبي");
            TextView medt = findViewById(R.id.medt);
            medt.setText("الأدوية التي تتناولها بأستمرار");
            Button ins = findViewById(R.id.insert);
            ins.setText("النشر");
        }catch (Exception e){

        //    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }




        }
    }


    void getstates(String country){

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<filter>> callPet = service.getcountries( "http://surapi.surgicaldr.com/Models/statefilter.php?country="+country);
            //progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<filter>>() {
                @Override
                public void onResponse(Call<List<filter>> call, Response<List<filter>> response) {
                    try {
                        //  progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<filter> ob=response.body();


                                ArrayList<filter> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){
//Toast.makeText(getApplicationContext(),ob.get(i).state ,Toast.LENGTH_LONG ).show();
                                            jobs.add(ob.get(i));

                                    }


/*}else{
                                for(int x=0;x<jobs.size();x++) {

                                    if (ob.get(i).country.equals(jobs.get(x).country)) {}else{
                                        jobs.add(ob.get(i));
                                        Toast.makeText(getApplicationContext(), ob.get(x).country,Toast.LENGTH_LONG ).show();
                                    }
                                }
  */
                                initiatestates(jobs);





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
                                //        progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                        //progressBar.setVisibility(View.INVISIBLE);
                        //    progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<filter>> call, Throwable t) {
                    try {
                        //progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
void getcountries()
    {

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<filter>> callPet = service.getcountries( "http://surapi.surgicaldr.com/Models/countryfilter.php");
            //progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<filter>>() {
                @Override
                public void onResponse(Call<List<filter>> call, Response<List<filter>> response) {
                    try {
                        //  progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<filter> ob=response.body();


                                ArrayList<filter> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++) {

                                    jobs.add(ob.get(i));



/*}else{
                                for(int x=0;x<jobs.size();x++) {

                                    if (ob.get(i).country.equals(jobs.get(x).country)) {}else{
                                        jobs.add(ob.get(i));
                                        Toast.makeText(getApplicationContext(), ob.get(x).country,Toast.LENGTH_LONG ).show();
                                    }
                                }
  */                          }
                                initiatecountry(jobs);





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
                                //        progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                        //progressBar.setVisibility(View.INVISIBLE);
                        //    progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<filter>> call, Throwable t) {
                    try {
                        //progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                        Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
void getroom(){
    final List<String> arr=new ArrayList<>();
    SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
    String language = prefs.getString("language", null);

    if(language!=null&&language.equals("arabic")){
        arr.add("جناح");
        arr.add("غرفة مستقلة");
        arr.add("غرفة مشتركة 2 سرير");
        arr.add("غرفة اكثر من 2 سرير");

    }else {
        arr.add("Suite");
        arr.add("Single room");
        arr.add("Double bed room");
        arr.add("Economy");
    }

    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
            android.R.layout.simple_spinner_item, arr);
    adapter.setDropDownViewResource(R.layout.drawable);
    roomtype.setAdapter(adapter);

    roomtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            roomtext=adapter.getItem(position);
           roomt.setText(roomtext);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }});
}
    void initiatestates(List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).state);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                citytext=adapter.getItem(position);
                cityt.setText(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});
      }
            void initiateroomt(){

    List<String> arr=new ArrayList<>();
                SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                String language = prefs.getString("language", null);

                if(language!=null&&language.equals("arabic")){
                    SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
                String languag = pref.getString("language", null);
                if(languag!=null&&languag.equals("arabic")){
                    arr.add("جناح");
                    arr.add("غرفة مستقلة");
                    arr.add("غرفة مشتركة 2 سرير");
                    arr.add("غرفة اكثر من 2 سرير");

                }else {
                    arr.add("Suite");
                    arr.add("Single room");
                    arr.add("Double bed room");
                    arr.add("Economy");
                }


                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
            android.R.layout.simple_spinner_item, arr);
    adapter.setDropDownViewResource(R.layout.drawable);
    roomtype.setAdapter(adapter);


}}
    void initiatecountry(final List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).country);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        country.setAdapter(adapter);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
countrytext=adapter.getItem(position);
countryt.setText(adapter.getItem(position));
//Toast.makeText(getApplicationContext(),countrytext ,Toast.LENGTH_LONG ).show();
                getstates(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

    }

    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        long msTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy  hh:mm");
        return sdf.format(cal.getTime());
    }
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
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
                        openAppSettings(PostJob.this);
                    }
                });
        alertDialog.show();
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
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



    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
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

                return getDataColumn(getApplicationContext(), contentUri, null, null);
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

                return getDataColumn(getApplicationContext(), contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(getApplicationContext(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
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






    void submit(String id,String name){
        try {
            progress.setVisibility(View.VISIBLE);
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            String filePath = getRealPathFromUri(fileUri);
            if (filePath != null && !filePath.isEmpty()) {
                File file = new File(filePath);
                if (file.exists()) {


                    try{
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://surapi.surgicaldr.com/")
                            .build();
                    String budge="";
                    String des=description.getText().toString();
                    String cit=citytext;
                    String stat=cit;

                    String time=giveDate();
                    String medication=medicationtaken.getText().toString();
                    String histor=history.getText().toString();
                    String blood=bloodgroup.getText().toString();
                    String chronic=chronicdisease.getText().toString();
                    String ag=age.getText().toString();
                    String countr=countrytext;
                    getroom();
                    String roomtyp=roomtext;
                    String bloodtr="";
                    String icutext="";
                    String noday="";//nodays.getText().toString();
                    String statss="pending";

                    String json = "{\"time\":\""+time+"\",\"providerid\":\""+id+"\",\"status\":\""+statss+"\",\"budget\":\""+budge+"\",\"city\":\""+cit+"\",\"state\":\""+cit+"\",\"description\":\""+des+"\",\"country\":\""+countr+"\",\"providername\":\""+name+"\",\"age\":\""+ag+"\",\"bloodgroup\":\""+blood+"\",\"medicationtaken\":\""+medication+"\",\"history\":\""+histor+"\",\"chronicdisease\":\""+chronic+"\",\"roomtype\":\""+roomtyp+"\",\"icuinclude\":\""+icutext+"\",\"bloodtransfer\":\""+bloodtr+"\",\"nodays\":\""+noday+"\"}";
                    JSONObject obj = new JSONObject(json);
                    RequestBody modelBody = RequestBody.create(MediaType.parse("application/json"), json);



                    final RetrofitInterface service=retrofit.create(RetrofitInterface.class);

                    // creates RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    // MultipartBody.Part is used to send also the actual filename
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    // adds another part within the multipart request
                    String descriptionString = "Sample description";
                    RequestBody jsonobject = RequestBody.create(MediaType.parse("multipart/form-data"),json);

                    RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
                    // executes the request



                    Call<ResponseBody> call = service.postjob(body, description,modelBody);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                               Response<ResponseBody> response) {
                            progress.setVisibility(View.INVISIBLE);
                            if(response.isSuccessful()) {
                                String ob = response.body().toString();

                                Toast.makeText(PostJob.this, "Posted!", Toast.LENGTH_SHORT).show();
finish();
                          //      Toast.makeText(PostJob.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Some error occured!", Toast.LENGTH_SHORT).show();

                        }
                    });













//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();





                    }catch (Exception e){
                        Toast.makeText(PostJob.this,e.toString() ,Toast.LENGTH_LONG ).show();}
                       }}}
        catch (Exception a) {
            progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
            Toast.makeText(PostJob.this,"This one " +a.toString() ,Toast.LENGTH_LONG ).show();

        }}
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_FROM_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            fileUri = data.getData();

            attachmenttext.setText(fileUri.getPath().toString());
            if (fileUri != null) {




                // uploadFile(fileUri); // uploads the file to the web service
            }
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
    int check(){
        try{
       // if(city.equals("")){
         //   return 0;
       // }
           // String budgettext=budget.getText().toString();
          ///  String statetext=state.getText().toString();
            String descriptiontext=description.getText().toString();
            String bloodgrouptext=bloodgroup.getText().toString();
            String chronictext=chronicdisease.getText().toString();
            String medicationtakentext=medicationtaken.getText().toString();
            String historytext=history.getText().toString();
            String agetext=age.getText().toString();

          //  String bloodtransfertext=bloodtransfer.getText().toString();
          //  String nodaystext=nodays.getText().toString();


         //   if(budgettext.isEmpty()){

        if(descriptiontext.isEmpty()){
            return 0;
        }

        if(bloodgrouptext.isEmpty()){
            return 0;
        }
        if(chronictext.isEmpty()){
            return 0;
        }
        if(medicationtakentext.isEmpty()){
            return 0;
        }
        if(historytext.isEmpty()){
            return 0;
        }
        if(agetext.isEmpty()){
            return 0;
        }


        return 1;}catch (Exception e){Toast.makeText(PostJob.this,e.toString() ,Toast.LENGTH_LONG ).show();}
    return 0;}
    void init(){

        roomtype=findViewById(R.id.roomtypetext);
     //   bloodtransfer=findViewById(R.id.bloodtransfertext);
        attachmenttext=findViewById(R.id.attachmenttext);
        age=findViewById(R.id.agetext);
        bloodgroup=findViewById(R.id.bloodgrouptext);
        medicationtaken=findViewById(R.id.medicationtakentext);
        history=findViewById(R.id.historytext);
        chronicdisease=findViewById(R.id.chronicdiseasetext);
        attachment=findViewById(R.id.attachment);
        bid=findViewById(R.id.insert);
        progress=findViewById(R.id.progress);
        city=findViewById(R.id.citytext);
       // budget=findViewById(R.id.budgettext);
        description=findViewById(R.id.descriptiontext);
        country=findViewById(R.id.countrytext);
        roomt=findViewById(R.id.texts);
        countryt=findViewById(R.id.countrytextt);
        cityt=findViewById(R.id.textcity);
    }



}
