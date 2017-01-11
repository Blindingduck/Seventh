package a57121035_0.it.montri.seventh;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaleActivity extends AppCompatActivity {
    TextView tv_Stock;
    EditText edt_Amount;
    Spinner sp_Product;
    Button btn_Save,btn_Reset;
    Uri uri_Product = Uri.parse("content://myDB/PRODUCTS");
    Uri uri_Sale = Uri.parse("content://myDB/SALES");
    String[] productname_data;
    int[] idValues;
    int[] stockValues;
    ArrayAdapter<String> spinnerAdapter;
    int id;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        tv_Stock = (TextView) findViewById(R.id.tvStock);
        edt_Amount = (EditText) findViewById(R.id.edtAmount);
        sp_Product = (Spinner) findViewById(R.id.spProduct);
        btn_Save = (Button) findViewById(R.id.btnSave);
        btn_Reset = (Button) findViewById(R.id.btnReset);
        setStartscreen();
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(edt_Amount.getText().toString().equals(""))
                        throw new NullPointerException();
                    else {
                    int stock = Integer.parseInt(tv_Stock.getText().toString());
                    int amount = Integer.parseInt(edt_Amount.getText().toString());
                    if (amount <= stock) {
                        //update amout
                        int check = UpdataData(id + "", (stock - amount));
                        if (check >= 1) {
                            InsertSaleData(amount);
                            Toast.makeText(SaleActivity.this, "Update Stock", Toast.LENGTH_SHORT).show();
                            tv_Stock.setText(""+(stock-amount));
                        } else {
                            Toast.makeText(SaleActivity.this, "Update Fail", Toast.LENGTH_SHORT).show();
                        }
                        //insert sale
                    } else {
                        AlertDialog Err = new AlertDialog.Builder(SaleActivity.this)
                                .setTitle("Error !")
                                .setMessage("Out of Stock")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();
                    }
                    }
                }
                catch (NullPointerException e)
                {
                    AlertDialog Err = new AlertDialog.Builder(SaleActivity.this)
                            .setTitle("Error !")
                            .setMessage("Please Input Amount")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            }
        });
        sp_Product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id = getid(i);
                int stock = getStock(i);
                tv_Stock.setText(String.valueOf(stock));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void setStartscreen(){
        cursor = SaleActivity.this.getContentResolver().query(uri_Product,null,null,null,"PRODUCTNAME");
        int i =0;
        productname_data = new String[cursor.getCount()];
        idValues = new int[cursor.getCount()];
        stockValues = new int[cursor.getCount()];
        while(cursor.moveToNext()){
            idValues[i] = cursor.getInt(0);
            productname_data[i] = cursor.getString(1);
            stockValues[i] = cursor.getInt(3);
            i++;
        }
        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,productname_data);
        sp_Product.setAdapter(spinnerAdapter);
    }
    private int getStock(int x){
        int res = 0;
        res = stockValues[x];
        return res;
    }
    private int getid(int x){
        int res = 0;
        res = idValues[x];
        return res;
    }
    private int UpdataData(String id , int Stock)
    {
        String where = "_id = "+id;
        ContentValues values = new ContentValues();
        values.put("STOCK",Stock);
        return getBaseContext().getContentResolver().update(uri_Product,values,where,null);
    }
    private void InsertSaleData(int amount){
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy");
        ContentValues insertValue = new ContentValues();
        insertValue.put("P_ID",id+"");
        insertValue.put("AMOUNT",amount);
        insertValue.put("S_DATE",df.format(d));
        SaleActivity.this.getContentResolver().insert(uri_Sale,insertValue);

    }
}
