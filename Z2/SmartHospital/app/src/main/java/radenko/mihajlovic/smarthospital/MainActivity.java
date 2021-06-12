package radenko.mihajlovic.smarthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup tContainer;
    private Button prijavise, registrujse, submit;
    private EditText username, password;
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
        username.addTextChangedListener(prijava);
        password.addTextChangedListener(prijava);

    }

    private TextWatcher prijava = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String korisnickoIme = username.getText().toString();
            String pasvord = password.getText().toString();

            prijavise.setEnabled(!korisnickoIme.isEmpty() && !pasvord.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.prijavise){
            visible = !visible;
            username.setVisibility(visible ? View.VISIBLE : View.GONE);
            password.setVisibility(visible ? View.VISIBLE : View.GONE);
            submit.setVisibility(visible ? View.VISIBLE : View.GONE);
        } else if(v.getId() == R.id.prijava) {
            //prelaz na new activity

            String korisnickoIme = username.getText().toString();
            if(korisnickoIme.toUpperCase(Locale.ROOT).equals("ADMIN")) {
                //admin activity
                Intent i2 = new Intent(MainActivity.this, LoginActivityAdmin.class);
                startActivity(i2);

            } else {
                //user activity
                Intent i3 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i3);
            }


        } else if(v.getId() == R.id.registrujse) {
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        }
    }
}