package lab.pak.com.app.BidsModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.Models.bids;
import lab.pak.com.app.R;

import static android.content.Context.MODE_PRIVATE;


public class CustomAdapterbids extends RecyclerView.Adapter<CustomAdapterbids.MyViewHolder> {

    private  List<bids> conversationmodel;
   Context context;
    private MyViewHolder vh;
String type;

    public CustomAdapterbids(Context context, List<bids> conversation){
conversationmodel=conversation;
this.context=context;
    type="user";}

    public CustomAdapterbids(Context bidHistory, ArrayList<bids> jobs, String type) {
        conversationmodel=jobs;
      this.type=type;
        this.context=bidHistory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid, parent, false);

        vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    View vl;
try {
    SharedPreferences pref =context. getSharedPreferences("userdata", MODE_PRIVATE);
    String language = pref.getString("language", null);
    String typee = pref.getString("type", null);
    if(language!=null&&language.equals("arabic")&&typee.equals("user")){
        holder.title.setText(" :وصف " + conversationmodel.get(position).specialization);

        holder.state.setText(conversationmodel.get(position).city + "," + conversationmodel.get(position).country);
        holder.date.setText(conversationmodel.get(position).time);

    }else {

        holder.title.setText("Description: " + conversationmodel.get(position).specialization);
        holder.state.setText(conversationmodel.get(position).city + "," + conversationmodel.get(position).country);
        holder.date.setText(conversationmodel.get(position).time);
    }

  holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Intent op = new Intent(context, BidDetails.class);
                bids o = conversationmodel.get(position);
                Gson gson = new Gson();
                String json = gson.toJson(o);
                op.putExtra("value","1" );
                op.putExtra("ob", json);
                context.startActivity(op);
            }catch (Exception e){

                //Toast.makeText(context,e.toString() ,Toast.LENGTH_LONG ).show();
                 }}
    });
}catch (Exception e){String ee= String.valueOf(e);
//Toast.makeText(context,ee ,Toast.LENGTH_LONG ).show();
}
// implement setOnClickListener event on item view.

}





    @Override
    public int getItemCount() {
        return conversationmodel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



     //   private final TextView status;
        // init the item view's
        TextView title,state,date;


        public MyViewHolder(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
// get status=itemView.findViewById(R.id.status);
           state=(TextView)itemView.findViewById(R.id.state);

date=itemView.findViewById(R.id.date);

        }
    }
}