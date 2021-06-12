package radenko.mihajlovic.smarthospital;

import android.net.IpSecManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

public class DeviceActivity extends AppCompatActivity {

    private String id;
    private TextView nameDevice;
    private TextView idDevice;
    private TextView stateDevice;
    private TextView typeDevice;

    private HttpHelper httpHelper;

    private static String GET_DEV_INFO = "http://192.168.1.167:8080/api/device/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        nameDevice = findViewById(R.id.nameOfDevice);
        idDevice = findViewById(R.id.idOfDevice);
        stateDevice = findViewById(R.id.stateOfDevice);
        typeDevice = findViewById(R.id.typeOfDevice);

        id = getIntent().getStringExtra("key");

        httpHelper = new HttpHelper();

        //idDevice.setText("ID uredjaja: " + id);
        //napraviti thread i uputiti http zahtev i to je to

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = httpHelper.getJSONObjectFromURL(GET_DEV_INFO + id);
                    //sad samo treba parsirati ovaj json objekat

                    String ime = "Name: " + jsonObject.getString("name");
                    String idNum = "ID: " + jsonObject.getString("id"); //vec imamo id, al aj :D
                    String stanje = "State: " + jsonObject.getString("state");
                    String tip = "Type: " + jsonObject.getString("type");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                nameDevice.setText(ime);
                                stateDevice.setText(stanje);
                                idDevice.setText(idNum);
                                typeDevice.setText(tip);
                            } catch (Exception e) {
                                e.printStackTrace();
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
