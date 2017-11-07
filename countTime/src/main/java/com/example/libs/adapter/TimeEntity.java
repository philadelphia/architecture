package com.example.libs.adapter;

/**
 * @description :其他的实体类继承该类
 * @autHor :  V.Wenju.Tian
 * @date : 2017/2/6 15:24
 */


public class TimeEntity {
    protected int entityId;
    //结束时间主要用于倒计时
    protected long end_time;
    //开始时间主要正计时
    protected long creat_time;

    public TimeEntity() {
    }

    public TimeEntity(int id, long endTime, long createTime) {
        this.entityId = id;
        this.end_time = endTime;
        this.creat_time = createTime;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(long creat_time) {
        this.creat_time = creat_time;
    }
}
