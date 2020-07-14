package com.lenovo.smarttraffic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lenovo.smarttraffic.ui.activity.BaseActivity;
import com.lenovo.smarttraffic.util.MyPerferencesData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetWorkSettingActivity extends BaseActivity implements View.OnClickListener{

    private EditText ntkACT_et1;
    private EditText ntkACT_et2;
    private EditText ntkACT_et3;
    private EditText ntkACT_et4;
    private Button btn_ok;
    private MyPerferencesData myPerferencesData;

    @Override
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        super.initToolBar(toolbar, homeAsUpEnabled, title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_setting);
        initToolBar(findViewById(R.id.toolbar),true,"ip设置");
        init();
    }

    private void init() {
        ntkACT_et1 = findViewById(R.id.ntkACT_et1);
        ntkACT_et2 = findViewById(R.id.ntkACT_et2);
        ntkACT_et3 = findViewById(R.id.ntkACT_et3);
        ntkACT_et4 = findViewById(R.id.ntkACT_et4);
        btn_ok = findViewById(R.id.nwsAC_ok);
        btn_ok.setOnClickListener(this);
        myPerferencesData = new MyPerferencesData(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == android.R.id.home ){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_net_work_setting;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.nwsAC_ok:
                String str1 = ntkACT_et1.getText().toString();
                String str2 = ntkACT_et2.getText().toString();
                String str3 = ntkACT_et3.getText().toString();
                String str4 = ntkACT_et4.getText().toString();

                if(!str1.equals("") && !str2.equals("") && !str3.equals("") && !str4.equals("") && isNumeric(str1) && isNumeric(str2) && isNumeric(str3) && isNumeric(str4)){

//                        Toast.makeText(this, isNumeric(str1)+"都是数字", Toast.LENGTH_SHORT).show();
                        if(Integer.parseInt(str1)<=255 &&Integer.parseInt(str2)<=255 &&Integer.parseInt(str3)<=255 &&Integer.parseInt(str4)<=255){
//                            Toast.makeText(this, isNumeric(str1)+"合法输入即将保存", Toast.LENGTH_SHORT).show();
                            if(myPerferencesData.saveData(str1+str2+str3+str4))Toast.makeText(this, "数据已保存", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, isNumeric(str1)+"您输入的有误，请重新输入", Toast.LENGTH_SHORT).show();
                            cleanET();
                        }

                }else{
                    Toast.makeText(this, "输入的内容不能为空哦！", Toast.LENGTH_SHORT).show();
                }


                break;
        }
        //switch结束
    }

    //保存到偏好参数

    //判断字符串中的内容
    public boolean isNumeric(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //清楚输入空内容
    public void  cleanET(){
        ntkACT_et1.setText("");
        ntkACT_et2.setText("");
        ntkACT_et3.setText("");
        ntkACT_et4.setText("");
    }


}