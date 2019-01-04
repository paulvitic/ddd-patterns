package net.vitic.ddd.eventsourcing.infrastructure.eventStore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class EventStreamConfiguration {

    @Bean
    MessageChannel domainEventStream() {
        return new DirectChannel();
    }
}
