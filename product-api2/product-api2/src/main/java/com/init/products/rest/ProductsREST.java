package com.init.products.rest;


import com.init.products.dao.ProductsDAO;
import com.init.products.entitys.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ProductsREST {

    @Autowired
    private ProductsDAO productDAO;

    @GetMapping
    public ResponseEntity<List<Product>> getProduct(){
        List<Product> products = productDAO.findAll();
        return ResponseEntity.ok(products);
    }

    @RequestMapping(value="{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
        Optional<Product> optionalProduct = productDAO.findById(productId);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product newProduct = productDAO.save(product);
        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping(value="{productId}")
    public ResponseEntity<Void> createProduct(@PathVariable("productId") Long productId){
        productDAO.deleteById(productId);
        return ResponseEntity.ok(null);
    }

    //@PutMapping
    @RequestMapping(value = "{productId}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Optional<Product> optionalProduct = productDAO.findById(product.getId());
        if (optionalProduct.isPresent()){
            Product UpdateProduct = optionalProduct.get();
            UpdateProduct.setName(product.getName());
            productDAO.save(UpdateProduct);
            return ResponseEntity.ok(UpdateProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //@GetMapping
    //@RequestMapping(value="hello", method = RequestMethod.GET)
    public String hello() {
            return "Hello world";
    }
}
