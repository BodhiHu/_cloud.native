package bodhitree.tree.models;

import bodhitree.tree.i18n.I18N;
import bodhitree.tree.lib.MobilePhone;
import bodhitree.tree.lib.RestException;
import bodhitree.tree.lib.Result;
import bodhitree.tree.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {

    @Autowired
    SmsService smsService;

    static TokenValidator _instance;
    public static TokenValidator instance () {
        if (_instance == null) {
            _instance = new TokenValidator();
        }
        return _instance;
    }

    public TokenValidator() {
        _instance = this;
    }

    public Result checkSmsCode(MobilePhone mobilePhone, String code)
        throws RestException {

        Result res = new Result(Result.SUCCESS);
        if (!smsService.verifyCode(mobilePhone, code)) {
            Result.reset(res, Result.ERROR,  I18N.t("SMS_INVALID_CODE"), "", null);
            throw new RestException(res);
        }

        return res;
    }

}
