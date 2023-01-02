package bodhitree.tree.models;

import bodhitree.tree.i18n.I18N;
import bodhitree.tree.lib.Result;
import bodhitree.tree.lib.RestException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class UserValidator {

    public UserValidator checkUser(User user) throws RestException {
        this.checkMobilePhone(user.getMobilePhone());
        this.checkPassword(user.getPassword());

        return this;
    }

    public UserValidator checkMobilePhone(String mobilePhone) throws RestException {
        Result res = new Result(Result.SUCCESS);

        if (StringUtils.isEmpty(mobilePhone)) {
            Result.reset(res, Result.ERROR, I18N.t("FIELD_VALUE_INVALID"), I18N.t("MOBILE_PHONE_INVALID"), null);
            throw new RestException(res);
        }

        return this;
    }
    public UserValidator checkPassword(String pwd) throws RestException {
        Result res = new Result(Result.SUCCESS);

        if (StringUtils.isEmpty(pwd) || pwd.length() < 8) {
            Result.reset(res, Result.ERROR, I18N.t("FIELD_VALUE_INVALID"), I18N.t("PASSWORD_VALIDATION_HINT_MSG"), null);
            throw new RestException(res);
        }

        return this;
    }
}
