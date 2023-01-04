package BodhiTree.tree.services;

import BodhiTree.tree.i18n.I18N;
import BodhiTree.tree.lib.aliyun.Cloud;
import BodhiTree.tree.models.Token;
import BodhiTree.tree.models.TokenRepo;
import BodhiTree.tree.lib.DateUtils;
import BodhiTree.tree.lib.MobilePhone;
import BodhiTree.tree.lib.NumberUtils;
import BodhiTree.tree.lib.Result;
import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class SmsService {

    static Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    private TokenRepo tokenRepo;

    public Result sendCode (String phone) throws IOException, ClientException {

        MobilePhone mobilePhone = new MobilePhone(phone);

        String code = NumberUtils.randomIntStr(999999, 6);

        Result res = Cloud.SMSIns().sendCode(mobilePhone, code);

        if (res.code == Result.SUCCESS) {
            tokenRepo.upsertByMobilePhone(mobilePhone.value(), code);
        }

        if (StringUtils.isEmpty(res.message)) {
            res.message = res.code == Result.SUCCESS ?
                I18N.t("SMS_SEND_CODE_SUCCESS") : I18N.t("SMS_SEND_CODE_ERROR");
        }

        return res;
    }

    public boolean verifyCode(MobilePhone mobilePhone, String code) {

        Token record = tokenRepo.findByMobilePhone(mobilePhone.value());

        if (record == null) {
            logger.debug("Code not found for " + mobilePhone.value());
            return false;
        }
        if (!record.getData().equals(code)) {
            logger.debug("Code not match");
            return false;
        }
        if (!DateUtils.isFuture(record.getUpdatedAt(), record.getLifespan() * 1000)) {
            logger.debug(
                "Code has expired for " + mobilePhone.value()
                    + ", updated @ " + record.getUpdatedAt() + ", lifespan=" + record.getLifespan() + " seconds"
            );
            return false;
        }

        return true;
    }
}