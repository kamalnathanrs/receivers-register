package com.devbank.receiversregister.service.receiver;

import com.devbank.receiversregister.delivery.rest.dto.Receiver;

public interface ReceiverService {

    void addReceiver(String userId, Receiver receiver);

    void deleteReceiver(String userId, Long receiverId);

    void updateReceiver(String userId, Long receiverId, Receiver receiver);
}
