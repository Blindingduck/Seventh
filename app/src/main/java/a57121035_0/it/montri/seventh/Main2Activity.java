package a57121035_0.it.montri.seventh;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Spinner sp_Proname,sp_Price,sp_Condition,sp_Sort,sp_Type;
    EditText edt_Proname,edt_Pricebegin,edt_Priceend,edtdialog_Proname,edtdialog_Price,edtdialog_Stock;
    Button btn_Search,btndialog_Edit,btndialog_Delete,btndialog_Close;
    Cursor myCursor;
    TextView tv_Text,tv_To,tv1,tv2,tv3,tv4,tv5,tvdialog_ID;
    Uri myUri;
    ListView lv_Display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv1 = (TextView) findViewById(R.id.text1);
        tv2 = (TextView) findViewById(R.id.text2);
        tv3 = (TextView) findViewById(R.id.text3);
        tv4 = (TextView) findViewById(R.id.text4);
        tv5 = (TextView) findViewById(R.id.text5);
        tv_Text = (TextView) findViewById(R.id.tvText);
        tv_To = (TextView) findViewById(R.id.tvTo);
        sp_Proname = (Spinner) findViewById(R.id.spProname);
        sp_Price = (Spinner) findViewById(R.id.spPrice);
        sp_Condition = (Spinner) findViewById(R.id.spCondition);
        sp_Sort = (Spinner) findViewById(R.id.spSort);
        sp_Type = (Spinner) findViewById(R.id.spType);
        edt_Proname  = (EditText) findViewById(R.id.edtProname);
        edt_Pricebegin = (EditText) findViewById(R.id.edtPricebegin);
        edt_Priceend = (EditText) findViewById(R.id.edtPriceend);
        btn_Search = (Button) findViewById(R.id.btnSearch);
        lv_Display = (ListView) findViewById(R.id.lvdisplay);
        myUri = Uri.parse("content://myDB//PRODUCTS");
        edt_Priceend.setVisibility(View.INVISIBLE);
        tv_To.setVisibility(View.INVISIBLE);
        tv_Text.setText(null);

        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        tv3.setVisibility(View.INVISIBLE);
        tv4.setVisibility(View.INVISIBLE);
        tv5.setVisibility(View.INVISIBLE);

        sp_Price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sp_Price.getSelectedItemPosition() == 0)
                {
                    edt_Priceend.setText("");
                    edt_Priceend.setVisibility(View.VISIBLE);
                    tv_To.setVisibility(View.VISIBLE);
                }
                else
                {
                    edt_Priceend.setVisibility(View.INVISIBLE);
                    tv_To.setVisibility(View.INVISIBLE);
                    edt_Priceend.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lv_Display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final HashMap<String,String> Item = (HashMap)adapterView.getItemAtPosition(i);
                final Dialog selectData = new Dialog(Main2Activity.this);
                selectData.setContentView(R.layout.selectdata_layout);
                selectData.show();
                tvdialog_ID = (TextView) selectData.findViewById(R.id.tvdialogID);
                edtdialog_Proname = (EditText) selectData.findViewById(R.id.edtdialogProname);
                edtdialog_Price = (EditText) selectData.findViewById(R.id.edtdialogPrice);
                edtdialog_Stock = (EditText) selectData.findViewById(R.id.edtdialogStock);
                btndialog_Edit = (Button) selectData.findViewById(R.id.btndialogEdit);
                btndialog_Delete = (Button) selectData.findViewById(R.id.btndialogDelete);
                btndialog_Close = (Button) selectData.findViewById(R.id.btndialogClose);

                tvdialog_ID.setText(Item.get("ID"));
                edtdialog_Price.setText(Item.get("Price"));
                edtdialog_Proname.setText(Item.get("ProductName"));
                edtdialog_Stock.setText(Item.get("Stock"));

                btndialog_Edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check = UpdataData(tvdialog_ID.getText().toString(),edtdialog_Proname.getText().toString(),edtdialog_Price.getText().toString(),edtdialog_Stock.getText().toString());
                        if(check >= 1)
                        {
                            selectData.dismiss();
                            lv_Display.setAdapter(null);
                            Toast.makeText(Main2Activity.this,"Update "+check+" Item",Toast.LENGTH_SHORT).show();
                            btn_Search.callOnClick();

                        }
                        else
                        {
                            Toast.makeText(Main2Activity.this,"Update Fail",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btndialog_Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check = DeleteData(tvdialog_ID.getText().toString());
                        if(check >= 1)
                        {
                            selectData.dismiss();
                            lv_Display.setAdapter(null);
                            Toast.makeText(Main2Activity.this,"Delete "+check+" Item",Toast.LENGTH_SHORT).show();
                            btn_Search.callOnClick();

                        }
                        else
                        {
                            Toast.makeText(Main2Activity.this,"Delete Fail",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btndialog_Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectData.dismiss();
                    }
                });
            }
        });
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Proname,Price,Condition,Sort,Type;
                double price_Begin = 0,price_End = 0;
                try
                {
                    if(edt_Pricebegin.getText().toString().equals("") || edt_Priceend.getText().toString().equals("")||edt_Proname.getText().toString().equals(""))
                    {
                        throw new NullPointerException();
                    }
                    else
                    {
                        price_Begin = Double.parseDouble(edt_Pricebegin.getText().toString());
                        price_End = Double.parseDouble(edt_Priceend.getText().toString());
                        Proname = ProductName(sp_Proname.getSelectedItemPosition(), edt_Proname.getText().toString());
                        Price = Price(sp_Price.getSelectedItemPosition(), price_Begin, price_End);
                        Condition = Condition(sp_Condition.getSelectedItemPosition());
                        Sort = Sort(sp_Sort.getSelectedItemPosition());
                        Type = Type(sp_Type.getSelectedItemPosition());
                        myCursor = Main2Activity.this.getContentResolver().query(myUri, null, Proname + Condition + Price, null, Sort + Type);
                    }
                    showData();
                }
                catch (NullPointerException e)
                {
                    Arr_Error("Please input all data");
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });

    }
    private String ProductName(int position,String prName){
        String text = "productname ";
        switch (position){
            case 0 : text += "= \'"+prName+"\' ";
                break;
            case 1 : text += "LIKE \'%"+prName+"\' ";
                break;
            case 2 : text += "LIKE \'"+prName+"%\'";
                break;
            case 3 : text += "LIKE \' %"+prName+"%\'";
                break;
            case 4 : text += "NOT LIKE \' %"+prName+"%\'";
                break;
        }
        return text;
    }
    private String Price(int position,double pricebegin,double priceend){
        String text = "price ";
        switch (position) {
            case 0:
                text += "BETWEEN " + pricebegin + " AND " + priceend + " ";
                break;
            case 1:
                text += "= " + pricebegin + " ";
                break;
            case 2:
                text += "> " + pricebegin + " ";
                break;
            case 3:
                text += ">= " + pricebegin + " ";
                break;
            case 4:
                text += "< " + pricebegin + " ";
                break;
            case 5:
                text += "<=" + pricebegin + " ";
                break;
            case 6:
                text += "!=" + pricebegin + " ";
                break;
        }
        return text;
    }
    private String Condition(int position){
        String text = "";
        switch (position)
        {
            case 0 : text = "AND ";
                break;
            case 1  : text = "OR ";
                break;
        }
        return text;
    }
    private String Sort(int position){
        String text=  "";
        switch (position)
        {
            case 0 : text = "_id ";
                break;
            case 1: text = "productname ";
                break;
            case 2: text = "price ";
                break;
            case 3: text = "stock ";
                break;
        }
        return text;
    }
    private String Type(int position){
        String text = "";
        switch (position)
        {
            case 0 : text = "asc ";
                break;
            case 1 : text = "desc ";
                break;
        }
        return  text;
    }
    public void Arr_Error(String text) {
        AlertDialog Err = new AlertDialog.Builder(Main2Activity.this)
                .setMessage(text)
                .setTitle("ERROR !")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    private void showData(){
        SimpleAdapter simpleAdapter = null;
        List<HashMap<String, String>> fill_data = new ArrayList<HashMap<String, String>>();
        int i =0;
        while (myCursor.moveToNext()){
            HashMap<String, String> myMap = new HashMap<String, String>();
            myMap.put("No","" + (i+1));
            myMap.put("ID", myCursor.getString(0));
            myMap.put("ProductName", myCursor.getString(1));
            myMap.put("Price", myCursor.getString(2));
            myMap.put("Stock", myCursor.getString(3));
            fill_data.add(myMap);
            i++;
        }
        tv_Text.setText("พบข้อมูลทั้งหมด "+myCursor.getCount()+" รายการ");
        String[] from = new String[]{"No", "ID","ProductName","Price","Stock"};
        int[] to = new int[]{R.id.tvNo, R.id.tvID,R.id.tvProname,R.id.tvPrice,R.id.tvStock};
        simpleAdapter = new SimpleAdapter(Main2Activity.this, fill_data, R.layout.listview_layout, from, to);
        if(myCursor.getCount() > 0){
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
        tv3.setVisibility(View.VISIBLE);
        tv4.setVisibility(View.VISIBLE);
        tv5.setVisibility(View.VISIBLE);
        lv_Display.setAdapter(simpleAdapter);
        }
    }
    private int DeleteData(String id){
        String whereClause = "_id = "+id;
        return  getBaseContext().getContentResolver().delete(myUri,whereClause,null);
    }
    private int UpdataData(String id , String Productname , String Price , String Stock) {
        String where = "_id = "+id;
        ContentValues values = new ContentValues();
        values.put("PRODUCTNAME",Productname);
        values.put("PRICE",Price);
        values.put("STOCK",Stock);
        return getBaseContext().getContentResolver().update(myUri,values,where,null);
    }

}