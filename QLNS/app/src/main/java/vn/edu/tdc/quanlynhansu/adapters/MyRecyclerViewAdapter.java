package vn.edu.tdc.quanlynhansu.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.tdc.quanlynhansu.Person;
import vn.edu.tdc.quanlynhansu.QLNS2021;
import vn.edu.tdc.quanlynhansu.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Activity context;
    private int layoutID;
    private int layoutID_IVERT;
    private ArrayList<Person> people;

    // tao ra contructor code -> generate -> chon 3 cai
    public MyRecyclerViewAdapter(Activity context, int layoutID,int layoutID_IVERT, ArrayList<Person> people) {
        this.context = context;
        this.layoutID = layoutID;
        this.layoutID_IVERT = layoutID_IVERT;
        this.people = people;
    }

    // Tao khung
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBangCap;
        TextView txtHoTen;
        TextView txtSoThich;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBangCap = itemView.findViewById(R.id.imgbangcap);
            txtHoTen = itemView.findViewById(R.id.lblhoten);
            txtSoThich = itemView.findViewById(R.id.lblsothich);

        }
    }

//    tao ra phan tu moi
    @NonNull
    @Override
//    viewType phân biệt layout
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        CardView view = (CardView)inflater.inflate(viewType, viewGroup,false);

        return new MyViewHolder(view);
    }

    //đổ dữ liệu
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        Person person = people.get(position);

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

    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    //Dựa vào vị trí lấy layout vd: Nam hiển thị layout khác, nữ khác
    @Override
    public int getItemViewType(int position) {
        if(position % 2 == 0){
            return layoutID;
        }else {
            return layoutID_IVERT;
        }
    }
}
