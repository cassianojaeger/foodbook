package br.ufrgs.foodbook.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("heroku")
public class HerokuDataSourceConfig
{
    @Value("${spring.datasource.driverClassName}") String driverClassName;
    @Bean
    public BasicDataSource dataSource()
    {
        String dbUrl = System.getenv().get("JDBC_DATABASE_URL");
        String username = System.getenv().get("JDBC_DATABASE_USERNAME");
        String password = System.getenv().get("JDBC_DATABASE_PASSWORD");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(driverClassName);

        return basicDataSource;
    }
}
