package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityPermissionBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    private ActivityPermissionBinding binding;
    private ViewPager viewPager;
    private int[] layouts = {R.layout.item_permission_storage};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppManager.changeStatusBarandBottomColor(PermissionActivity.this);

        viewPager = findViewById(R.id.view_pager);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setPageTransformer(true, new PopPageTransformer());

        findViewById(R.id.enable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Dexter.withContext(PermissionActivity.this)
                            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                    if(multiplePermissionsReport.isAnyPermissionPermanentlyDenied()){
                                        openAppSettings();
                                    }else {
                                        startActivity(new Intent(PermissionActivity.this,LoginScreen.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                                }
                            }).check();
                }else {
                    startActivity(new Intent(PermissionActivity.this,LoginScreen.class));
                }
            }
        });
//        binding.allow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    Dexter.withContext(PermissionActivity.this)
//                            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
//                                @Override
//                                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
//                                    if(multiplePermissionsReport.isAnyPermissionPermanentlyDenied()){
//                                        openAppSettings();
//                                    }else {
//                                        startActivity(new Intent(PermissionActivity.this,LoginScreen.class));
//                                        finish();
//                                    }
//                                }
//
//                                @Override
//                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//
//                                }
//                            }).check();
//                }else {
//
//                }
//            }
//        });
    }

    private void openAppSettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required");
        builder.setMessage("This app requires storage permission to function properly.");
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public class PopPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            page.setScaleX(0.9f);
            page.setScaleY(0.9f);
            page.setAlpha(0.7f);

            if (position <= -1.0F || position >= 1.0F) {
                page.setAlpha(0.0F);
            } else if (position == 0.0F) {
                page.setAlpha(1.0F);
                page.setScaleX(1.0f);
                page.setScaleY(1.0f);
            } else {
                // Position is between -1.0F & 0.0F OR 0.0F & 1.0F
                page.setAlpha(1.0F - Math.abs(position));
                page.setScaleX(1.0f - Math.abs(position));
                page.setScaleY(1.0f - Math.abs(position));
            }
        }
    }


}