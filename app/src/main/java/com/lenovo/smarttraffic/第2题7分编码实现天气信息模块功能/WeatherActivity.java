package com.lenovo.smarttraffic.第2题7分编码实现天气信息模块功能;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.activity.BaseActivity;

public class WeatherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initToolBar(findViewById(R.id.toolbar),true,"天气预报");

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            /**
             *写R.id.home 不会执行下面的两行代码
             * android.R.id.home  这样才能执行下面的代码
             */
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}