package pre.chl.mypetstore.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pre.chl.mypetstore.security.domain.JwtAuthenticationEntryPoint;
import pre.chl.mypetstore.security.service.JwtAuthenticationTokenFilter;
import pre.chl.mypetstore.security.service.JwtTokenUtil;
import pre.chl.mypetstore.security.service.JwtUserDetailService;

/**
 * 安全配置类
 * 配置哪些请求要经过安全检查
 */
//@SuppressWarnings("SpringJavaAutowiringInspection")   //抑制了一个警告
@Configuration
@EnableWebSecurity    //启用web安全检查
@EnableGlobalMethodSecurity(prePostEnabled = true)    //启用全局方法的安全检查（预处理预授权的属性为true）
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    //未授权响应处理类
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailService jwtUserDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.route.authentication.path}")
    //获取授权令牌的路径（配置文件里面）
    private String authenticationPath;

//    @Autowired
//    //限定实现类实例名
//    @Qualifier("service")   //限定接口UserDetailsService必须绑jwtUserDetailsService
//    private UserDetailsService UserDetailsService;

    @Autowired
    //配置一个全局的授权管理器
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
       // return  new NoOpPasswordEncoder();
    }

//    //全局配置
//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .userDetailsService(this.UserDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }

    @Bean
    @Override
    //授权管理器
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //安全配置
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .cors().and() // 支持跨域访问

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()


                // Un-secure 注册 登录 验证码
                //放在这里面是任何人都可以访问的--不做安全检查的
                .antMatchers(
                        "/login",
                        "/register",
                        "/imagecode",//  验证码
                        "/",
                        "/catalog/**"
                ).permitAll()
                // secure other api
                // 其他api请求都必须做安全校验
                .anyRequest().authenticated();  //除了上面申明的其余的都要权限访问

        // Custom JWT based security filter
        //创建过滤器，过滤jwt请求
        JwtAuthenticationTokenFilter authenticationTokenFilter=new JwtAuthenticationTokenFilter(userDetailsService(), jwtTokenUtil, tokenHeader);
        httpSecurity    //把过滤器添加到安全策略里面去
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        authenticationPath    //从配置文件里面读取出来的
                )

                // allow anonymous resource requests
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                );
    }
}