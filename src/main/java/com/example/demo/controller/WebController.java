package com.example.demo.controller;

import com.example.demo.bean.CodeBean;
import com.example.demo.bean.ReturnBean;
import com.example.demo.repository.CodeRepository;
import com.example.demo.tools.DesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WebController {

    @Autowired
    private CodeRepository codeRepository;

    // 登陆
    @PostMapping(value = "/login")
    public ReturnBean login(@RequestParam(value = "userName", required = false) String userName,
                            @RequestParam(value = "passWord", required = false) String passWord) {
        ReturnBean returnBean = new ReturnBean();
        if (userName.equals("root") && passWord.equals("root")) {
            returnBean.setCode("0");
            returnBean.setMsg("登陆成功！");
        } else {
            if (!userName.equals("root")) {
                returnBean.setCode("1");
                returnBean.setMsg("用户不存在！");
            } else {
                returnBean.setCode("2");
                returnBean.setMsg("密码不正确！");
            }
        }
        return returnBean;
    }

    // 插入激活码（id，设备id，会员类型，是否激活，激活日期，到期日期）
    @PostMapping(value = "/addCode")
    public ReturnBean addCode(@RequestParam(value = "deviceId", required = false) String deviceId,
                              @RequestParam(value = "totalNum", required = false) Long totalNum,
                              @RequestParam(value = "totalTime", required = false) Long totalTime,
                              @RequestParam(value = "vipType", required = false) Integer vipType) {
        CodeBean codeBean = new CodeBean();
        codeBean.setIsActivated(0);
        codeBean.setDeviceId(deviceId);
        codeBean.setTotalNum(totalNum);
        codeBean.setTotalTime(totalTime);
        codeBean.setVipType(vipType);
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
        ReturnBean returnBean = new ReturnBean();
        returnBean.setCode("0");
        returnBean.setMsg("创建成功！");
        returnBean.setData(codeBean);
        return returnBean;
    }

    // 查找全部激活码（最近100条）
    @PostMapping(value = "/findAllCode")
    public ReturnBean findAllCode() {
        // 降序Sort.Direction.DESC
        List<CodeBean> codeBeans = codeRepository.findAll(new Sort(Sort.Direction.DESC,"id"));
        List<CodeBean> codeBeanList = new ArrayList<>();
        if (codeBeans.size() > 100) {
            codeBeanList.addAll(codeBeans.subList(0, 100));
        } else {
            codeBeanList.addAll(codeBeans);
        }
        ReturnBean returnBean = new ReturnBean();
        returnBean.setCode("0");
        returnBean.setMsg("");
        returnBean.setData(codeBeanList);
        return returnBean;
    }

    // 查找全部激活码（通过KEY）
    @PostMapping(value = "/findCodeByKey")
    public ReturnBean findCodeByKey(@RequestParam(value = "mKey", required = false) String mKey) {
        CodeBean codeBean = codeRepository.findCodeBeanByKey(mKey);
        ReturnBean returnBean = new ReturnBean();
        returnBean.setCode("0");
        returnBean.setMsg("");
        returnBean.setData(codeBean);
        return returnBean;
    }

    // 查找全部激活码（通过设备Id）
    @PostMapping(value = "/findCodeByDeviceId")
    public ReturnBean findCodeByDeviceId(@RequestParam(value = "deviceId", required = false) String deviceId) {
        List<CodeBean> codeBeans = codeRepository.findCodeBeanByDeviceId(deviceId);
        ReturnBean returnBean = new ReturnBean();
        returnBean.setCode("0");
        returnBean.setMsg("");
        returnBean.setData(codeBeans);
        return returnBean;
    }


}
