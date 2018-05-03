package br.ufpe.cin.if1001.rss.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class RssProvider extends ContentProvider {

    private SQLiteRSSHelper db;
    public RssProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return db.getWritableDatabase().delete(SQLiteRSSHelper.DATABASE_TABLE,selection,selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        return uri.toString();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Long flag = db.getWritableDatabase().insert(SQLiteRSSHelper.DATABASE_TABLE,null,values);
    return Uri.withAppendedPath(uri,Long.toString(flag));
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = SQLiteRSSHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = db.getWritableDatabase().query(SQLiteRSSHelper.DATABASE_TABLE,SQLiteRSSHelper.columns,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
    return db.getWritableDatabase().update(SQLiteRSSHelper.DATABASE_TABLE,values,selection,selectionArgs);
    }
}
