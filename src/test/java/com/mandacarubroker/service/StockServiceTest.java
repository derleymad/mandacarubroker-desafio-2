package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockNotFoundException;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {
    @Mock
    private StockRepository stockRepository;

    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockService(stockRepository);
    }

    @Test
    void getAllShouldWork() {
        List<Stock> expectedStocks = new ArrayList<>();
        Mockito.when(stockRepository.findAll()).thenReturn(expectedStocks);

        List<Stock> actualStocks = stockService.getAllStocks();

        assertEquals(expectedStocks, actualStocks);
    }

    @Test
    void getByIdShouldWork() {
        String id = "1";
        Stock expectedStock = new Stock();
        Mockito.when(stockRepository.findById(id)).thenReturn(Optional.of(expectedStock));

        Optional<Stock> actualStock = stockService.getStockById(id);

        assertEquals(Optional.of(expectedStock), actualStock);
    }

    @Test
    void updateStockShouldWork_ifValid() {
        String id = "1";
        Stock updatedStock = new Stock();
        Stock expectedStock = new Stock();
        Mockito.when(stockRepository.findById(id)).thenReturn(Optional.of(updatedStock));
        Mockito.when(stockRepository.save(updatedStock)).thenReturn(expectedStock);

        Optional<Stock> actualStock = stockService.updateStock(id, updatedStock);

        assertEquals(Optional.of(expectedStock), actualStock);
    }

    @Test
    void updateStockShouldThrow_ifInvalid() {
        String id = "1";
        Stock updatedStock = new Stock();
        Mockito.when(stockRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Stock> actualStock = stockService.updateStock(id, updatedStock);

        assertEquals(Optional.empty(), actualStock);
    }

    @Test
    @SneakyThrows
    void deleteShouldWork_ifFound() {
        String id = "1";
        Stock existingStock = new Stock();
        Mockito.when(stockRepository.findById(id)).thenReturn(Optional.of(existingStock));
        Mockito.doNothing().when(stockRepository).deleteById(id);

        stockService.deleteStock(id);

        Mockito.verify(stockRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteShouldThrow_ifNotFound() {
        String id = "1";
        Mockito.when(stockRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StockNotFoundException.class, () -> stockService.deleteStock(id));
    }

    @Test
    void createStockShouldWork_ifValid() {
        RequestStockDTO requestData = new RequestStockDTO("ABC1", "Test Company", 1);
        Stock expectedStock = new Stock();
        Mockito.when(stockRepository.save(ArgumentMatchers.any())).thenReturn(expectedStock);

        Stock actualStock = stockService.validateAndCreateStock(requestData);

        assertEquals(expectedStock, actualStock);
    }

    @Test
    void createStockShouldThrow_ifInvalid() {
        RequestStockDTO requestData = new RequestStockDTO("ABC1", "Test Company", 1);
        Set<ConstraintViolation<RequestStockDTO>> violations = new HashSet<>();
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        Mockito.when(stockRepository.save(ArgumentMatchers.any())).thenThrow(exception);

        assertThrows(ConstraintViolationException.class, () -> stockService.validateAndCreateStock(requestData));
    }
}
