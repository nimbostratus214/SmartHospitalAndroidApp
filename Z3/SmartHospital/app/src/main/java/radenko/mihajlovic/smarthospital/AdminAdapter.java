package radenko.mihajlovic.smarthospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<admin_view> adminViewvs;

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

        admin_view av = (admin_view) getItem(position);
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.naziv.setText(av.getNaziv());
        vh.slika.setImageResource(av.getSlika());
        //vh.uredjaj.setChecked(av.isPrekidac());
        //vh.slika.setImageDrawable(av.getSlika());
        //vh.uredjaj.setChecked(av.isPrekidac());

        vh.uredjaj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    vh.t1.setVisibility(View.INVISIBLE);
                    //av.setPrekidac(isChecked);
                    //vh.uredjaj.setChecked(isChecked);
                    //av.setPrekidac(isChecked);
                    vh.uredjaj.setTag(position);
                } else {
                    vh.t1.setVisibility(View.VISIBLE);
                    //av.setPrekidac(isChecked);
                    //vh.uredjaj.setChecked(isChecked);
                    //av.setPrekidac(isChecked);
                    vh.uredjaj.setTag(position);

                }
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
