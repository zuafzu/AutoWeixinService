package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    // 插入激活码（id，设备id，会员类型，是否激活，激活日期，到期日期）
    @GetMapping(value = "/addCode")
    public String addCode() {

        return "cyf hello!";
    }

    // 查找激活码（string为空全部查找）
    @GetMapping(value = "/findCode")
    public String findCode(String string) {

        return "cyf hello!";
    }


}
