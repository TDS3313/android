package com.lenovo.smarttraffic.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lenovo.smarttraffic.greendao.pojo.DaoMaster;
import com.lenovo.smarttraffic.greendao.pojo.DaoSession;
import com.lenovo.smarttraffic.greendao.pojo.PersonInfor;
import com.lenovo.smarttraffic.greendao.pojo.PersonInforDao;

import java.util.List;

public class DbController {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */

    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private PersonInforDao personInforDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context){
        if(mDbController == null){
            synchronized (DbController.class){
                if(mDbController == null){
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }
    /**
     * 初始化
     * @param context
     */

    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context,"person.db", null);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        personInforDao = mDaoSession.getPersonInforDao();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,"person.db",null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,"person.db",null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     * @param personInfor
     */
    public void insertOrReplace(PersonInfor personInfor){
        personInforDao.insertOrReplace(personInfor);
    }
    /**插入一条记录，表里面要没有与之相同的记录
     *
     * @param personInfor
     */
    public long insert(PersonInfor personInfor){
        return  personInforDao.insert(personInfor);
    }

    /**
     * 更新数据
     * @param personInfor
     */
    public void update(PersonInfor personInfor){
        PersonInfor mOldPersonInfor = personInforDao.queryBuilder().where(PersonInforDao.Properties.Id.eq(personInfor.getId())).build().unique();//拿到之前的记录
        if(mOldPersonInfor !=null){
            mOldPersonInfor.setName("张三");
            personInforDao.update(mOldPersonInfor);
        }
    }
    /**
     * 按条件查询数据
     */
    public List<PersonInfor> searchByWhere(String wherecluse){
        List<PersonInfor>personInfors = (List<PersonInfor>) personInforDao.queryBuilder().where(PersonInforDao.Properties.Name.eq(wherecluse)).build().unique();
        return personInfors;
    }
    /**
     * 查找该用户是否存在
     */
    public boolean selectPersonByName(String name){
        PersonInfor person = personInforDao.queryBuilder().where(PersonInforDao.Properties.Name.eq(name)).build().unique();
        return person!=null ? true : false;
    }
    /**
     *判断密码是否正确
     */
    public boolean isPassword(String name,String password){
        String password1 = personInforDao.queryBuilder().where(PersonInforDao.Properties.Name.eq(name)).build().unique().getPassword();
        return password1.equals(password) ? true : false;
    }


    /**
     * 查询所有数据
     */
    public List<PersonInfor> searchAll(){
        List<PersonInfor>personInfors=personInforDao.queryBuilder().list();
        return personInfors;
    }
    /**
     * 删除数据
     */
    public void delete(String wherecluse){
        personInforDao.queryBuilder().where(PersonInforDao.Properties.Name.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }


}