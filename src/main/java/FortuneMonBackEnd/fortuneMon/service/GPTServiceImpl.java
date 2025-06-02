package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.GPTDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GPTServiceImpl implements GPTService{

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = ""; // 사용할 api url 선택
    private final String API_KEY = ""; // api key 주입

    public String requestGPT(String prompt){
        GPTDTO.Message message = new GPTDTO.Message("user", prompt);
        GPTDTO.GPTRequest request = new GPTDTO.GPTRequest("모델 설정", Collections.singletonList(message), 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<GPTDTO.GPTRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<GPTDTO.GPTResponse> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, httpEntity, GPTDTO.GPTResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return responseEntity.getBody().getChoices().get(0).getMessage().getContent();
        } else {
            return "GPT 응답 실패";
        }

    }
}
