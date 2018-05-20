package com.example.demo.controller;

import com.example.demo.bean.CodeBean;
import com.example.demo.bean.ReturnBean;
import com.example.demo.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileController {

    @Autowired
    private CodeRepository codeRepository;

    // 激活激活码
    @GetMapping(value = "/codeActivated")
    public ReturnBean codeActivated(@RequestParam(value = "mKey", required = false) String mKey,
                                    @RequestParam(value = "deviceId", required = false) String deviceId) {
        ReturnBean returnBean = new ReturnBean();
        // 查找是否有效（激活码和设备id是否存在，是否已激活，设备id但是否有效）
        CodeBean codeBean = codeRepository.findCodeBeanByKey(mKey);
        if (codeBean != null) {
            if (codeBean.getDeviceId().equals(deviceId)) {
                if (codeBean.getIsActivated() == 0) {
                    // 激活（修改激活码状态为激活，开始日期和截至日期填充数据）
                    codeBean.setIsActivated(1);
                    codeRepository.save(codeBean);
                    returnBean.setCode("0");
                    returnBean.setMsg("激活码已激活！");
                } else {
                    returnBean.setCode("3");
                    returnBean.setMsg("激活码已经激活过了！");
                }
            } else {
                returnBean.setCode("2");
                returnBean.setMsg("该激活码不属于当前设备！");
            }
        } else {
            returnBean.setCode("1");
            returnBean.setMsg("激活码无效！");
        }
        return returnBean;
    }

}
