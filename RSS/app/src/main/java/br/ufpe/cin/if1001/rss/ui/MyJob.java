package br.ufpe.cin.if1001.rss.ui;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import br.ufpe.cin.if1001.rss.R;
import br.ufpe.cin.if1001.rss.services.DownloadViaServices;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJob extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String linkfeed = preferences.getString("rssfeedlink", getResources().getString(R.string.rssfeed));
        Intent downloadViaService = new Intent(getApplicationContext(), DownloadViaServices.class);
        downloadViaService.putExtra("url",linkfeed);
        Log.i("teste","ultima parte antes de da start service");
        startService(downloadViaService);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Intent downloadVia = new Intent(getApplicationContext(), DownloadViaServices.class);
        getApplicationContext().stopService(downloadVia);
        return false;
    }
}
