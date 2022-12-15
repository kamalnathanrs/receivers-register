package com.devbank.receiversregister.repository.jpa.entities;

import com.devbank.receiversregister.repository.jpa.entities.enumeration.CurrencyType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RECEIVER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private @NonNull String name;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "IBAN", nullable = false)
    private String IBAN;

    @Column(name = "currency_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @ToString.Exclude
    @ManyToMany(mappedBy = "receivers", fetch = FetchType.LAZY)
    private Set<BankUserEntity> bankUsers;
}
