package com.mllwf.lookfiletowps.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mllwf.lookfiletowps.R;
import com.mllwf.lookfiletowps.WpsModel;
import com.mllwf.lookfiletowps.adapter.FileFmAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ML on 2017/4/10.
 */

public class FileListFm extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;
    private String rootPath;
    private TextView tvPath;
    private ListView fileList;
    private File[] mFiles;
    private FileFmAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_file_list_view, container, false);
        tvPath = (TextView) rootView.findViewById(R.id.tv_path);
        fileList = (ListView) rootView.findViewById(R.id.file_list);
        fileList.setOnItemClickListener(this);
        rootPath = Environment.getExternalStorageDirectory().getPath();
        getFileList(rootPath);
        return rootView;
    }

    private void getFileList(String filePath) {
        tvPath.setText(filePath);
        File file = new File(filePath);
        mFiles = file.listFiles();
        sortFileList(mFiles);
        mAdapter = new FileFmAdapter(getActivity(), mFiles);
        fileList.setAdapter(mAdapter);
    }

    //TODO: 给文件列表
    public void sortFileList(File[] files) {
        if (files == null) {
            return;
        }
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile()) {
                    return -1;
                } else if (o1.isFile() && o2.isDirectory()) {
                    return 1;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File file = mFiles[position];
        if (file.isFile()) {
            if (isAvilible(getContext(), "cn.wps.moffice_eng")) {
                String filePath = file.getAbsolutePath();
                openFile(filePath);
                Toast.makeText(getActivity(), file.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "请先安装WPS移动版", Toast.LENGTH_SHORT).show();
            }

        } else {
            getFileList(file.getAbsolutePath());
        }
    }

    public boolean openFile(String path) {
        String packageName = getActivity().getPackageName();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
        bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
        bundle.putString(WpsModel.THIRD_PACKAGE, packageName); // 第三方应用的包名，用于对改应用合法性的验证
        bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
        // bundle.putBoolean(CLEAR_FILE, true); //关闭后删除打开文件
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName(WpsModel.PackageName.NORMAL, WpsModel.ClassName.NORMAL);
        File file = new File(path);
        if (file == null || !file.exists()) {
            System.out.println("文件为空或者不存在");
            return false;
        }
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        intent.putExtras(bundle);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            System.out.println("打开wps异常：" + e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager(); //获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名//从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public void backDo() {
        String curPath = tvPath.getText().toString();
        curPath = curPath.substring(0, curPath.lastIndexOf("/"));
        if (!TextUtils.isEmpty(curPath)) {
            getFileList(curPath);
        } else {
            getActivity().finish();
        }
    }

}
