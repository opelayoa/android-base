package mx.com.tiendas3b.internetcompartido;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();


    @BindView(R.id.ic_loading)
    ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading_rotate_animation);
        loading.startAnimation(startRotateAnimation);

    }
}
