package lab.pak.com.app.Notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.AllConversation.ConversationActivity;
import lab.pak.com.app.MyJobsProvider.MenuProvider;
import lab.pak.com.app.MyJobsUser.MenuUseer;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.R;
import lab.pak.com.app.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class CustomAdapterNotifications extends RecyclerView.Adapter<CustomAdapterNotifications.MyViewHolder> {

    private final ArrayList<notification> mainob;
   Context context;
    private MyViewHolder vh;


    public CustomAdapterNotifications(Context context, ArrayList<notification> mainob) {
        this.context = context;
        this.mainob=mainob;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications, parent, false);

        vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    View vl;
try {

holder.message.setText(mainob.get(position).message);
holder.time.setText(mainob.get(position).times);
//mainob.get(position).
  holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
if(mainob.get(position).conversationstatus!=null) {
    if (mainob.get(position).conversationstatus.equals("yes")) {

        Intent o = new Intent(context, ConversationActivity.class);
        context.startActivity(o);
    } else {

        ((Notifications) context).startprogress();
        login(mainob.get(position).orderid);
    }
}else{
    ((Notifications) context).startprogress();
    login(mainob.get(position).orderid);
}
            }catch (Exception e){Toast.makeText(context,e.toString() ,Toast.LENGTH_LONG ).show();}}
    });
}catch (Exception e){String ee= String.valueOf(e);
Toast.makeText(context,ee ,Toast.LENGTH_LONG ).show();}
// implement setOnClickListener event on item view.

}


    void login(final String orderid){
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<Jobs>> callPet = service.getjobs( "http://surapi.surgicaldr.com/Models/jo.php");
         //   progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<Jobs>>() {
                @Override
                public void onResponse(Call<List<Jobs>> call, Response<List<Jobs>> response) {
                    try {
                       // progress.setVisibility(View.INVISIBLE);
                        ((Notifications)context).stoprogress();
                        if (response.isSuccessful()) {

                            try {

                                List<Jobs> ob=response.body();


                                ArrayList<Jobs> jobs=new ArrayList<>();
                                SharedPreferences prefs =context. getSharedPreferences("userdata", MODE_PRIVATE);
                                String id = prefs.getString("id", null);
                                //    Toast.makeText(Myjobs.this, id, Toast.LENGTH_SHORT).show();
                                if (id != null) {

                                    for(int i=0;i<ob.size();i++){
                                        if(ob.get(i).id.equals(orderid)) {
                                            jobs.add(ob.get(i));
                                        }
                                    }}
                                String type = prefs.getString("type", null);

                                if(type.equals("user")) {
                                    Intent intent = new Intent(context, MenuUseer.class);
                                    Jobs obs = jobs.get(0);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(obs);
                                    intent.putExtra("ob", json);
                                    context.startActivity(intent);
                                }else{
                                    Intent intent = new Intent(context, MenuProvider.class);
                                    Jobs obs = jobs.get(0);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(obs);
                                    intent.putExtra("ob", json);
                                    context.startActivity(intent);
                                }
                               // RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
// set a LinearLayoutManager with default vertical orientaion
                               // GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                             //   recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
                                //CustomAdapterJobs customAdapter = new CustomAdapterJobs(Myjobs.this,jobs);// address,name,amount,idd,lat,lon,phone);
                              //  recyclerView.setAdapter(customAdapter);





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
                           //     progress.setVisibility(View.INVISIBLE);
                                ((Notifications)context).stoprogress();

                                //       progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG ).show();

                         //       Toast.makeText(Myjobs.this, "Problem Connecting", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {
                        Toast.makeText(context,e.toString(),Toast.LENGTH_LONG ).show();
                        ((Notifications)context).stoprogress();

                        //progressBar.setVisibility(View.INVISIBLE);
                       // progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Jobs>> call, Throwable t) {
                    try {
                     //   progress.setVisibility(View.INVISIBLE);
                        ((Notifications)context).stoprogress();

                        //  progressBar.setVisibility(View.INVISIBLE);

                             Toast.makeText(context,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                       /// progress.setVisibility(View.INVISIBLE);
                        ((Notifications)context).stoprogress();

                        //e.printStackTrace();
                        Toast.makeText(context, a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
          //  progress.setVisibility(View.INVISIBLE);
            ((Notifications)context).stoprogress();

            //e.printStackTrace();
            Toast.makeText(context,"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }


    @Override
    public int getItemCount() {
        return mainob.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



     //   private final TextView status;
        // init the item view's
        TextView message,time;

        public MyViewHolder(View itemView) {
            super(itemView);


// get status=itemView.findViewById(R.id.status);
           message=(TextView)itemView.findViewById(R.id.message);
            time=(TextView)itemView.findViewById(R.id.time);


        }
    }
}