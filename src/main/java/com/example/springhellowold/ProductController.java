package com.example.springhellowold;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
        logger.debug("ProductController.<init> - initialized");
    }

    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public List<Product> getAllProducts() {
        logger.debug("getAllProducts - enter");
        List<Product> list = productService.getAllProducts();
        logger.info("getAllProducts - returning {} products", list.size());
        return list;
    }

    @Operation(summary = "Get product by ID", description = "Returns a single product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.debug("getProductById - id={}", id);
        return productService.getProductById(id)
                .map(p -> {
                    logger.info("getProductById - found product id={} name={}", id, p.getProductName());
                    return ResponseEntity.ok(p);
                })
                .orElseGet(() -> {
                    logger.error("getProductById - not found id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Add a new product", description = "Creates a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        logger.debug("addProduct - payload={}", product);
        Product created = productService.addProduct(product);
        logger.info("addProduct - created id={} name={}", created.getProductId(), created.getProductName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a product", description = "Updates an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        logger.debug("updateProduct - id={} payload={}", id, product);
        return productService.updateProduct(id, product)
                .map(p -> {
                    logger.info("updateProduct - updated id={} name={}", id, p.getProductName());
                    return ResponseEntity.ok(p);
                })
                .orElseGet(() -> {
                    logger.error("updateProduct - not found id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.debug("deleteProduct - id={}", id);
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            logger.info("deleteProduct - deleted id={}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.error("deleteProduct - not found id={}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
