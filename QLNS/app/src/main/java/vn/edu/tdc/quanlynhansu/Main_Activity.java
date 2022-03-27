package vn.edu.tdc.quanlynhansu;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Main_Activity extends AppCompatActivity {
    public static  String trungcap = "TRUNG CAP";
    public static String caodang = "CAO DANG";
    public static String daihoc = "DAI HOC";
    public static String khongbangcap = "KHONG BANG CAP";

    private ArrayList<Person> persons = new ArrayList<Person> ();
    private ArrayAdapter<Person> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlynhansu_constrainlayout);
    }
}