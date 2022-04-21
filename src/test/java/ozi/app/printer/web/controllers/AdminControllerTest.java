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
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.services.orderService.OrderServices;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    OrderServices orderServices;

    @Autowired
    MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private OrderCreationRequest orderCreationRequest;
    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        orderCreationRequest = new OrderCreationRequest();
        orderCreationRequest.setUserId("9403JHNAKSIAojIR");
        orderCreationRequest.setSize(12);
        orderCreationRequest.setQuantity(4);
        orderCreationRequest.setImageUrl("aurl");
    }

    @Test
    public void test_createOrder() throws Exception{
        //given @ setup

        //when then assert
        mockMvc.perform(post("/api/admin/print/order")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderCreationRequest)))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void test_incompleteDetailsThrowsBusinessLogicException() throws Exception {
        //given
        orderCreationRequest.setImageUrl(null);

        //when then assert
        mockMvc.perform(post("/api/admin/print/order")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderCreationRequest)))
                .andExpect(status().is(400))
                .andDo(print());

    }

    @Test
    public void test_getOrderById() throws Exception{

        //give
        OrderCreationResponse orderCreationResponse = orderServices.createOrder(orderCreationRequest);
        String orderId = orderCreationResponse.getId();

        //when then assert
        mockMvc.perform(get("/api/admin/print/order/"+orderId)
                .contentType("application/json"))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    public void test_wrongIdThrowsBusinessLogicException() throws Exception {
        //when then assert
        mockMvc.perform(get("/api/admin/print/order/uwtul593Ih9khh")
                        .contentType("application/json"))
                .andExpect(status().is(400))
                .andDo(print());
    }

}