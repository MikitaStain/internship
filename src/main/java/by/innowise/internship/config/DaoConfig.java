package by.innowise.internship.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("by.innowise.internship")
@PropertySource("classpath:application.yml")
@EnableJpaRepositories(basePackages = "by.innowise.internship.repository.dao")
@EnableTransactionManagement
public class DaoConfig {

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {

        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
        builder.scanPackages("by.innowise.internship");
        builder.setProperty("hibernate.show_sql", "true");
//        builder.setProperty("hibernate.ddl-auto","create");
        builder.setProperty("hibernate.default_schema", "application");
        builder.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        builder.setProperty("hibernate.generate_statistics", "true");

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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("by.innowise.internship.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.cache.use_second_level_cache", "true");
        properties.setProperty("hibernate.cache.region.factory_class",
                "org.hibernate.cache.ehcache.EhCacheRegionFactory");

        return properties;
    }


}
