package radenko.mihajlovic.smarthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private HttpHelper HttpHelper;
    ViewGroup tContainer;
    private Button prijavise, registrujse, submit;
    private EditText username, password;
    private TextView greska;
    boolean visible;
    public int check;
    DBHelper DB;

    public static String GET_ALL_DEVICES = "http://192.168.1.167:8080/api/devices/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpHelper = new HttpHelper();

        tContainer = findViewById(R.id.transitionContainer);
        prijavise = findViewById(R.id.prijavise);
        registrujse = findViewById(R.id.registrujse);

        username = findViewById(R.id.username);
        password = findViewById(R.id.pasvord);
        greska = findViewById(R.id.greska);
        submit = findViewById(R.id.prijava);

        username.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);

        prijavise.setOnClickListener(this);
        submit.setOnClickListener(this);
        registrujse.setOnClickListener(this);
        username.addTextChangedListener(prijava);
        password.addTextChangedListener(prijava);

        DB = new DBHelper(this); //ovo je falilo


        password.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        //TEST JNI
        //JNIExample jniEx = new JNIExample();
        //int res = jniEx.hashPassword(45310188, 2142018);
        //Log.d("Sifrovano: ", String.valueOf(res));

        //int res2 = jniEx.unhashPassword(2142018, res);
        //Log.d("Desifrovano: ", String.valueOf(res2));
        //zakljucak nakon testiranja:
        //ograniciti password da budu samo cifre, ne sme poceti sa nulom i duzina mora biti <= 8 karaktera !

    }

    private TextWatcher prijava = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String korisnickoIme = username.getText().toString();
            String pasvord = password.getText().toString();

            //prijavise.setEnabled(!korisnickoIme.isEmpty() && !pasvord.isEmpty());
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

                //clear db
                //PROVERITI OVAJ KOD !!!!!
                admin_view[] admin = DB.readUredjaji(); //ovo moze da se obrise, jer se tamo svakako ponovo dodaju u bazu
                if(admin.length > 0) {
                    DB.deleteUredjaji();
                }
/*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonobject = HttpHelper.getJSONArrayFromURL(GET_ALL_DEVICES); //dobili smo json objekat sada
                            //jsonobject = [{}, {}, .... , {} ]
                            Log.e("Duzina niza: ", "ovo je: " + jsonobject.length());
                            for (int i = 0; i < jsonobject.length(); i++) {
                                //for each el of json arr, take one json obj
                                JSONObject el = jsonobject.getJSONObject(i);
                                Log.d("JSON data- ", el.toString());
                                String ime = el.getString("name");
                                String id = el.getString("id");
                                String st = el.getString("state");
                                boolean stanje;
                                if (st.toUpperCase().equals("ON")) {
                                    stanje = true;
                                } else {
                                    stanje = false;
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(id.equals("1")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.temperature, stanje);
                                            av.setPrekidac(stanje);
                                            DB.insertInUredjaji(av);
                                        } else if (id.equals("2")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.battery, stanje);
                                            av.setPrekidac(stanje);
                                            DB.insertInUredjaji(av);
                                        } else if (id.equals("3")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.sijalica, stanje);
                                            av.setPrekidac(stanje);
                                            DB.insertInUredjaji(av);
                                        } else if (id.equals("4")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.smoke, stanje);
                                            av.setPrekidac(stanje);
                                            DB.insertInUredjaji(av);
                                        } else {
                                            admin_view av = new admin_view(id, ime, R.drawable.car, stanje);
                                            av.setPrekidac(stanje);
                                            DB.insertInUredjaji(av);
                                        }
                                    }
                                });



                            }
                        } catch (IOException e) {
                            //"There are no available devices"
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "There are no available devices!", Toast.LENGTH_LONG).show();
                                }
                            });
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
*/
                startActivity(i2);

            } else {
                //user activity
                //treba uraditi proveru da li taj student postoji u bazi podataka, ako ne postoji ispisati gresku
                //username je ime.prezime, dakle ovo splitovati po "."
                //treba nam metoda u DBHelperu koja iz baze cita korisnika sa odredjenim imenom, prezimenom i sifrom!
                if(username.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                {
                    greska.setVisibility(View.VISIBLE);
                    greska.setText("Unesite username i lozinku!");
                } else {
                    try {
                        String[] arr = username.getText().toString().split("\\."); //escape "." !!! Pattern.quote(".")
                        String ime, prezime, lozinka;
                        ime = arr[0];
                        prezime = arr[1];
                        lozinka = password.getText().toString();
                        //Log.d("Pre readK: ", lozinka);
                        //unhash Password
                        //potrebno je ponovo pozvati hashPassword f-ju nad sifrom koju je korisnik uneo
                        //jer korisnik nece uneti "sifrovanu" sifru, vec original
                        //samim tim, mi moramo ponovo sifrovati to, pretraziti bazu i ako nadjemo korisnika
                        //sa tim imenom-prezimenom-sifrom onda je sve okej, u suprotnom korisnik ne postoji u bazi
                        //i potrebno je da se registruje
                        //samim tim, f-ja unhasPassword gubi smisao i nema je potrebe koristiti
                        // jedan od nacina da ovo resimo je da smatramo da svi korisnici imaju razlicita imena i prezimena
                        //dobavimo korisnika po imenu i prezimenu i onda proverimo da li se input lozinka slaze
                        //sa lozinkom dobavljenog korisnika !

                        //Korisnik k = DB.readK(ime, prezime, lozinka); //comment this for now
                        Korisnik k = DB.readKorisnik(ime, prezime);

                        if (k != null) {
                            //ako postoji u bazi, proveri ispravnost lozinke
                            //jedan od nacina da se ipak iskoristi unhashPassword! :D
                            int dobavljenaSifra = Integer.parseInt(k.getLozinka());
                            JNIExample jniEx = new JNIExample();
                            int jniSifra = jniEx.unhashPassword(2142018, dobavljenaSifra);
                            int loz = 0;
                            try {
                                loz = Integer.parseInt(lozinka);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (jniSifra == loz) {
                                Intent i3 = new Intent(MainActivity.this, LoginActivity.class);
                                //pokupiti id korisnika i poslati na sledeci aktivity
                                i3.putExtra("key", k.getId());
                                startActivity(i3);
                                greska.setVisibility(View.INVISIBLE);
                            } else {
                                greska.setVisibility(View.VISIBLE);
                                greska.setText("Neispravan password!");
                            }
                        } else {
                            greska.setVisibility(View.VISIBLE);
                            greska.setText("Nepostojeci korisnik! Registrujte se!");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        greska.setVisibility(View.VISIBLE);
                        greska.setText("Neispravan username!");
                    }
                }
            }
        } else if(v.getId() == R.id.registrujse) {
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        }
    }
}