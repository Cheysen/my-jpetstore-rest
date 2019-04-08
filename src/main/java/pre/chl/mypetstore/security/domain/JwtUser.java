package pre.chl.mypetstore.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class JwtUser implements UserDetails {
    private final Integer id;
    private final String username;
    private final String password;
    private final String state;
    private final String email;
    private final Date lastPasswordResetDate;
    private final boolean enabled;
    private final Date loginTime;
    private final Collection<?extends GrantedAuthority> authorities;
    public JwtUser(Integer id, String username, String password,String state,String email,Date lastPasswordResetDate,boolean enabled,Date loginTime, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.state = state;
        this.email = email;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.loginTime = loginTime;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore   //将JwtUser序列化时，有些属性的值我们是不序列化出来的，所以可以加这个注解
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    @JsonIgnore
    public Integer getId() {
        return id;
    }
    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public Date getLoginTime() {
        return loginTime;
    }



}
