package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockNotFoundException;
import com.mandacarubroker.domain.stock.StockRepository;
import org.springframework.stereotype.Service;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(String id) {
        return stockRepository.findById(id);
    }

    public Stock validateAndCreateStock(RequestStockDTO data) {
        validateRequestStockDTO(data);
        Stock stock = new Stock(data);
        return stockRepository.save(stock);
    }

    public Optional<Stock> updateStock(String id, Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.getSymbol());
                    stock.setCompanyName(updatedStock.getCompanyName());
                    double newPrice = stock.changePrice(updatedStock.getPrice(), true);
                    stock.setPrice(newPrice);
                    return stockRepository.save(stock);
                });
    }

    public void deleteStock(String id) throws StockNotFoundException {
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isPresent()) {
            stockRepository.deleteById(id);
        } else {
            throw new StockNotFoundException("Stock not found with ID: " + id);
        }
    }

    public static void validateRequestStockDTO(RequestStockDTO data) {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }

            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }
}
