package a57121035_0.it.montri.seventh;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Bosschuse on 1/4/2017 AD.
 */

public class useContentProvider extends ContentProvider {
    useSQLiteOpenHelper myDB;
    SQLiteDatabase db;

    static UriMatcher matcher; {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI("myDB","PRODUCTS",1);
        matcher.addURI("myDB","SALES",2);
        matcher.addURI("myDB","SALES_PRODUCT",3);
    }

    long id =0;
    @Override
    public boolean onCreate() {
        myDB = new useSQLiteOpenHelper(getContext(),"MontriDatabase.db",null,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //SELECT (projection)
        // from (URI)
        // where (selection || selectionArgs)
        // order by (sortOrder)
        Cursor myCursor = null;
        db = myDB.getReadableDatabase();
        String table = matchUriAll(matcher.match(uri));
        myCursor = db.query(table,projection,selection,selectionArgs,null,null,sortOrder);
        return myCursor;
    }
    private String matchUriAll(int matcher_Uri){
        String text = "";
        switch (matcher_Uri){
            case 1 :text = "PRODUCTS";
                break;
            case 2 : text = "SALES";
                break;
            case 3 : text = "SALES_PRODUCT";
                break;
        }
        return text;
    }
    private String matchUritable(int matcher_Uri){
        String text = "";
        switch (matcher_Uri){
            case 1 :text = "PRODUCTS";
                break;
            case 2 : text = "SALES";
                break;
        }
        return text;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        //INSERT INTO (URI)
        //VALUES (contentValues)
        String table = matchUritable(matcher.match(uri));
        Uri myUri = null;
        db = myDB.getWritableDatabase();
        id = db.insert(table,null,contentValues);
        myUri = ContentUris.withAppendedId(uri,id);
        return myUri;
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        String table = matchUritable(matcher.match(uri));
        db = myDB.getWritableDatabase();
        int delete = db.delete(table,whereClause,whereArgs);
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String whereClause, String[] whereArgs) {
        String table = matchUritable(matcher.match(uri));
        db = myDB.getWritableDatabase();
        int update = db.update(table,contentValues,whereClause,whereArgs);
        return update;
    }
}
