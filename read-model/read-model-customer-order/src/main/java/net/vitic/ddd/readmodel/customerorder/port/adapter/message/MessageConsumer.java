package net.vitic.ddd.readmodel.customerorder.port.adapter.message;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.readmodel.customerorder.domain.model.CustomerOrder;
import net.vitic.ddd.domain.event.DomainEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@AllArgsConstructor
@EnableBinding({EventSink.class})
public class MessageConsumer {

    private final CustomerOrder customerOrder;

	@StreamListener(EventSink.EVENT_INPUT)
	public void process(Flux<DomainEvent> inbound) {
        inbound
            .doOnNext(customerOrder::mutate)
            .subscribe();
	}
}
