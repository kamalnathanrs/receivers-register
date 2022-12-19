package com.devbank.receiversregister.delivery.rest;

import com.devbank.receiversregister.delivery.rest.dto.Receiver;
import com.devbank.receiversregister.service.receiver.ReceiverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserReceiverController.class)
@AutoConfigureMockMvc(addFilters = false)
class UTUserReceiverControllerTest {
    @MockBean
    ReceiverService receiverService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addReceiver() throws Exception {
        String userId = "73a79008-ebd6-4921-ad82-e5062e59a471";
        Receiver receiverRequest = new Receiver()
                .name("John James")
                .fatherName("Jim James")
                .email("test@example.com")
                .mobile("004712345678")
                .IBAN("NO 93 8601 1117949")
                .currency(Receiver.CurrencyEnum.NOK);

        Receiver receiverResponse = new Receiver()
                .receiverId(2L)
                .name("John James")
                .fatherName("Jim James")
                .email("test@example.com")
                .mobile("004712345678")
                .IBAN("NO 93 8601 1117949")
                .currency(Receiver.CurrencyEnum.NOK);

        Mockito.when(receiverService.addReceiver(userId, receiverRequest)).thenReturn(receiverResponse);

        MvcResult mvcResult = mockMvc.perform(post("/bank-user/73a79008-ebd6-4921-ad82-e5062e59a471/receiver")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(receiverRequest))).andReturn();

        assertEquals(201, mvcResult.getResponse().getStatus());
        Receiver result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Receiver.class);
        assertEquals("NO 93 8601 1117949", result.getIBAN());
    }
}