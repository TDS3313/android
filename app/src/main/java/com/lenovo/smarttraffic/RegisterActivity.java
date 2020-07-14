package com.lenovo.smarttraffic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lenovo.smarttraffic.greendao.DbController;
import com.lenovo.smarttraffic.greendao.pojo.PersonInfor;
import com.lenovo.smarttraffic.ui.activity.BaseActivity;
import com.lenovo.smarttraffic.ui.activity.LoginActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private Button regAC_btn_register;
    private EditText regAC_et_name;
    private EditText regAC_et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initToolBar(findViewById(R.id.toolbar),true,"注册");
        initUI();
    }

    private void initUI() {
        regAC_btn_register = findViewById(R.id.regAC_btn_register);
        regAC_btn_register.setOnClickListener(this);
        regAC_et_name = findViewById(R.id.regAC_et_name);
        regAC_et_password = findViewById(R.id.regAC_et_password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.regAC_btn_register:
                String name = regAC_et_name.getText().toString();
                String password = regAC_et_password.getText().toString();
                if(!name.equals("") && !password.equals("")) {
                    if(DbController.getInstance(this).selectPersonByName(name)){
                        Toast.makeText(this, "该用户已存在", Toast.LENGTH_SHORT).show();
                    }else{
                        PersonInfor personInfor = new PersonInfor();
                        personInfor.setName(name);
                        personInfor.setPassword(password);
                        DbController.getInstance(this).insert(personInfor);
                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                }else{
                    Toast.makeText(this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }//switch结尾
    }
}