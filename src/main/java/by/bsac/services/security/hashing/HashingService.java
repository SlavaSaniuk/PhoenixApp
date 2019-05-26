package by.bsac.services.security.hashing;


public interface HashingService {

    public byte[] hash();

    public byte[] generateSalt();

}
