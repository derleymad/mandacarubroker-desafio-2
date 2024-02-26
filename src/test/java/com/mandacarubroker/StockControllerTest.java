package com.mandacarubroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.controller.StockController;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    private MockMvc mockMvc;

    @Test
    void testGetAllStocks() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Stock stock1 = new Stock(id1.toString(), "BBS3", "Banco do Brasil 2", 60.00);
        Stock stock2 = new Stock(id2.toString(), "PTR4", "Petrobras", 30.00);
        when(stockService.getAllStocks()).thenReturn(Arrays.asList(stock1, stock2));

        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetStockById() throws Exception {
        UUID id = UUID.randomUUID();
        Stock stock = new Stock(id.toString(), "BBB3", "Banco do brasil 3", 60.00);
        when(stockService.getStockById(id.toString())).thenReturn(Optional.of(stock));

        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
        mockMvc.perform(get("/stocks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.symbol").value("BBB3"))
                .andExpect(jsonPath("$.companyName").value("Banco do brasil 3"))
                .andExpect(jsonPath("$.price").value(60.00));
    }

    @Test
    void testCreateStock() throws Exception {
        UUID id = UUID.randomUUID();
        RequestStockDTO requestStockDTO = new RequestStockDTO("BBB2", "Banco do brasil 2", 50.10);
        Stock createdStock = new Stock(id.toString(), "BBB2", "Banco do brasil 2", 50.10);
        when(stockService.validateAndCreateStock(any(RequestStockDTO.class))).thenReturn(createdStock);

        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestStockDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("BBB2"))
                .andExpect(jsonPath("$.companyName").value("Banco do brasil 2"))
                .andExpect(jsonPath("$.price").value(50.10));
    }

    @Test
    void testUpdateStock() throws Exception {
        UUID id = UUID.randomUUID();
        Stock updatedStock = new Stock(id.toString(), "BBB4", "Banco do Brasil 4", 60.00);
        when(stockService.updateStock(any(String.class), any(Stock.class))).thenReturn(Optional.of(updatedStock));

        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
        mockMvc.perform(put("/stocks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedStock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.symbol").value("BBB4"))
                .andExpect(jsonPath("$.companyName").value("Banco do Brasil 4"))
                .andExpect(jsonPath("$.price").value(60.00));
    }

    @Test
    void testDeleteStock() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
        mockMvc.perform(delete("/stocks/{id}", id))
                .andExpect(status().isNoContent());
    }
}
