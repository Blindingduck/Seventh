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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edt_Productname,edt_Price,edt_Stock;
    Button btn_Save,btn_Search,btn_Practice,btn_View,btn_Sale;
    Uri myUri;
    ContentValues insertValue = null;
    String result= "";
    Cursor myCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<HashMap<String,String>> myList = new ArrayList<>();
        edt_Productname = (EditText) findViewById(R.id.edtProductName);
        edt_Price = (EditText) findViewById(R.id.edtPrice);
        edt_Stock = (EditText) findViewById(R.id.edtStock);
        btn_Sale = (Button) findViewById(R.id.btnSale);
        btn_View = (Button) findViewById(R.id.btnView);
        btn_Save = (Button) findViewById(R.id.btnSave);
        btn_Search = (Button) findViewById(R.id.btnSearch);
        btn_Practice = (Button) findViewById(R.id.btnPractice);
        myUri = Uri.parse("content://myDB/PRODUCTS");

        btn_Sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sale = new Intent(MainActivity.this,SaleActivity.class);
                startActivity(sale);
            }
        });
        btn_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri myUr = Uri.parse("content://myDB/SALES_PRODUCT");
                //Log.e("asdasdk",myUri.toString());
                myCursor = MainActivity.this.getContentResolver().query(myUr,null,null,null,null);
                result = "Found Data : "+myCursor.getCount() +"list \n\n";
                while (myCursor.moveToNext()){
                    result += myCursor.getString(0) +" | "+ myCursor.getString(1) + " | " +myCursor.getString(2) +" | "+"\n";
                }
                Arr_information(result);
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkdata()){
                    insertValue = new ContentValues();
                    insertValue.put("productname",edt_Productname.getText().toString());
                    insertValue.put("price",edt_Price.getText().toString());
                    insertValue.put("stock",edt_Stock.getText().toString());
                    MainActivity.this.getContentResolver().insert(myUri,insertValue);
                    ClearEdt();
                    Arr_information("Save Success ! !");
                }
            }
        });
        btn_Practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(myIntent);
            }
        });
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] a = {"_id","productname","stock"};
                myCursor = MainActivity.this.getContentResolver().query(myUri,a,"price > 1000 and stock < 20",null,"stock desc , price desc");
                result = "Found Data : "+myCursor.getCount() +"list \n\n";
                while (myCursor.moveToNext()){
                    result += myCursor.getString(0) +" | "+ myCursor.getString(1) + " | " +myCursor.getString(2) +" | "+"\n";
                }
                Arr_information(result);
            }
        });
    }
    private void ClearEdt(){
        edt_Stock.setText(null);
        edt_Price.setText(null);
        edt_Productname.setText(null);
    }
    public void Arr_information(String text) {
        AlertDialog info = new AlertDialog.Builder(MainActivity.this)
                .setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    public boolean checkdata(){
        boolean ans = false;
        if(edt_Productname.getText().toString().equals("")|| edt_Price.getText().toString().equals("") || edt_Stock.getText().toString().equals("")){
            Arr_information("Please Input data");
        }
        else if(Integer.parseInt(edt_Stock.getText().toString()) < 0 )
        {
            Arr_information("Stock must grater than 0");
        }
        else if(Float.parseFloat(edt_Price.getText().toString()) < 0.00)
        {
            Arr_information("Price must grater than 0.00");
        }
        else
            ans =true;
        return ans;
    }
}
