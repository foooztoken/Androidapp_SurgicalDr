package lab.pak.com.app.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lab.pak.com.app.R;
import lab.pak.com.app.Review.Review;


public class CustomAdapterReview extends RecyclerView.Adapter<CustomAdapterReview.MyViewHolder> {

    private  List<Review> conversationmodel;
   Context context;
    private MyViewHolder vh;


    public CustomAdapterReview(Context context, List<Review> conversation){
conversationmodel=conversation;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);

        vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    View vl;
try {

    holder.time.setText(conversationmodel.get(position).tims);
holder.title.setText(conversationmodel.get(position).name);
holder.review.setText(conversationmodel.get(position).review);
float n= Float.parseFloat(conversationmodel.get(position).stars);
holder.ratingbar.setRating(n);
  holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {

            }catch (Exception e){Toast.makeText(context,e.toString() ,Toast.LENGTH_LONG ).show();}}
    });
}catch (Exception e){String ee= String.valueOf(e);
Toast.makeText(context,ee ,Toast.LENGTH_LONG ).show();}
// implement setOnClickListener event on item view.

}





    @Override
    public int getItemCount() {
        return conversationmodel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



     //   private final TextView status;
        // init the item view's
        TextView title,review,time;
          RatingBar   ratingbar;

        public MyViewHolder(View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.time);

            title=itemView.findViewById(R.id.title);
// get status=itemView.findViewById(R.id.status);
           review=(TextView)itemView.findViewById(R.id.review);
            ratingbar=(RatingBar)itemView.findViewById(R.id.ratingBar);


        }
    }
}