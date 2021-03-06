package net.vitic.ddd.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.vitic.ddd.model.PhoneNumberProcess;

import java.util.Date;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "eventType")
@JsonSubTypes({
                  @JsonSubTypes.Type(value = AllPhoneNumbersCounted.class, name = "AllPhoneNumbersCounted"),
                  @JsonSubTypes.Type(value = AllPhoneNumbersListed.class, name = "AllPhoneNumbersListed"),
                  @JsonSubTypes.Type(value = MatchedPhoneNumbersCounted.class, name = "MatchedPhoneNumbersCounted"),
                  @JsonSubTypes.Type(value = PhoneNumbersMatched.class, name = "PhoneNumbersMatched")
              })
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public abstract class PhoneNumberProcessEvent implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private String processId;
    private boolean local;

    PhoneNumberProcessEvent(String aProcessId, boolean local) {
        super();
        this.local = local;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.processId = aProcessId;
    }

    @Override
    public String type() {
        return this.getClass().getName();
    }

    @Override
    public int version() {
        return this.eventVersion;
    }

    @Override
    public String aggregate() {
        return PhoneNumberProcess.class.getName();
    }

    @Override
    public String aggregateId() {
        return this.processId;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    @Override
    public Long sequence() {
        return 0L;
    }

    public boolean isLocal() {
        return this.local;
    }
}
