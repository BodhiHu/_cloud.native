package bodhitree.tree.lib.aliyun.mock;

import bodhitree.tree.lib.MobilePhone;
import bodhitree.tree.lib.Result;
import bodhitree.tree.lib.aliyun.SMS;
import com.aliyuncs.exceptions.ClientException;

import java.io.IOException;

public class SMSMock implements SMS {

    public Result sendCode (MobilePhone mobilePhone, String code)
        throws ClientException, IOException, IllegalArgumentException {

        return new Result(Result.SUCCESS, "mock_code=" + code);
    }
}
