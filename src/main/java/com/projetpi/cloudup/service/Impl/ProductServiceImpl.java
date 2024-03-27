package com.projetpi.cloudup.service.Impl;

import com.projetpi.cloudup.dto.ProductDto;
import com.projetpi.cloudup.entities.Product;
import com.projetpi.cloudup.repository.IProductRepository;
import com.projetpi.cloudup.service.IProductService;
import lombok.AllArgsConstructor;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class ProductServiceImpl implements IProductService {

    private Mapper mapper;
    private IProductRepository productRepository;
    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(
                        product -> mapper.map(product, ProductDto.class)
                ).toList();
    }

    @Override
    public ProductDto create(ProductDto productDTO) {
        return mapper.map(
                productRepository.save(
                        mapper.map(productDTO, Product.class)
                )
                , ProductDto.class
        );
    }

    @Override
    public void delete(Long idPro) {
        productRepository.deleteById(idPro);

    }

    @Override
    public ProductDto update(ProductDto productDTO) {

        return productRepository.findById(productDTO.getId())
                .map(product -> {
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setPrice(productDTO.getPrice());
                    product.setAvailable(productDTO.isAvailable());
                    product.setImageUrl(productDTO.getImageUrl());
                    product.setRating(productDTO.getRating());
                    product.setRatingsUsersNumber(productDTO.getRatingsUsersNumber());
                    return mapper.map(productRepository.save(product), ProductDto.class);
                }).orElse(null);

    }


    @Override
    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .map(product -> mapper.map(product, ProductDto.class))
                .orElse(null);
    }

    @Override
    public List<ProductDto> findProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(
                        product -> mapper.map(product, ProductDto.class)
                ).toList();
    }

    @Override
    public List<ProductDto> findProductsByNameContaining(String keyword) {
        return productRepository.findByNameContaining(keyword)
                .stream()
                .map(
                        product -> mapper.map(product, ProductDto.class)
                ).toList();
    }
}
