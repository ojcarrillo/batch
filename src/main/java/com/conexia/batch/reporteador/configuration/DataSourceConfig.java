package com.conexia.batch.reporteador.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
	
	@Bean(name = "h2DataSource")
	@Primary
	public DataSource h2DataSource(@Value("${h2.url}") String url, 
											@Value("${h2.username}") String username, 
											@Value("${h2.password}") String password,
											@Value("${h2.driver-class-name}") String driver) throws NamingException {
	    return DataSourceBuilder.create()
		      .url(url)
		      .driverClassName(driver)
		      .username(username)
		      .password(password)
		      .build();
	}

	@Bean(name = "postgresqDataSource")
	public DataSource postgresqDataSource(@Value("${postgres.url}") String url, 
											@Value("${postgres.username}") String username, 
											@Value("${postgres.password}") String password,
											@Value("${postgres.platform}") String platform,
											@Value("${postgres.driver-class-name}") String driver) throws NamingException {
	    return DataSourceBuilder.create()
		      .url(url)
		      .driverClassName(driver)
		      .username(username)
		      .password(password)
		      .build();
	}	

}
