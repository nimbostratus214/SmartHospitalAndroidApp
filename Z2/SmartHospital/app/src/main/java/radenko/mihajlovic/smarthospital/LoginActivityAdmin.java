package radenko.mihajlovic.smarthospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivityAdmin extends AppCompatActivity {
     private ListView lista;
     private AdminAdapter adapter;
     private TextView naziv_uredjaja;
     private Switch dugme_uredjaja;
     private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        lista = findViewById(R.id.lista_admin);
        adapter = new AdminAdapter(this);
        naziv_uredjaja = findViewById(R.id.naziv_uredjaja);

        dugme_uredjaja = findViewById(R.id.dugme_uredjaja);
        lista.setAdapter(adapter);

        //dodati ostale uredjaje
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

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1 = new Intent(LoginActivityAdmin.this, DeviceActivity.class);
                startActivity(i1);
            }
        });

    }
}
