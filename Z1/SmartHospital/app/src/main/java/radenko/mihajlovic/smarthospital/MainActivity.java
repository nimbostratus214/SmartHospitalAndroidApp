package radenko.mihajlovic.smarthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup tContainer;
    Button prijavise, registrujse, submit;
    EditText username, password;
    boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tContainer = findViewById(R.id.transitionContainer);
        prijavise = findViewById(R.id.prijavise);
        registrujse = findViewById(R.id.registrujse);
        username = findViewById(R.id.username);
        password = findViewById(R.id.pasvord);
        submit = findViewById(R.id.prijava);

        username.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);

        prijavise.setOnClickListener(this);
        submit.setOnClickListener(this);
        registrujse.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.prijavise){
            visible = !visible;
            username.setVisibility(visible ? View.VISIBLE : View.GONE);
            password.setVisibility(visible ? View.VISIBLE : View.GONE);
            submit.setVisibility(visible ? View.VISIBLE : View.GONE);
        } else if(v.getId() == R.id.prijava) {
            //prelaz na new activity
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        } else if(v.getId() == R.id.registrujse) {
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        }
    }
}