package mx.com.tiendas3b.internetcompartido.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.tiendas3b.internetcompartido.R;
import mx.com.tiendas3b.internetcompartido.login.LoginActivity;
import mx.com.tiendas3b.internetcompartido.root.App;
import mx.com.tiendas3b.internetcompartido.util.PermissionUtil;

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

        logo.startAnimation(animation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    goHome();
                } else {

                }
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
                    sleep(1000);
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
