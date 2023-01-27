package BodhiTree.tree.lib.aliyun;

import BodhiTree.tree.lib.MobilePhone;
import BodhiTree.tree.lib.Result;
import com.aliyuncs.exceptions.ClientException;

import java.io.IOException;

public interface SMS {
    Result sendCode (MobilePhone mobilePhone, String code)
        throws ClientException, IOException, IllegalArgumentException;
}

