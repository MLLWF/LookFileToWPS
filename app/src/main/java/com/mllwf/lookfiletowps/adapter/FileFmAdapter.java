package com.mllwf.lookfiletowps.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mllwf.lookfiletowps.R;

import java.io.File;

/**
 * Created by ML on 2017/4/10.
 */

public class FileFmAdapter extends BaseAdapter {

    private File[] lists;
    private Context mContext;

    public FileFmAdapter(Context context, File[] lists) {
        mContext = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        if (lists == null) {
            return 0;
        }
        return lists.length;
    }

    @Override
    public File getItem(int position) {
        return lists[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.fm_file_list_cell, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.adapter_icon);
            holder.tv = (TextView) convertView.findViewById(R.id.adapter_txt);
            holder.size = (TextView) convertView.findViewById(R.id.adapter_size);
            holder.select = (CheckBox) convertView.findViewById(R.id.file_select);
            holder.select.setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置convertView中控件的值
        setconvertViewRow(holder, position);
        return convertView;
    }

    private void setconvertViewRow(ViewHolder holder, int position) {
        File file = lists[position];
        holder.tv.setText(file.getName());
        if (file.isFile()) {
            holder.iv.setImageResource(R.drawable.f_file);
            holder.size.setVisibility(View.VISIBLE);
            holder.size.setText(file.length() + "");
        } else {
            holder.iv.setImageResource(R.drawable.f_folder);
            holder.size.setVisibility(View.GONE);
        }
    }


    public class ViewHolder {
        public ImageView iv;
        public TextView tv;
        public TextView size;
        public CheckBox select;
    }
}
