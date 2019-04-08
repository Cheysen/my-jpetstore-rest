package pre.chl.mypetstore.domain;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Account implements Serializable {

  private static final long serialVersionUID = 8751282105532159742L;
  private Integer aid;
  private String username;
  private String password;
  private String email;
  private String state;
  private Date lastpasswordresetdate;
  private Integer aexist;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date loginTime;
  //用户的角色集合。用户登录后就可以获得它自己的权限（权限是查出来的）
  private List<Authority> authorities;


  public Integer getAid() {
    return aid;
  }

  public void setAid(Integer aid) {
    this.aid = aid;
  }

  public Date getLastpasswordresetdate() {
    return lastpasswordresetdate;
  }

  public void setLastpasswordresetdate(Date lastpasswordresetdate) {
    this.lastpasswordresetdate = lastpasswordresetdate;
  }

  public Integer getAexist() {
    return aexist;
  }

  public void setAexist(Integer aexist) {
    this.aexist = aexist;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }


  public String getUsername() {
    return username;
  }



  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }
}
