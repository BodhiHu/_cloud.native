package BodhiTree.tree.lib.aliyun.mock;

import BodhiTree.tree.lib.MobilePhone;
import BodhiTree.tree.lib.Result;
import BodhiTree.tree.lib.aliyun.SMS;
import com.aliyuncs.exceptions.ClientException;

import java.io.IOException;

public class SMSMock implements SMS {

    public Result sendCode (MobilePhone mobilePhone, String code)
        throws ClientException, IOException, IllegalArgumentException {

        return new Result(Result.SUCCESS, "mock_code=" + code);
    }
}
