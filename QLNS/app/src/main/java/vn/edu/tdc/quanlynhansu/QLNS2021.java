package vn.edu.tdc.quanlynhansu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import vn.edu.tdc.quanlynhansu.adapters.MyListViewAdapter;
import vn.edu.tdc.quanlynhansu.adapters.MyRecyclerViewAdapter;
import vn.edu.tdc.quanlynhansu.component.MyDialog;
import vn.edu.tdc.quanlynhansu.database_layer.PersonDAO;
import vn.edu.tdc.quanlynhansu.database_layer.PersonDatabase;
import vn.edu.tdc.quanlynhansu.database_layer.PersonDatabaseAPIs;

public class QLNS2021 extends AppCompatActivity {
    public static String trungcap = "TRUNG CAP";
    public static String caodang = "CAO DANG";
    public static String daihoc = "DAI HOC";
    public static String khongbangcap = "KHONG BANG CAP";

    private int selectedGrow = -1;// cờ để xem đang chọn view nào
    private int backcolor;// biến lưu màu cũ để khi ko chọn nữa thì trả về màu cũ
//    private LinearLayout priviousItem;
    private CardView priviousItem;

    private EditText hoten, sothichkhac;
    private CheckBox check1, check2;
    private RadioGroup radgroup;
    private RadioButton radDH, radCD, radTC;
    private Button btnthem;

    private ArrayList<Person> persons = new ArrayList<Person>();
    //private ArrayAdapter<Person> adapter;
    //private MyListViewAdapter adapter;
    private MyRecyclerViewAdapter adapter;
    private PersonDatabaseAPIs DAO;
    //private Person person;

    /*MyDialog dialog = new MyDialog(this);
    dialog.setMessage("hello");
    dialog.show();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_layout);

        Button btnthoat = findViewById(R.id.btnthoat);
        btnthem = findViewById(R.id.btnthem);
        hoten = findViewById(R.id.edthoten);
        sothichkhac = findViewById(R.id.edtsothichkhac);
        radgroup = findViewById(R.id.radgroup);
        radDH = findViewById(R.id.raddaihoc);
        radCD = findViewById(R.id.radcaodang);
        radTC = findViewById(R.id.radtrungcap);
        check1 = findViewById(R.id.checkdocsach);
        check2 = findViewById(R.id.checkdulich);
       // ListView list = findViewById(R.id.list);
        RecyclerView list = findViewById(R.id.list);

//        adapter = new MyListViewAdapter(this, R.layout.customizelistview, persons);
        adapter = new MyRecyclerViewAdapter(this, R.layout.customizelistview, R.layout.customizelistview_invert, persons);
        ///Adapter Layout manager
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        list.setLayoutManager(layoutManager);
        //Set Adapter
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemCLick(int position, View view) {
                if (selectedGrow == -1) {
                    selectedGrow = position;
                    disablebtnthem();
                    setPersonToLayout(persons.get(position));
                    CardView brItem = findViewById(R.id.brlistview);
                    backcolor = brItem.getSolidColor();
                    brItem.setBackgroundColor(getResources().getColor(R.color.btnColor, getTheme()));
                    priviousItem = brItem;
                } else {
                    if (selectedGrow == position) {
                        clear();
                        selectedGrow = -1;
                        enablebtnthem();
                        CardView brItem = findViewById(R.id.brlistview);
                        brItem.setBackgroundColor(backcolor);
                    } else {
                        priviousItem.setBackgroundColor(backcolor);// cái trước đó đưa về màu cũ
                        selectedGrow = position;
                        disablebtnthem();
                        setPersonToLayout(persons.get((int) selectedGrow));
                        CardView brItem = findViewById(R.id.brlistview);
                        backcolor = brItem.getSolidColor();
                        brItem.setBackgroundColor(getResources().getColor(R.color.btnColor, getTheme()));
                        priviousItem = brItem;
                    }

                }
            }
        });

       /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Đổi màu cho item
                if (selectedGrow == -1) {
                    selectedGrow = i;
                    disablebtnthem();
                    setPersonToLayout(persons.get(i));
                    LinearLayout brItem = findViewById(R.id.brlistview);
                    backcolor = brItem.getSolidColor();
                    brItem.setBackgroundColor(getResources().getColor(R.color.btnColor, getTheme()));
                    priviousItem = brItem;
                } else {
                    if (selectedGrow == l) {
                        clear();
                        selectedGrow = -1;
                        enablebtnthem();
                        LinearLayout brItem = findViewById(R.id.brlistview);
                        brItem.setBackgroundColor(backcolor);
                    } else {
                        priviousItem.setBackgroundColor(backcolor);// cái trước đó đưa về màu cũ
                        selectedGrow = i;
                        disablebtnthem();
                        setPersonToLayout(persons.get((int) selectedGrow));
                        LinearLayout brItem = findViewById(R.id.brlistview);
                        backcolor = brItem.getSolidColor();
                        brItem.setBackgroundColor(getResources().getColor(R.color.btnColor, getTheme()));
                        priviousItem = brItem;
                    }

                }
            }
        });

        */


        //Load database
        DAO = new PersonDatabaseAPIs(this);
        DAO.getAlPerson(persons, adapter);
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedGrow == -1) {
                    Person nhanSu = createPerfromLayout();
                    if (nhanSu != null) {
                        //Thêm vào database
                        //DAO.savePerson(nhanSu);
                        //thêm vào list view
                        persons.add(nhanSu);
                        adapter.notifyDataSetChanged();
                        clear();
                    }
                }
            }
        });
    }
    private void disablebtnthem(){
        btnthem.setEnabled(false );
    }
    private void enablebtnthem(){
        btnthem.setEnabled(true );
    }
    private Person createPerfromLayout() {
        Person nhanSu = null;
        if (!hoten.getText().toString().isEmpty()) {
            nhanSu = new Person();
            nhanSu.setHoTen(hoten.getText().toString());
            int id = radgroup.getCheckedRadioButtonId();
            switch (id) {
                case R.id.raddaihoc:
                    nhanSu.setBangCap(daihoc);
                    break;
                case R.id.radcaodang:
                    nhanSu.setBangCap(caodang);
                    break;
                case R.id.radtrungcap:
                    nhanSu.setBangCap(trungcap);
                    break;
                default:
                    nhanSu.setBangCap(khongbangcap);
                    break;
            }
            String hoppies = "";
            if (check1.isChecked()) {
                hoppies += check1.getText() + ";";
            }
            if (check2.isChecked()) {
                hoppies += check2.getText() + ";";
            }
            hoppies += sothichkhac.getText();
            nhanSu.setSoThich(hoppies);
        }
        return nhanSu;
    }

    private void setPersonToLayout(Person person) {
        //TODO
        clear();
        hoten.setText(person.getHoTen());
        if (person.getBangCap().equalsIgnoreCase(radDH.getText().toString())) {
            radDH.setChecked(true);
        } else if (person.getBangCap().equalsIgnoreCase(radCD.getText().toString())) {
            radCD.setChecked(true);
        } else if (person.getBangCap().equalsIgnoreCase(radTC.getText().toString())) {
            radTC.setChecked(true);
        }
        StringTokenizer tokenizer = new StringTokenizer(person.getSoThich());
        String other = "";
        if (person.getSoThich().contains(check1.getText().toString())) {
            check1.setChecked(true);
            tokenizer.nextToken(";");
        }
        if (person.getSoThich().contains(check2.getText().toString())) {
            check2.setChecked(true);
            tokenizer.nextToken(";");
        }
        //String[] sothich = person.getSoThich().split(";");
        if (tokenizer.hasMoreElements()) {
            other = tokenizer.nextToken(";");
            sothichkhac.setText(other);
        }
    }

    private void clear() {
        //TODO clear
        hoten.setText("");
        radgroup.clearCheck();
        check1.setChecked(false);
        check2.setChecked(false);
        sothichkhac.setText("");
        hoten.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        return true;
    }
    //xử lý hàm menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                save();
                return true;
            case R.id.menu_update:
                return true;
            case R.id.menu_deltete:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {
        //TODO
        DAO.saveAllPersonVersion2(persons);
    }

    private void updatePerson() {
        //TODO
        if(selectedGrow != -1) {
            Person person = createPerfromLayout();
            if(person != null){
                persons.get(selectedGrow).setHoTen(person.getHoTen());
                persons.get(selectedGrow).setBangCap(person.getBangCap());
                persons.get(selectedGrow).setSoThich(person.getSoThich());
                if(persons.get(selectedGrow).getId() != 0){
                    DAO.update(persons.get(selectedGrow));
                }
                adapter.notifyDataSetChanged();
                clear();
                selectedGrow = -1;
                enablebtnthem();
            }
        }
    }

    private void delete() {
        //TODO
        if(selectedGrow != -1){
            DAO.delPerson(persons.get((int)selectedGrow));
            clear();
            persons.remove(selectedGrow);
            adapter.notifyDataSetChanged();
            selectedGrow = -1;
            enablebtnthem();
        }

    }
}