package com.example.demo.repository;

import com.example.demo.bean.CodeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeRepository extends JpaRepository<CodeBean, Integer> {

    @Query("from CodeBean u where u.deviceId=:deviceId")
    List<CodeBean> findCodeBeanByDeviceId(@Param("deviceId") String deviceId);

    @Query("from CodeBean u where u.mKey=:mKey")
    CodeBean findCodeBeanByKey(@Param("mKey") String mKey);

}
