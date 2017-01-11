package a57121035_0.it.montri.seventh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bosschuse on 1/4/2017 AD.
 */

public class useSQLiteOpenHelper extends SQLiteOpenHelper {

    public useSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql;

        sql = "CREATE TABLE 'products' (_id INTEGER primary key autoincrement,productname TEXT,price DECIMAL ,stock INTEGER);";
        sqLiteDatabase.execSQL(sql);
        //dummy data
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Pen',23.5,4);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Dish',100,560);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Sock',60,70);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Sneaker',500,10);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Baggage',10000,5);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Pencil',10.5,5);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Mirror',10,50);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Chair',800,50);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Sandal',80,20);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Watch',3000,5);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Razor',10,10);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Mouse',888,125);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Flashlight',50,100);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Purse',450,350);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Glass',45,1002);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Lamp',600,30);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Scissors',25.5,100);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Television',15600,7);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Radio',1500,12);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Table',2300,3);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Carpet',10000,2);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Mattresses',25000,5);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Underpants',450,17);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Spoon',20,259);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Briefcase',1450,4);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Towel',200,76);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Handkerchief',50,100);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Sanitary',57,120);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Ribbon',20,300);");
        sqLiteDatabase.execSQL("INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,STOCK) VALUES ('Undershirt',400,100);");

        sql = "CREATE TABLE 'SALES' (_ID INTEGER PRIMARY KEY autoincrement," +
                "P_ID INTEGER," +
                "AMOUNT INTEGER," +
                "S_DATE TEXT," +
                "FOREIGN KEY(P_ID) REFERENCES PRODUCTS(_ID));";
        sqLiteDatabase.execSQL(sql);

        sqLiteDatabase.execSQL("INSERT INTO SALES (P_ID,AMOUNT,S_DATE) VALUES (9,1,'10-01-17');");
        sqLiteDatabase.execSQL("INSERT INTO SALES (P_ID,AMOUNT,S_DATE) VALUES (9,5,'10-01-17');");
        sqLiteDatabase.execSQL("INSERT INTO SALES (P_ID,AMOUNT,S_DATE) VALUES (10,3,'10-01-17');");
        sqLiteDatabase.execSQL("INSERT INTO SALES (P_ID,AMOUNT,S_DATE) VALUES (11,7,'10-01-17');");
        sqLiteDatabase.execSQL("INSERT INTO SALES (P_ID,AMOUNT,S_DATE) VALUES (12,7,'10-01-17');");
        sqLiteDatabase.execSQL("INSERT INTO SALES (P_ID,AMOUNT,S_DATE) VALUES (12,10,'10-01-17');");
        sqLiteDatabase.execSQL("INSERT INTO SALES (P_ID,AMOUNT,S_DATE) VALUES (13,5,'10-01-17');");

        sql = "CREATE VIEW SALES_PRODUCT as " +
                "SELECT P.PRODUCTNAME as PRODUCTNAME , COUNT(S._ID) \"NO_OF_SALES\",SUM(S.AMOUNT)  \"TOTAL\" " +
                "FROM PRODUCTS P,SALES S " +
                "WHERE P._ID = S.P_ID " +
                "GROUP BY P.PRODUCTNAME";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table products");
        onCreate(sqLiteDatabase);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

}
