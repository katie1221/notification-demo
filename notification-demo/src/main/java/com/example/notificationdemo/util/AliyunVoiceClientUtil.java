package com.example.notificationdemo.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 阿里云 语音通话 --- 根据文本转语音模板进行语音通话
 * @author qzz
 * @date 2024/6/14
 */
@Component
@Slf4j
public class AliyunVoiceClientUtil {

    private  String accessKeyId = "你的阿里云Key";
    private  String accessKeySecret = "你的阿里云Secret";

    /**
     * 语音通话到某个用户（阿里云 V1.0 SDK 写法）
     * @param phoneNumber 被叫号码
     * @param ttsParam 语音模板中的变量参数 --- 请按模版参数有序存入
     * @param ttsCode Tts模板ID
     * @return
     */
    public SingleCallByTtsResponse sendSingleCallToUser(String phoneNumber, String ttsCode, String ttsParam, String outId) throws ClientException {
        //1.初始化acsClient实例 暂时不支持多region
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dyvmsapi", "dyvmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //2.创建请求并设置参数
        SingleCallByTtsRequest request = new SingleCallByTtsRequest();
        //必填-被叫号码
        request.setCalledNumber(phoneNumber);
        //必填-Tts模板ID
        request.setTtsCode(ttsCode);
        //语音模板中的变量参数 示例：{"name":"123456","rainfall":50} 的JSON字符串
        request.setTtsParam(ttsParam);
        //可选-音量 取值范围 0--100 默认取值 100
        request.setVolume(100);
        //可选-播放次数 默认取3
        request.setPlayTimes(3);
        //可选-语音通话的语速。取值范围为：-500~500
        request.setSpeed(5);
        //可选-外部扩展字段,此ID将在回执消息中带回给调用方
        request.setOutId(outId);

        //3.发送请求并获取响应
        SingleCallByTtsResponse singleCallByTtsResponse = acsClient.getAcsResponse(request);
        if(singleCallByTtsResponse.getCode() != null && singleCallByTtsResponse.getCode().equals("OK")) {
            //请求成功
            log.info("processing sendCVoice success！RequestId = %s , Code = %s , phone = %s",
                    singleCallByTtsResponse.getRequestId(), singleCallByTtsResponse.getCode(), phoneNumber);
        }
        return singleCallByTtsResponse;
    }
}
