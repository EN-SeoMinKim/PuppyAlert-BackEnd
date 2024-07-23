package seominkim.puppyAlert.global.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seominkim.puppyAlert.global.exception.errorCode.ErrorCode;

@AllArgsConstructor
@Getter
public class PuppyException extends RuntimeException{
    private ErrorCode errorCode;
}
