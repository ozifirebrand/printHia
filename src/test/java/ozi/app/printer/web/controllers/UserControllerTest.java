package ozi.app.printer.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void makePrintOrder() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OrderCreationRequest request = new OrderCreationRequest();
        request.setOrderDate(LocalDateTime.now());
        request.setQuantity(1);
        request.setImageUrl("animageurl");
        request.setSize(42);

        //when
        mockMvc.perform(post("/api/user/print/order")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    public void viewOrder() {

    }

    @Test
    public void viewAllOrders() {
    }

    @Test
    public void viewProfileDetails() {
    }

    @Test
    public void editProfileDetails() {
    }
}