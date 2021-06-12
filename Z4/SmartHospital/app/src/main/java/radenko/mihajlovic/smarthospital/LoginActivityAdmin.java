package radenko.mihajlovic.smarthospital;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.Iterator;


public class LoginActivityAdmin extends AppCompatActivity implements View.OnClickListener {

     private HttpHelper HttpHelper; //helper za komunikaciju sa serverom

     public static String BASE_URL = "http://192.168.1.167:8080/api"; //192.168.1.167 - localhost
     public static String GET_ALL_DEVICES = "http://192.168.1.167:8080/api/devices/";


     private ListView lista;
     private AdminAdapter adapter;
     private TextView naziv_uredjaja;
     private Switch dugme_uredjaja;
     private TextView t1;
     private Button dodaj_uredjaj;

     private DBHelper dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        HttpHelper = new HttpHelper(); //uvezemo ga !

        lista = findViewById(R.id.lista_admin);
        adapter = new AdminAdapter(this);
        naziv_uredjaja = findViewById(R.id.naziv_uredjaja);
        dodaj_uredjaj = findViewById(R.id.dodaj_uredjaj);
        dugme_uredjaja = findViewById(R.id.dugme_uredjaja);
        lista.setAdapter(adapter);

        dodaj_uredjaj.setOnClickListener(this);

        dataBase = new DBHelper(this);

        admin_view[] uredjaji = dataBase.readUredjaji();
        adapter.update(uredjaji);


       Log.d("Dugme_uredjaja: ", String.valueOf(dugme_uredjaja));
        /*
        admin_view a1 = new admin_view("1", "Video nadzor", R.drawable.video, false);
        admin_view a2 = new admin_view("2","Senzor dima", R.drawable.smoke, false);
        admin_view a3 = new admin_view("3","Senzor kvaliteta vazduha", R.drawable.air, false);
        admin_view a4 = new admin_view("4","Senzor osvetljenja", R.drawable.sijalica, false);
        admin_view a5 = new admin_view("5","Senzor temperature I sprat", R.drawable.temperature, false);
        admin_view a6 = new admin_view("6","Senzor temperature II sprat", R.drawable.temperature, false);
        admin_view a7 = new admin_view("7","Senzor temperature III sprat", R.drawable.temperature, false);
        admin_view a8 = new admin_view("8","Sistem za akumulaciju energije", R.drawable.battery, false);
        admin_view a9 = new admin_view("9","Punjac elektricnih vozila", R.drawable.car, false);
        admin_view a10 = new admin_view("10","Ventilacija", R.drawable.ventilacija, false);
        */

        //admin_view[] admin = dataBase.readUredjaji();

        //if(admin.length > 0) {
            //dataBase.deleteUredjaji();
        //}
        //dataBase.insertInUredjaji(a1);
        //admin_view[] uredjaji = dataBase.readUredjaji();
        //ovde bi trebalo uputiti get zahtev serveru i dobabviti uredjaje, pa ih dodati u bazu!
        //u bazu mozemo dodati samo objekte klase admin_view
        //pa cemo parsiranjem JSON stringa razbiti svaki uredjaj na te tipove i dodavati redom!


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

                        Log.d("Dugme_uredjaja_thread: ", String.valueOf(dugme_uredjaja));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(id.equals("1")) {
                                    admin_view av = new admin_view(id, ime, R.drawable.temperature, stanje);
                                    av.setPrekidac(stanje);
                                    dataBase.insertInUredjaji(av);
                                    adapter.addElement(av);
                                } else if (id.equals("2")) {
                                    admin_view av = new admin_view(id, ime, R.drawable.battery, stanje);
                                    av.setPrekidac(stanje);
                                    dataBase.insertInUredjaji(av);
                                    adapter.addElement(av);

                                } else if (id.equals("3")) {
                                    admin_view av = new admin_view(id, ime, R.drawable.sijalica, stanje);
                                    av.setPrekidac(stanje);
                                    dataBase.insertInUredjaji(av);
                                    adapter.addElement(av);

                                } else if (id.equals("4")) {
                                    admin_view av = new admin_view(id, ime, R.drawable.smoke, stanje);
                                    av.setPrekidac(stanje);
                                    dataBase.insertInUredjaji(av);
                                    adapter.addElement(av);

                                } else {
                                    admin_view av = new admin_view(id, ime, R.drawable.car, stanje);
                                    av.setPrekidac(stanje);
                                    dataBase.insertInUredjaji(av);
                                    adapter.addElement(av);

                                };
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

        /*

        //ovde se dodaju uredjaji u bazu
        dataBase.insertInUredjaji(a1);
        dataBase.insertInUredjaji(a2);
        dataBase.insertInUredjaji(a3);
        dataBase.insertInUredjaji(a4);
        dataBase.insertInUredjaji(a5);
        dataBase.insertInUredjaji(a6);
        dataBase.insertInUredjaji(a7);
        dataBase.insertInUredjaji(a8);
        dataBase.insertInUredjaji(a9);
        dataBase.insertInUredjaji(a10);

        */



        //dodati ostale uredjaje
        /*

        admin_view podatak1 = new admin_view("Video nadzor", getResources().getDrawable(R.drawable.video), false);
        admin_view podatak2 = new admin_view("Senzor temperature", getResources().getDrawable(R.drawable.temperature), false);
        admin_view podatak3 = new admin_view("Senzor osvetljenja", getResources().getDrawable(R.drawable.sijalica), false);
        admin_view podatak4 = new admin_view("Senzor napunjenosti baterija", getResources().getDrawable(R.drawable.battery), false);
        admin_view podatak5 = new admin_view("Senzor pokreta", getResources().getDrawable(R.drawable.motion_sensor), false);
        admin_view podatak6 = new admin_view("Senzor dima", getResources().getDrawable(R.drawable.smoke), false);


        adapter.addElement(podatak1);
        adapter.addElement(podatak2);
        adapter.addElement(podatak3);
        adapter.addElement(podatak4);
        adapter.addElement(podatak5);
        adapter.addElement(podatak6);
        adapter.addElement(podatak6);
        adapter.addElement(podatak6);
        adapter.addElement(podatak6);
*/


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1 = new Intent(LoginActivityAdmin.this, DeviceActivity.class);
                //dobavljen id uredjaja, treba ga poslati na sledeci aktivity i tamo uputiti http get zahtev na osnovu id-a
                admin_view av = (admin_view) adapter.getItem(position);

                i1.putExtra("key", av.getID()); //prosledjen id na deviceActivity
                Log.d("Ovo je id na koji je kliknuto: ", "id: " +  av.getID());
                startActivity(i1);
            }
        });

    }
//ovde se uredjaji citaju iz baze i prikazuju u login aktivitiju za admina
    @Override
    protected void onResume() {
        super.onResume();

        admin_view[] uredjaji = dataBase.readUredjaji();

        adapter.update(uredjaji);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dodaj_uredjaj){
            //ako se klikne dodaj uredjaj treba otvoriti novi activity AddNewDeviceActivity
            Intent i5 = new Intent(LoginActivityAdmin.this, AddNewDeviceActivity.class);
            startActivity(i5);
        }
    }
}
