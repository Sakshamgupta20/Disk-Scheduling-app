package labs.a.s.disk_scheduling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Eheader, Eprerequest, Ecylinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Eheader = (EditText) findViewById(R.id.header_Edit);
        Eprerequest = (EditText) findViewById(R.id.prerequest_edit);
        Ecylinder = (EditText) findViewById(R.id.cylinder_Edit);
    }

    public void enter(View view) {
       //check if empty
        if (TextUtils.isEmpty(Ecylinder.getText()) || TextUtils.isEmpty(Eprerequest.getText())
                || TextUtils.isEmpty(Eheader.getText())) {
            Toast.makeText(MainActivity.this, "Please Enter all Values", Toast.LENGTH_SHORT).show();
        }

        else {
            //Getting data from edittexts
            int ncylinder = Integer.parseInt(Ecylinder.getText().toString());
            int prerequest = Integer.parseInt(Eprerequest.getText().toString());
            int header = Integer.parseInt(Eheader.getText().toString());

            //putting data into bundle
            Bundle bundle = new Bundle();
            bundle.putInt("cylinder", ncylinder);
            bundle.putInt("prerequest", prerequest);
            bundle.putInt("header", header);

            //starting new activity
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


}
