package com.devbank.receiversregister.repository.jpa.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BANK_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_user_id", nullable = false, unique = true)
    private String bankUserId;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "user_receivers",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_RECEIVERS_USER_ID")),
            inverseJoinColumns = @JoinColumn(name = "receiver_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_RECEIVERS_RECEIVER_ID")))
    private Set<ReceiverEntity> receivers = new HashSet<>();

    public boolean addReceiver(ReceiverEntity receiver) {
        return receivers.add(receiver);
    }

    public boolean removeReceiver(ReceiverEntity receiver) {
        return receivers.remove(receiver);
    }

}