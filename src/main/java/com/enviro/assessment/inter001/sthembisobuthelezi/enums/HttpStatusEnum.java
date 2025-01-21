package com.enviro.assessment.inter001.sthembisobuthelezi.enums;

public enum HttpStatusEnum {

    SUCCESSFUL("200", "zion.success", "Success", "200"),
    SECOND_NAME_ERROR("400","second.name.error","Second name must be provided", "200"),
    VERIFICATION_STATUS("400","verification_status_error", "Verification status error", "200"),
    PHONE_NUMBER_VALIDATION("400","phone_number_error", "Phone number error", "200"),
    NOT_FOUND("400","phone.number.not.found", "phone number not found", "200"),
    PASSWORD_DO_NOT_MATCH("500", "password.not.match","password not match","200"),
    ACCOUNT_EXIST("400","account.exist", "Account already exist", "200"),
    EMAIL_INVALID("400","email.invalid", "Email you entered is invalid", "200"),
    USERNAME_ERROR("400","username.error","Username must be provided", "200"),
    WHATSAPP_NUMBER_ERROR("400","whatsapp.number.error","Whatsapp number must be provided", "200"),
    CHURCH_ERROR("400","church.error","Church must be provided", "200"),
    GENDER_ERROR("400","gender.error","Gender must be provided", "200"),
    PROVINCE_ERROR("400","province.error","Province must be provided", "200"),
    ACCOUNT_NOT_EXIST("500", "account.dont.exist", "account doest not exist", "200"),
    PIN_INCORRECT("500", "incorrect.incorrect", "incorrect pin", "200"),

    EMAIL_ERROR("400","email.error","Email must be provided", "200"),

    SERVER_ERROR("500", "server.error", "Failed", "500"),
    UNAUTHORIZED("500","server.error", "Failed", "500");

    private String responseCode;
    private String responseMessageKey;
    private String developerMessage;
    private String httpStatus;

    /**
     * @param responseCode
     * @param responseMessageKey
     */
    private HttpStatusEnum(String responseCode, String responseMessageKey, String developerMessage, String httpStatus) {
        this.responseCode = responseCode;
        this.responseMessageKey = responseMessageKey;
        this.httpStatus = httpStatus;
        this.setDeveloperMessage(developerMessage);
    }

    /**
     * @return String
     */
    public String getCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return String
     */
    public String getKey() {
        return this.responseMessageKey;
    }

    /**
     * @param responseMessageKey
     */
    public void setResponseMessageKey(String responseMessageKey) {
        this.responseMessageKey = responseMessageKey;
    }

    /**
     * @return String
     */
    public String getDeveloperMessage() {
        return this.developerMessage;
    }

    /**
     * @param developerMessage
     */
    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    /**
     * @param httpStatus
     */
    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * @return String
     */
    public String getHttpStatus() {
        return this.httpStatus;
    }

}

