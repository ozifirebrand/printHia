package ozi.app.printer.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.services.orderService.OrderServices;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class UserControllerTest {

    private ObjectMapper objectMapper;
    private OrderCreationRequest request;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Mock

    private OrderServices orderServices;

    @BeforeEach
    public void setUp(){
        request = new OrderCreationRequest();

        request.setOrderDate(LocalDateTime.now());
        request.setQuantity(1);
        request.setImageUrl("animageurl");
        request.setSize(42);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Test
    public void makePrintOrder() throws Exception {
        //given @ setup


        //when
        //assert
        mockMvc.perform(post("/api/user/print/order")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    public void viewOrder() throws Exception {
        //given @ setup
        OrderCreationResponse response = orderServices.createOrder(request);

        //when
        String id = response.getId();
        log.info(" id is {}", id);
        mockMvc.perform(get("/api/user/print/order/ff8080817fc6adad017fc6adbb270000")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(200))
                .andDo(print()
        );

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