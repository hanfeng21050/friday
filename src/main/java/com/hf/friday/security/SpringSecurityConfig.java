package com.hf.friday.security;

import com.hf.friday.security.authentication.MyAuthenticationFailureHandler;
import com.hf.friday.security.authentication.MyAuthenticationSuccessHandler;
import com.hf.friday.security.authentication.MyLogoutSuccessHandler;
import com.hf.friday.security.authentication.RestAuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //'X-Frame-Options' to 'deny'
        http.headers().frameOptions().sameOrigin();

        //任何http请求都需要进行验证
        http.authorizeRequests()
                .antMatchers("/treetable-lay/**",
                        "/xadmin/**",
                        "/ztree/**",
                        "/static/**",
                        "/my/**",
                        "/flow-loading/**",
                        "login.html")
                .permitAll()
                .anyRequest()
                .authenticated();
        //指定登录页面和指定默认url
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login").permitAll()
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .logout().permitAll()
                .invalidateHttpSession(true)//session失效
                .deleteCookies("JESSIONID")
                .logoutSuccessHandler(myLogoutSuccessHandler);

        //异常处理
        http.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);
    }



}
