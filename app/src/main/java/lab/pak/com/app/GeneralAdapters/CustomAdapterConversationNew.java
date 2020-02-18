package lab.pak.com.app.GeneralAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lab.pak.com.app.Models.Messages;
import lab.pak.com.app.R;

import static android.content.Context.MODE_PRIVATE;


public class CustomAdapterConversationNew extends RecyclerView.Adapter<CustomAdapterConversationNew.MyViewHolder> {

    private final String nam;
    private  List<Messages> conversationmodel;
   Context context;
    private MyViewHolder vh;


    public CustomAdapterConversationNew(Context context, List<Messages> conversation){
conversationmodel=conversation;
this.context=context;
        SharedPreferences prefs = context.getSharedPreferences("userdata", MODE_PRIVATE);
        nam = prefs.getString("name", null);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation, parent, false);

        vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    View vl;
try {
    if(conversationmodel.get(position).attachmenturl.equals("no")){
        holder.attachment.setVisibility(View.INVISIBLE);
    }else{

        holder.attachment.setVisibility(View.VISIBLE);
    }
    holder.attachment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {try {
            String mains = conversationmodel.get(position).attachmenturl;
            String newstring = mains.replace("uploading/", "");

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://surapi.surgicaldr.com/Models/uploading/" + newstring ));
            context.startActivity(browserIntent);
        }catch (Exception e){Toast.makeText(context,e.toString() ,Toast.LENGTH_LONG ).show();}}
    });


if(conversationmodel.get(position).bywho.equals("user")){
    holder.conversation.setBackgroundResource(R.drawable.bubble);

    holder.message.setTextColor(Color.parseColor("#000000"));
    holder.attachment.setTextColor(Color.parseColor("#000000"));

}else{
    holder.conversation.setBackgroundResource(R.drawable.bubbletwo);

    holder.message.setTextColor(Color.parseColor("#000000"));
    holder.attachment.setTextColor(Color.parseColor("#000000"));

  }
holder.time.setText(conversationmodel.get(position).dates);
holder.message.setText(conversationmodel.get(position).message);
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


        public RelativeLayout conversation;
        //   private final TextView status;
        // init the item view's
        TextView time,message;
TextView attachment;
        public MyViewHolder(View itemView) {
            super(itemView);
            conversation=itemView.findViewById(R.id.conversation);
attachment=itemView.findViewById(R.id.attachment);
           // name=itemView.findViewById(R.id.name);
// get status=itemView.findViewById(R.id.status);
           time=(TextView)itemView.findViewById(R.id.time);
            message=(TextView)itemView.findViewById(R.id.message);


        }
    }
}