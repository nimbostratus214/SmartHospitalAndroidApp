package radenko.mihajlovic.smarthospital;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class AddNewDeviceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ime_uredjaja_ovog;
    private EditText tip_uredjaja_ovog;
    private Button dugme_sacuvaj_ovog;
    private HttpHelper httpHelper;
    private DBHelper dataBase;


    public static String POST_DEVICE = "http://192.168.1.167:8080/api/device"; // + /id




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_device_layout);

        ime_uredjaja_ovog = findViewById(R.id.ime_uredjaja_ovog);
        tip_uredjaja_ovog = findViewById(R.id.tip_uredjaja_ovog);
        dugme_sacuvaj_ovog = findViewById(R.id.dugme_sacuvaj_ovog);

        dugme_sacuvaj_ovog.setOnClickListener(this);

        httpHelper = new HttpHelper();
        dataBase = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.dugme_sacuvaj_ovog){
            //treba pokupiti tekst iz edit_tekstova
            if (ime_uredjaja_ovog.getText().length() <= 0 && tip_uredjaja_ovog.getText().length() <= 0) {
                Toast.makeText(getApplicationContext(), "Niste uneli potrebne podatke!", Toast.LENGTH_LONG).show();
            } else {
                String name = ime_uredjaja_ovog.getText().toString();
                String type = tip_uredjaja_ovog.getText().toString();

                //treba dodati ovaj uredjaj u listu na http serveru
                //prvo treba da kreiramo JSON objekat od ovoga
                /*{
                    "name": "uredjaj3",
                        "id": "7",
                        "state": "off",
                        "type": "sensor"
                }*/


                //okey, now i have json object, lets put it in http server database
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //lets auto generate id for json object, and if this id already in database
                            //server return code 400 -> this will be fun now!
                            Random rn = new Random();
                            int answer = rn.nextInt(10) + 1;
                            //za testiranje koda greske
                            //answer = 20;

                            String value = "" + answer + "";

                            JSONObject json = new JSONObject();
                            try{
                                json.put("name", name);
                                json.put("id",  value);
                                json.put("state", "off");
                                json.put("type", type);
                                Log.d("Json_obj-> ", json.toString());

                            } catch (JSONException e) {
                                //TO DO obraditi izuzetak
                                e.printStackTrace();
                            }

                            JSONObject jsonObject = httpHelper.PostJSONObjectFromURL(POST_DEVICE, json);
                            //json je uredjaj koji smo dodali!
                            //medjutim, ovde treba ga dodati i u internu sql bazu, hajde da i to odradimo

                            String ime = json.getString("name");
                            String id = json.getString("id");
                            String st = json.getString("state");
                            //u sql bazi ne cuvamo tip uredjaja, pa nam taj parametar ne treba
                            boolean stanje;
                            if (st.toUpperCase().equals("ON")) {
                                stanje = true;
                            } else {
                                stanje = false;
                            }

                             Log.d("JSON-obj_USPESNO DODAT: ", jsonObject.toString());

                            //succesful added, lets take message from server
                            /*  {
                                "message": "OK",
                                "code": 200
                                }
                            */
                            String poruka = jsonObject.getString("message");
                            String kodGreske = jsonObject.getString("code");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(kodGreske.equals("200")) {
                                        Toast.makeText(getApplicationContext(), "Successfuly!", Toast.LENGTH_LONG).show();

                                        //////////// dodamo uredjaj u internu bazu

                                        if(id.equals("1")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.temperature, stanje);
                                            av.setPrekidac(stanje);
                                            dataBase.insertInUredjaji(av);
                                        } else if (id.equals("2")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.battery, stanje);
                                            av.setPrekidac(stanje);
                                            dataBase.insertInUredjaji(av);
                                        } else if (id.equals("3")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.sijalica, stanje);
                                            av.setPrekidac(stanje);
                                            dataBase.insertInUredjaji(av);
                                        } else if (id.equals("4")) {
                                            admin_view av = new admin_view(id, ime, R.drawable.smoke, stanje);
                                            av.setPrekidac(stanje);
                                            dataBase.insertInUredjaji(av);
                                        } else {
                                            admin_view av = new admin_view(id, ime, R.drawable.car, stanje);
                                            av.setPrekidac(stanje);
                                            dataBase.insertInUredjaji(av);
                                        }
                                        //ovde dodati Notifikaciju da je uredjaj uspesno dodat u bazu
                                        //: „Senzor poplave je dodat u bazu podataka“.
                                        //name i type
                                        String poruka = type + " " + name + " je dodat u bazu podataka.";
                                        shotNotification(poruka);


                                    } else {
                                        Toast.makeText(getApplicationContext(), poruka.toString() + " Error: " + kodGreske.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });



                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();



            }

        }


    }

    private void shotNotification(String poruka) {
        String id_notifikacija = "Add_New_Device_Channel";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            CharSequence name = "Channel Name";
            String description = id_notifikacija;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(id_notifikacija, name, importance);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.enableVibration(false);

            if(notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        //notifikacija
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, id_notifikacija);
        notificationBuilder.setSmallIcon(R.drawable.notify_ikonica_background);
        notificationBuilder.setContentTitle("Successfully added");
        notificationBuilder.setContentText(poruka);
        notificationBuilder.setLights(Color.WHITE, 500, 5000);
        notificationBuilder.setColor(Color.RED);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1000, notificationBuilder.build());
    }
}
