package com.commerceBeauty.commerce.rest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.commerceBeauty.commerce.entities.ImageProduct;
import com.commerceBeauty.commerce.entities.Product;
import com.commerceBeauty.commerce.service.ProductService;



@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = { "/addNewProduct" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Product addNewProduct(@RequestPart("product") Product product,
            @RequestPart("imageFile") MultipartFile[] file) {

        try {
            Set<ImageProduct> images = uploadImage(file);
            product.setProductImages(images);
            return productService.addNewProduct(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Set<ImageProduct> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageProduct> imageModels = new HashSet<>();
       
        for (MultipartFile file : multipartFiles) {
        	 ImageProduct imageModel = new  ImageProduct(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
            imageModels.add(imageModel);
        }

        return imageModels;
    }

    @GetMapping({ "/getAllProducts" })
    public List<Product> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "") String searchKey) {
        List<Product> result = productService.getAllProducts(pageNumber, searchKey);
        System.out.println("Result size is " + result.size());
        return result;
    }

    @GetMapping({ "/getProductDetailsById/{productId}" })
    public Product getProductDetailsById(@PathVariable("productId") Integer productId) {
        return productService.getProductDetailsById(productId);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping({ "/deleteProductDetails/{productId}" })
    public void deleteProductDetails(@PathVariable("productId") Integer productId) {
        productService.deleteProductDetails(productId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({ "/getProductDetails/{isSingleProductCheckout}/{productId}" })
    public List<Product> getProductDetails(
            @PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
            @PathVariable(name = "productId") Integer productId) {
        return productService.getProductDetails(isSingleProductCheckout, productId);
    }
}
