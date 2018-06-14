package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 设置登录和退出地址，
     * 给静态资源加上.permitAll();
     * 其他资源访问需要权限认证    .authenticated();
     * 静态资源界面不支持CSFR(跨站请求伪造),要禁掉csrf
     * 最后开启Http的基本认证
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login.html")
                .permitAll();
        http.logout().logoutUrl("/logout");
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/login.html", "/**/**.css", "/img/**.css", "/thrid-party/**")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/**")
                .authenticated();
        http.httpBasic();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /*
//         没有调用auth.userDetailsService(userDetailsService())，用户名和密码，使用配置文件中的root root
//         如果auth.userDetailsService(userDetailsService()) , 使用自定义UserDetailsService，里面定义的用户名和密码
//         */
//        auth.userDetailsService(userDetailsService());
//    }
//
//    @Bean
//    @Override
//	public UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // 在内存中存放用户信息
//		manager.createUser(User.withUsername("JayHe").password("123456").roles("USER").build());
//		manager.createUser(User.withUsername("admin").password("123456").roles("USER","ADMIN").build());
//		return manager;
//	}
//
//	/*
//	解决：There is no PasswordEncoder mapped for the id "null"
//	  参考：
//	      https://blog.csdn.net/dream_an/article/details/79381459
//	 */
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
}
