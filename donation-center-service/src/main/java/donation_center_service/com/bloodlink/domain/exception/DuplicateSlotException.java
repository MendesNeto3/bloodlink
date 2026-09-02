package donation_center_service.com.bloodlink.domain.exception;

public class DuplicateSlotException extends RuntimeException {
    public DuplicateSlotException(String message) {
        super(message);
    }
}
