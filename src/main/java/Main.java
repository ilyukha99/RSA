import utils.RSAExecutor;

public class Main {
    public static void main(String[] args) {
        RSAExecutor rsaExecutor = new RSAExecutor();
        long message = 122122L;
        long val = rsaExecutor.decodeLong(rsaExecutor.encodeLong(message));
        System.out.println(val);

        String stringMessage = "Kekw";
        String result = rsaExecutor.decodeString(rsaExecutor.encodeString(stringMessage));
        System.out.println(result);
    }
}
