package com.tp.serviceley.server.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.VerificationToken;
import com.tp.serviceley.server.model.enums.TokenType;
import com.tp.serviceley.server.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class SmsService {
    @Value("${snsTopicARN}")
    private String topicARN;

    @Autowired
    private AmazonSNSClient amazonSNSClient;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    private static final String AWS_SNS_SMS_TYPE = "AWS.SNS.SMS.SMSType";
    private static final String AWS_SNS_SMS_TYPE_VALUE = "Transactional";
    private static final String AWS_SNS_DATA_TYPE = "String";

    public void sendOtp(User user, TokenType tokenType, Long phone){
        Integer num = ThreadLocalRandom.current().nextInt(100001, 999999);
        String sms = "OTP for verifying your phone number on serviceley is "+num+".";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(num.toString());
        verificationToken.setTokenType(tokenType);
        verificationToken.setUpdatingValue(phone.toString());
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        sendSms(phone, sms);
    }

    public String sendSms(Long phone, String message){
        try {
            int requestTimeout = 3000;
            Map<String, MessageAttributeValue> smsAttributes =
                    new HashMap<>();
            smsAttributes.put(AWS_SNS_SMS_TYPE, new MessageAttributeValue()
                    .withStringValue(AWS_SNS_SMS_TYPE_VALUE)
                    .withDataType(AWS_SNS_DATA_TYPE));

            PublishResult request = amazonSNSClient.publish(new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber("+91"+phone)
                    .withMessageAttributes(smsAttributes)
                    .withSdkRequestTimeout(requestTimeout));
            System.out.println(request);
            log.info(String.valueOf(request));
            /*
            -----------------------------------------------------------------------------------------------------------
            Another way to send sms. But in this way you have to first create an SNS 'Topic' through AWS console
            Then you have to store the SNS topic ARN in application.properties file and fetch its value here.
            And in this way you have to first subscribe the Sns topic created in previous step, then publish the
            message to the topic. And finally unsubscribe the topic as if you don't do so then next time when you
            will send sms on some other number then all numbers which had subscribed the topic earlier will also
            get the sms. So this is not
            recommended for sending sms to a number. But it can be useful if you want to send same sms to multiple
            numbers like promotional sms.
            -----------------------------------------------------------------------------------------------------------
            SubscribeRequest subscribeRequest = new SubscribeRequest(topicARN, "sms", "+91"+phone);
            SubscribeResult sub = amazonSNSClient.subscribe(subscribeRequest);

            PublishRequest publishRequest = new PublishRequest(topicARN, message);
            amazonSNSClient.publish(publishRequest);
            UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest(subscribeRequest.getTopicArn());

            amazonSNSClient.unsubscribe(sub.getSubscriptionArn());
            */
            return "Sms sent successfully!";
        } catch (Exception e) {
            log.error(String.valueOf(e.getStackTrace()));
            throw new BackendException("Some error occurred in sending sms."+e.getMessage());
        }
    }
}