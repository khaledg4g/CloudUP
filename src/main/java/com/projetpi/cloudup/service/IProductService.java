package com.projetpi.cloudup.service;

import com.projetpi.cloudup.dto.ProductDto;

import java.util.List;

public interface IProductService {
    public List<ProductDto> getAll();
    public ProductDto create(ProductDto productDTO);
    public void delete(Long id);
    public ProductDto update(ProductDto productDTO);
    public ProductDto getById(Long id);
    public List<ProductDto> findProductsByPriceRange(double minPrice, double maxPrice);
    public List<ProductDto> findProductsByNameContaining(String keyword);
}
