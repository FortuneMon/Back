package FortuneMonBackEnd.fortuneMon.apiPayload.exception.handler;


import FortuneMonBackEnd.fortuneMon.apiPayload.code.BaseErrorCode;
import FortuneMonBackEnd.fortuneMon.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
