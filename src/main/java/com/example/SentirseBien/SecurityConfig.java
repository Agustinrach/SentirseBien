package com.example.SentirseBien;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/img/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/lib/**").permitAll()
                        .requestMatchers("/.idea/**").permitAll()
                        .requestMatchers("/mail/**").permitAll()
                        .requestMatchers("/scss/**").permitAll()
                        .requestMatchers("/", "/index", "/login", "/cliente/**", "/servicios", "/noticias", "/empleo", "/contacto","/servicio").permitAll() // Páginas públicas
                        .requestMatchers("/empleado/**", "/consulta/editar/**").hasAnyAuthority("EMPLEADO" , "ADMIN") // Solo empleados
                        .requestMatchers("/reservas").hasAnyAuthority("ADMIN","SECRETARIA") // Solo admin
                        .requestMatchers("/utilidades").hasAuthority("ADMIN") // Solo admin

                        .requestMatchers("/hacerreserva","/crear_reservas","/consulta","/reserva").hasAnyAuthority("CLIENTE","ADMIN") // Solo clientes
                )
                .formLogin(form -> form
                        .loginPage("/login") // Página de inicio de sesión personalizada
                        .defaultSuccessUrl("/", true) // Redirige a la página de inicio tras un login exitoso
                        .permitAll() // Permitir el acceso a la página de login
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")) // Página de acceso denegado
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                ); // Redirigir a la página de inicio tras el logout
        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}