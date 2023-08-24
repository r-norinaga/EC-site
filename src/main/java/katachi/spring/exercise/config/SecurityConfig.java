package katachi.spring.exercise.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

    // ログイン後は/homeに遷移させる
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers(header -> {
            header.frameOptions().disable();
        });
        http.authorizeHttpRequests(authz -> authz
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .mvcMatchers("/").permitAll()
            .mvcMatchers("/login").permitAll()
            .mvcMatchers("/general").hasRole("GENERAL")
            .mvcMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/signup/signup").permitAll()
            .antMatchers("/item").permitAll()
            .antMatchers("/item/*").permitAll()
            .antMatchers("/item/personalInfo").permitAll()
            .antMatchers("/picture/*").permitAll()
            .antMatchers("/order/*").permitAll()
            .anyRequest().authenticated()
        );




        http.formLogin(form -> form
    		.loginProcessingUrl("/login")
        	.loginPage("/login")
//        	.defaultSuccessUrl("/signup/userMenu")
            .defaultSuccessUrl("/item/itemList")
            .failureUrl("/login?error")
            .usernameParameter("userName")
            .passwordParameter("password")
            .permitAll()
        );
        http.logout(logout -> logout
        	.logoutUrl("/logout")
        	.logoutSuccessUrl("/login")
        );
        return http.build();
    }
//    @Bean
//    public UserDetailsManager userDetailsService(){
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
//        // データベースにuser1/userというレコードが追加されます
//        UserDetails user = User.withUsername("user1")
//                .password(
//                PasswordEncoderFactories
//                        .createDelegatingPasswordEncoder()
//                        .encode("user"))
//                .roles("USER")
//                .build();
//        users.createUser(user);
//        return users;
//    }
    // user1/userでログインする

    @Bean
    public UserDetailsManager userDetailsService(){

        /*  独自テーブルではない場合はこちら
         *  既存User : user1/user
         *  JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
         */

    	String USERQUERY = "select user_name, password,'True' from users where user_name = ? and is_deleted = 0";
    	String AUTHQUERY = "select user_name,'GENERAL' from users where user_name = ?";
    	JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
    	users.setUsersByUsernameQuery(USERQUERY);
    	users.setAuthoritiesByUsernameQuery(AUTHQUERY);
    	return users;
    }


}
