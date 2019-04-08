package pre.chl.mypetstore.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pre.chl.mypetstore.domain.Account;
import pre.chl.mypetstore.domain.Authority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    private JwtUserFactory(){
    }

   // 创建JwtUser的方法
    public static JwtUser create(Account user){
        return new JwtUser(user.getAid(),
                user.getUsername(),
                user.getPassword(),
                user.getState(),
                user.getEmail(),
                user.getLastpasswordresetdate(),
                user.getAexist()==1?true:false,
                user.getLoginTime(),
                mapToGrantedAuthorities(user.getAuthorities())); //调用下面的静态方法
    }

    /*
        将查询的用户角色集合转换成security框架授权的角色集合
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities){
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
        //把集合变成流，然后用函数式编程的方法处理，最后把流又变成集合

    }

}
