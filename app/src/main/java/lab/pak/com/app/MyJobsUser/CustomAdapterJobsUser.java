package lab.pak.com.app.MyJobsUser;

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

import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.R;

import static android.content.Context.MODE_PRIVATE;


public class CustomAdapterJobsUser extends RecyclerView.Adapter<CustomAdapterJobsUser.MyViewHolder> {

    private final ArrayList<Jobs> mainob;
    Context context;
    private MyViewHolder vh;


    public CustomAdapterJobsUser(Context context, ArrayList<Jobs> mainob) {
        this.context = context;
        this.mainob=mainob;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs, parent, false);

        vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    View vl;

        SharedPreferences prefs =context. getSharedPreferences("userdata", MODE_PRIVATE);
        String language = prefs.getString("language", null);
        String typee = prefs.getString("type", null);
        if(language!=null&&language.equals("arabic")&&typee.equals("user")){
            holder.desctitle.setText("وصف");
            holder.amount.setText(mainob.get(position).budget);
            holder.date.setText(mainob.get(position).timedate);
            holder.jobname.setText(mainob.get(position).description);
    }else{
    holder.amount.setText(mainob.get(position).budget);
    holder.date.setText(mainob.get(position).timedate);
    holder.jobname.setText(mainob.get(position).description);}
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           try {
               Intent i=new Intent(context, JobDetailNew.class);
               Gson gson = new Gson();
              // String studentDataObjectAsAString =context. getIntent().getStringExtra("ob");
              // jobs = gson.fromJson(studentDataObjectAsAString, Jobs.class);
               Gson gso = new Gson();
               String json = gso.toJson(mainob.get(position));
               i.putExtra("ob", json);
         context.      startActivity(i);
            }catch (Exception e){Toast.makeText(context,e.toString() ,Toast.LENGTH_LONG ).show();}}
    });

// implement setOnClickListener event on item view.

        }






    @Override
    public int getItemCount() {
        return mainob.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



     //   private final TextView status;
        // init the item view's
        TextView jobname,date,amount,desctitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            desctitle=itemView.findViewById(R.id.jobname);
            jobname=itemView.findViewById(R.id.name);
// get status=itemView.findViewById(R.id.status);
           date=(TextView)itemView.findViewById(R.id.date);
            amount=(TextView)itemView.findViewById(R.id.budget);


        }
    }
}