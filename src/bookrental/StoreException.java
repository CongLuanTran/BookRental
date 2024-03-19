package bookrental;

public class StoreException extends Exception {

    public StoreException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "MaxBookException: " + super.getMessage();
    }

}
