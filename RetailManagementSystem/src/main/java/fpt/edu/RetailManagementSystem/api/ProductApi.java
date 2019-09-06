package fpt.edu.RetailManagementSystem.api;

import fpt.edu.RetailManagementSystem.service.dto.ProductDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
@RequestMapping("/products")
public interface ProductApi {
    @ApiOperation(tags = {"Product",}, notes = "", value = "Get all product")
    @GetMapping("/getAllProduct")
    ResponseEntity<List<ProductDTO>> getAllProduct();

    @ApiOperation(tags = {"Product",}, notes = "", value = "Delete product by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id);

    @ApiOperation(tags = {"Product",}, notes = "", value = "Get product by ID")
    @GetMapping("/findByID")
    ResponseEntity<ProductDTO> findByID(@RequestParam("id") Integer id);

    @ApiOperation(tags = {"Product",}, notes = "", value = "insert product")
    @PostMapping("")
    ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO);

    @ApiOperation(tags = {"Product",}, notes = "", value = "update product")
    @PutMapping("")
    ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO);
}
