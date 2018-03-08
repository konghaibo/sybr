package com.sybr.backend.controller;

import com.sybr.backend.utils.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/wx")
public class WeiXinController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String token = "weixintest";

    @GetMapping("/wechat")
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("-----------------/wx/list---------------------");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if(checkSignature(signature, timestamp, nonce)){
            return echostr;
        }
        return null;
    }

    @GetMapping("/getToken")
    public String getToken() throws Exception {
        System.out.println("-----------------/wx/getToken---------------------");
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("appID","wxdb680670dd119b43");
        params.put("secret","d4624c36b6795d1d99dcf0547af5443d");
        String str = HttpUtil.doGet(url, params);
        System.out.println(str);
        return str;
    }

    @GetMapping("/createMenu")
    public String createMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String FromUserName = request.getParameter("FromUserName");
        System.out.println("FromUserName1111111111111111111111111111111:"+FromUserName);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=7_jNO68ovsXgz76VCXUhNhJi0v4edueh5VL2h-K3q8i5Ngv45i94tXHVszvZzhQ4Eh0Palnn5XSE9mYb8dBTjisKlU5qYtNM-LLMusomIEafKMWeS1GHClodshgwpq87CJ83XK_u3NiK6U4dfaTMThAGALEL";
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject btn1 = new JSONObject();
        btn1.put("type","click");
        btn1.put("name","今日歌曲");
        btn1.put("key","V1001_TODAY_MUSIC");
        array.add(btn1);

        JSONObject btn2 = new JSONObject();
        btn2.put("name","菜单");
        JSONArray btn2Array = new JSONArray();
        JSONObject btn2Child1 = new JSONObject();
        btn2Child1.put("type","view");
        btn2Child1.put("name","授权信息页面");
        String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdb680670dd119b43&redirect_uri=http://f04233a6.ngrok.io/users/list&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
        btn2Child1.put("url",authorizeUrl);
//        JSONObject btn2Child2 = new JSONObject();
//        btn2Child2.put("type","miniprogram");
//        btn2Child2.put("name","wxa");
//        btn2Child2.put("url","http://mp.weixin.qq.com");
//        btn2Child2.put("appid","wx286b93c14bbf93aa");
//        btn2Child2.put("pagepath","pages/lunar/index");
        JSONObject btn2Child3 = new JSONObject();
        btn2Child3.put("type","click");
        btn2Child3.put("name","赞一下我们");
        btn2Child3.put("key","V1001_GOOD");
        btn2Array.add(btn2Child1);
//        btn2Array.add(btn2Child2);
        btn2Array.add(btn2Child3);

        btn2.put("sub_button",btn2Array);
        array.add(btn2);

        json.put("button",array);
        String str = HttpUtil.doPost2(url, json);
        System.out.println(str);
        return str;
    }

    private boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(arr);

        //生成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        //sha1加密
        String temp = getSha1(content.toString());
        return temp.equals(signature);
    }

    private String getSha1(String str){
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
