package com.hypocriticalfish.crowdfunding.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.hypocriticalfish.crowdfunding.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Hypocritical Fish
 * @Create 2022/4/26 22:03
 * @Description 通用工具方法类
 */

public class CrowdfundingUtil {

    /**
     * @Description  调用第三方短信接口发送验证码
     * @date 2022/5/8 15:47
     * @param phoneNum  接收验证码的手机号
     * @param appcode   第三方api的认证码
     * @param templateId    模板id，0002为注册，0003为登录
     * @return com.hypocriticalfish.crowdfunding.util.ResultEntity<java.lang.String>
    */
    public static ResultEntity<String> sendShortMessage(String phoneNum,String appcode,String templateId){
        String host = "https://miitangs09.market.alicloudapi.com";
        String path = "/v1/tools/sms/code/sender";
        String method = "POST";
        //String appcode = "c91e49f1530242858bed37c751f5ed07";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        //需要给X-Ca-Nonce的值生成随机字符串，每次请求不能相同
        headers.put("X-Ca-Nonce", UUID.randomUUID().toString());
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();

        //生成验证码
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 10);
            sb.append(random);
        }

        String code = sb.toString();

        bodys.put("phoneNumber", phoneNum);
        bodys.put("reqNo", "reqNo");
        bodys.put("smsTemplateNo", templateId);
        bodys.put("smsSignId", "0000");
        bodys.put("verifyCode", code);


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();

            Logger logger = LoggerFactory.getLogger(CrowdfundingUtil.class);
            logger.info(response.toString());
            logger.info(EntityUtils.toString(response.getEntity()));

            //状态码：200正常  400URL无效  401：appcode错误  403 次数用完  500 API网关错误
            int statusCode = statusLine.getStatusCode();

            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode == 200) {
                return ResultEntity.successWithData(code);
            }
            return ResultEntity.failed(reasonPhrase);

            //System.out.println(response.toString());
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * @param request
     * @return boolean
     * @Description 判断一个请求是否为ajax请求
     * @date 2022/5/2 15:29
     */
    public static boolean judgeRequestType(HttpServletRequest request) {

        // 1. 获取请求头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Request-With");

        // 2. 判断
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
    }


    /**
     * @param source 明文字符串
     * @return java.lang.String 加密的结果
     * @Description 对明文字符串进行md5加密
     * @date 2022/5/2 15:29
     */
    public static String md5(String source) {
        // 1.判断source是否有效
        if (source == null || source.length() == 0) {
            // 2.如果不是有效字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            // 3.获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 4.获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

            // 5.执行加密
            byte[] output = messageDigest.digest(input);

            // 6.创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 7.按照16进制将bigInteger的值转换为字符串
            int radix = 16;
            String encode = bigInteger.toString(radix).toUpperCase();

            return encode;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 专门负责上传文件到OSS服务器的工具方法
     * @param endpoint			OSS参数
     * @param accessKeyId		OSS参数
     * @param accessKeySecret	OSS参数
     * @param inputStream		要上传的文件的输入流
     * @param bucketName		OSS参数
     * @param bucketDomain		OSS参数
     * @param originalName		要上传的文件的原始文件名
     * @return	包含上传结果以及上传的文件在OSS上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(
            String endpoint,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 生成上传文件在OSS服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用UUID生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");

        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));

        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;

        try {
            // 调用OSS客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);

            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();

            // 根据响应状态码判断请求是否成功
            if(responseMessage == null) {

                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;

                // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();

                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();

                // 当前方法返回失败
                return ResultEntity.failed("当前响应状态码="+statusCode+" 错误消息="+errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();

            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        } finally {

            if(ossClient != null) {

                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }

}
