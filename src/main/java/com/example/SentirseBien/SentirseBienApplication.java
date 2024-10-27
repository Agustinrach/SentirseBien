package com.example.SentirseBien;

import com.example.SentirseBien.Entidad.Empleado;
import com.example.SentirseBien.servicio.EmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.SentirseBien.repositorio.EmpleadoRepositorio;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SentirseBienApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SentirseBienApplication.class, args);

	}

	@Autowired
	private EmpleadoRepositorio empleadoRepositorio;



	@Override
	public void run(String... args) throws Exception {
		/*Empleado empleado1 = new Empleado("Agustin","Rach","agustinrach00@gmail.com","43110081","3624031760");
		empleadoRepositorio.save(empleado1);

		Empleado empleado2 = new Empleado("Agustin2","Rach2","agustinrach00@gmail.com2","431100812","36240317602");
		empleadoRepositorio.save(empleado2);*/


	}

	@Configuration
	public static class Myconfiguration{
		@Bean
		public WebMvcConfigurer corsConfigurer(){
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
				}
			};
		}
	}




}
