package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockNotFoundException;
import com.mandacarubroker.service.StockService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable String id) {
        Optional<Stock> stock = stockService.getStockById(id);

        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data) {
        try {
            Stock createdStock = stockService.validateAndCreateStock(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable String id, @RequestBody Stock updatedStock) {
        Optional<Stock> updated = stockService.updateStock(id, updatedStock);

        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable String id) {
        try {
            stockService.deleteStock(id);
            return ResponseEntity.noContent().build();
        } catch (StockNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
