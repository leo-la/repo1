package com.leo.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService detailsService;

    private String username;
    private String password;
    @Component
    public class MyApplicationListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

        @Override
        public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
            Object userName = event.getAuthentication().getPrincipal();
            Object credentials = event.getAuthentication().getCredentials();
            username = (String) userName;
            password = (String) credentials;

        }
    }

    public class CustomAuthenticationFailureHandler
            implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            Map<String, Object> data = new HashMap<>();
            data.put("exception", exception.getMessage());
            request.getSession().setAttribute("username",username);
            request.getSession().setAttribute("password",password);
            request.getRequestDispatcher("/user/failure?error=true").forward(request,response);
        }
    }
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        System.out.println(detailsService);
        authenticationProvider.setUserDetailsService(detailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/WEB-INF/templates/index2.html").permitAll()
                .antMatchers("/templates/login.html").permitAll()//访问index.html不要权限验证
                .antMatchers("/user/toIndex").permitAll()//访问index.html不要权限验证
                .antMatchers("/user/checkAuthoo").permitAll()//访问index.html不要权限验证
                .antMatchers("/img/**").permitAll()//取消样式拦截
                .antMatchers("/css/**").permitAll()//取消样式拦截
                .antMatchers("/script/**").permitAll()//取消样式拦截
                .antMatchers("/bootstrap/**").permitAll()//取消样式拦截
                .antMatchers("/fonts/**").permitAll()//取消样式拦截
                .antMatchers("/jquery/**").permitAll()//取消样式拦截
                .antMatchers("/ztree/**").permitAll()//取消样式拦截
                .antMatchers("/user/toRegister").permitAll()//注册页面方法取消拦截
                .antMatchers("/user/checkacct").permitAll()//注册页面方法取消拦截
                .antMatchers("/user/register").permitAll()//注册页面取消拦截
                .antMatchers("/main/*").hasRole("SA")
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()//其他所有路径都需要权限校验
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/toIndex")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable()//默认开启，这里先显式关闭
                .formLogin()  //内部注册 UsernamePasswordAuthenticationFilter
                .loginPage("/user/toLogin") //表单登录页面地址
                .loginProcessingUrl("/user/login")//form表单POST请求url提交地址，默认为/login
                .passwordParameter("password")//form表单用户名参数名
                .usernameParameter("username") //form表单密码参数名
                /* .successForwardUrl("/success.html")  //登录成功跳转地址*/
                .defaultSuccessUrl("/user/login")
                //.failureForwardUrl("/error.html") //登录失败跳转地址
                .failureHandler(customAuthenticationFailureHandler())
                //.failureUrl("/user/failure?error=true")
                //.defaultSuccessUrl()//如果用户没有访问受保护的页面，默认跳转到页面
                //.failureUrl()
                //.failureHandler()
                //.successHandler(AuthenticationSuccessHandler)
                //.failureUrl("/login?error")
                .permitAll();//允许所有用户都有权限访问登录页面

        http.rememberMe()
                // tokenBased, store token and user name in token data.
                .rememberMeServices(tokenBasedRememberMeServices())
                // .tokenRepository(new InMemoryTokenRepositoryImpl())
                // persistent serials/token, invalidate after delete from token store
                .and()
                .authenticationProvider(rememberMeAuthenticationProvider());
    }

    private static final String SECRET = "scio@2019";
    @Bean("tokenBaseRememberMeServices")
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices(SECRET, detailsService);
        rememberMeServices.setAlwaysRemember(false);
        rememberMeServices.setCookieName("remember-me");
        rememberMeServices.setTokenValiditySeconds(AbstractRememberMeServices.TWO_WEEKS_S);
        return rememberMeServices;
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(SECRET);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}