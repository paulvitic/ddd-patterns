package net.vitic.ddd.infrastructure.eventBus;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.events.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Component
@Slf4j
public class LocalEventPublisher implements Consumer<FluxSink<DomainEvent>> {

    private TaskExecutor asyncExecutor;
    private final BlockingQueue<DomainEvent> queue = new LinkedBlockingQueue<>();
    private AtomicBoolean isConsuming = new AtomicBoolean(true);

    @Autowired
    LocalEventPublisher(TaskExecutor asyncExecutor) {
        this.asyncExecutor = asyncExecutor;
    }

    @EventListener
    public void onApplicationEvent(DomainEvent event) {
        this.queue.offer(event);
    }

    @PreDestroy
    public void preStop() {
        isConsuming.set(false);
    }

    @Override
    public void accept(FluxSink<DomainEvent> sink) {
        this.asyncExecutor.execute(() -> {
            while (isConsuming.get()) {
                try {
                    DomainEvent event = queue.take();
                    sink.next(event);

                } catch (InterruptedException e) {
                    sink.error(e);
                    // See: https://grokonez.com/reactive-programming/reactor/reactor-handle-error
                    //ReflectionUtils.rethrowRuntimeException(e);
                }
            }

            sink.complete();
        });
    }
}
