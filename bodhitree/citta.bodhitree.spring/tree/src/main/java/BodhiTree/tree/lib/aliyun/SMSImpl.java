package BodhiTree.tree.lib.aliyun;

import BodhiTree.tree.lib.JSON;
import BodhiTree.tree.lib.MobilePhone;
import BodhiTree.tree.lib.Result;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class SMSImpl implements SMS {

    static Logger logger = LoggerFactory.getLogger(SMSImpl.class);

    public Result sendCode (MobilePhone mobilePhone, String code)
        throws ClientException, IOException, IllegalArgumentException {

        if (mobilePhone == null || !mobilePhone.isValid() || StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("invalid mobilePhone or code");
        }

        String phoneNumber = mobilePhone.value().replace("-", "");
        if ("86".equals(mobilePhone.countryCode)) {
            phoneNumber = mobilePhone.value().replace("86-", "");
        }

        DefaultProfile profile = DefaultProfile.getProfile("default", "EbvPQc4FZjMsFHjP", "ahqj0QPdowqUhK4kH3j3nNT0c5Qqv7");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "BodhiTree");
        request.putQueryParameter("TemplateCode", "SMS_134321473");
        request.putQueryParameter("TemplateParam", "{ \"code\": \"" + code + "\" }");

        CommonResponse response = client.getCommonResponse(request);

        JsonNode resJson = JSON.getMapper().readTree(response.getData());

        String smsServiceRetCode = resJson.get("Code").asText();
        if (response.getHttpStatus() == HttpStatus.OK.value() && "ok".equals(smsServiceRetCode.toLowerCase())) {
            return new Result(Result.SUCCESS);
        }

        Result res = new Result(Result.ERROR);
        switch (smsServiceRetCode) {
            case "isv.MOBILE_NUMBER_ILLEGAL":
            default: {
                logger.error("SMS code sending error: " + smsServiceRetCode);
            }
        }

        return res;
    }
}
