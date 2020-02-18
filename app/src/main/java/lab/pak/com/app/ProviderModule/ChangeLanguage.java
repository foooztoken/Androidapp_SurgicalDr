package lab.pak.com.app.ProviderModule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import lab.pak.com.app.R;

public class ChangeLanguage extends AppCompatActivity {

    private Button btnDisplay;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        addListenerOnButton();
        Toolbar toolbar = findViewById(R.id.header);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
           // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        btnDisplay = (Button) findViewById(R.id.ok);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);
if(radioSexButton.getText().toString().equals("Arabic")){
    SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
    editor.putString("language", "arabic");
editor.apply();
finish();
Intent o=new Intent(getApplicationContext(),ProviderMain.class);
startActivity(o);
}
else{
    SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
    editor.putString("language", "English");
    editor.apply();
    finish();
    Intent o=new Intent(getApplicationContext(),ProviderMain.class);
    startActivity(o);
}
    //            Toast.makeText(getApplicationContext(),
      //                  radioSexButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });

    }


}
