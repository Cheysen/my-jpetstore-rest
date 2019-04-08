package pre.chl.mypetstore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pre.chl.mypetstore.security.domain.AuthenticationException;
import pre.chl.mypetstore.security.domain.JwtUser;
import pre.chl.mypetstore.security.service.JwtTokenUtil;
import pre.chl.mypetstore.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;


@RestController
@CrossOrigin
public class AccountController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    AccountService accountService;
    //WebSecurityConfig类里面配置的，用来校验用户名和密码的

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("service")
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam ("username")String uname,@RequestParam("password")String pwd){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        //加密"1"
//        String encode = bCryptPasswordEncoder.encode("1");
//        System.out.println(encode);
        authenticate(uname,pwd);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(uname);
        //然后传入用户生成令牌token
        final String token = jwtTokenUtil.generateToken(userDetails);
        //把token封装到JwtAuthenticationResponse里面返回
        // Return the token
        return ResponseEntity.ok(token);

    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
        public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);    //用户名作为用户的唯一标识（不是用户id）
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
    /**
     * 刷新
     *
     * @param
     * @return
     */
//    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
//    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
//        String authToken = request.getHeader(tokenHeader);
//        final String token = authToken.substring(7);
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
//
//        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
//            String refreshedToken = jwtTokenUtil.refreshToken(token);
//            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//        } else {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }

}
