package com.devbank.receiversregister.service.receiver;

import com.devbank.receiversregister.delivery.rest.dto.Receiver;
import com.devbank.receiversregister.repository.jpa.entities.BankUserEntity;
import com.devbank.receiversregister.repository.jpa.entities.ReceiverEntity;
import com.devbank.receiversregister.repository.jpa.entities.enumeration.CurrencyType;
import com.devbank.receiversregister.repository.jpa.repositories.BankUserRepository;
import com.devbank.receiversregister.repository.jpa.repositories.ReceiverRepository;
import com.devbank.receiversregister.service.receiver.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {
    private final BankUserRepository bankUserRepository;
    private final ReceiverRepository receiverRepository;

    @Override
    public void addReceiver(String userId, Receiver receiverDto) {
        BankUserEntity bankUserEntity = bankUserRepository.findByBankUserId(userId)
                .orElse(BankUserEntity.builder()
                        .bankUserId(userId)
                        .build());
        if (bankUserRepository.getCountOfReceiverByIBAN(userId, receiverDto.getIBAN()) > 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "A Receiver already exists for the given IBAN.");
        }

        ReceiverEntity receiverEntity = ReceiverEntity.builder()
                .name(receiverDto.getName())
                .fatherName(receiverDto.getFatherName())
                .mobile(receiverDto.getMobile())
                .email(receiverDto.getEmail())
                .IBAN(receiverDto.getIBAN())
                .currencyType(CurrencyType.valueOf(receiverDto.getCurrency().name()))
                .build();

        bankUserEntity.addReceiver(receiverEntity);
    }

    @Override
    public void deleteReceiver(String userId, Long receiverId) {
        BankUserEntity bankUserEntity = bankUserRepository.findByBankUserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, format("Bank user with id %s does not exist.", userId)));
        ReceiverEntity receiverEntity = receiverRepository.findById(receiverId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, format("Receiver with id %s does not exist.", receiverId)));

        bankUserEntity.removeReceiver(receiverEntity);
    }

    @Override
    public void updateReceiver(String userId, Long receiverId, Receiver receiverDto) {
        BankUserEntity bankUserEntity = bankUserRepository.findByBankUserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, format("Bank user with id %s does not exist.", userId)));
        ReceiverEntity receiverEntity = receiverRepository.findById(receiverId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, format("Receiver with id %s does not exist.", receiverId)));
        receiverEntity.setName(receiverDto.getName());
        receiverEntity.setFatherName(receiverDto.getFatherName());
        receiverEntity.setMobile(receiverDto.getMobile());
        receiverEntity.setEmail(receiverDto.getEmail());
        receiverEntity.setIBAN(receiverDto.getIBAN());
        receiverEntity.setCurrencyType(CurrencyType.valueOf(receiverDto.getCurrency().name()));
    }

}
