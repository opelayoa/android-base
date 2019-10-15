package mx.com.tiendas3b.internetcompartido.root;

import android.support.v7.app.AppCompatActivity;

import com.datami.smi.SmiSdk;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        SmiSdk.stopSponsoredData();
        super.onStop();
    }
}
