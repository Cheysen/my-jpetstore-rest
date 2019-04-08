package pre.chl.mypetstore.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pre.chl.mypetstore.domain.Account;
import pre.chl.mypetstore.security.domain.JwtUserFactory;
import pre.chl.mypetstore.service.AccountService;

@Service("service")
public class JwtUserDetailService implements UserDetailsService {
    @Autowired
    AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Account account = accountService.getAccount(username);
        if(account == null){
            throw new UsernameNotFoundException("用户名不存在");
        }else {
            return JwtUserFactory.create(account);
        }
    }
}
