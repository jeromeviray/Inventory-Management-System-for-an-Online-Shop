package com.project.inventory;

import com.project.inventory.webSecurity.config.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
@ComponentScan( value = "com.project.inventory" )
@EnableConfigurationProperties( AppProperties.class )
public class InventoryApplication extends SpringBootServletInitializer {

    public static void main( String[] args ) {
        SpringApplication.run( InventoryApplication.class, args );
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public JavaMailSender getJavaMailer(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost( "smtp.gmail.com" );
        mailSender.setPort(587);

        mailSender.setUsername("inventorymanagement002@gmail.com");
        mailSender.setPassword("6!cAG6@fheh#NcQx");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        return mailSender;
    }
    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder applicationBuilder ) {
        return applicationBuilder.sources( InventoryApplication.class );
    }

}
