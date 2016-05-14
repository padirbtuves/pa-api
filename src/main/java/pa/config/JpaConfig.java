package pa.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "pa.repositories")
@EntityScan(basePackages = {"pa.domain"})
public class JpaConfig {

}
