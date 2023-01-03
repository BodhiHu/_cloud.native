package BodhiTree.tree.lib.aliyun;

import BodhiTree.tree.lib.aliyun.mock.SMSMock;

public class Cloud {

    static boolean isMock = false;

    public static void configure(boolean isMock) {
        Cloud.isMock = isMock;
    }

    static SMS sms;
    public static SMS SMSIns() {
        if (isMock && !(sms instanceof SMSMock)) {
            sms = new SMSMock();
        }
        if (!isMock && !(sms instanceof SMSImpl)) {
            sms = new SMSImpl();
        }

        return sms;
    }
}
