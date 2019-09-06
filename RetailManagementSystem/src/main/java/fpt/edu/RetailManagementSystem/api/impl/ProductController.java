package fpt.edu.RetailManagementSystem.api.impl;

import fpt.edu.RetailManagementSystem.api.ProductApi;
import fpt.edu.RetailManagementSystem.service.ProductService;
import fpt.edu.RetailManagementSystem.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController implements ProductApi {
    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> productDTOS = productService.getAll();
        return ResponseEntity.ok(productDTOS);
    }


    @Override
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id) {
        Boolean result = productService.deleteByID(id);
        Map<String, Boolean> resul = new HashMap<>();
        resul.put("Content", result);
        return ResponseEntity.ok(resul);
    }

    @Override
    public ResponseEntity<ProductDTO> findByID(@PathVariable("id") Integer id){
        ProductDTO productDTO = productService.findByID(id);
        return ResponseEntity.ok(productDTO);
    }

    @Override
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO) {
        ProductDTO dto = productService.insert(productDTO);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
        ProductDTO dto = productService.update(productDTO);
        return ResponseEntity.ok(dto);
    }
}
