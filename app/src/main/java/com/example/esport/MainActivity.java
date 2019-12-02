package com.example.esport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    UserInfo info;
    private static int login = 1001;
    static public FirebaseAuth auth;
    FirebaseDatabase firebaseDb;
    String mUid;
    DatabaseReference mDbRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (auth.getCurrentUser() == null) {
            navigationView.getMenu().findItem(R.id.mn_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.mn_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.mn_admin).setVisible(false);
            navigationView.getMenu().setGroupVisible(R.id.mn_main, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.mn_home);
            Log.d("login123", "Guest");
        }
        else{
            setAuth();
        }

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.mn_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mn_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.mn_guide:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GuideFragment()).commit();
                break;
            case R.id.mn_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewsFragment()).commit();
                break;
            case R.id.mn_update:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UpdateFragment()).commit();
                break;
            case R.id.mn_tour:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TourFragment()).commit();
                break;
            case R.id.mn_sp:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SupportFragment()).commit();
                break;
            case R.id.mn_posts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PostFragment()).commit();
                break;
            case R.id.mn_catalog:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CatalogFragment()).commit();
                break;
            case R.id.mn_users:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UsersFragment()).commit();
                break;
            case R.id.mn_comments:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CommentsFragment()).commit();
                break;
            case R.id.mn_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, login);
                break;
            case R.id.mn_logout:
                AuthUI.getInstance().signOut(getApplicationContext()).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                navigationView.getMenu().findItem(R.id.mn_logout).setVisible(false);
                                navigationView.getMenu().findItem(R.id.mn_login).setVisible(true);
                                navigationView.getMenu().findItem(R.id.mn_admin).setVisible(false);
                                navigationView.getMenu().setGroupVisible(R.id.mn_main, true);
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new HomeFragment()).commit();
                                navigationView.setCheckedItem(R.id.mn_home);
                                ((TextView) findViewById(R.id.nav_username)).setText(R.string.acc_name);
                                ((TextView) findViewById(R.id.nav_email)).setText(R.string.acc_email);
                            }
                        });
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int request, int result, Intent intent) {
        super.onActivityResult(request, result, intent);
        if (result == Activity.RESULT_OK) {
            if (request == login) {
                setAuth();
            }
        }
    }

    public void setAuth(){
        firebaseDb = FirebaseDatabase.getInstance();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDbRoot = firebaseDb.getReference().child("users").child(mUid).child("userInfo");
        mDbRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                info = dataSnapshot.getValue(UserInfo.class);
                ((TextView) findViewById(R.id.nav_username)).setText(info.getUsername());
                ((TextView) findViewById(R.id.nav_email)).setText(info.getEmail());
                if (info.getRole().equals("admin")) {
                    navigationView.getMenu().findItem(R.id.mn_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.mn_login).setVisible(false);
                    navigationView.getMenu().setGroupVisible(R.id.mn_main, false);
                    navigationView.getMenu().findItem(R.id.mn_admin).setVisible(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new PostFragment()).commit();
                    navigationView.setCheckedItem(R.id.mn_posts);
                }
                if (info.getRole().equals("user") || info.getRole().equals("employee")) {
                    navigationView.getMenu().findItem(R.id.mn_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.mn_login).setVisible(false);
                }
                mDbRoot.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
