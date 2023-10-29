package com.example.eventelevate.Manager;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.BuildConfig;
import com.example.eventelevate.Activity.LoginActivity;
import com.example.eventelevate.Activity.MainActivity;
import com.example.eventelevate.Activity.SplashScreen;
import com.example.eventelevate.Components.ProgressRound;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.SettingModel;
import com.example.eventelevate.R;
import com.google.android.material.snackbar.Snackbar;

import org.apache.http.params.CoreConnectionPNames;

public  class AppManager {

    public static LoginModel.User user;
    public static SettingModel.Setting setting;
    private static ProgressRound progressBar;
    public static String SelectedLocation="Surat";
    public static String selectedService;

    public static void changeActivity(Context context, Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public static void FullScreen(AppCompatActivity context) {
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void ShareApplication(Context context){

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage= "\nEnjoying "+R.string.app_name+" Share it with your friends and help them discover something amazing\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + context.getPackageName() +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public static void RateUs(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.rate_us_dialog);
        dialog.setCancelable(false);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating);
        dialog.findViewById(R.id.cancekl).setOnClickListener(new View.OnClickListener() { // from class: m.a.a.b.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() { // from class: m.a.a.b.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                launchMarket(context);
            }
        });
        dialog.show();
    }

    private static void launchMarket(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public static void HideActionBar(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().hide();
        }
    }

    public static void showProgress(Activity context) {
        try {
            if (context != null && progressBar == null) {
                progressBar = new ProgressRound(context);
                progressBar.setCancelable(false);
                progressBar.setCanceledOnTouchOutside(false);
                progressBar.getWindow().setDimAmount(0.0f);
                progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressBar.show();
            }
        } catch (Exception e) {
            Log.e("error", "showSmallProgressDialog " + e.toString());
        }
    }

    public static void changeStatusBarandBottomColor(Activity context){

        Window window = context.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(context, R.color.start_color));
        context.getWindow().setNavigationBarColor(context.getResources().getColor(R.color.start_color));
    }

    public static void hideProgress() {
        try {
            if (progressBar != null && progressBar.isShowing()) {
                progressBar.dismiss();
                progressBar = null;
            }
        } catch (Exception e) {
            Log.e("error", "hideSmallProgressDialog " + e.toString());
        }
    }

    public static void ChangeColorOfStatusBar(AppCompatActivity activity,int colorResId) {
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                if (window != null) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(activity.getResources().getColor(colorResId));
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = activity.getWindow();
                if (window != null) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    View statusBarTintView = new View(activity);
                    statusBarTintView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
                    statusBarTintView.setBackgroundColor(activity.getResources().getColor(colorResId));
                    ViewGroup decorView = (ViewGroup) window.getDecorView();
                    decorView.addView(statusBarTintView);
                }
            }
        }
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void SaveShareData(Context context,String key,String value){

        SharedPreferences preferences = context.getSharedPreferences(String.valueOf(R.string.user_session_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getSaveShareData(Context context,String key){

        SharedPreferences preferences = context.getSharedPreferences(String.valueOf(R.string.user_session_key),Context.MODE_PRIVATE);
       return preferences.getString(key,"");
    }

    public static String getDeviceID(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static void StatusDialog(Context context,boolean status){

        if(status==true){
            Dialog download_completed = new Dialog(context);
            download_completed.setCancelable(false);
            download_completed.setContentView(R.layout.task_completed_view_dialog);
            download_completed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT ));
            download_completed.show();

            download_completed.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   download_completed.dismiss();

                }
            });
        }
    }

}
