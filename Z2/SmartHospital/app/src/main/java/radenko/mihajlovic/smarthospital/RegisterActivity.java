package radenko.mihajlovic.smarthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup registerActivity;
    Button submit;
    TextView usernameJe, viewInput;
    EditText ime, prezime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerActivity = findViewById(R.id.activityRegister);
        submit = findViewById(R.id.submitDugme);
        usernameJe = findViewById(R.id.usernameJe);
        ime = findViewById(R.id.unetiIme);
        prezime = findViewById(R.id.unetiPrezime);
        viewInput = findViewById(R.id.viewInput);

        usernameJe.setVisibility(View.GONE);
        submit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submitDugme) {
            usernameJe.setVisibility(View.VISIBLE);
            if((ime.getText().length() != 0) && (prezime.getText().length() != 0)) {
                String name = ime.getText().toString();
                String surname = prezime.getText().toString();
                viewInput.setText(name + "." + surname);
            } else {
                usernameJe.setVisibility(View.GONE);
                viewInput.setText("Unesite Vaše ime i prezime!");
            }
        }
    }
}