package mx.com.tiendas3b.internetcompartido.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.tiendas3b.internetcompartido.R;
import mx.com.tiendas3b.internetcompartido.login.LoginActivity;
import mx.com.tiendas3b.internetcompartido.root.App;

public class SplashActivity extends AppCompatActivity implements SplashMVP.View, ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject
    protected SplashMVP.Presenter presenter;

    private static final int READ_EXTERNAL_STORAGE = 0;
    private View layout;

    @BindView(R.id.ic_loading)
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ((App) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        presenter.setView(this);

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.logo_fadein_animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!hasPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    runtimePermissions();
                } else {
                    goHome();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        logo.startAnimation(animation);


    }

    public boolean runtimePermissions() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return true;
        }
        return false;
    }

    @SuppressLint("ObsoleteSdkInt")
    public boolean hasPermissions(String... permissions) {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                goHome();
            } else {
                runtimePermissions();
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void goHome() {
        Thread loading = new Thread() {
            public void run() {
                try {
                    sleep(750);
                    Intent main = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(main);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        loading.start();
    }
}
