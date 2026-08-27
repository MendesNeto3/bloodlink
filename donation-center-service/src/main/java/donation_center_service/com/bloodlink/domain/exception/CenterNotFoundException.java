package donation_center_service.com.bloodlink.domain.exception;

public class CenterNotFoundException extends RuntimeException {
    public CenterNotFoundException(String message) {
        super(message);
    }
}
