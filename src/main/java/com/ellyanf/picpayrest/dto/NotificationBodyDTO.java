package com.ellyanf.picpayrest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationBodyDTO {
    @NotNull
    private String email;

    @NotNull
    private String message;
}
