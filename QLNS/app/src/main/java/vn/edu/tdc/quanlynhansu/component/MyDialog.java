package vn.edu.tdc.quanlynhansu.component;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.edu.tdc.quanlynhansu.R;

public class MyDialog {
    private Activity context;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView lblMess;

    public MyDialog(Activity context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        ViewGroup viewgroup = context.findViewById(android.R.id.content);
        View dialogView = context.getLayoutInflater().inflate(R.layout.dialog_layout,viewgroup, false);
        lblMess = dialogView.findViewById(R.id.lblMess);
        Button btnok = dialogView.findViewById(R.id.btnOK);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        builder.setView(dialogView);
        dialog = builder.create();

    }
    public void show(){
        dialog.show();
    }
    public void dismiss(){

    }
    public void setMessage(String meesage){
        lblMess.setText(meesage);
    }


}
