package bodhitree.tree.controllers;

import bodhitree.tree.lib.JSON;
import bodhitree.tree.lib.RestException;
import bodhitree.tree.lib.Result;
import bodhitree.tree.models.User;
import bodhitree.tree.services.AuthService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path="/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    private static ObjectMapper mapper = JSON.getMapper();

    /**
     * @param httpEntity
     * @return String
     *
     * @details
     *
     *  reqBody: {
     *      "user": {
     *          "mobilePhone": "+86-18645678901",
     *          "password": "very secured pwd",
     *          "name": "Bodhi Hu",
     *          "countryCode": "CN",
     *          "locale": "zh-CN"
     *      },
     *      "smsCode": "476832"
     *  }
     *
     */
    @RequestMapping(value="/register", method=POST, consumes=APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    Result register (HttpEntity<String> httpEntity) throws Exception {
        String json = httpEntity.getBody();

        JsonNode jsonNode = JSON.readTree(json);

        String smsCode = jsonNode.get("smsCode").asText();
        User   newUser = (User) JSON.parse(jsonNode.get("user"), User.class);

        return authService.register(newUser, smsCode);
    }

    @PostMapping("/login")
    Result login (@RequestBody User user) throws RestException {
        return authService.login(user);
    }

    @DeleteMapping("/logout")
    Result logout() {
        return authService.logout();
    }

    @PostMapping("/resetPwd")
    Result resetPwd(HttpEntity<String> httpEntity)
        throws IOException, RestException {

        String json = httpEntity.getBody();

        JsonNode jsonNode = mapper.readTree(json);

        String smsCode      = jsonNode.get("smsCode").asText();
        String mobilePhone  = jsonNode.get("mobilePhone").asText();
        String password     = jsonNode.get("password").asText();

        return authService.resetPwd(mobilePhone, smsCode, password);
    }

}

