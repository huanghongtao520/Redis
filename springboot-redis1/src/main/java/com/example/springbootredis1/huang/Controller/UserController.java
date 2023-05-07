package com.example.springbootredis1.huang.Controller;

import com.example.springbootredis1.huang.pojo.User;
import com.example.springbootredis1.huang.utils.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    @Qualifier("redisTemplate")
    //使用自定义的redisTemplate
    private RedisTemplate redisTemplate;

    // 导入自定义的工具类
    // redisTemplate.opsForValue().set("user",user);
    // 直接通过redisUtil.set()设置，简化开发
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/*")
    @ResponseBody
    public  String test1() throws JsonProcessingException {
        User user = new User("黄宏涛", 22);
        //redisTemplate.opsForValue().set("user",user);
        redisUtil.set("user",user);
        return redisUtil.get("user").toString();
    }
}
