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
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.services.userService.UserServices;

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
    private UserServices userServices;


    private UserCreationRequest userCreationRequest;
    private UserCreationResponse userCreationResponse;
    @BeforeEach
    public void setUp() throws BusinessLogicException {
        request = new OrderCreationRequest();

        request.setOrderDate(LocalDateTime.now());
        request.setQuantity(1);
        request.setImageUrl("animageurl");
        request.setSize(42);

        userCreationRequest.setLastName("Mobola");
        userCreationRequest.setUsername("herUsername");
        userCreationRequest.setPassword("amiMobOla?");
        userCreationRequest.setEmail("mobola@gmail.com");

        userCreationRequest.setFirstName("Folade");
        userCreationResponse = userServices.createUser(userCreationRequest);

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
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void test_badRequest_ifRequestBodyIncomplete() throws Exception {
        //given
        request.setSize(0);
        mockMvc.perform(post("/api/user/print/order")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andDo(print());
    }


    @Test
    public void test_badRequest_withInvalidId() throws Exception {
        //assert
        mockMvc.perform(get("/api/user/print/order/09489dagrauaAjyash")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andDo(print());

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