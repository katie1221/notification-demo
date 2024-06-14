package com.example.notificationdemo.controller;

import com.aliyuncs.exceptions.ClientException;
import com.example.notificationdemo.util.AliyunVoiceClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qzz
 * @date 2024/6/14
 */
@RestController
public class TestController {

    @Autowired
    private AliyunVoiceClientUtil aliyunVoiceClientUtil;

    /**
     * 发送语音通话
     * @param phoneNumber 被叫手机号
     * @param ttsCode Tts模板ID
     * @param ttsParam 语音模板中的变量参数---请按模版参数有序存入
     * @param outId 可选-外部扩展字段,此ID将在回执消息中带回给调用方
     * @return
     */
    @PostMapping(value = "/api/{version}/send/single-call/")
    public void sendSingleCall(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("ttsCode") String ttsCode,
                               @RequestParam("ttsParam") String ttsParam,
                               @RequestParam(value = "outId", required = false) String outId) throws ClientException {

        aliyunVoiceClientUtil.sendSingleCallToUser(phoneNumber,ttsCode,ttsParam,outId);

    }
}
