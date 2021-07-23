package project.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class JdbCprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbCprojectApplication.class, args);
	}

}
