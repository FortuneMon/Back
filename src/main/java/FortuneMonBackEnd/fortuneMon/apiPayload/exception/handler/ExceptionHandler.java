package FortuneMonBackEnd.fortuneMon.apiPayload.exception.handler;


import FortuneMonBackEnd.fortuneMon.apiPayload.code.BaseErrorCode;
import FortuneMonBackEnd.fortuneMon.apiPayload.exception.GeneralException;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseErrorCode code) {
        super(code);
    }
}
