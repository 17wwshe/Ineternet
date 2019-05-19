package com.example.administrator.ineternet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    //接口地址
//    private static final String BASE_URL = "http://apis.haoservice.com/lifeservice/Joke/ContentList&key=your_AppKey";
//    private static final String BASE_URL = "http://apis.haoservice.com/lifeservice/Joke/ContentList";
    private static final String BASE_URL = "http://www.baidu.com";
    private static final String Baseurl  = "http://apis.haoservice.com/lifeservice/Joke/ContentList?pagesize=10&page=1&" +
            "key=8b3264c50ab346f39d1b4eac0dd5bc1c";

    private Button BtSame;
    private Button BtDif;
    private Button Btpost;
    private OkHttpClient client;
    private Request request;
//    private String result;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client=new OkHttpClient();
        setContentView(R.layout.activity_main);
        text=(TextView) findViewById(R.id.textView);
        //按钮同步

        BtSame = (Button) findViewById(R.id.button_same);
            BtSame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        getSync();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        //按钮异步
        BtDif = (Button)findViewById(R.id.button_dif);
            BtDif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        getAsync();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

        Btpost = (Button)findViewById(R.id.bt_post);
            Btpost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        postAsync();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

    }

    //异步请求
    public void getAsync() throws Exception
    {

        request=new Request.Builder()
                .url(Baseurl)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG",BASE_URL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    final String result=response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /* 原生解析
                            String forResult = part1(result);
                            part2(forResult);
                            */
                            part3(result);
                        }
                    });

                }
                else
                {
                    Log.d("tag","failed");
                }

            }
        });
    }

    //利用原生解析json对象
    private String part1(String jsonData)
    {
        JSONObject obj = null;
        String forResult;
        forResult = "not Found!";
        if (!jsonData.isEmpty())
        {
            try
            {
                obj = new JSONObject(jsonData);
                forResult = obj.optString("result");//把result对象提取出来
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return forResult;

    }

    //利用原生解析json数组[]
    private void part2(String jsonData)
    {
        try
        {
            JSONArray jsonArray= new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (null !=  jsonObject)
                {
                    String title = "标题："+jsonObject.optString("title");
                    String content = "正文："+jsonObject.optString("content");
                    String type = "类型："+jsonObject.optString("type");
                    String updatetime = "更新时间："+jsonObject.optString("updatetime");
                    text.append(title+"\n"+content+"\n"+type+"\n"+updatetime+"\n\n");
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void part3(String jsonData)
    {
        /*
            Gson gson = new Gson();
            Person person = gson.fromJson(jsonData,Person.class);
//            JSONArray jsonArray = person.getResult();
            String error = person.getError_code()+"";
            String reason = person.getReason();
            text.setText(error);
            text.append(reason);         //可以输出，但是不能输出result，不能用String来表示
//            for (int i=0;i<jsonArray.length();i++)
//            {
//                //处理json数组
//            }
*/
        Gson gson = new Gson();
        Person person = gson.fromJson(jsonData,Person.class);
        Log.d("person",person+"");
        List<Result> result = person.getResult();
        text.append(result+"");
        /*

      迭代器
        Iterator it = user.iterator();
        while(it.hasNext()) {
            text.append(it.next() + "\n");  //输出是地址？？
        }
*/
    }

    //同步请求
    private void getSync() throws Exception
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    request=new Request.Builder()
                            .url(BASE_URL)
                            .build();
                    Response response = null;
                    Call call = client.newCall(request);
                    response = call.execute();
                    if (response.isSuccessful()) {

                        Log.d("kwwl", "response.code()==" + response.code());
                        Log.d("kwwl", "response.message()==" + response.message());
                        Log.d("kwwl", "res==" + response.body().string());

//                        text.setText("同步请求成功");
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                    else
                    {
                        text.setText("同步请求失败");
                        Log.d("Fail","get请求失败");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //post 请求
    private void postAsync() throws Exception
    {
        final String url = " http://apis.haoservice.com/lifeservice/Joke/ContentList?pagesize=10&page=1&key=8b3264c50ab346f39d1b4eac0dd5bc1c";
        RequestBody formBody = new FormBody.Builder()   //创建表单请求体
                                .add("username","17wwshe")
                                .build();
        request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody)//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.d("TAG  post失败",url);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if(response.isSuccessful())
                {
                    final  String content=response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG post成功",content);
                            text.setText(content);
                        }
                    });

                }
                else
                {
                    Log.d("PostFail ",url);
                }
            }
        });

    }
//成功！！

}
