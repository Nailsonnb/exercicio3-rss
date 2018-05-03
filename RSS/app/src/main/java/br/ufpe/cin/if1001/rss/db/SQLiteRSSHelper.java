package br.ufpe.cin.if1001.rss.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

import br.ufpe.cin.if1001.rss.domain.ItemRSS;


public class SQLiteRSSHelper extends SQLiteOpenHelper {
    //Nome do Banco de Dados
    private static final String DATABASE_NAME = "rss";
    //Nome da tabela do Banco a ser usada
    public static final String DATABASE_TABLE = "items";
    //Versão atual do banco
    private static final int DB_VERSION = 1;

    //alternativa
    Context c;

    private SQLiteRSSHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        c = context;
    }

    private static SQLiteRSSHelper db;

    //Definindo Singleton
    public static SQLiteRSSHelper getInstance(Context c) {
        if (db==null) {
            db = new SQLiteRSSHelper(c.getApplicationContext());
        }
        return db;
    }

    //Definindo constantes que representam os campos do banco de dados
    public static final String ITEM_ROWID = RssProviderContract._ID;
    public static final String ITEM_TITLE = RssProviderContract.TITLE;
    public static final String ITEM_DATE = RssProviderContract.DATE;
    public static final String ITEM_DESC = RssProviderContract.DESCRIPTION;
    public static final String ITEM_LINK = RssProviderContract.LINK;
    public static final String ITEM_UNREAD = RssProviderContract.UNREAD;

    //Definindo constante que representa um array com todos os campos
    public final static String[] columns = { ITEM_ROWID, ITEM_TITLE, ITEM_DATE, ITEM_DESC, ITEM_LINK, ITEM_UNREAD};

    //Definindo constante que representa o comando de criação da tabela no banco de dados
    private static final String CREATE_DB_COMMAND = "CREATE TABLE " + DATABASE_TABLE + " (" +
            ITEM_ROWID +" integer primary key autoincrement, "+
            ITEM_TITLE + " text not null, " +
            ITEM_DATE + " text not null, " +
            ITEM_DESC + " text not null, " +
            ITEM_LINK + " text not null, " +
            ITEM_UNREAD + " boolean not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Executa o comando de criação de tabela
        db.execSQL(CREATE_DB_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //estamos ignorando esta possibilidade no momento
        throw new RuntimeException("nao se aplica");
    }

	//IMPLEMENTAR ABAIXO
    //Implemente a manipulação de dados nos métodos auxiliares para não ficar criando consultas manualmente
    public long insertItem(ItemRSS item) {
        return insertItem(item.getTitle(),item.getPubDate(),item.getDescription(),item.getLink());
    }
    public long insertItem(String title, String pubDate, String description, String link) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_TITLE, title);
        contentValues.put(ITEM_DATE, pubDate);
        contentValues.put(ITEM_DESC,description);
        contentValues.put(ITEM_LINK, link);
        contentValues.put(ITEM_UNREAD, 1);

        SQLiteDatabase writlableDatabase = db.getWritableDatabase();
        return writlableDatabase.insert(DATABASE_TABLE,null,contentValues);
    }
    public ItemRSS getItemRSS(String link) throws SQLException {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();
        Cursor cursor;
        String sql = "SELECT "+ITEM_TITLE+", "+ITEM_DATE+","+ITEM_DESC+" FROM "+DATABASE_TABLE+" WHERE "+ITEM_LINK+ "= '"+link+"';" ;
        cursor = writableDatabase.rawQuery(sql, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndex(ITEM_TITLE));
            String date = cursor.getString(cursor.getColumnIndex(ITEM_DATE));
            String desc = cursor.getString(cursor.getColumnIndex(ITEM_DESC));
            return new ItemRSS(title, link, date, desc);
        }
        return null;
    }
    public Cursor getItems() throws SQLException {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();
        //retorna apenas os não lidos
        Cursor cursor = writableDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE+" WHERE "+ITEM_UNREAD+ "="+ 1+" ORDER BY '"+ ITEM_DATE+"' DESC;",null);
        return cursor;
    }
    public boolean markAsUnread(String link) {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();
        writableDatabase.execSQL("UPDATE "+DATABASE_TABLE+" SET "+ITEM_UNREAD+" = "+1+" WHERE "+ITEM_LINK+" = '"+link+"';");
        writableDatabase.close();
        return true;
    }

    public boolean markAsRead(String link) {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();
        writableDatabase.execSQL("UPDATE "+DATABASE_TABLE+" SET "+ITEM_UNREAD+" = "+0+" WHERE "+ITEM_LINK+" = '"+link+"';");
        writableDatabase.close();
        return false;
    }

}
