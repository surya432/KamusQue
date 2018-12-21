package com.surya432.kamusque;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.surya432.kamusque.Helper.KamusHelper;
import com.surya432.kamusque.Model.KamusModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    KamusHelper kamusHelper;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kamusHelper = new KamusHelper(this);
        new LoadData().execute();
    }

    public ArrayList<KamusModel> preLoadRaw(int data) {
        ArrayList<KamusModel> listModel = new ArrayList<>();
        BufferedReader reader;
        try {
            InputStream raw_dict = getResources().openRawResource(data);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line = null;
            do {
                line = reader.readLine();
                String[] pecah = line.split("\t");
                KamusModel list;
                list = new KamusModel(pecah[0],pecah[1]);
                listModel.add(list);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listModel;
    }
    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;
        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirst();
            if (firstRun) {
                ArrayList<KamusModel> kamusEnglish = preLoadRaw(R.raw.english_indonesia);
                ArrayList<KamusModel> kamusIndonesia = preLoadRaw(R.raw.indonesia_english);
                publishProgress((int) progress);
                try {
                    kamusHelper.Open();
                } catch (Exception e) {
                    e.toString();
                }
                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) /
                        (kamusEnglish.size() + kamusIndonesia.size());
                kamusHelper.insertTransaction(kamusEnglish, true);
                progress += progressDiff;
                publishProgress((int) progress);
                kamusHelper.insertTransaction(kamusIndonesia, false);
                progress += progressDiff;
                publishProgress((int) progress);
                try {
                    kamusHelper.close();
                } catch (Exception e) {
                    e.toString();
                }
                appPreference.setFirst(false);
                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);
                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                    Log.d("ERROR PRELOAD", e.toString());
                }
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(MainActivity.this, KamusActivity.class);
            startActivity(i);
            finish();
        }
    }
}
