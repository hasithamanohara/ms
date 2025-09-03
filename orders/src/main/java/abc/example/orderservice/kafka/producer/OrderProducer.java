package abc.example.orderservice.kafka.producer;

import com.example.basemodule.dto.OrderEventDTOT;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventDTOT.class);
    private final NewTopic orderTopic;
    private final KafkaTemplate<String, OrderEventDTOT> orderKafkaTemplate;

    public void sendMessage(OrderEventDTOT orderEventDTO) {
        LOGGER.info(String.format("Sending order event to kafka topic: %s", orderEventDTO.toString()));

        Message<OrderEventDTOT> message = MessageBuilder
                .withPayload(orderEventDTO)
                .setHeader(KafkaHeaders.TOPIC, orderTopic.name())
                .build();

        orderKafkaTemplate.send(message);
    }
}
