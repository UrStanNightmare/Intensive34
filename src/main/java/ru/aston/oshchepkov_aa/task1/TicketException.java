package ru.aston.oshchepkov_aa.task1;

public class TicketException extends RuntimeException {
    private final ErrorCode errorCode;

    public TicketException() {
        super(ErrorCode.UNDEFINED.getDescription());
        this.errorCode = ErrorCode.UNDEFINED;
    }

    public TicketException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public TicketException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getDescription(), cause);
        this.errorCode = errorCode;
    }

    public TicketException(Throwable cause) {
        super(ErrorCode.UNDEFINED.getDescription(), cause);
        this.errorCode = ErrorCode.UNDEFINED;
    }

    protected TicketException(ErrorCode errorCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode.getDescription(), cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
