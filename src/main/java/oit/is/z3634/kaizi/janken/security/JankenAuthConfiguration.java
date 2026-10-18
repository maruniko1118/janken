package oit.is.z3634.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class JankenAuthConfiguration {
  /**
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")) // ログアウト後に / にリダイレクト
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/janken").authenticated()
            .requestMatchers("/janken/*").authenticated() // /janken 以下は認証済みであること
            .anyRequest().permitAll()); // 上記以外は全員アクセス可能

    return http.build();
  }

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   *
   * @return
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    // ユーザ名，パスワード，ロールを指定してbuildする
    // パスワードはBCryptでハッシュ化されているため，{bcrypt}とつける

    UserDetails user1 = User.withUsername("user1")
        .password("{bcrypt}$2y$05$JFz.JSJHvYsE8H/HYodo8ePNvdM741Qmb7A8Q9TTVKk7LXZN6BvPC").roles("USER").build();
    UserDetails user2 = User.withUsername("user2")
        .password("{bcrypt}$2y$05$jbGp.n0DOX4.4S13SxmG8ea85ec9iMnK6fOC7pWpy/jtag9AQ2vVC").roles("USER").build();

    // 生成したユーザをImMemoryUserDetailsManagerに渡す
    return new InMemoryUserDetailsManager(user1, user2);
  }
}
