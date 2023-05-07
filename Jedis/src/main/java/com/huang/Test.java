package com.huang;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class Test {
    public static void main(String[] args) {
        // 连接jedis
        Jedis jedis=new Jedis("127.0.0.1",6379);
        //清空数据
        jedis.flushAll();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","黄宏涛");
        jsonObject.put("age",22);
        // 开启事务
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();
        //添加乐观锁
        jedis.watch(result);
        try{
            multi.set("user1",result);
            multi.set("user2",result);
            //代码抛出异常，事务执行失败
            int i=1/0;
            multi.exec();
        }catch (Exception e){
            //事务执行失败，放弃事务
            multi.discard();
        }finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            //关闭连接
            jedis.close();
        }

    }
}
