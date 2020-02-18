package lab.pak.com.app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import lab.pak.com.app.FindJobs.JobsList;
import lab.pak.com.app.Models.filter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilterFragment extends Fragment {


        private View rootview;
    private ProgressBar progress;
    //private AdView add;


    //  ProgressBar progressBar;
Button save;
    private Retrofit retrofit;
    private Spinner city,country;
    private Button close;
    private static String citytext,countrytext;

    @Override
        public void onResume() {

            super.onResume();

            getView().setFocusableInTouchMode(true);
            getView().requestFocus();

        }

        @Override
        public View onCreateView(LayoutInflater inflater,

                                 ViewGroup container, Bundle savedInstanceState) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            rootview=inflater.inflate(R.layout.filter, container, false);
            progress=(ProgressBar)rootview.findViewById(R.id.progress);
            ImageView apple=rootview.findViewById(R.id.apple);
            apple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            getActivity().getWindow().setBackgroundDrawableResource(R.mipmap.back);
            save=rootview.findViewById(R.id.insert);

            city=rootview.findViewById(R.id.citytext);
            country=rootview.findViewById(R.id.countrytext);
            progress=rootview.findViewById(R.id.progress);
            close=rootview.findViewById(R.id.close);

            getcountries();



            try{

    close.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

try {
    getActivity().onBackPressed();
}catch (Exception e){}



        }});
    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {





try {
    ((JobsList) getActivity()).intiatefilter(citytext, countrytext);
    getActivity().onBackPressed();

}catch (Exception e){}



            //  login(email.getText().toString(), pass.getText().toString());
        }});

            }catch (Exception e){}
            return rootview; }







//


















    void getstates(String country){

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<filter>> callPet = service.getcountries( "http://surapi.surgicaldr.com/Models/statefilter.php?country="+country);
            //progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<filter>>() {
                @Override
                public void onResponse(Call<List<filter>> call, Response<List<filter>> response) {
                    try {
                        //  progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<filter> ob=response.body();


                                ArrayList<filter> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++){
//Toast.makeText(getApplicationContext(),ob.get(i).state ,Toast.LENGTH_LONG ).show();
                                    jobs.add(ob.get(i));

                                }



                                initiatestates(jobs);





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
                                //        progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                       //         Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

                 //       Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                        //progressBar.setVisibility(View.INVISIBLE);
                        //    progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<filter>> call, Throwable t) {
                    try {
                        //progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);
                   //     Toast.makeText(getActivity(), call.toString(), Toast.LENGTH_LONG).show();

                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                 //       Toast.makeText(getActivity(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
      //      Toast.makeText(getActivity(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }
    void getcountries()
    {

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://surapi.surgicaldr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitInterface service=retrofit.create(RetrofitInterface.class);
            Call<List<filter>> callPet = service.getcountries( "http://surapi.surgicaldr.com/Models/countryfilter.php");
            //progress.setVisibility(View.VISIBLE);
            callPet.enqueue(new Callback<List<filter>>() {
                @Override
                public void onResponse(Call<List<filter>> call, Response<List<filter>> response) {
                    try {
                        //  progress.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()) {

                            try {

                                List<filter> ob=response.body();


                                ArrayList<filter> jobs=new ArrayList<>();
                                for(int i=0;i<ob.size();i++) {

                                    jobs.add(ob.get(i));



/*}else{
                                for(int x=0;x<jobs.size();x++) {

                                    if (ob.get(i).country.equals(jobs.get(x).country)) {}else{
                                        jobs.add(ob.get(i));
                                        Toast.makeText(getApplicationContext(), ob.get(x).country,Toast.LENGTH_LONG ).show();
                                    }
                                }
  */                          }
                                initiatecountry(jobs);





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
                                //        progress.setVisibility(View.INVISIBLE);

                                //       progressBar.setVisibility(View.INVISIBLE);

                //                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (Exception e) {{
                        //        progress.setVisibility(View.INVISIBLE);

                        //       progressBar.setVisibility(View.INVISIBLE);

               //         Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                        //progressBar.setVisibility(View.INVISIBLE);
                        //    progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<filter>> call, Throwable t) {
                    try {
                        //progress.setVisibility(View.INVISIBLE);

                        //  progressBar.setVisibility(View.INVISIBLE);
            //            Toast.makeText(getActivity(), call.toString(), Toast.LENGTH_LONG).show();
//
                        //     Toast.makeText(Main.this,"Error!Check Connection" ,Toast.LENGTH_LONG ).show();
                    } catch (Exception a) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        // progress.setVisibility(View.INVISIBLE);

                        //e.printStackTrace();
                ///        Toast.makeText(getActivity(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                }

            });







//        Toast.makeText(MainActivity.this,s ,Toast.LENGTH_LONG ).show();
        } catch (Exception a) {
            //progressBar.setVisibility(View.INVISIBLE);
            // progress.setVisibility(View.INVISIBLE);

            //e.printStackTrace();
      ///      Toast.makeText(getActivity(),"Problem occuring ",Toast.LENGTH_LONG ).show();

        }


    }

    void initiatestates(List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).state);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                citytext=adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

    }

    void initiatecountry(final List<filter> countries){
        List<String> arr=new ArrayList<>();
        for(int i=0;i<countries.size();i++){
            arr.add(countries.get(i).country);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.drawable);
        country.setAdapter(adapter);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                countrytext=adapter.getItem(position);
//Toast.makeText(getApplicationContext(),countrytext ,Toast.LENGTH_LONG ).show();
                getstates(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

    }




}