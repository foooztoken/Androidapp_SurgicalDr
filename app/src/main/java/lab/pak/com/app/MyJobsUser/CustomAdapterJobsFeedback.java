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

import lab.pak.com.app.Profile.ProfileFeedback;
import lab.pak.com.app.R;

import static android.content.Context.MODE_PRIVATE;


public class CustomAdapterJobsFeedback extends RecyclerView.Adapter<CustomAdapterJobsFeedback.MyViewHolder> {

    private final ArrayList<Jobs> mainob;
    Context context;
    private MyViewHolder vh;


    public CustomAdapterJobsFeedback(Context context, ArrayList<Jobs> mainob) {
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
        SharedPreferences pref =context. getSharedPreferences("userdata", MODE_PRIVATE);
        String language = pref.getString("language", null);
        String typee = pref.getString("type", null);
        if(language!=null&&language.equals("arabic")&&typee.equals("user")){
            holder.jobname.setText(  mainob.get(position).description);
           holder.jobnam.setText(":وصف");
            //hol :وصف \;
        }else{
            holder.jobname.setText(mainob.get(position).description);
        }
    holder.amount.setText(mainob.get(position).budget);
    holder.date.setText(mainob.get(position).timedate);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           try {
               Intent op = new Intent(context, ProfileFeedback.class);
                Jobs ob = mainob.get(position);
           //    Jobs ob = mainob.get(position);
               Gson gson = new Gson();
               String json = gson.toJson(ob);
               op.putExtra("ob", json);
                op.putExtra("id", mainob.get(position).userid);
                op.putExtra("type", "user");
                context.startActivity(op);
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
        TextView jobname,date,amount,jobnam;

        public MyViewHolder(View itemView) {
            super(itemView);
                  jobnam=itemView.findViewById(R.id.jobname);
            jobname=itemView.findViewById(R.id.name);
// get status=itemView.findViewById(R.id.status);
           date=(TextView)itemView.findViewById(R.id.date);
            amount=(TextView)itemView.findViewById(R.id.budget);


        }
    }
}