package com.lenovo.smarttraffic.greendao.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PersonInfor {
    @Id(autoincrement = true)//设置自增长
    private Long id;

    @Index(unique = true)//设置唯一性
    private String name;//人员姓名

    private String password;//用户密码

    @Generated(hash = 227194913)
    public PersonInfor(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Generated(hash = 1362534400)
    public PersonInfor() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
