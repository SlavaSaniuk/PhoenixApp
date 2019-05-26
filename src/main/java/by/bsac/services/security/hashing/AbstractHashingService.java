package by.bsac.services.security.hashing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.SecureRandom;

public abstract class AbstractHashingService implements HashingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(by.bsac.services.security.hashing.AbstractHashingService.class);

    protected MessageDigest digest;
    protected String hashing_algorythm;
    protected int hash_length;

    public abstract byte[] hash(String hashed_word);

    public byte[] generateSalt() {

        //Salt generator
        SecureRandom sr = new SecureRandom();

        //Resulting array
        byte[] res = new byte[this.hash_length];

        //Generate bytes
        sr.nextBytes(res);

        //Return statement
        return res;
    }

}
