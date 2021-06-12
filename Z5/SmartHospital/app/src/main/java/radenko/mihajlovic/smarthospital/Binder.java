package radenko.mihajlovic.smarthospital;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.UiThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Binder extends IBinderAIDL.Stub{
    public static String GET_ALL_DEVICES = "http://192.168.1.167:8080/api/devices/";
    private NewDevices mCaller;
    private Context context;

    public Binder(Context context) {this.context = context;}

    @Override
    public void start() throws RemoteException {
        mCaller = new NewDevices(context);
        mCaller.start();
    }

    @Override
    public void stop() throws RemoteException {
        mCaller.stop();
    }

    private class NewDevices implements Runnable {

        private android.os.Handler mHandler = null; // ?
        private static final long PERIOD = 10000L; //10s //treba staviti 60000 za 1sekundu //stavljeno namerno ovako da bi videli brze rezultate!
        private boolean mRun = true;
        private HttpHelper HttpHelper;
        private DBHelper dataBase;
        public AdminAdapter adapter;
        private LoginActivityAdmin admin;




        private NewDevices(Context context){
            HttpHelper = new HttpHelper();
            dataBase = new DBHelper(context);
            //adapter =  new AdminAdapter(context);
            adapter = LoginActivityAdmin.adapter;
        }

        public void start() {
            mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(this, PERIOD);
        }

        private void stop() {
            mRun = false;
            mHandler.removeCallbacks(this);
        }


        @Override
        public void run() {

            if(!mRun) return;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        JSONArray jsonobject = HttpHelper.getJSONArrayFromURL(GET_ALL_DEVICES); //dobili smo json objekat sada
                        //jsonobject = [{}, {}, .... , {} ]
                        Log.e("Duzina niza: ", "ovo je: " + jsonobject.length());
                        dataBase.deleteUredjaji(); //clear database and insert again
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

                            if (id.equals("1")) {
                                admin_view av = new admin_view(id, ime, R.drawable.temperature, stanje);
                                av.setPrekidac(stanje);
                                dataBase.insertInUredjaji(av);
                                //adapter.addElement(av); // proveriti da li treba u adapter?
                            } else if (id.equals("2")) {
                                admin_view av = new admin_view(id, ime, R.drawable.battery, stanje);
                                av.setPrekidac(stanje);
                                dataBase.insertInUredjaji(av);
                                //adapter.addElement(av);

                            } else if (id.equals("3")) {
                                admin_view av = new admin_view(id, ime, R.drawable.sijalica, stanje);
                                av.setPrekidac(stanje);
                                dataBase.insertInUredjaji(av);
                                //adapter.addElement(av);

                            } else if (id.equals("4")) {
                                admin_view av = new admin_view(id, ime, R.drawable.smoke, stanje);
                                av.setPrekidac(stanje);
                                dataBase.insertInUredjaji(av);
                                //adapter.addElement(av);

                            } else {
                                admin_view av = new admin_view(id, ime, R.drawable.car, stanje);
                                av.setPrekidac(stanje);
                                dataBase.insertInUredjaji(av);
                                //adapter.addElement(av); //notify data change!

                            }
                            ;
                        }
                        /*test refresh adapter data*/

                        LoginActivityAdmin.runOnUI(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    admin_view[] uredjaji = dataBase.readUredjaji();
                                    LoginActivityAdmin.adapter.update(uredjaji);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            mHandler.postDelayed(this, PERIOD);
        }
    }

}
