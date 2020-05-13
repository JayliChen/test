package com.zhongxingwang.center.user.web.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@ToString
@Setter
@Getter
public class BaseEo {
    @Id
    @GeneratedValue(generator = "JDBC")
    public Long id;
    public String createPerson;
    public Date createTime;
    public String updatePerson;
    public Date updateTime;
    public Integer dr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatePerson() {
        if (createPerson == null) {
            //需要从上下文获取
            return "系统用户";
        } else {
            return createPerson;
        }
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return new Date();
        } else {
            return createTime;
        }
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;

    }

    public String getUpdatePerson() {
        if (updatePerson == null) {
            return "系统用户";
        } else {
            return updatePerson;
        }
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateTime() {
        if (updateTime == null) {
            return new Date();
        } else {
            return updateTime;
        }
    }

    public void setUpdateTime(Date updateTime) {

        this.updateTime = updateTime;

    }

    public Integer getDr() {
        if (dr == null) {
            return 0;
        } else {
            return dr;
        }
    }

    public void setDr(Integer dr) {

        this.dr = dr;

    }

}
