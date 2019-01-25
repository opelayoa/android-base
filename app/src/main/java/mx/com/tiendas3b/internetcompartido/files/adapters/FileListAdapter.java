package mx.com.tiendas3b.internetcompartido.files.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.com.tiendas3b.internetcompartido.R;
import mx.com.tiendas3b.internetcompartido.files.dto.FileDto;
import mx.com.tiendas3b.internetcompartido.files.util.Constants;

import static mx.com.tiendas3b.internetcompartido.files.util.Constants.FILE_LOADED;
import static mx.com.tiendas3b.internetcompartido.files.util.Constants.FILE_UPLOADED;

public class FileListAdapter extends BaseAdapter {


    private int layout;
    private List<FileDto> files;
    private Context context;

    public FileListAdapter(int layout, List<FileDto> files, Context context) {
        this.layout = layout;
        this.files = files;
        this.context = context;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public FileDto getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return files.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.itemDocName);
            viewHolder.icon = convertView.findViewById(R.id.fileIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final FileDto currentFileDto = getItem(position);

        viewHolder.name.setText(currentFileDto.getName());
        viewHolder.icon.setBackgroundResource(getIconByStatus(currentFileDto.getStatus()));

        return convertView;
    }

    private int getIconByStatus(int status) {
        if (status == FILE_LOADED) {
            return R.drawable.ic_add_doc_red;
        } else if (status == FILE_UPLOADED) {
            return R.drawable.ic_file_success;
        } else if (status == Constants.FILE_ERROR) {
            return R.drawable.ic_file_error;
        } else {
            return R.drawable.ic_add_doc_red;
        }
    }

    static class ViewHolder {
        private TextView name;
        private ImageView icon;
    }
}
