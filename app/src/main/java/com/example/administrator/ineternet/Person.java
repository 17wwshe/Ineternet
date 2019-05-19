package com.example.administrator.ineternet;

/*
 * Created by Administrator on 2018/8/9.
 */

import java.util.List;

public class Person
{
    private int error_code;
    private String reason;
    private List<Result> result; //result是数组所以用集合泛型的形式声明
//构造
    Person(int error_code,String reason,List<Result> result)
    {
        this.error_code = error_code;
        this.reason = reason;
        this.result = result;
    }

    int getError_code()
    {
        return error_code;
    }

    String getReason()
    {
        return reason;
    }

    List<Result> getResult()
    {
        return result;
    }

    @Override //一定要加个toString才会转化为String字符串的形式输出
    public String toString()
    {
        return "result:"+result;
    }
}
