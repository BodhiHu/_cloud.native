package bodhitree.tree.lib;

import org.springframework.util.StringUtils;

public class MobilePhone {
    public String countryCode;
    public String number;

    public MobilePhone (String mobilePhone) {
        String [] parts = mobilePhone.split("-");
        normalize(this, parts[0], parts[1]);
    }
    public MobilePhone (String countryCode, String number) {
        normalize(this, countryCode, number);
    }

    public static MobilePhone normalize(MobilePhone oThis, String countryCode, String number) {
        oThis.countryCode = countryCode.trim().replace("+", "");
        oThis.number = number.trim();
        return oThis;
    }

    public MobilePhone () {}

    public String value() {
        return countryCode + "-" + number;
    }

    public boolean isValid () {
        return (!StringUtils.isEmpty(countryCode) && !StringUtils.isEmpty(number));
    }
}
