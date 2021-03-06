package pa.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(new AlwaysSendUnauthorized401AuthenticationEntryPoint());

        http.authorizeRequests().antMatchers("/webjars/**").permitAll();

	    http
	      .formLogin().defaultSuccessUrl("/asdasd")
	      .and()
	      .logout().logoutSuccessUrl("/").and()
	      .antMatcher("/**")
	      .authorizeRequests()
	        .antMatchers("/", "/event", "/up", "/login**", "/auth/nfc", "/auth/log", "/auth/logs", "/stats/**", "/finances", "/hook/**")
	        .permitAll()
	      .anyRequest()
	        .authenticated().and().csrf().disable();
	        //.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf().csrfTokenRepository(csrfTokenRepository());
	}

//	@Bean
//	@ConfigurationProperties("security.csrf.tokenRepository")
//	protected CsrfTokenRepository csrfTokenRepository() {
//		return new HttpSessionCsrfTokenRepository();
//	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/img/**");
	}
}