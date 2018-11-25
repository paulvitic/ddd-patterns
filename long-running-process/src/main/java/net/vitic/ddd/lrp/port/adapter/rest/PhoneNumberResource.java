package net.vitic.ddd.lrp.port.adapter.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.lrp.application.command.StartProcessCommand;
import net.vitic.ddd.lrp.application.service.PhoneNumberProcessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Api(value = "phoneNumbers", description = "Endpoint for starting phone numbers process")
@RestController
@RequestMapping("/phoneNumbers")
public class PhoneNumberResource {

    private final PhoneNumberProcessService phoneNumberProcessService;

    public PhoneNumberResource(
        PhoneNumberProcessService phoneNumberProcessService) {
        this.phoneNumberProcessService = phoneNumberProcessService;
    }

    @ApiOperation(
        nickname = "phoneNumbers",
        value = "starts process",
        notes = "Starts process")
    @PostMapping
    ResponseEntity startProcess(@RequestBody List<String> phoneNumbers) {

        for (int i = 0; i < 10; i++) {
            phoneNumberProcessService.startProcess(new StartProcessCommand(
                IntStream.rangeClosed(1, 1000).boxed().map(a -> "303-" + a).collect(Collectors.toList())));
        }

        //phoneNumberProcessService.startProcess(new StartProcessCommand(phoneNumbers));
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}
