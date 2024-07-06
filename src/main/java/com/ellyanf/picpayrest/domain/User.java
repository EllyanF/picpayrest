package com.ellyanf.picpayrest.domain;

import com.ellyanf.picpayrest.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    private static final String DOCUMENT_PATTERN = "^(\\d{2,3})\\.?(\\d{3})\\.?(\\d{3})(-?\\d{2}|/?\\d{4}-?\\d{2})$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = DOCUMENT_PATTERN, message = "CPF or CNPJ must match proper pattern")
    private String document;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @NotNull
    private BigDecimal balance;

    @Column(unique = true)
    @NotNull
    @Email
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        cleanDocument();
        identifyUserType();
    }

    private void identifyUserType() {
        if (document.matches("^[0-9]{11}$")) {
            userType = UserType.COMMON;
        } else if (document.matches("^[0-9]{14}$")) {
            userType = UserType.MERCHANT;
        }
    }

    private void cleanDocument() {
        document = document.replaceAll("[./-]", "");
    }
}
