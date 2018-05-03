package br.ufpe.cin.if1001.rss.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.ufpe.cin.if1001.rss.db.SQLiteRSSHelper;
import br.ufpe.cin.if1001.rss.domain.ItemRSS;
import br.ufpe.cin.if1001.rss.util.ParserRSS;
import br.ufpe.cin.if1001.rss.ui.MainActivity;
import br.ufpe.cin.if1001.rss.ui.MyApplication;

public class DownloadViaServices extends IntentService {

    SQLiteRSSHelper db;
    public DownloadViaServices(String name) {
        super(name);
    }

    public DownloadViaServices(){
        super("DownloadViaServices");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        boolean flag_problema = false;
            db = SQLiteRSSHelper.getInstance(getApplicationContext());
            List<ItemRSS> items = null;
            try {
                String feed = getRssFeed(intent.getStringExtra("url"));
                items = ParserRSS.parse(feed);
                for (ItemRSS i : items) {
                    Log.d("DB", "Buscando no Banco por link: " + i.getLink());
                    ItemRSS item = db.getItemRSS(i.getLink());
                    if (item == null) {
                        if(MyApplication.isActivityVisible()) {
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("my-local-broadcast"));//broadcast para atualizar as noticias novas com app em primeiro plano
                        }else {
                            sendBroadcast(new Intent("nova-noticia"));//caso o usuario esteja com app em segundo plano
                        }
                        Log.d("DB", "Encontrado pela primeira vez: " + i.getTitle());
                        db.insertItem(i);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                flag_problema = true;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                flag_problema = true;
            }

        if (flag_problema) {
            Log.d("RSS", "ocorreu um erro no Service de download");
        } else {
            Log.d("RSS", "Service de download terminado com sucesso");
            sendBroadcast(new Intent("fim-service"));//caso o service seja terminado enviar este broadcast

        }

    }

    private String getRssFeed(String feed) throws IOException {
        InputStream in = null;
        String rssFeed = "";
        try {
            URL url = new URL(feed);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, count);
            }
            byte[] response = out.toByteArray();
            rssFeed = new String(response, "UTF-8");
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return rssFeed;
    }
}
