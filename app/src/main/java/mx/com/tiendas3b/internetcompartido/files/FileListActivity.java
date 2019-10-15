package mx.com.tiendas3b.internetcompartido.files;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mx.com.tiendas3b.internetcompartido.R;
import mx.com.tiendas3b.internetcompartido.files.adapters.FileListAdapter;
import mx.com.tiendas3b.internetcompartido.files.dto.FileDto;
import mx.com.tiendas3b.internetcompartido.files.util.Constants;
import mx.com.tiendas3b.internetcompartido.root.BaseActivity;
import mx.com.tiendas3b.internetcompartido.util.DialogFactory;

import static mx.com.tiendas3b.internetcompartido.files.util.Constants.FILE_LOADED;

public class FileListActivity extends BaseActivity {

    private static final int REQUEST_CODE = 6384; // onActivityResult request


    private List<FileDto> list;

    private ListView listView;
    private FileListAdapter fileListAdapter;

    private long fileIndex;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Envío de archivos - FTP");
        setSupportActionBar(toolbar);

        list = new ArrayList<>();

        listView = findViewById(R.id.filesDto);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            PopupMenu popup = new PopupMenu(FileListActivity.this, view);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.menu_file_item, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    list.remove(position);
                    notifyDataSetChanged();
                    return true;
                }
            });
            popup.show();
        });

        fileListAdapter = new FileListAdapter(R.layout.item_file_list, list, this);
        listView.setAdapter(fileListAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            final Intent target = new Intent(Intent.ACTION_OPEN_DOCUMENT);

            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                    + File.separator + "Store-T3B" + File.separator);
            System.out.println(Environment.getExternalStorageDirectory().getPath()
                    + "/Store-T3B/");
            File file = new File(uri.toString());
            if (!file.exists()) {
                file.mkdir();
            }
            target.setDataAndType(uri, "*/*");

            target.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            target.addCategory(Intent.CATEGORY_OPENABLE);

            Intent intent = Intent.createChooser(
                    target, "Selecciona tus archivos");
            try {
                startActivityForResult(intent, REQUEST_CODE);
            } catch (ActivityNotFoundException e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        System.exit(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (null != data) { // checking empty selection
                if (null != data.getClipData()) { // checking multiple selection or not
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        FileDto fileDto = getRealPathFromURI(uri);
                        if (fileDto != null) {
                            if (!validateIfExist(uri, list)) {
                                list.add(fileDto);
                                notifyDataSetChanged();
                            }

                        }
                    }
                } else {
                    Uri uri = data.getData();
                    FileDto fileDto = getRealPathFromURI(uri);
                    if (!validateIfExist(uri, list)) {
                        list.add(fileDto);
                        notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_file_clean:
                list.clear();
                notifyDataSetChanged();

                return true;
            case R.id.menu_file_upload:

                new FTPUtil().execute();
                //callFTP();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void notifyDataSetChanged() {
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    private FileDto getRealPathFromURI(Uri contentURI) {
        FileDto result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = null;
        } else {
            result = new FileDto();
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
            result.setName(cursor.getString(idx));

            idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            result.setSize(cursor.getDouble(idx));
            result.setFile(new File(contentURI.getPath()));
            result.setStatus(FILE_LOADED);

            result.setId(++fileIndex);
            result.setUri(contentURI);

            cursor.close();
        }
        return result;
    }

    private boolean validateIfExist(Uri uri, List<FileDto> list) {
        boolean validate = false;
        for (FileDto fileDto : list) {
            if (fileDto.getUri().toString().equals(uri.toString())) {
                validate = true;
                break;
            }
        }
        return validate;
    }

    class FTPUtil extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            for (FileDto fileDto : list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setTitle("Subiendo archivo: " + fileDto.getName());
                    }
                });
                callFTP(fileDto);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = DialogFactory.getProgressDialog(FileListActivity.this, "Enviando Archivos");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    protected void callFTP(FileDto fileDto) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("201.161.90.100");
            ftpClient.login("datami", "secret");
            ftpClient.changeWorkingDirectory("test");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setConnectTimeout(5000);


            InputStream inputStream = getContentResolver().openInputStream(fileDto.getUri());


            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(inputStream);
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(fileDto.getName(), buffIn);
            buffIn.close();
            ftpClient.logout();
            ftpClient.disconnect();
            fileDto.setStatus(Constants.FILE_UPLOADED);
        } catch (IOException e) {
            fileDto.setStatus(Constants.FILE_ERROR);
        }

    }


}