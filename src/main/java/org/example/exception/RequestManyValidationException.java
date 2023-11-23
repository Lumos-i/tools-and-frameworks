package org.example.exception;

/**
 * 运行时异常
 * @author binbin.hou
 * @since 0.0.1
 */
public class RequestManyValidationException extends RuntimeException {

    public RequestManyValidationException() {
    }

    public RequestManyValidationException(String message) {
        super(message);
    }

    public RequestManyValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestManyValidationException(Throwable cause) {
        super(cause);
    }

    public RequestManyValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
