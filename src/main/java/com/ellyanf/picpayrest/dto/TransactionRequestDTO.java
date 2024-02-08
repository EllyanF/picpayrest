package com.ellyanf.picpayrest.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO(Long payerId, Long payeeId, BigDecimal value) {
}
