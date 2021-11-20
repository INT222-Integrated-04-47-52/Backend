package sit.int222.mongkolthorn.exceptions;

import java.time.LocalDateTime;

public class ExceptionResponse {
    public static enum ERROR_CODE {
        PRODUCT_DOES_NOT_EXIST(101), PRODUCT_ALREADY_EXIST(102),
        PRODUCT_ID_ALREADY_EXIST(103), PRODUCT_NAME_ALREADY_EXIST(104),
        PRODUCT_IMAGE_NULL(105), ACCOUNT_ID_NOT_EXIST(201),
        ACCOUNT_ID_ALREADY_EXIST(202), ACCOUNT_EMAIL_ALREADY_EXIST(203),
        ACCOUNT_DETAIL_IS_NULL(204),
        LOGIN_ID_NOT_EXIST(301), LOGIN_ID_ALREADY_EXIST(302),
        LOGIN_EMAIL_ALREADY_EXIST(303),LOGIN_DETAIL_IS_NULL(304);

        private int errorValue;

        ERROR_CODE(int value){
            this.errorValue = errorValue;
        }
    }

    private ERROR_CODE errorCode;
    private String message;
    private LocalDateTime dateTime;

    public ExceptionResponse(ERROR_CODE errorCode, String message, LocalDateTime dateTime) {
        this.errorCode = errorCode;
        this.message = message;
        this.dateTime = dateTime;
    }
}
