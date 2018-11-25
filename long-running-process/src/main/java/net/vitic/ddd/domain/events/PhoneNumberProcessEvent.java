package net.vitic.ddd.domain.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public PhoneNumberProcessEvent(String aProcessId, boolean local) {
        super();
        this.local = local;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.processId = aProcessId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    @Override
    public String processId() {
        return this.processId;
    }

    @Override
    public String type() {
        return this.getClass().getName();
    }

    @Override
    public boolean isLocal() {
        return this.local;
    }
}
