package br.ufpe.cin.if1001.rss.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import br.ufpe.cin.if1001.rss.ui.MainActivity;

public class FimDoServiceDownload extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Service de dowload terminado com sucesso!",Toast.LENGTH_LONG);

    }
}
