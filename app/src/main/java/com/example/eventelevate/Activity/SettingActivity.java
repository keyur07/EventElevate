package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivitySettingBinding;

import java.io.File;
import java.text.DecimalFormat;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Init();
    }

    private void Init() {
        AppManager.changeStatusBarandBottomColor(SettingActivity.this);
        GetthesystemCache();

        binding.cacheClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trimCache(SettingActivity.this);
                GetthesystemCache();
            }
        });

        binding.documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,DocumentsActivity.class));
            }
        });

        binding.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse("https://sites.google.com/view/videodownloderpolicy/home"));
                startActivity(intent);
            }
        });

        binding.shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Discover the best service provider and event booking app! Simplify your event planning and make bookings effortlessly. DOWNLOAD NOW!";
                String sub= "http://play.google.com/store/apps/details?id="+getPackageName();;
                intent.putExtra(Intent.EXTRA_TEXT,body);
                intent.putExtra(Intent.EXTRA_TEXT,sub);
                startActivity(Intent.createChooser(intent,"Share Using"));
            }
        });
    }

    public static void trimCache(Context context) {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
                Toast.makeText(context, "Cache Cleared...", 0).show();
            }
        } catch (Exception unused) {
        }
    }

    public static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            for (String str : file.list()) {
                if (!deleteDir(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }


    private void GetthesystemCache() {
        try {
            ((TextView) findViewById(R.id.version)).setText(this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        File cacheDir = getCacheDir();
        binding.cache.setText(String.valueOf(cacheDir.length()));


        binding.cache.setText(readableFileSize(getDirSize(this.getExternalCacheDir()) + getDirSize(this.getCacheDir()) + cacheDir.length()));
    }

    public static String readableFileSize(long j2) {
        double d2 = 0;
        if (j2 <= 0) {
            return "0 Bytes";
        }
        int log10 = (int) (Math.log10(j2) / Math.log10(1024.0d));
        return new DecimalFormat("#,##0.#").format(d2 / Math.pow(1024.0d, log10)) + " " + new String[]{"Bytes", "kB", "MB", "GB", "TB"}[log10];
    }

    public long getDirSize(File file) {
        File[] listFiles;
        long length = 0;
        long j2 = 0;
        if (!(file == null || !file.exists() || file.listFiles().length == 0)) {
            for (File file2 : file.listFiles()) {
                if (file2 == null || !file2.isDirectory()) {
                    if (file2 != null && file2.isFile()) {
                        length = file2.length();
                    }
                } else {
                    length = getDirSize(file2);
                }
                j2 += length;
            }
        }
        return j2;
    }
}