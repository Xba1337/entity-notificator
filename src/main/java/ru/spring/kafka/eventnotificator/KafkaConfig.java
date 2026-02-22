package ru.spring.kafka.eventnotificator;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.spring.kafka.eventnotificator.notification.EventChangeMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConsumerFactory<Integer, EventChangeMessage> consumerFactory() {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "notificator-group");
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);

        var factory = new DefaultKafkaConsumerFactory<Integer, EventChangeMessage>(configProperties);

        factory.setValueDeserializer(new JsonDeserializer<>(EventChangeMessage.class, false));

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, EventChangeMessage> containerFactory(
            ConsumerFactory<Integer, EventChangeMessage> consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Integer, EventChangeMessage>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
