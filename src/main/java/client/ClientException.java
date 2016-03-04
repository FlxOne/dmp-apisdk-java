package client;

/**
 * Created by rv186025 on 04/03/16.
 */
public class ClientException extends Exception {
    public ClientException(Exception e) {
        super(e);
    }

    public void printStacktrace() {
        printStackTrace();
    }
}
