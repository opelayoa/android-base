package mx.com.tiendas3b.internetcompartido.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.codec.digest.Crypt;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import mx.com.tiendas3b.internetcompartido.R;
import mx.com.tiendas3b.internetcompartido.files.FileListActivity;

public class LoginActivity extends AppCompatActivity implements LoginMVP.View {

    @BindView(R.id.login_btn_login)
    Button btnLogin;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.login_btn_login)
    protected void login() {
        String username = Crypt.crypt(this.username.getText().toString().trim().equalsIgnoreCase("") ? "" : this.username.getText().toString().trim(), getString(R.string.salt));
        String password = Crypt.crypt(this.password.getText().toString().trim().equalsIgnoreCase("") ? "" : this.password.getText().toString().trim(), getString(R.string.salt));

        if (username.equals(getString(R.string.username)) && password.equals(getString(R.string.password))) {
            Intent intent = new Intent(LoginActivity.this, FileListActivity.class);
            startActivity(intent);
            finish();
        } else {

            Snackbar.make(btnLogin, "Usuario y/o Contraseña incorrecta, intente nuevamente", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
