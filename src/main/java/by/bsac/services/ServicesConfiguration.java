package by.bsac.services;

import by.bsac.services.security.hashing.HashAlgorithm;
import by.bsac.services.security.hashing.HashServicesFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    //Hash service factory
    @Bean
    public HashServicesFactory createHashServiceFactory() {
        HashServicesFactory factory = new HashServicesFactory();
        //Set hash algorithm
        factory.setAlgorithm(HashAlgorithm.SHA512);
        return factory;
    }
}
