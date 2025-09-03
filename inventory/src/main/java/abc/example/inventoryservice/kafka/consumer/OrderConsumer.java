package abc.example.inventoryservice.kafka.consumer;

import com.example.basemodule.dto.OrderEventDTOT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventDTOT.class);

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "spring.kafka.consumer.group-id"
    )

    public void consume(OrderEventDTOT dto) {
        LOGGER.info("Received OrderEventDTO: {}", dto);
    }

}
