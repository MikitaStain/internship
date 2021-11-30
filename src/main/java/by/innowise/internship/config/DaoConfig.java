package by.innowise.internship.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableWebMvc
@ComponentScan("by.innowise.internship")
public class DaoConfig {

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
        builder.scanPackages("by.innowise.internship");
        builder.setProperty("hibernate.show_sql", "true");
//        builder.setProperty("hibernate.ddl-auto","create");
        builder.setProperty("hibernate.default_schema", "application");
        builder.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        return builder.buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.postgresql.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Ошибка загрузки драйвера", e);
        }
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/crm");
        cpds.setUser("postgres");
        cpds.setPassword("7403663");
        return cpds;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setLiquibaseSchema("application");
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-root.xml");
        liquibase.setDataSource(dataSource);

        return liquibase;
    }

}
