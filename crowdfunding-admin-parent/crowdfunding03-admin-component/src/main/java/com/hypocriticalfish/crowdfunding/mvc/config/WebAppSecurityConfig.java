package com.hypocriticalfish.crowdfunding.mvc.config;

import com.hypocriticalfish.crowdfunding.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/4 20:34
 * @Description
 */
@Configuration // 当前类为配置类
@EnableWebSecurity  // 启用web环境下权限控制功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
//启用全局方法权限控制,prePostEnabled=true保证@PreAuthority、@PostAuthority、 @PreFilter、 @PostFilter 生效
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        //临时使用内存版登录模式
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security
                .authorizeRequests()                            // 对请求进行授权
                .antMatchers("/admin/to/login/page.html")  // 针对登录页进行设置
                .permitAll()
                .antMatchers("/bootstrap/**")       // 针对静态资源进行设置， 无条件访问
                .permitAll()
                .antMatchers("/assets/**")       // 针对静态资源进行设置， 无条件访问
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/fonts/**")
                .permitAll()
                .antMatchers("/img/**")
                .permitAll()
                .antMatchers("/jquery/**")
                .permitAll()
                .antMatchers("/layer/**")
                .permitAll()
                .antMatchers("/script/**")
                .permitAll()
                .antMatchers("/ztree/**")
                .permitAll()
                .antMatchers("/admin/get/page.html")
                .hasRole("经理")
                .antMatchers("/admin/get/page.html")
                .access("hasRole('经理') OR hasAuthority('user:get')")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, e) -> {
                    request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                    request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                })
                .and()
                .csrf().disable()                               // 关闭防跨站请求伪造
                .formLogin()                                    // 开启表单登录功能
                .loginPage("/admin/to/login/page.html")                // 指定登录页面
                .loginProcessingUrl("/security/do/login.html")    // 指定处理登录请求的地址
                .defaultSuccessUrl("/admin/to/main/page.html")         // 指定登录成功后前往的地址
                .usernameParameter("loginAcct")                 // 账号的请求参数名称
                .passwordParameter("userPswd")                  // 密码的请求参数名称
                .and()
                .logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
        ;
        ;

    }

}
