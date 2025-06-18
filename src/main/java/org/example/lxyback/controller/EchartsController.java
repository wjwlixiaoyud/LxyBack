package org.example.lxyback.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.io.unit.DataUnit;
import org.example.lxyback.common.Result;
import org.example.lxyback.entity.User;
import org.example.lxyback.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map=new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/membera")
    public Result membera(){
        List<User> list=userService.list();
        //划分四个季度
        int q1=0;
        int q2=0;
        int q3=0;
        int q4=0;
        for (User user:list){
            Date createTime=user.getCreateTime();
            Quarter quarter=DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1:q1 +=1;break;
                case Q2:q2 +=1;break;
                case Q3:q3 +=1;break;
                case Q4:q4 +=1;break;
                default:break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }
}
