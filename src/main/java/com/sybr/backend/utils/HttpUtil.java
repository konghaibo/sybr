package com.sybr.backend.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP 请求工具类
 * @author wistronits
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static String doGet(String url)  throws Exception {
        return doGet(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) throws Exception {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
            }
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpPost = new HttpGet(apiUrl);
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println("执行状态码 : " + statusCode);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
                //EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
            httpclient.close();
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public static String doPost2(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            System.out.println("httpStr:"+httpStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl API接口URL
     * @param json   JSON对象
     * @return
     */
    public static String doPostSSL(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    public static String doGetHttps(String url, String userName, String password) throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, trustAllCerts, new SecureRandom());
            HostnameVerifier hostnameVerifier = new HttpUtil().new NullHostNameVerifier();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,hostnameVerifier);

            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            //GET请求
            HttpGet httpget = new HttpGet(url);
            String auth = "Basic " + Base64.encode((userName + ":" + password).getBytes());
            httpget.addHeader("Authorization", auth);

            logger.info("执行请求：" + httpget.getRequestLine());
            response = httpclient.execute(httpget);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_ACCEPTED) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }

            String httpStr = EntityUtils.toString(entity);
            logger.info("----------------------------------------");
            logger.info("响应代码：" + statusCode);
            logger.info("内容：" + httpStr);
            logger.info("----------------------------------------");

            return httpStr;
        } finally {
            response.close();
            httpclient.close();
        }
    }

    public static String doHttps(String url, String userName, String password, String method, String jsonValue) throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, trustAllCerts, new SecureRandom());
            HostnameVerifier hostnameVerifier = new HttpUtil().new NullHostNameVerifier();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,hostnameVerifier);

            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            //请求
            HttpRequestBase reqeust = null;
            StringEntity stringEntity = null;
            if(StringUtils.isNotEmpty(jsonValue)){
                stringEntity = new StringEntity(jsonValue.toString(), "UTF-8");//解决中文乱码问题
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
            }
            if(StringUtils.equals("POST", method)) {
                reqeust = new HttpPost(url);
                if(StringUtils.isNotEmpty(jsonValue)) {
                    ((HttpPost) reqeust).setEntity(stringEntity);
                }
            }else if(StringUtils.equals("PUT", method)) {
                reqeust = new HttpPut(url);
                if(StringUtils.isNotEmpty(jsonValue)) {
                    ((HttpPut) reqeust).setEntity(stringEntity);
                }
            }else if(StringUtils.equals("PATCH", method)) {
                reqeust = new HttpPatch(url);
                if(StringUtils.isNotEmpty(jsonValue)) {
                    ((HttpPatch) reqeust).setEntity(stringEntity);
                }
            }else if(StringUtils.equals("GET", method)) {
                reqeust = new HttpGet(url);
            }else if(StringUtils.equals("DELETE", method)) {
                reqeust = new HttpDelete(url);
            }

            String auth = "Basic " + Base64.encode((userName + ":" + password).getBytes());
            reqeust.addHeader("Authorization", auth);

            logger.info("执行请求：" + reqeust.getRequestLine());
            response = httpclient.execute(reqeust);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_ACCEPTED) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }

            String httpStr = EntityUtils.toString(response.getEntity());
            logger.info("----------------------------------------");
            logger.info("响应代码：" + response.getStatusLine().getStatusCode());
            logger.info("内容：" + httpStr);
            logger.info("----------------------------------------");

            return httpStr;
        }catch (Exception e){
            logger.error("path error:"+url, e);
        }finally {
            response.close();
            httpclient.close();
        }
        return null;
    }


    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        HttpUtil.doHttps("https://10.5.43.241/redfish/v1/AccountService/Accounts/7/", "ADMIN", "ADMIN", "DELETE", null);
//        HttpUtil.doHttps("https://10.5.43.241/redfish/v1/AccountService/Accounts/5", "ADMIN", "ADMIN", "GET", null);
//        HttpUtil.doHttps("https://10.5.43.241/redfish/v1/AccountService/Accounts/", "ADMIN", "ADMIN", "POST", "{\"UserName\":\"User_NameA\",\"Password\":\"User_PasswordB\",\"RoleId\":\"Operator\",\"Enabled\":true}");
        HttpUtil.doHttps("https://10.5.43.241/redfish/v1/AccountService/Accounts/7/", "ADMIN", "ADMIN", "PATCH", "{\"UserName\":\"User_NameA1\",\"Password\":\"User_PasswordB1\",\"RoleId\":\"Operator\",\"Enabled\":true}");

//        HttpHost target = new HttpHost("10.5.43.241", 443, "https");
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials(
//                new AuthScope(target.getHostName(), target.getPort()),
//                new UsernamePasswordCredentials("ADMIN", "ADMIN"));
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setDefaultCredentialsProvider(credsProvider).build();
//        try {
//
//            // Create AuthCache instance
//            AuthCache authCache = new BasicAuthCache();
//            // Generate BASIC scheme object and add it to the local
//            // auth cache
//            BasicScheme basicAuth = new BasicScheme();
//            authCache.put(target, basicAuth);
//
//            // Add AuthCache to the execution context
//            HttpClientContext localContext = HttpClientContext.create();
//            localContext.setAuthCache(authCache);
//
//            HttpGet httpget = new HttpGet("https://10.5.43.241/redfish/v1/AccountService/Accounts/");
//
//            System.out.println("Executing request " + httpget.getRequestLine() + " to target " + target);
//            for (int i = 0; i < 3; i++) {
//                CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);
//                try {
//                    System.out.println("----------------------------------------");
//                    System.out.println(response.getStatusLine());
//                    System.out.println(EntityUtils.toString(response.getEntity()));
//                } finally {
//                    response.close();
//                }
//            }
//        } finally {
//            httpclient.close();
//        }

//        //SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,new TrustSelfSignedStrategy()).build();
//        SSLContext sslcontext = SSLContext.getInstance("TLS");
//        sslcontext.init(null, trustAllCerts, new SecureRandom());
//
//        HostnameVerifier hostnameVerifier = new HttpUtil().new NullHostNameVerifier();
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,hostnameVerifier);
//
//        // 凭据提供器
////        CredentialsProvider credsProvider = new BasicCredentialsProvider();
////        credsProvider.setCredentials(
////                // 认证范围
////                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
////                // 认证用户名和密码
////                new UsernamePasswordCredentials("ADMIN", "ADMIN"));
//
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
////                .setDefaultCredentialsProvider(credsProvider)
//                .build();
//        try {
//            //GET请求
//            HttpGet httpget = new HttpGet("https://10.5.43.241/redfish/v1/AccountService/Accounts/");
//            String auth = "Basic " + Base64.encode(("ADMIN" + ":" + "ADMIN").getBytes());
//            httpget.addHeader("Authorization", auth);
//
//            System.out.println("执行请求：" + httpget.getRequestLine());
//            CloseableHttpResponse response = httpclient.execute(httpget);
//            try {
//                System.out.println("----------------------------------------");
//                System.out.println("响应：" + response.getStatusLine());
//                System.out.println("内容：" + EntityUtils.toString(response.getEntity()));
//                System.out.println("----------------------------------------");
//            } finally {
//                response.close();
//            }
//        } finally {
//            httpclient.close();
//        }

    }

    public class NullHostNameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }

    static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    } };
}
