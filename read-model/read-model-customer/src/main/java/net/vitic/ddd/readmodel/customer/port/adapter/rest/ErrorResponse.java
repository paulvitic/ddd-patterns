package net.vitic.ddd.readmodel.customer.port.adapter.rest;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponse {
    @NonNull
    String error;
}
