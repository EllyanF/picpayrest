package com.ellyanf.picpayrest.domain;

import com.ellyanf.picpayrest.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"transactionsPaid", "transactionsReceived"})
public class User {
    private static final String DOCUMENT_PATTERN = "^(\\d{2,3})\\.(\\d{3})\\.(\\d{3})(-\\d{2}|/\\d{4}-\\d{2})$";
    private static final String CPF_PATTERN = "^(\\d{3}\\.){2}(\\d{3})-(\\d{2})$";
    private static final String CNPJ_PATTERN = "^(\\d{2})(\\.\\d{3}){2}(/\\d{4}-\\d{2})$";

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
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "payer")
    private List<Transaction> transactionsPaid;

    @OneToMany(mappedBy = "payee")
    private List<Transaction> transactionsReceived;

    @CreationTimestamp
    private LocalDateTime localDateTime;

    @PrePersist
    protected void identifyUserType() {
        if (document.matches(CPF_PATTERN)) {
            userType = UserType.COMMON;
        } else if (document.matches(CNPJ_PATTERN)) {
            userType = UserType.MERCHANT;
        } else {
            throw new RuntimeException("Pattern Error. Could not define user type.");
        }
    }
}
