package bodhitree.tree.services;

import bodhitree.tree.i18n.I18N;
import bodhitree.tree.lib.MobilePhone;
import bodhitree.tree.lib.RestException;
import bodhitree.tree.lib.Result;
import bodhitree.tree.lib.auth.AuthUserDetails;
import bodhitree.tree.lib.auth.JwtToken;
import bodhitree.tree.lib.auth.JwtTokenUtil;
import bodhitree.tree.models.Token;
import bodhitree.tree.models.User;
import bodhitree.tree.models.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static bodhitree.tree.lib.Result.SUCCESS;

@Service
public class AuthService  implements UserDetailsService {

    static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /* implements UserDetailsService *************************************************************/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findById(username);
        if (!optUser.isPresent()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        User user = optUser.get();
        return new AuthUserDetails(user.getId() + "", user.getPassword());
    }
    /* ******************************************************************************************/

    //@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    //public ResponseEntity<?> authenticate(@RequestBody User user) {
    //    return doAuthenticate(user);
    //}

    public Result register (User newUser, String smsCode) throws RestException {

        String rawPwd  = newUser.getPassword();

        User
            .validator().checkUser(newUser);
        Token
            .validator().checkSmsCode(new MobilePhone(newUser.getMobilePhone()), smsCode);

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser = userRepo.save(newUser);

        return this.authenticate(newUser, rawPwd);
    }

    public Result login (User user) throws RestException {
        User.validator().checkUser(user);

        User savedUser = this.findUserIfPwdMatch(user);
        String rawPwd = user.getPassword();

        return this.authenticate(savedUser, rawPwd);
    }

    public Result logout () {
        return new Result(Result.SUCCESS);
    }

    public Result resetPwd (String mobilePhone, String smsCode, String password) throws RestException {
        User.validator()
            .checkMobilePhone(mobilePhone)
            .checkPassword(password);
        Token
            .validator()
            .checkSmsCode(new MobilePhone(mobilePhone), smsCode);

        User user = userRepo.findByMobilePhone(mobilePhone);
        user.setPassword(passwordEncoder.encode(password));

        userRepo.save(user);

        return new Result(SUCCESS);
    }

    public Result authenticate (User user, String rawPwd) {
        Result res;
        final UserDetails userDetails = loadUserByUsername(user.getId() + "");

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getId() + "",
                rawPwd
            )
        );

        final String token = jwtTokenUtil.generateToken(userDetails);

        res = new Result()
            .code(Result.SUCCESS)
            .data(new JwtToken(token));

        return res;
    }

    public User findUserIfPwdMatch(User user) throws RestException {
        if (null == user) {
            return null;
        }
        if (null == user.getPassword()) {
            return null;
        }

        User savedUser = null;

        if (!StringUtils.isEmpty(user.getMobilePhone())) {
            savedUser = userRepo.findByMobilePhone(user.getMobilePhone());
        }
        if (null == savedUser && !StringUtils.isEmpty(user.getEmail())) {
            savedUser = userRepo.findByEmail(user.getEmail());
        }
        if (null == savedUser && !StringUtils.isEmpty(user.getUsername())) {
            savedUser = userRepo.findByEmail(user.getEmail());
        }

        if (null == savedUser) {
            throw new RestException(HttpServletResponse.SC_NOT_FOUND,
                new Result(Result.NOT_FOUND, I18N.t("PASSWORD_NOT_MATCH"))
            );
        }

        if (!passwordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
            throw new RestException(HttpServletResponse.SC_UNAUTHORIZED,
                new Result(Result.WRONG_PWD, I18N.t("PASSWORD_NOT_MATCH"))
            );
        }

        return savedUser;
    }
}
