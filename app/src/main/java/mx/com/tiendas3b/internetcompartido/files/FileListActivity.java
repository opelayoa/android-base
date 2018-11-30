package mx.com.tiendas3b.internetcompartido.files;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.List;

import mx.com.tiendas3b.internetcompartido.R;

public class FileListActivity extends AppCompatActivity {


    private File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        File file = Environment.getExternalStorageDirectory();

        if (file.isDirectory()) {
            files = file.listFiles();
            for (File currentFile : files) {

                System.out.println(currentFile.getAbsolutePath());
            }
        }
    }
}
