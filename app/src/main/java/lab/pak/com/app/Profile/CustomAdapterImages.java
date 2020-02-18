package lab.pak.com.app.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import lab.pak.com.app.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CustomAdapterImages extends RecyclerView.Adapter<CustomAdapterImages.MyViewHolder> {

    private final ArrayList<String> mainob;
   Context context;
    private MyViewHolder vh;
ProgressBar progressBar;
    private String history,med,disease;
    private String aget;
    private String blood;

    public CustomAdapterImages(Context context, ArrayList<String> mainob) {
        this.context = context;
        this.mainob=mainob;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleimage, parent, false);

        vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

    View vl;
try {





    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url(mainob.get(position))
            .build();
//holder.progressBar.setVisibility(View.VISIBLE);
    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
         //   holder.progressBar.setVisibility(View.INVISIBLE);
            //holder.progressBar.setVisibility(View.INVISIBLE);
            holder.image.setBackgroundColor(Color.parseColor("#f5f2d0"));
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
           // holder.progressBar.setVisibility(View.INVISIBLE);
     try{
         //holder.image.setBackgroundColor(Color.parseColor("#f5f2d0"));

         //  holder.progressBar.setVisibility(View.INVISIBLE);
            response.body().byteStream(); // Read the data from the stream
            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
           holder.image.setImageBitmap(bmp);
     }catch (Exception e){Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();}}


    });

  holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {

                String age;
                Intent op = new Intent(context, PictureDetail.class);

op.putExtra("url", mainob.get(position));

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
       ImageView image;
public  ProgressBar progressBar;
        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
// get status=itemView.findViewById(R.id.status);
progressBar=(ProgressBar) itemView.findViewById(R.id.progress);
  //          amount=(TextView)itemView.findViewById(R.id.budget);


        }
    }
}