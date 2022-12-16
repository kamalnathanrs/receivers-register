package com.devbank.receiversregister.repository.jpa.entities;

import com.devbank.receiversregister.repository.jpa.entities.enumeration.CurrencyType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "RECEIVER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "fk_bank_user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RECEIVER_BANK_USER_ID"))
    private BankUserEntity bankUser;
}
