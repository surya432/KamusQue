package com.surya432.kamusque;

import android.app.SearchManager;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.surya432.kamusque.Helper.KamusHelper;
import com.surya432.kamusque.Model.KamusModel;
import com.surya432.kamusque.adapter.KamusAdapter;

import java.util.ArrayList;

public class KamusActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<KamusModel> list = new ArrayList<>();
    private KamusAdapter adapter;
    private KamusHelper kamusHelper;
    private boolean isEnglish = true;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        kamusHelper = new KamusHelper(this);
        getLists();
        loadData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kamus, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.searchIcon)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    loadData(newText);
                    return true;
                }
            });
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            getSupportActionBar().setSubtitle(getResources().getString(R.string.EngIndo));
            Toast.makeText(this,R.string.EngIndo,Toast.LENGTH_LONG).show();
            isEnglish = true;
            loadData();
        } else if (id == R.id.nav_gallery) {
            getSupportActionBar().setSubtitle(getResources().getString(R.string.IndoEng));
            Toast.makeText(this, R.string.IndoEng, Toast.LENGTH_LONG).show();
            isEnglish = false;
            loadData();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getLists(){
        kamusHelper = new KamusHelper(this);
        adapter = new KamusAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private void loadData() {
        loadData("");
    }
    private void loadData(String search) {
        try {
            try {
                kamusHelper.Open();
            } catch (Exception e) {
                e.toString();
            }
            if (search.isEmpty()) {
                list = kamusHelper.getAllData(isEnglish);
            } else {
                list = kamusHelper.getDataByName(search, isEnglish);
            }

            if (isEnglish) {
                getSupportActionBar().setSubtitle(getResources().getString(R.string.EngIndo));
            } else {
                getSupportActionBar().setSubtitle(getResources().getString(R.string.IndoEng));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                kamusHelper.close();
            } catch (Exception e) {
                e.toString();
            }
        }
        adapter.renewAdapter(this,list);
    }
}
