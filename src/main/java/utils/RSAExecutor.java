package utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * K_secret = {p, q, secretKeyPart}
 * K_open = {number, openKeyPart}
 */
public class RSAExecutor {
    private final int bitLength;
    private BigInteger openKeyPart;
    private BigInteger secretKeyPart;
    private BigInteger phi;
    private BigInteger number;
    private BigInteger p;
    private BigInteger q;

    private static final Random random = new Random(System.currentTimeMillis());
    private final Logger logger = MyLogger.getLogger(RSAExecutor.class.getName());

    public RSAExecutor(int bitLength) {
        this.bitLength = bitLength;
        init();
    }

    public RSAExecutor() {
        this(2048);
    }

    private void init() {
        p = BigInteger.probablePrime(bitLength, random);
        q = BigInteger.probablePrime(bitLength, random);
        number = p.multiply(q);
        phi = q.subtract(BigInteger.ONE).multiply(p.subtract(BigInteger.ONE));
        openKeyPart = BigInteger.probablePrime(bitLength, random);
        secretKeyPart = openKeyPart.modInverse(phi);
        logger.log(Level.FINE, "Initialization completed");
    }

    public BigInteger encodeLong(long message) {
        return BigInteger.valueOf(message).modPow(openKeyPart, number);
    }

    public long decodeLong(BigInteger encodedMessage) {
        return encodedMessage.modPow(secretKeyPart, number).longValue();
    }

    public <T extends Number> T decode(BigInteger encodedMessage, Function<BigInteger, T> function) {
        return function.apply(encodedMessage.modPow(secretKeyPart, number));
    }

    public BigInteger encodeInt(int message) {
        return BigInteger.valueOf(message).modPow(openKeyPart, number);
    }

    public int decodeInt(BigInteger encodedMessage) {
        return encodedMessage.modPow(secretKeyPart, number).intValue();
    }

    public List<BigInteger> encodeString(String message) {
        return message.chars()
                .mapToObj(this::encodeInt)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String decodeString(List<BigInteger> encodedMessage) {
        return encodedMessage.stream()
                .map(this::decodeInt)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
