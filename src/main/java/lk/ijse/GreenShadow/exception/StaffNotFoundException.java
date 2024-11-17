package lk.ijse.GreenShadow.exception;

public class StaffNotFoundException extends RuntimeException {
  public StaffNotFoundException() {
    super();
  }

  public StaffNotFoundException(String message) {
    super(message);
  }

  public StaffNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
