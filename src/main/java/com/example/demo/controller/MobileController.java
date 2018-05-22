package com.example.demo.controller;

import com.example.demo.bean.CodeBean;
import com.example.demo.bean.ReturnBean;
import com.example.demo.repository.CodeRepository;
import com.example.demo.tools.DesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MobileController {

    @Autowired
    private CodeRepository codeRepository;

    // 是否可以使用体验会员（0可以，1不可以）
    @PostMapping(value = "/canProbation")
    public ReturnBean canProbation(@RequestParam(value = "deviceId", required = false) String deviceId) {
        ReturnBean returnBean = new ReturnBean();
        List<CodeBean> codeBeans = codeRepository.findCodeBeanByDeviceId(deviceId);
        if (codeBeans.size() == 0) {
            returnBean.setCode("0");
            returnBean.setMsg("");
        } else {
            returnBean.setCode("1");
            returnBean.setMsg("");
        }
        return returnBean;
    }

    // 使用体验会员激活码
    @PostMapping(value = "/useProbation")
    public ReturnBean useProbation(@RequestParam(value = "deviceId", required = false) String deviceId) {
        ReturnBean returnBean = new ReturnBean();
        List<CodeBean> codeBeans = codeRepository.findCodeBeanByDeviceId(deviceId);
        if (codeBeans.size() == 0) {
            CodeBean codeBean = new CodeBean();
            codeBean.setIsActivated(1);
            codeBean.setDeviceId(deviceId);
            codeBean.setTotalNum(50L);
            codeBean.setTotalTime(3 * 24 * 60 * 60 * 1000L);
            codeBean.setVipType(0);
            codeBean.setActivatedDate(System.currentTimeMillis());
            codeBean.setEndDate(System.currentTimeMillis() + codeBean.getTotalTime());
            // 生成密钥key
            String key = "";
            try {
                key = DesTools.encrypt("" + System.currentTimeMillis(), "gaimingbao").
                        replace("+", "a").
                        replace("/", "b").
                        replace("==", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            codeBean.setmKey(key);
            // 保存数据
            codeRepository.save(codeBean);
            returnBean.setCode("0");
            returnBean.setMsg("激活成功！");
            returnBean.setData(codeBean);
        }else{
            returnBean.setCode("1");
            returnBean.setMsg("无法使用体验会员！");
        }
        return returnBean;
    }

    // 激活激活码
    @PostMapping(value = "/codeActivated")
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
                    codeBean.setActivatedDate(System.currentTimeMillis());
                    codeBean.setEndDate(System.currentTimeMillis() + codeBean.getTotalTime());
                    codeRepository.save(codeBean);
                    returnBean.setCode("0");
                    returnBean.setMsg("激活码已激活！");
                    returnBean.setData(codeBean);
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
