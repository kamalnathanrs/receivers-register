package com.devbank.receiversregister.service.receiver;

import com.devbank.receiversregister.delivery.rest.dto.Receiver;
import com.devbank.receiversregister.delivery.rest.dto.UserReceiversResult;
import com.devbank.receiversregister.repository.jpa.entities.BankUserEntity;
import com.devbank.receiversregister.repository.jpa.entities.ReceiverEntity;
import com.devbank.receiversregister.repository.jpa.entities.enumeration.CurrencyType;
import com.devbank.receiversregister.repository.jpa.repositories.BankUserRepository;
import com.devbank.receiversregister.repository.jpa.repositories.ReceiverRepository;
import com.devbank.receiversregister.service.receiver.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {
    private final BankUserRepository bankUserRepository;
    private final ReceiverRepository receiverRepository;

    @Override
    public Receiver addReceiver(String userId, Receiver receiverDto) {
        BankUserEntity bankUserEntity = bankUserRepository.findByUserId(userId)
                .orElse(BankUserEntity.builder()
                        .userId(userId)
                        .build());
        if (bankUserRepository.getCountOfReceiverByIBAN(userId, receiverDto.getIBAN()) > 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "A Receiver already exists for the given IBAN.");
        }

        ReceiverEntity receiverEntity = mapFromDto(null, receiverDto);
        bankUserEntity.addReceiver(receiverEntity);
        bankUserRepository.saveAndFlush(bankUserEntity);
        return mapToDto(receiverEntity);
    }

    @Override
    public Receiver updateReceiver(String userId, Long receiverId, Receiver receiverDto) {
        ReceiverEntity receiverEntity = getUserReceiver(userId, receiverId);
        receiverEntity = mapFromDto(receiverEntity, receiverDto);
        return mapToDto(receiverRepository.save(receiverEntity));
    }

    @Override
    public UserReceiversResult listUserReceivers(String userId, Integer pageNumber, Integer entries) {
        Pageable pageRequest = PageRequest.of(pageNumber, entries);
        Page<ReceiverEntity> userReceiversPage = receiverRepository.findAllByBankUser_UserId(userId, pageRequest);
        return new UserReceiversResult()
                .resultsFound(Math.toIntExact(userReceiversPage.getTotalElements()))
                .resultsFetched(userReceiversPage.getNumberOfElements())
                .receivers(userReceiversPage.map(this::mapToDto).getContent());
    }

    @Override
    public void deleteReceiver(String userId, Long receiverId) {
        ReceiverEntity receiverEntity = getUserReceiver(userId, receiverId);
        receiverRepository.delete(receiverEntity);
    }

    private ReceiverEntity getUserReceiver(String userId, Long receiverId) {
        return receiverRepository.findByIdAndBankUser_UserId(receiverId, userId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, format("Record not found with receiverId %s and userId %s.", receiverId, userId)));
    }

    private ReceiverEntity mapFromDto(ReceiverEntity receiverEntity, Receiver receiverDto) {
        if(receiverEntity == null) {
            receiverEntity = new ReceiverEntity();
        }
        receiverEntity.setName(receiverDto.getName());
        receiverEntity.setFatherName(receiverDto.getFatherName());
        receiverEntity.setMobile(receiverDto.getMobile());
        receiverEntity.setEmail(receiverDto.getEmail());
        receiverEntity.setIBAN(receiverDto.getIBAN());
        receiverEntity.setCurrencyType(CurrencyType.valueOf(receiverDto.getCurrency().name()));
        return receiverEntity;
    }

    private Receiver mapToDto(ReceiverEntity receiverEntity) {
        return new Receiver()
                .receiverId(receiverEntity.getId())
                .name(receiverEntity.getName())
                .fatherName(receiverEntity.getFatherName())
                .mobile(receiverEntity.getMobile())
                .email(receiverEntity.getEmail())
                .IBAN(receiverEntity.getIBAN())
                .currency(Receiver.CurrencyEnum.fromValue(receiverEntity.getCurrencyType().name()));
    }

}
