package net.vitic.ddd.domain.model;

import lombok.RequiredArgsConstructor;
import net.vitic.ddd.domain.CustomerValueCleared;
import net.vitic.ddd.domain.DomainEventType;
import net.vitic.ddd.domain.QueueEntryInserted;
import net.vitic.ddd.domain.event.DomainEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QueueView implements ReadModel {

    /*private final QueueProjectionRepository queueProjectionRepository;
    private final QueueEntryProjectionRepository queueEntryProjectionRepository;*/


    @Override
    @Transactional
    @EventListener
    public void mutate(DomainEvent event) {
        switch (DomainEventType.valueOf(event.type())) {
            case CustomerValueCleared:
                when((CustomerValueCleared) event);
                break;
            case QueueEntryInserted:
                when((QueueEntryInserted) event);
                break;
        }
    }


    private void when(QueueEntryInserted event){
        /*QueueProjection queueProjection =
            queueProjectionRepository.findOneByPartition(
                QueueProjectionId.builder()
                                 .instanceName(event.getQueueName())
                                 .branch(event.getBranchName())
                                 .advisorGroup(event.getGroupName())
                                 .build())
                                     .orElseThrow(() -> new IllegalStateException(""));

        queueProjection.addEntry(event.getCustomerNo(), event.getValue());*/
    }


    private void when(CustomerValueCleared event) {
        /*queueEntryProjectionRepository
            .deleteByIdCustomerNo(event.getCustomerNo());*/
    }
}