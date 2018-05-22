package com.example.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CodeBean {

    @Id
    @GeneratedValue
    private Integer id;

    private String mKey;//激活码
    private Integer isActivated;//是否被激活
    private Integer vipType;//会员类型
    private String deviceId;//设备Id
    private Long activatedDate;//激活时间（毫秒数）
    private Long endDate;//终止时间（毫秒数）
    private Long totalTime;//全部可使用时间（毫秒数）
    private Long totalNum;//全部可使用次数

    public CodeBean() {
        super();
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Long activatedDate) {
        activatedDate = activatedDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        return "CodeBean{" +
                "id=" + id +
                ", mKey='" + mKey + '\'' +
                ", isActivated=" + isActivated +
                ", vipType=" + vipType +
                ", deviceId='" + deviceId + '\'' +
                ", ActivatedDate=" + activatedDate +
                ", endDate=" + endDate +
                ", totalTime=" + totalTime +
                ", totalNum=" + totalNum +
                '}';
    }
}
