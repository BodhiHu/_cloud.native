package bodhitree.tree.lib.aliyun;

import bodhitree.tree.lib.MobilePhone;
import bodhitree.tree.lib.Result;
import com.aliyuncs.exceptions.ClientException;

import java.io.IOException;

public interface SMS {
    Result sendCode (MobilePhone mobilePhone, String code)
        throws ClientException, IOException, IllegalArgumentException;
}

