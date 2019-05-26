package by.bsac.services.security.hashing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Sha512HashService extends AbstractHashingService {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(by.bsac.services.security.hashing.Sha512HashService.class);

    //Constructor
    public Sha512HashService() {

        //Set to use "SHA-512" algorithm
        super.hashing_algorythm = "SHA-512";
        //Set length
        //Create message digest
        try {
            super.digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException exc) {
            LOGGER.error("No such algorithm for: " +super.hashing_algorythm);
        }
        //Set hash length (bytes)
        super.hash_length = 64;
    }

    @Override
    public byte[] hash(String hashed_word) {

        //String to byte array
        byte[] hashed_bytes = hashed_word.getBytes();

        //hash bytes
        return super.digest.digest(hashed_bytes);
    }

}
