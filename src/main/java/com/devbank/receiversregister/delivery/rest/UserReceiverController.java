package com.devbank.receiversregister.delivery.rest;

import com.devbank.receiversregister.delivery.rest.api.UserReceiverApi;
import com.devbank.receiversregister.delivery.rest.dto.Receiver;
import com.devbank.receiversregister.service.receiver.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserReceiverController implements UserReceiverApi {
    private final ReceiverService receiverService;

    @Override
    public ResponseEntity<Receiver> addReceiver(String userId, Receiver receiver) {
        receiverService.addReceiver(userId, receiver);
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteReceiver(String userId, Long receiverId) {
        receiverService.deleteReceiver(userId, receiverId);
        return null;
    }

    @Override
    public ResponseEntity<Receiver> updateReceiver(String userId, Long receiverId, Receiver receiver) {
        receiverService.updateReceiver(userId, receiverId, receiver);
        return null;
    }
}
