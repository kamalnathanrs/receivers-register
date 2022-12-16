package com.devbank.receiversregister.repository.jpa.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceiverEntity> receivers;

    public void addReceiver(ReceiverEntity receiver) {
        receiver.setBankUser(this);
        getReceivers().add(receiver);
    }

    public List<ReceiverEntity> getReceivers() {
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        return receivers;
    }

}