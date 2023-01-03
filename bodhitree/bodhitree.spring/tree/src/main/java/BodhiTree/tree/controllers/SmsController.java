package BodhiTree.tree.controllers;

import BodhiTree.tree.lib.Result;
import BodhiTree.tree.services.SmsService;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path="/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/sendCode")
    Result sendCode (
        @RequestParam(required=true) String phone
    ) throws ClientException, IOException {

        return smsService.sendCode(phone);
    }


}
