package by.bsac.services.security.hashing;


public interface HashingService {

    public byte[] hash(String hashed_word);

    public byte[] generateSalt();

}
