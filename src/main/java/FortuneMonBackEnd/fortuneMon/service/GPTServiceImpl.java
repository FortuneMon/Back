package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.GPTDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GPTServiceImpl implements GPTService{

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://api.openai.com/v1/chat/completions"; // 사용할 api url 선택

    @Value("${openai.api.key}")
    private String API_KEY; // api key 주입

    public String requestGPT(String fortune, String category){
        String prompt = String.format("오늘의 운세: %s\n카테고리: %s\n오늘의 운세 내용을 바탕으로 %s에 대한 조언 한 문장 부탁해", fortune, category, category);
        GPTDTO.Message message = new GPTDTO.Message("user", prompt);
        GPTDTO.GPTRequest request = new GPTDTO.GPTRequest("gpt-4o-mini", Collections.singletonList(message), 0.7);

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
