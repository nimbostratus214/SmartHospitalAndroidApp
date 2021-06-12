package radenko.mihajlovic.smarthospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    ViewGroup registerActivity;
    DBHelper db;
    Button submit;
    TextView usernameJe, viewInput;
    EditText ime, prezime, password;
    RadioButton M, Z, O;
    CalendarView kalendar;
    public String datum;
    public String pol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerActivity = findViewById(R.id.activityRegister);

        submit = findViewById(R.id.submitDugme);
        usernameJe = findViewById(R.id.usernameJe);

        ime = findViewById(R.id.unetiIme);
        prezime = findViewById(R.id.unetiPrezime);
        password = findViewById(R.id.lozinka);
        M = (RadioButton) findViewById(R.id.musko);
        Z = (RadioButton) findViewById(R.id.zensko);
        O = (RadioButton) findViewById(R.id.ostalo);
        kalendar = (CalendarView) findViewById(R.id.calendar);

        password.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        viewInput = findViewById(R.id.viewInput);
        db = new DBHelper(this);
        usernameJe.setVisibility(View.GONE);
        submit.setOnClickListener(this);
        kalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                datum = dayOfMonth + "." + month + "." + year;
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitDugme) {
            usernameJe.setVisibility(View.VISIBLE);
            if ((ime.getText().length() != 0) && (prezime.getText().length() != 0) && (password.getText().length() != 0)) {
                String name = ime.getText().toString();
                String surname = prezime.getText().toString();

                String lozinka = password.getText().toString();

                char first = lozinka.charAt(0);
                if(first != '0' && password.getText().length() < 9) {


                    String pregledi = "Dermatolog" + "\n" +
                            "Rendgen\n" +
                            "Skidanje gipsa\n" +
                            "Pedijatar\n " +
                            "Kontrola nalaza\n" +
                            "Operacija srca\n" +
                            "Ultrazvuk";
                    String datumi = "13.06.2021.\n" +
                            "14.08.2021.\n" +
                            "31.07.2021.\n" +
                            "21.08.2021.\n" +
                            "12.09.2021.\n" +
                            "15.10.2021.\n" +
                            "12.12.1212\n";
                    //hashPasword
                    JNIExample jniEx = new JNIExample();
                    //convert String to int now!
                    //Sifrovanje je bilo uspesno, sad je okej! :D
                    int sifra = Integer.parseInt(lozinka);
                    int encLozinka = jniEx.hashPassword(sifra, 2142018);

                    db.insert(new Korisnik(null, name, surname, pol, datum, String.valueOf(encLozinka), pregledi));
                    db.insertInPregledi(new user_view(datumi, pregledi)); //za istog korisnika smo popunili obe tabele
                    viewInput.setVisibility(View.VISIBLE);
                    viewInput.setText(name + "." + surname);
                } else {
                    usernameJe.setVisibility(View.GONE);
                    viewInput.setVisibility(View.GONE);
                    Toast.makeText(this, "Lozinka ne moze poceti cifrom 0!", Toast.LENGTH_SHORT).show();
                }

            } else {
                usernameJe.setVisibility(View.GONE);
                viewInput.setText("Unesite Vaše ime, prezime i lozinku!");
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.musko:
                if (checked)
                    pol = "Musko";
                    break;
            case R.id.zensko:
                if (checked)
                    pol = "Zensko";
                    break;
            case R.id.ostalo:
                if(checked)
                    pol = "Ostalo";
                break;
        }
    }


}