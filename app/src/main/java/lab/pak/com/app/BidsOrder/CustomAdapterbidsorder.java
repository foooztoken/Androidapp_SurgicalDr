package lab.pak.com.app.BidsOrder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lab.pak.com.app.Models.bids;
import lab.pak.com.app.R;


public class CustomAdapterbidsorder extends RecyclerView.Adapter<CustomAdapterbidsorder.MyViewHolder> {

    private  List<bids> conversationmodel;
   Context context;
    private MyViewHolder vh;


    public CustomAdapterbidsorder(Context context, List<bids> conversation){
conversationmodel=conversation;
this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidorder, parent, false);

        vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    View vl;
try {

holder.title.setText("Description: "+ conversationmodel.get(position).specialization);
holder.state.setText("Budget:$"+conversationmodel.get(position).budget);
    holder.date.setText(conversationmodel.get(position).time);


  holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
//.startActivity(op);
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