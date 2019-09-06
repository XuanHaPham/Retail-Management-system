package fpt.edu.RetailManagementSystem.service;

import fpt.edu.RetailManagementSystem.service.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();

    Boolean deleteByID(Integer id);

    ProductDTO findByID(Integer id);

    ProductDTO insert(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);
}
