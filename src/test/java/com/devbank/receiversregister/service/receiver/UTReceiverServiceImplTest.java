package com.devbank.receiversregister.service.receiver;

import com.devbank.receiversregister.delivery.rest.dto.Receiver;
import com.devbank.receiversregister.delivery.rest.dto.UserReceiversResult;
import com.devbank.receiversregister.repository.jpa.repositories.BankUserRepository;
import com.devbank.receiversregister.repository.jpa.repositories.ReceiverRepository;
import com.devbank.receiversregister.service.receiver.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = "spring.profiles.active = dev")
class UTReceiverServiceImplTest {
    @Resource
    BankUserRepository bankUserRepository;

    @Resource
    ReceiverRepository receiverRepository;

    ReceiverService receiverServiceTest;

    @BeforeEach
    void setUp() {
        receiverServiceTest = new ReceiverServiceImpl(bankUserRepository, receiverRepository);
    }

    @Test
    void addReceiver_success() {
        // prepare
        String userId = "73a79008-ebd6-4921-ad82-e5062e59a471";
        Receiver receiverRequest = new Receiver()
                .name("John James")
                .fatherName("Jim James")
                .email("test@example.com")
                .mobile("004712345678")
                .IBAN("NO 93 8601 1117949")
                .currency(Receiver.CurrencyEnum.NOK);

        // execute
        Receiver result = receiverServiceTest.addReceiver(userId, receiverRequest);

        // verify
        assertNotNull(result);
        assertEquals("John James", result.getName());
        assertEquals("Jim James", result.getFatherName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("004712345678", result.getMobile());
        assertEquals("NO 93 8601 1117949", result.getIBAN());
        assertEquals(Receiver.CurrencyEnum.NOK, result.getCurrency());
    }

    @Test
    void addReceiver_whenReceiverAlreadyExistsByIBAN_thenFail() {
        // prepare
        String userId = "73a79008-ebd6-4921-ad82-e5062e59a471";
        Receiver addedReceiver = createReceiver(userId, "NO 93 8601 1117949");

        Receiver receiverRequest = new Receiver()
                .name("John James")
                .fatherName("Jim James")
                .email("test@example.com")
                .mobile("004712345678")
                .IBAN(addedReceiver.getIBAN())
                .currency(Receiver.CurrencyEnum.NOK);

        // execute
        try{
            receiverServiceTest.addReceiver(userId, receiverRequest);
            fail("Excepted exception when receiver exists with same IBAN.");
        } catch (CustomException ce) {
            // verify
            assertEquals(HttpStatus.BAD_REQUEST, ce.getHttpStatus());
            assertEquals("A Receiver already exists for the given IBAN.", ce.getMessage());
        }
    }

    @Test
    void updateReceiver() {
        // prepare
        String userId = "73a79008-ebd6-4921-ad82-e5062e59a471";
        Receiver receiverToUpdate = createReceiver(userId, "NO 93 8601 1117949");

        // Update receiver
        Receiver updateReceiverRequest = new Receiver()
                .name("John James 1")
                .fatherName("Jim James 1")
                .email("john@example.com")
                .mobile("004787654321")
                .IBAN("NO 94 8601 1117949")
                .currency(Receiver.CurrencyEnum.DKK);

        // Execute
        Receiver result = receiverServiceTest.updateReceiver(userId, receiverToUpdate.getReceiverId(), updateReceiverRequest);

        // verify
        assertNotNull(result);
        assertEquals("John James 1", result.getName());
        assertEquals("Jim James 1", result.getFatherName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("004787654321", result.getMobile());
        assertEquals("NO 94 8601 1117949", result.getIBAN());
        assertEquals(Receiver.CurrencyEnum.DKK, result.getCurrency());
    }

    @Test
    void updateReceiver_whenAnotherReceiverAlreadyExistsByIBAN_thenFail() {
        // prepare
        String userId = "73a79008-ebd6-4921-ad82-e5062e59a471";
        Receiver existingReceiver = createReceiver(userId, "NO 93 8601 1117949");
        Receiver receiverToUpdate = createReceiver(userId, "no 93 8601 1117950");

        Receiver updateReceiverRequest = new Receiver()
                .name("John James")
                .fatherName("Jim James")
                .email("test@example.com")
                .mobile("004712345678")
                .IBAN(existingReceiver.getIBAN())
                .currency(Receiver.CurrencyEnum.NOK);

        // execute
        try{
            receiverServiceTest.updateReceiver(userId, receiverToUpdate.getReceiverId(), updateReceiverRequest);
            fail("Excepted exception when another receiver exists with same IBAN.");
        } catch (CustomException ce) {
            // verify
            assertEquals(HttpStatus.BAD_REQUEST, ce.getHttpStatus());
            assertEquals("A Receiver already exists for the given IBAN.", ce.getMessage());
        }
    }

    @Test
    void listUserReceivers() {
        // prepare
        String userId = "73a79008-ebd6-4921-ad82-e5062e59a471";
        createReceiver(userId, "NO 93 8601 1117949");

        // Execute
        UserReceiversResult result = receiverServiceTest.listUserReceivers(userId, 0, 10);

        // verify
        assertNotNull(result);
        assertEquals(1, result.getReceivers().size());
        assertEquals("John James", result.getReceivers().get(0).getName());
        assertEquals("Jim James", result.getReceivers().get(0).getFatherName());
        assertEquals("test@example.com", result.getReceivers().get(0).getEmail());
        assertEquals("004712345678", result.getReceivers().get(0).getMobile());
        assertEquals("NO 93 8601 1117949", result.getReceivers().get(0).getIBAN());
        assertEquals(Receiver.CurrencyEnum.NOK, result.getReceivers().get(0).getCurrency());
    }

    @Test
    void deleteReceiver() {
        // prepare
        String userId = "73a79008-ebd6-4921-ad82-e5062e59a471";
        Receiver addedReceiver = createReceiver(userId, "NO 93 8601 1117949");

        // execute
        receiverServiceTest.deleteReceiver(userId, addedReceiver.getReceiverId());
    }

    private Receiver createReceiver(String userId, String IBAN) {
        Receiver createReceiverRequest = new Receiver()
                .name("John James")
                .fatherName("Jim James")
                .email("test@example.com")
                .mobile("004712345678")
                .IBAN(IBAN)
                .currency(Receiver.CurrencyEnum.NOK);
        return receiverServiceTest.addReceiver(userId, createReceiverRequest);
    }
}