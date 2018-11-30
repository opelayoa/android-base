package mx.com.tiendas3b.internetcompartido.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.com.tiendas3b.internetcompartido.R;
import mx.com.tiendas3b.internetcompartido.files.FileListActivity;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.login_btn_login)
    protected void login() {
        Intent intent = new Intent(LoginActivity.this, FileListActivity.class);
        startActivity(intent);
    }
}
