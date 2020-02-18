package lab.pak.com.app.FindJobs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import lab.pak.com.app.JobDetail;
import lab.pak.com.app.Models.Jobs;
import lab.pak.com.app.R;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final ArrayList<Jobs> mainob;
   Context context;
    private MyViewHolder vh;
ProgressBar progressBar;
    private String history,med,disease;
    private String aget;
    private String blood;

    public CustomAdapter(Context context,ArrayList<Jobs> mainob) {
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
try {

holder.amount.setText(mainob.get(position).budget);
holder.date.setText(mainob.get(position).timedate);
holder.jobname.setText(mainob.get(position).description);
  holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {

                String age;
                Intent op = new Intent(context, JobDetail.class);
                String nam;
                try {
                    nam = mainob.get(position).providername;
                } catch (Exception e) {
                    nam = "Not available";
                }
                op.putExtra("name", nam);
                String dess;
                try {
                    dess = mainob.get(position).description;
                } catch (Exception e) {
                    dess = "Not available";
                }
                String am;
                try {
                    am = mainob.get(position).budget;
                } catch (Exception e) {
                    am = "Not available";
                }
                String rmt;
                try {
                    rmt = mainob.get(position).city;
                } catch (Exception e) {
                    rmt = "Not available";
                }
                String job;
                try {
                    job = mainob.get(position).state;
                } catch (Exception e) {
                    job = "Not available";
                }
                String dc;
                try {
                    dc = mainob.get(position).category;
                } catch (Exception e) {
                    dc = "Not available";
                }
                String dt;
                try {
                    dt = mainob.get(position).country;
                } catch (Exception e) {
                    dt = "Not available";
                }
                try {
                    aget = mainob.get(position).age;
                } catch (Exception e) {
                    dt = "Not available";
                }
                try {
                    med = mainob.get(position).medicationtaken;
                } catch (Exception e) {
                    dt = "Not available";
                }
                try {
                    disease = mainob.get(position).chronicedisease;
                } catch (Exception e) {
                    dt = "Not available";
                }
                try {
                    history = mainob.get(position).previoushistory;
                } catch (Exception e) {
                    dt = "Not available";
                }
                try {
                    blood = mainob.get(position).bloodgroup;
                } catch (Exception e) {
                    dt = "Not available";
                }

                op.putExtra("des", dess);


                op.putExtra("amount", am);

                op.putExtra("roomtype", rmt);

                op.putExtra("jobtype", job);

                op.putExtra("degreeclass", dc);

                op.putExtra("doctortype", dt);
                op.putExtra("bloodgroup", blood);
                op.putExtra("age", aget);
                op.putExtra("history", history);
                op.putExtra("disease", disease);
                op.putExtra("med", med);

                op.putExtra("roomtype", mainob.get(position).roomtype);
                op.putExtra("bloodtransfer", mainob.get(position).bloodtransfer);
                op.putExtra("nodays", mainob.get(position).nodays);
                op.putExtra("icuinclude", mainob.get(position).icuinclude);

    Jobs ob = mainob.get(position);
                Gson gson = new Gson();
                String json = gson.toJson(ob);
                op.putExtra("ob", json);

// To retrieve object in second Activity

                context.startActivity(op);
            }catch (Exception e){Toast.makeText(context,e.toString() ,Toast.LENGTH_LONG ).show();}}
    });
}catch (Exception e){String ee= String.valueOf(e);
Toast.makeText(context,ee ,Toast.LENGTH_LONG ).show();}
// implement setOnClickListener event on item view.

}





    @Override
    public int getItemCount() {
        return mainob.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



     //   private final TextView status;
        // init the item view's
        TextView jobname,date,amount;
ProgressBar progressBar;
        public MyViewHolder(View itemView) {
            super(itemView);

            jobname=itemView.findViewById(R.id.name);
// get status=itemView.findViewById(R.id.status);
           date=(TextView)itemView.findViewById(R.id.date);
            amount=(TextView)itemView.findViewById(R.id.budget);


        }
    }
}