package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class GPTDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GPTRequest{
        private String model;
        private List<Message> messages;
        private double temperature;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message{
        private String role;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GPTResponse{
        private List<Choice> choices;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Choice{
            private Message message;
        }
    }
}
