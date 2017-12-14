package com.vdsirotkin.telegram.resendbot.destiny2.responses;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class BaseResponse {
    private Integer ErrorCode;
    private Integer ThrottleSeconds;
    private String ErrorStatus;
    private String Message;

    public Integer getErrorCode() {
        return ErrorCode;
    }

    public BaseResponse setErrorCode(Integer errorCode) {
        ErrorCode = errorCode;
        return this;
    }

    public Integer getThrottleSeconds() {
        return ThrottleSeconds;
    }

    public BaseResponse setThrottleSeconds(Integer throttleSeconds) {
        ThrottleSeconds = throttleSeconds;
        return this;
    }

    public String getErrorStatus() {
        return ErrorStatus;
    }

    public BaseResponse setErrorStatus(String errorStatus) {
        ErrorStatus = errorStatus;
        return this;
    }

    public String getMessage() {
        return Message;
    }

    public BaseResponse setMessage(String message) {
        Message = message;
        return this;
    }
}
