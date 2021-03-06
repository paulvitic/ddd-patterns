package net.vitic.ddd.infrastructure;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.event.DomainEvent;
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
public class ApplicationEventConsumer implements Consumer<FluxSink<DomainEvent>> {

    private final TaskExecutor asyncExecutor;
    private final BlockingQueue<DomainEvent> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean isConsuming = new AtomicBoolean(true);

    @Autowired
    ApplicationEventConsumer(TaskExecutor asyncExecutor) {
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

    public boolean isStopped(){
        return !isConsuming.get();
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
                    log.error("Exception while consuming application event", e);
                }
            }

            sink.complete();
        });
    }
}
