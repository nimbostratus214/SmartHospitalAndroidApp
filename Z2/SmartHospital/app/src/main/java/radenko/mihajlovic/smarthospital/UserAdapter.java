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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_view, null);
        }

        user_view podaci = (user_view) getItem(position);

        TextView datum = convertView.findViewById(R.id.datum);
        TextView pregled = convertView.findViewById(R.id.pregled);

        datum.setText(podaci.getDatum());
        pregled.setText(podaci.getPregled());

        return convertView;

    }
}
