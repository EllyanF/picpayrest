package com.ellyanf.picpayrest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionDTO {
    @NotNull
    private String payerDocument;

    @NotNull
    private String payeeDocument;

    @NotNull
    @DecimalMin(value = "1.00", message = "The value must be at least 1.00")
    private BigDecimal amount;
}
