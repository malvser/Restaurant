package malov.serg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getShaPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").hasAnyRole("USER", "ADMIN", "COOK")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/statistic_viewed_advertisement").hasRole("ADMIN")
                .antMatchers("/statistic_cooked_order").hasRole("ADMIN")
                .antMatchers("/statistic_no_advertisement").hasRole("ADMIN")
                .antMatchers("/add_dish").hasRole("ADMIN")
                .antMatchers("/dish/add").hasRole("ADMIN")
                .antMatchers("/add_tablet").hasRole("ADMIN")
                .antMatchers("/tablet/add").hasRole("ADMIN")
                .antMatchers("/tabletList").hasRole("ADMIN")
                .antMatchers("/tablet/delete").hasRole("ADMIN")
                .antMatchers("/cook_add").hasRole("ADMIN")
                .antMatchers("/cook/add").hasRole("ADMIN")
                .antMatchers("/cookList").hasRole("ADMIN")
                .antMatchers("/cook/delete").hasRole("ADMIN")
                .antMatchers("/advertisementList").hasRole("ADMIN")
                .antMatchers("/advertisement/add_page").hasRole("ADMIN")
                .antMatchers("/advertisement/add").hasRole("ADMIN")
                .antMatchers("/advertisement/delete").hasRole("ADMIN")
                .antMatchers("/enter_cook").hasAnyRole("COOK", "ADMIN")
                .antMatchers("/order_for_cooks").hasRole("COOK")
                .antMatchers("/cooked_order").hasRole("COOK")
                .antMatchers("/director/pages").hasRole("ADMIN")
                .antMatchers("/register").permitAll()
                .antMatchers("/menu").permitAll()
                .antMatchers("/order").permitAll()
                .antMatchers("/made/order").permitAll()
                .antMatchers("/advertisement/view").permitAll()
                .antMatchers("/advertisement/view/{photo_id}").permitAll()
                .antMatchers("/advertisement/viewed/{photo_id}").permitAll()
                .antMatchers("/photo/advertisement/{photo_id}").permitAll()
                .and()
        .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
        .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/spring_security_check")
                .failureUrl("/login?error")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
                .and()
        .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);
    }

    private ShaPasswordEncoder getShaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }
}
