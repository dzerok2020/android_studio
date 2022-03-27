package vn.edu.tdc.quanlynhansu.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import vn.edu.tdc.quanlynhansu.Person;
import vn.edu.tdc.quanlynhansu.QLNS2021;
import vn.edu.tdc.quanlynhansu.R;

public class MyListViewAdapter extends ArrayAdapter<Person> {
    private Activity activity;
    private int layoutID;
    private ArrayList<Person> persons;

    public MyListViewAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Person> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.layoutID = resource;
        this.persons = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        Person person = persons.get(position);

        if (convertView == null) {
            view = activity.getLayoutInflater().inflate(layoutID, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imgBangCap = view.findViewById(R.id.imgbangcap);
            viewHolder.txtHoTen = view.findViewById(R.id.lblhoten);
            viewHolder.txtSoThich = view.findViewById(R.id.lblsothich);

            //building the viewholder to the neư listview item
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.txtHoTen.setText(person.getHoTen());
        viewHolder.txtSoThich.setText(person.getSoThich());

        if (person.getBangCap().equalsIgnoreCase(QLNS2021.daihoc)) {
            viewHolder.imgBangCap.setImageResource(R.mipmap.university);
        } else if (person.getBangCap().equalsIgnoreCase(QLNS2021.caodang)) {
            viewHolder.imgBangCap.setImageResource(R.mipmap.college);
        } else if (person.getBangCap().equalsIgnoreCase(QLNS2021.trungcap)) {
            viewHolder.imgBangCap.setImageResource(R.mipmap.midium);
        } else {
            viewHolder.imgBangCap.setImageResource(R.mipmap.none);
        }

        return view;

    }

    static class ViewHolder {
        ImageView imgBangCap;
        TextView txtHoTen;
        TextView txtSoThich;

    }

}
