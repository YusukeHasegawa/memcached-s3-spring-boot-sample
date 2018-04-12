package io.github.yusukehasegawa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import io.github.yusukehasegawa.config.ApplicationProperties;

@EnableCaching
@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
