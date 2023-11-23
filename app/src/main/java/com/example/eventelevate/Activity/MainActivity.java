package com.example.eventelevate.Activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient signInClient;

    private ViewPager2 viewPager2;
    ActivityMainBinding binding;
    Fragment selectedFragment = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDataFromGoogleAccount();
        viewPager2 = findViewById(R.id.viewPager); // Change to viewPager2
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // Use FragmentStateAdapter for ViewPager2
        MyPagerAdapter adapter = new MyPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                int position = 0;

                if (itemId == R.id.event) {
                    position = 0;
                } else if (itemId == R.id.foryou) {
                    position = 1;
                } else if (itemId == R.id.create) {
                    position = 2;
                } else if (itemId == R.id.profile) {
                    position = 3;
                } else if (itemId == R.id.messages) {
                    position = 4;
                }

                viewPager2.setCurrentItem(position, false); // Disable smooth scroll
                return true;
            }
        });

        // Set the default selected item to "For You"
        bottomNavigation.setSelectedItemId(R.id.foryou);
        viewPager2.setCurrentItem(1, false); // Set the default page to "For You"
        viewPager2.setUserInputEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    public class MyPagerAdapter extends FragmentStateAdapter {
        public MyPagerAdapter(@NonNull AppCompatActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ForYouFragment();
                case 1:
                    return new EventsFragment();
                case 2:
                    return new ManageFragment();
                case 3:
                    return new ProfileFragment();
                case 4:
                    return new MessagesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 5; // Number of tabs
        }
    }
}
