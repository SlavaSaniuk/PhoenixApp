package by.bsac.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Main persistence configuration class. Defining a persistence beans such as
 * datasources (depending on active profile), EntityManagerFactories, TransactionManagement bean.
 * Also parse persistence properties from application.properties file for datasource and Hibernate JPA configurations.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "by.bsac.data")
public class PersistenceConfiguration {

    //Datasource
    private DataSource ds;
    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfiguration.class);
    //Spring beans
    private Environment spring_environment;

    /**
     * Production datasource for "phoenix_users" database.
     * Method lookup in environment context for JNDI Datasource defined in Tomcat Context descriptor(WEB-INF/Context.xml).
     * Note you must indicate JNDI Name for phoenix_user datasource in application.properties file ("persistence.datasource.jndi_name.phoenix_users"  key).
     * @return JNDI {@link javax.sql.DataSource}.
     * @throws NamingException - If JNDI datasource can't be found in environment context.
     * Check you application.properties file on correct value of "persistence.datasource.jndi_name.phoenix_users" key.
     */
    @Bean(name = "production-users-datasource")
    @Description("Production datasource for \"phoenix_users\" database.")
    @Profile("Production")
    public DataSource usersProductionDataSource() throws NamingException {
        //Log
        LOGGER.info("Create \"Users\" production datasource.");
        //Lookup for JNDI resource
        this.ds = (DataSource) new JndiTemplate().lookup("java:comp/env/" +spring_environment.getProperty("persistence.datasource.jndi.name.phoenix_users"));
        return this.ds;
    }

    /**
     * Create a {@link org.springframework.jdbc.datasource.DriverManagerDataSource} with parameters initializing in application.properties
     * file in persistence.datasource.development.user.* section/
     * @return Configured {@link org.springframework.jdbc.datasource.DriverManagerDataSource}.
     */
    @Bean(name ="development-users-datasource")
    @Description("Development datasource for \"phoenix_users\" database.")
    @Profile("Development")
    public DataSource usersDevelopmentDataSource() {

        //Log
        LOGGER.info("Create \"Users\" development datasource.");

        //Create datasource
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Connection properties
        Properties connection_properties = new Properties();
        connection_properties.put("initialSize",this.spring_environment.getProperty("persistence.datasource.development.user.initialSize", "10"));
        connection_properties.put("maxActive",this.spring_environment.getProperty("persistence.datasource.development.user.maxActive", "10"));
        connection_properties.put("maxIdle",this.spring_environment.getProperty("persistence.datasource.development.user.maxIdle", "10"));
        connection_properties.put("defaultAutoCommit",this.spring_environment.getProperty("persistence.datasource.development.user.defaultAutoCommit", "true"));

        //Set datasource properties
        ds.setDriverClassName(this.spring_environment.getProperty("persistence.datasource.development.user.driverClass", "com.mysql.cj.jdbc.Driver"));
        ds.setUrl(this.spring_environment.getProperty("persistence.datasource.development.user.url"));
        ds.setUsername(this.spring_environment.getProperty("persistence.datasource.development.user.username"));
        ds.setPassword(this.spring_environment.getProperty("persistence.datasource.development.user.password"));
        ds.setConnectionProperties(connection_properties);

        //Mapping
        this.ds = ds;

        //Return configured datasource
        return ds;
    }

    /**
     * Bean used as factory to getting shared {@link javax.persistence.EntityManager} beans.
     * Factory use a datasource and Hibernate vendor adapter.
     * @return - Configured {@link javax.persistence.EntityManagerFactory} bean implementation;
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        //Create entity manager factory
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        //Set datasource
        emf.setDataSource(this.ds);

        //Create Hibernate vendor adapter
        final JpaVendorAdapter vendor_adapter = new HibernateJpaVendorAdapter();

        //Set it to emf
        emf.setJpaVendorAdapter(vendor_adapter);
        //Set Hibernate properties
        emf.setJpaProperties(this.getHibernateProperties());

        //Return configured entity manager factory.
        return emf;
    }

    /**
     * Get Hibernate properties from applications.properties file.
     * @return - Properties object which containing a hibernate properties.
     */
    private Properties getHibernateProperties() {

        //Create properties
        Properties hibernate_properties = new Properties();

        //Put properties
        hibernate_properties.put("hibernate.dialect", this.spring_environment.getProperty("persistence.hibernate.dialect"));
        hibernate_properties.put("hibernate.show_sql", this.spring_environment.getProperty("persistence.hibernate.show_sql"));
        hibernate_properties.put("hibernate.format_sql", this.spring_environment.getProperty("persistence.hibernate.format_sql"));
        hibernate_properties.put("hibernate.default_entity_mode", this.spring_environment.getProperty("persistence.hibernate.default_entity_mode"));
        hibernate_properties.put("hibernate.use_sql_comments", this.spring_environment.getProperty("persistence.hibernate.use_sql_comments"));
        hibernate_properties.put("hibernate.connection.autocommi", this.spring_environment.getProperty("persistence.hibernate.connection.autocommit"));
        hibernate_properties.put("hibernate.hbm2ddl.auto", this.spring_environment.getProperty("persistence.hibernate.hbm2ddl.auto"));
        hibernate_properties.put("hibernate.cache.use_query_cache", this.spring_environment.getProperty("persistence.hibernate.cache.use_query_cache"));

        //Return properties
        return hibernate_properties;
    }

    /**
     * Create implementation of transaction manager.
     * Associate given transaction manager with Entity manager factory.
     * @param emf - {@link javax.persistence.EntityManagerFactory} factory.
     * @return - Implementation of {@link PlatformTransactionManager} interface.
     */
    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        //Create transaction manager
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        //Return configured transaction manager
        return tm;
    }

    //Spring autowiring
    @Autowired
    public void autowire(Environment a_environment){
        //Mapping
        this.spring_environment = a_environment;
    }


}
