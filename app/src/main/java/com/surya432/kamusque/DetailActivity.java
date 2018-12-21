package com.surya432.kamusque;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.surya432.kamusque.Model.KamusModel;

public class DetailActivity extends AppCompatActivity {
    private TextView tvKey, tvValue;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvKey = findViewById(R.id.detailKey);
        tvValue = findViewById(R.id.detailValue);
        KamusModel items = getIntent().getParcelableExtra("EXTRAS_Movies");
        tvKey.setText(items.getKey());
        tvValue.setText(items.getTerjemahan());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
