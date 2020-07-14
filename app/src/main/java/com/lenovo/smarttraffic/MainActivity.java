package com.lenovo.smarttraffic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.smarttraffic.ui.activity.BaseActivity;
import com.lenovo.smarttraffic.ui.activity.Item1Activity;
import com.lenovo.smarttraffic.ui.activity.LoginActivity;
import com.lenovo.smarttraffic.ui.fragment.DesignFragment;
import com.lenovo.smarttraffic.ui.fragment.MainContentFragment;
import com.lenovo.smarttraffic.util.MyPerferencesData;
import com.lenovo.smarttraffic.第2题7分编码实现天气信息模块功能.WeatherActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private MainContentFragment mMainContent;
    private DesignFragment mDesignFragment;
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "bottomItem";
    private static final int FRAGMENT_MAIN = 0;
    private static final int FRAGMENT_DESIGN = 1;
    private BottomNavigationView bottom_navigation;
    private MyPerferencesData myPerferencesData;
    //    private int position;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initData();
        if (savedInstanceState != null) {
            loadMultipleRootFragment(R.id.container,0,mMainContent, mDesignFragment);   //使用fragmentation加载根组件
            // 恢复 recreate 前的位置
            showFragment(savedInstanceState.getInt(POSITION));
            bottom_navigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            showFragment(FRAGMENT_MAIN);
        }
    }

    //设置标题文字居中
    public void setTitleCenter(Toolbar toolbar) {
        String title = "title";
        final CharSequence originalTitle = toolbar.getTitle();
        toolbar.setTitle(title);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (title.equals(textView.getText())) {
                    textView.setGravity(Gravity.CENTER);
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    textView.setLayoutParams(params);
                }
            }
            toolbar.setTitle(originalTitle);
        }
    }//设置标题居中方法结尾
    private void showFragment(int index) {
//        position = index;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (index) {
            case FRAGMENT_MAIN:
                mToolbar.setTitle(R.string.title_main);
                setTitleCenter(mToolbar);
                if (mMainContent == null){
                    mMainContent = MainContentFragment.getInstance();
                    ft.add(R.id.container,mMainContent,MainContentFragment.class.getName());
                }else {
                    ft.show(mMainContent);
                }
                break;
            case FRAGMENT_DESIGN:
                mToolbar.setTitle(R.string.creative_design);
                if (mDesignFragment == null){
                    mDesignFragment = DesignFragment.getInstance();
                    ft.add(R.id.container,mDesignFragment,DesignFragment.class.getName());
                }else {
                    ft.show(mDesignFragment);
                }
                break;
        }
        ft.commit();

    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (mMainContent != null) {
            ft.hide(mMainContent);
        }
        if (mDesignFragment != null) {
            ft.hide(mDesignFragment);
        }
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        CircleImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
        setSupportActionBar(mToolbar);
        imageView.setOnClickListener(this);
        /*设置选择item监听*/
        navigationView.setNavigationItemSelectedListener(this);
        myPerferencesData = new MyPerferencesData(this);
    }

    private void initData() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawer.addDrawerListener(toggle);
        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_main:
                    showFragment(FRAGMENT_MAIN);
                    break;
                case R.id.action_creative:
                    showFragment(FRAGMENT_DESIGN);
                    break;
            }
            return true;
        });

    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {    /*打开或关闭左边的菜单*/
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressedSupport();
            showExitDialog();
        }
    }

    /*是否退出项目*/
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialogInterface, i) -> InitApp.getInstance().exitApp());
        builder.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /*设置选中item事件*/
        int id = item.getItemId();
        String string = null;
        switch (id){
            case R.id.nav_account:
                string = "个人";
                break;
            case R.id.item_1:
                string = "item1";
                startActivity(new Intent(this, Item1Activity.class));
                break;
            case R.id.天气预报:
                string = "天气预报";
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case R.id.item_3:
                string = "item3";
                break;
            case R.id.nav_setting:
                string = "设置";
                break;
            case R.id.nav_about:
                string = "关于";
                break;
        }
        if (!TextUtils.isEmpty(string))
        Toast.makeText(InitApp.getInstance(), "你点击了"+"\""+string+"\"", Toast.LENGTH_SHORT).show();
//        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.closeDrawers();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ivAvatar){    /*点击头像跳转登录界面*/
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        mDrawer.closeDrawer(GravityCompat.START);
    }
}
