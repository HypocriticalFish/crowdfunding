package com.hypocriticalfish.crowdfunding.exception;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/2 15:39
 * @Description 登录失败后抛出的异常
 */
public class LoginFailedException extends RuntimeException {

    private static final long serialVersionUID = 697036337613031119L;

    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    protected LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
