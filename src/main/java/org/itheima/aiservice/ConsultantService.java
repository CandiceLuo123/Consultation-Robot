package org.itheima.aiservice;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        streamingChatModel =  "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface ConsultantService {
    // 聊天
//    String chat(String message);
    //@SystemMessage("你是帅气聪明的主人提供的小烙助手，体贴可爱帅气又多金")
    @SystemMessage(fromResource = "system.txt")
    Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);

}
