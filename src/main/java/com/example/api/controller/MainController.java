package com.example.api.controller;

import com.example.api.model.Product;
import com.example.api.model.Term;
import com.example.api.service.ProductService;
import com.example.api.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TermService termService;

    // Endpoints para Product
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/products/sku/{sku}", method = RequestMethod.GET)
    public Product getProductBySku(@PathVariable String sku) {
        return productService.getProductBySku(sku);
    }

    @RequestMapping(value = "/products/name/{name}", method = RequestMethod.GET)
    public Product getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    // Endpoints para Term
    @RequestMapping(value = "/terms", method = RequestMethod.POST)
    public Term addTerm(@RequestBody Term term) {
        return termService.addTerm(term);
    }

    @RequestMapping(value = "/terms", method = RequestMethod.GET)
    public List<Term> getAllTerms() {
        return termService.getAllTerms();
    }

    // Cálculo de cotización
    @RequestMapping(value = "/quote/calculate", method = RequestMethod.GET)
    public String calculateQuote(@RequestParam String productSku, @RequestParam Long termId) {
        Product product = productService.getProductBySku(productSku);
        Term term = termService.getAllTerms().stream()
            .filter(t -> t.getId().equals(termId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Term not found"));

        if (product == null || term == null) {
            return "Product or Term not found";
        }

        double normalPayment = ((product.getPrice() * term.getNormalRate()) + product.getPrice()) / term.getWeeks();
        double punctualPayment = ((product.getPrice() * term.getPunctualRate()) + product.getPrice()) / term.getWeeks();

        return String.format("Normal Payment: %.2f, Punctual Payment: %.2f", normalPayment, punctualPayment);
    }
}
