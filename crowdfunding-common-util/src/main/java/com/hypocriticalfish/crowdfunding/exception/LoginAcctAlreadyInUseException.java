package com.hypocriticalfish.crowdfunding.exception;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/2 22:05
 * @Description 保存或更新Admin时检测到登录账号重复抛出异常
 */
public class LoginAcctAlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID = 637264369512510572L;

    public LoginAcctAlreadyInUseException() {
        super();
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
