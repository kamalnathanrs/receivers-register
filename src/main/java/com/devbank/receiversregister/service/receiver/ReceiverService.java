package com.devbank.receiversregister.service.receiver;

import com.devbank.receiversregister.delivery.rest.dto.Receiver;
import com.devbank.receiversregister.delivery.rest.dto.UserReceiversResult;

public interface ReceiverService {
    Receiver addReceiver(String userId, Receiver receiver);

    Receiver updateReceiver(String userId, Long receiverId, Receiver receiver);

    UserReceiversResult listUserReceivers(String userId, Integer pageNumber, Integer entries);

    void deleteReceiver(String userId, Long receiverId);
}
