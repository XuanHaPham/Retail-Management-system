package fpt.edu.RetailManagementSystem.service.impl;

import fpt.edu.RetailManagementSystem.persistent.entity.Product;
import fpt.edu.RetailManagementSystem.persistent.repository.ProductRepository;
import fpt.edu.RetailManagementSystem.service.ProductService;
import fpt.edu.RetailManagementSystem.service.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAll(){
        List<Product> products = productRepository.findAllProduct();
        List<ProductDTO> productDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Product pro : products ) {
            ProductDTO productDTO = modelMapper.map(pro, ProductDTO.class);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public Boolean deleteByID(Integer id){
        Optional.ofNullable(productRepository.findById(id)).orElseThrow(() ->new EntityNotFoundException());
        productRepository.deleteByID(id);
        return true;
    }

    @Override
    public ProductDTO findByID(Integer id){
        Product product = productRepository.findByID(id);
        if(ObjectUtils.isEmpty(product)) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }
    @Override
    public ProductDTO insert(ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productDTO, Product.class);
        product = productRepository.save(product);
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        return dto;
    }

    @Override
    public ProductDTO update(ProductDTO productDTO){
        Optional.ofNullable(productRepository.findById(productDTO.getId())).orElseThrow(() -> new EntityNotFoundException());
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productDTO, Product.class);
        product = productRepository.saveAndFlush(product);
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        return dto;
    }

}
