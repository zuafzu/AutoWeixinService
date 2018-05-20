package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileController {

    // 激活激活码
    @GetMapping(value = "/codeActivated")
    public String codeActivated() {
        // 查找是否有效（激活码和设备id是否存在，是否已激活，设备id但是否有效）

        // 激活（修改激活码状态为激活，开始日期和截至日期填充数据）

        return "cyf hello!";
    }

}
