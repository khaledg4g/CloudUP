package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.dto.ProductDto;
import com.projetpi.cloudup.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private IProductService productService;
    @PostMapping()
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDTO){

        try {

            return new ResponseEntity<>(productService.create(productDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @GetMapping("/{id}")

    public ResponseEntity<ProductDto> getById(@PathVariable Long id){

        ProductDto productDTO = productService.getById(id);

        if (productDTO != null) {
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }
    @PutMapping()
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDTO){
        ProductDto product = productService.update(productDTO);
        if (product != null) {

            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        try {
            return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by/minPice/{minPrice}/maxPrice/{maxPrice}")
    public ResponseEntity<List<ProductDto>> findProductsByPriceRange(@PathVariable Integer minPrice, @PathVariable Integer maxPrice){
        try {
            return new ResponseEntity<>(productService.findProductsByPriceRange(minPrice,maxPrice), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by/keyWord/{keyWord}")
    public ResponseEntity<List<ProductDto>> findByKeyWord(@PathVariable String keyWord){
        try {
            return new ResponseEntity<>(productService.findProductsByNameContaining(keyWord), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
