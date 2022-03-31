package ozi.app.printer.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void test_createOrder() throws Exception{
        //given
        OrderCreationRequest orderCreationRequest = new OrderCreationRequest();
        orderCreationRequest.setUserId("9403JHNAKSIAojIR");
        orderCreationRequest.setOrderDate(LocalDateTime.now());
        orderCreationRequest.setSize(12);
        orderCreationRequest.setQuantity(4);
        orderCreationRequest.setImageUrl("aurl");

        //when
        mockMvc.perform(post("/api/admin/print/order")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderCreationRequest)))
                .andExpect(status().is(201))
                .andDo(print());
    }
}