package ua.kiev.prog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(value = "ua.kiev.prog.repositories.others",
        entityManagerFactoryRef = "clientsEntityManagerFactory",
        transactionManagerRef = "clientsTransactionManager")
@PropertySource("classpath:application.properties")
public class ClientsDatabaseConfig {

    @Value("${spring.datasource.url}")
    private String jdbcURL;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;


    @Primary
    @Bean(name = "clientsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean clientsEntityManagerFactory(
            @Qualifier("clientsDataSource") DataSource dataSource,
            @Qualifier("clientsJpaVendorAdapter") JpaVendorAdapter jpaVendorAdapter
    ) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPackagesToScan("ua.kiev.prog.entities.others");
        entityManagerFactory.setJpaProperties(additionalProperties());
        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "clientsTransactionManager")
    public PlatformTransactionManager  clientsTransactionManager(
            @Qualifier("clientsEntityManagerFactory") EntityManagerFactory entityManagerFactory)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Primary
    @Bean(name = "clientsJpaVendorAdapter")
    public JpaVendorAdapter clientsJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        return adapter;
    }

    @Primary
    @Bean (name = "clientsDataSource")
    public DataSource clientsDataSource() {
        return DbConfig.createDataSource(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }
}
