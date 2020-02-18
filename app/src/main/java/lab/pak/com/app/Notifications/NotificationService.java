package lab.pak.com.app.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationService extends Service {
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private Retrofit retrofit;

    public void startNotificationListener() {
    //start's a new thread
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
            SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
            final String providerid = prefs.getString("id", null);


            String type = prefs.getString("type", null);

            if(type.equals("provider")) {

                getnotifications(providerid, "0");

            }else{
                getnotifications("0", providerid);

            }}



            //fetching notifications from server
            //if there is notifications then call this method


    },5);
}
@Override
public void onCreate()
{
    startNotificationListener();
    super.onCreate();
}
@Override
public int onStartCommand(Intent intent, int flags, int startId)
{
    return super.onStartCommand(intent,flags,startId);
}
@Override
public void onDestroy()
{
    super.onDestroy();
}

@Nullable
@Override
public IBinder onBind(Intent intent) {
    return null;
}

public void ShowNotification(String content)
{

    Intent intent = new Intent(getApplicationContext(), Notifications.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

    // Will display the notification in the notification bar

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
    Notification notification = new NotificationCompat.Builder(getBaseContext(),"notification_id")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("SurgicalDr")
            .setContentText(content)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setContentIntent(pendingIntent)
            .build();
    notificationManager.notify(0, notification);
    //the notification is not showing

}

    void getnotifications(String userid, String providerid){
        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<notification>> callPet = service.getnotifications( "http://surapi.surgicaldr.com/Models/notifications.php?providerid="+providerid+"&userid="+userid);
          //  progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<notification>>() {
                @Override
                public void onResponse(Call<List<notification>> call, Response<List<notification>> response) {
                    try {
            //            progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<notification> ob=response.body();


                                ArrayList<notification> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){

if(ob.get(i).seen.equals("seen")){

}else{
    jobs.add(ob.get(i));
}


                                }


                                int n=jobs.size();
                                if(n<=0) {

                                }else{
                                    String content="You have "+n+ " new notification";
                                    ShowNotification(content);
                                }


                            } catch (Exception e) {
                               // progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

//                               Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        //progressBar.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<List<notification>> call, Throwable t) {
                    try {

                        //  progressBar.setVisibility(View.INVISIBLE);

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);

            //e.printStackTrace();

        }


    }

}