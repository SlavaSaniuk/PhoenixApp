package by.bsac.services.security.hashing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class HashServicesFactory implements FactoryBean<HashingService>, InitializingBean {

    //Bean variables
    private HashAlgorithm algorithm;
    private static final Logger LOGGER = LoggerFactory.getLogger(by.bsac.services.security.hashing.HashServicesFactory.class);

    public void afterPropertiesSet() throws Exception {
        if (this.algorithm == null) {
            LOGGER.error("Hash algorithm is not setup");
            throw new NullPointerException("Hash algorithm must not be a null.");
        }
    }


    public HashAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(HashAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public HashingService getObject() throws Exception {
        switch (this.algorithm) {
            case SHA512:
                LOGGER.info("All hashing beans are work in " +algorithm.toString() +" algorithm.");
                return new Sha512HashService();
            default:
                LOGGER.info(algorithm.toString() +" is not supported");
                throw new IllegalArgumentException("Current algorithm is not supported");
        }

    }

    public Class<?> getObjectType() {
        return by.bsac.services.security.hashing.HashingService.class;
    }


}
