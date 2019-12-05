package com.cozing.monalisa.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

/**
 * desc:扩展Activity
 *
 * Created by wanghuquan on 2018/2/11.
 */

public class MActivity extends Activity{

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);



        afterCreate();
    }

    protected void afterCreate(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
