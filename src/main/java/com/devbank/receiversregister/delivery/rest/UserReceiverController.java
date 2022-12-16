package com.devbank.receiversregister.delivery.rest;

import com.devbank.receiversregister.delivery.rest.api.UserReceiverApi;
import com.devbank.receiversregister.delivery.rest.dto.Receiver;
import com.devbank.receiversregister.delivery.rest.dto.UserReceiversResult;
import com.devbank.receiversregister.service.receiver.ReceiverService;
import com.devbank.receiversregister.service.receiver.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserReceiverController implements UserReceiverApi {
    private final ReceiverService receiverService;

    @Override
    public ResponseEntity<Receiver> addReceiver(String userId, Receiver receiver) {
        return new ResponseEntity<>(
                receiverService.addReceiver(userId, receiver),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Receiver> updateReceiver(String userId, Long receiverId, Receiver receiver) {
        return new ResponseEntity<>(
                receiverService.updateReceiver(userId, receiverId, receiver),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserReceiversResult> listUserReceivers(String userId, Integer pageNumber, Integer entries) {
        return new ResponseEntity<>(
                receiverService.listUserReceivers(userId, pageNumber, entries),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteReceiver(String userId, Long receiverId) {
        receiverService.deleteReceiver(userId, receiverId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getMessage());
    }
}
