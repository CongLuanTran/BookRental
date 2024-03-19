package bookrental;

public class BookException extends Exception {
    public BookException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "PriceException: " + super.getMessage();
    }

}
