package com.mllwf.lookfiletowps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mllwf.lookfiletowps.fragment.FileListFm;


/**
 * Created by ML on 2017/4/10.
 */

public class FileListActivity extends AppCompatActivity {
    private FileListFm fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain_view);
        fm = new FileListFm();
        getSupportFragmentManager().beginTransaction().add(R.id.contain, fm).commit();
    }

    @Override
    public void onBackPressed() {
        fm.backDo();
    }
}
