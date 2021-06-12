package radenko.mihajlovic.smarthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class LoginActivity extends AppCompatActivity {

    private ListView lista;
    private UserAdapter UserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lista = findViewById(R.id.lista);
        UserAdapter = new UserAdapter(this);
        lista.setAdapter(UserAdapter);

        user_view podatak0 = new user_view("", "");
        user_view podatak1 = new user_view("13.06.2021.", "Dermatolog");
        user_view podatak2 = new user_view("14.08.2021.", "Rendgen");
        user_view podatak3 = new user_view("31.07.2021.", "Skidanje gipsa");
        user_view podatak4 = new user_view("21.08.2021.", "Pedijatar");
        user_view podatak5 = new user_view("12.09.2021.", "Kontrola nalaza");
        user_view podatak6 = new user_view("15.10.2021.", "Operacija srca");
        user_view podatak7 = new user_view("12.12.2021.", "Ultrazvuk");

        UserAdapter.addElement(podatak0);
        UserAdapter.addElement(podatak1);
        UserAdapter.addElement(podatak2);
        UserAdapter.addElement(podatak3);
        UserAdapter.addElement(podatak4);
        UserAdapter.addElement(podatak5);
        UserAdapter.addElement(podatak6);
        UserAdapter.addElement(podatak7);
    }
}