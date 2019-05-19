package com.example.administrator.ineternet;

/*
 * Created by Administrator on 2018/8/9.
 */

public class Result {
    private String title;
    private String content;
    private String type;
    private String updatetime;
    //构造
    Result(String title,String content,String type,String updatetime)
    {
        this.title = title;
        this.content = content;
        this.type = type;
        this.updatetime = updatetime;
    }

    String getTitle()
    {
        return title;
    }
    String getContent()
    {
        return content;
    }
    String getType()
    {
        return type;
    }
    String getUpdatetime()
    {
        return updatetime;
    }
    //一定要加toString
    @Override
    public String toString()
    {
        return  "\n标题："+title+
                "\n正文:"+content+
                "\n类型："+type+
                "\n更新时间："+updatetime;
    }

}
