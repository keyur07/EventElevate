package com.example.eventelevate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.usage.EventStats;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.eventelevate.Adapter.ViewPagerAdapter;
import com.example.eventelevate.Fragments.CreateFragment;
import com.example.eventelevate.Fragments.EventFragment;
import com.example.eventelevate.Fragments.EventsFragment;
import com.example.eventelevate.Fragments.ForYouFragment;
import com.example.eventelevate.Fragments.ManageFragment;
import com.example.eventelevate.Fragments.MessagesFragment;
import com.example.eventelevate.Fragments.ProfileFragment;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient signInClient;


    private ViewPager2 viewPager2;
    private TabLayout tab;
    ActivityMainBinding binding;
    Fragment selectedFragment = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentChanger(new EventsFragment());

        getDataFromGoogleAccount();

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                Fragment selectedFragment = null;
                String fragmentTag = null;

                if (itemId == R.id.event) {
                    selectedFragment = new ForYouFragment();
                } else if (itemId == R.id.foryou) {
                    selectedFragment = new EventsFragment();
                } else if (itemId == R.id.create) {
                    selectedFragment = new ManageFragment();
                } else if (itemId == R.id.profile) {
                    selectedFragment = new ProfileFragment();
                }else if (itemId == R.id.messages) {
                    selectedFragment = new MessagesFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout, selectedFragment, fragmentTag)
                            .commitNow();

                }

                return true;
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.start_color));
        }

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.start_color));

    }

    private void getDataFromGoogleAccount() {

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(MainActivity.this, googleSignInOptions);

        GoogleSignInAccount signIn = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

        if (signIn != null) {
            Log.e("datdatadata", signIn.getDisplayName());
            Log.e("datdatadata", signIn.getEmail());
        } else {

        }

    }


    @Override
    public void onBackPressed() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialog);
        View bottom = LayoutInflater.from(MainActivity.this).inflate(R.layout.exit_dailog, null);
        bottomSheetDialog.setContentView(bottom);
        bottomSheetDialog.show();

        bottom.findViewById(R.id.notnow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottom.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showProgress(MainActivity.this);
                bottomSheetDialog.dismiss();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppManager.hideProgress();
                        finish();
                        System.exit(0);
                    }
                }, 2000);

            }
        });
    }

    public void FragmentChanger(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();

    }

}