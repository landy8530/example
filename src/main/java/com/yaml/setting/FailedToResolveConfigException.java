package com.yaml.setting;

public class FailedToResolveConfigException extends RuntimeException {

    public FailedToResolveConfigException() {
        super();
    }

    public FailedToResolveConfigException(String message) {
        super(message);
    }

    public FailedToResolveConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToResolveConfigException(Throwable cause) {
        super(cause);
    }

    protected FailedToResolveConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
