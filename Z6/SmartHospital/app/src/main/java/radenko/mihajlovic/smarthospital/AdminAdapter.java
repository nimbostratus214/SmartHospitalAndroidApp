package radenko.mihajlovic.smarthospital;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AdminAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<admin_view> adminViewvs;
    private static String HTTP_POST_STATE = "http://192.168.1.167:8080/api/device/"; //url post state + id/state/
    private HttpHelper httpHelper;

    private DBHelper dbHelper;

    public AdminAdapter(Context context) {
        this.context = context;
        adminViewvs = new ArrayList<admin_view>();
    }

    @Override
    public int getCount() {
        return adminViewvs.size();
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0)
        {
            return adminViewvs.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(admin_view[] uredjaji)
    {
        adminViewvs.clear();
        if (uredjaji != null){
            for(admin_view uredjaj : uredjaji){
                adminViewvs.add(uredjaj);
            }
        }
        notifyDataSetChanged();
    }

    public void addElement(admin_view element)
    {
        adminViewvs.add(element);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.admin_view, null);

            ViewHolder vh = new ViewHolder(); //definisana klasa ispod..

            vh.t1 = convertView.findViewById(R.id.neaktivan);
            vh.naziv = convertView.findViewById(R.id.naziv_uredjaja);
            vh.slika = convertView.findViewById(R.id.slika_uredjaja);
            vh.uredjaj = convertView.findViewById(R.id.dugme_uredjaja);
            //vh.uredjaj.setTag(position);
            vh.t1.setTag(position);
            convertView.setTag(vh);
        }

        admin_view av = (admin_view) getItem(position); //okej, ovde je dobavljen uredjaj na koji je kliknuo!
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.naziv.setText(av.getNaziv());
        vh.slika.setImageResource(av.getSlika());
        //vh.uredjaj.setChecked(av.isPrekidac());
        //vh.slika.setImageDrawable(av.getSlika());
        //vh.uredjaj.setChecked(av.isPrekidac());

        vh.uredjaj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String id_uredjaja = av.getID(); //dobavljen id uredjaja, treba nam da bi uputili http zahtev
                httpHelper = new HttpHelper();
                if(isChecked) {
                    //na promenu ovog dugmeta treba valjda dodati da se promena
                    //odigra i na serveru a samim tim i u internoj sql bazi

                    vh.t1.setVisibility(View.INVISIBLE);
                    //av.setPrekidac(isChecked);
                    vh.uredjaj.setChecked(isChecked);
                    av.setPrekidac(isChecked);
                    Log.d("Checked? : ", String.valueOf(isChecked));

                    vh.uredjaj.setTag(position);
                } else {
                    vh.t1.setVisibility(View.VISIBLE);
                    //av.setPrekidac(isChecked);
                    //vh.uredjaj.setChecked(isChecked);
                    av.setPrekidac(isChecked);
                    Log.d("Checked? : ", String.valueOf(isChecked));
                    vh.uredjaj.setTag(position);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String state = null;
                            if(isChecked){
                                state = "on";
                            } else {
                                state = "off";
                            }
                            String url = HTTP_POST_STATE + String.valueOf(id_uredjaja) + "/" + state ;
                            Log.d("Zahtev POST_STATE: ", url);
                            JSONObject json = new JSONObject();
                            try{
                                json.put("message", "10");
                                json.put("code",  "10");

                            } catch (JSONException e) {
                                //TO DO obraditi izuzetak
                                e.printStackTrace();
                            }

                            JSONObject check = httpHelper.POSTJSONObjectFromURL(url);
                            Log.d("Zahtev Post_state uspeo?: ", check.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        return convertView;
    }

    public class ViewHolder{
        public TextView naziv = null;
        public ImageView slika = null;
        public Switch uredjaj = null;
        public TextView t1;
    }
}
