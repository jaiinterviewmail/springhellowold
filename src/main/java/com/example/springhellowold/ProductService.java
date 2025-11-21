package com.example.springhellowold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        logger.debug("ProductService.<init> - initializing sample products");
        // Hardcoded sample products
        products.add(new Product(1L, "Apple", 0.99f, LocalDate.now().plusDays(10)));
        products.add(new Product(2L, "Banana", 0.59f, LocalDate.now().plusDays(7)));
        products.add(new Product(3L, "Carrot", 0.25f, LocalDate.now().plusDays(30)));
        logger.info("ProductService.<init> - initialized {} products", products.size());
    }

    public List<Product> getAllProducts() {
        logger.debug("getAllProducts - returning {} products", products.size());
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(Long id) {
        logger.debug("getProductById - id={}", id);
        Optional<Product> result = products.stream().filter(p -> p.getProductId().equals(id)).findFirst();
        if (result.isPresent()) {
            logger.info("getProductById - found product id={}", id);
        } else {
            logger.error("getProductById - product not found id={}", id);
        }
        return result;
    }

    public Product addProduct(Product product) {
        logger.debug("addProduct - product={}", product);
        try {
            // Simple ID assignment if null
            if (product.getProductId() == null) {
                long maxId = products.stream().mapToLong(p -> p.getProductId()).max().orElse(0);
                product.setProductId(maxId + 1);
                logger.trace("addProduct - assigned id={} to new product", product.getProductId());
            }
            products.add(product);
            logger.info("addProduct - created product id={} name={}", product.getProductId(), product.getProductName());
            return product;
        } catch (RuntimeException ex) {
            logger.error("addProduct - failed to add product {}", product, ex);
            throw ex;
        }
    }

    public Optional<Product> updateProduct(Long id, Product product) {
        logger.debug("updateProduct - id={} product={}", id, product);
        Optional<Product> existing = getProductById(id);
        existing.ifPresent(p -> {
            p.setProductName(product.getProductName());
            p.setProductCost(product.getProductCost());
            p.setProductExpireDate(product.getProductExpireDate());
            logger.info("updateProduct - updated product id={} name={}", id, p.getProductName());
        });
        if (!existing.isPresent()) {
            logger.error("updateProduct - product not found id={}", id);
        }
        return existing;
    }

    public boolean deleteProduct(Long id) {
        logger.debug("deleteProduct - id={}", id);
        boolean removed = products.removeIf(p -> p.getProductId().equals(id));
        if (removed) {
            logger.info("deleteProduct - deleted product id={}", id);
        } else {
            logger.error("deleteProduct - product not found id={}", id);
        }
        return removed;
    }
}
