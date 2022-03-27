package vn.edu.tdc.quanlynhansu;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static vn.edu.tdc.quanlynhansu.Person.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Person {
    public static final String TABLE_NAME = "person_table" ;
    public static final String NAME = "name" ;
    public static final String BANG_CAP = "degree" ;
    public static final String SO_THICH = "so thich" ;


    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = NAME)
    private String hoTen;
    @ColumnInfo (name = BANG_CAP)
    private String bangCap;
    @ColumnInfo (name = SO_THICH)
    private String soThich;


    //Setter and getter
    public Person() {
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getBangCap() {
        return bangCap;
    }

    public void setBangCap(String bangCap) {
        this.bangCap = bangCap;
    }

    public String getSoThich() {
        return soThich;
    }

    public void setSoThich(String soThich) {
        this.soThich = soThich;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return this.hoTen + "#" + this.bangCap + "#" + this.soThich;
    }
}
