package radenko.mihajlovic.smarthospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<user_view> userviews;

    public UserAdapter(Context context) {
        this.context = context;
        userviews = new ArrayList<user_view>();
    }

    public void addElement(user_view element)
    {
        userviews.add(element);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userviews.size();
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0) {
            return userviews.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //zapravo bi trebala biti lista pregleda
    //ovo nije dobro, jer dodaje jedan element liste gde su svi pregledi
    //treba napraviti listu pregleda koja se dodaje ovde
    //dakle mozda je greska i u prvom koraku jer smo u tabeli korisnici dodali kolonu pregledi
    //koja nije u formi liste nego je jedan string sa datuimima i pregledima
    //pokusati splitovati taj string u nazad i kreirati listu u ovom adapteru i loginActivity-u
    public void update(user_view[] korisnici){
        userviews.clear();
        if (korisnici != null){
            for(user_view korisnik : korisnici){
            userviews.add(korisnik);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_view, null);

            ViewHolder holder = new ViewHolder();
            holder.datum = convertView.findViewById(R.id.datum);
            holder.pregled = convertView.findViewById(R.id.pregled);

            convertView.setTag(holder);
        }
            user_view korisnik = (user_view) getItem(position);
            ViewHolder holder = (ViewHolder) convertView.getTag();

            holder.datum.setText(korisnik.getDatum()); //iscitacemo iz baze !
            holder.pregled.setText(korisnik.getPregled());

            return convertView;
//izmenio da bude preko ViewHoldera dodavanje jednog elementa liste!
        /*user_view podaci = (user_view) getItem(position);

        TextView datum = convertView.findViewById(R.id.datum);
        TextView pregled = convertView.findViewById(R.id.pregled);

        datum.setText(podaci.getDatum());
        pregled.setText(podaci.getPregled());

        return convertView;
        */
    }

    private class ViewHolder {
        public TextView datum = null;
        public TextView pregled = null;
    }
}
