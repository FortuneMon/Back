package FortuneMonBackEnd.fortuneMon.apiPayload.exception;

import FortuneMonBackEnd.fortuneMon.apiPayload.code.BaseErrorCode;
import FortuneMonBackEnd.fortuneMon.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
