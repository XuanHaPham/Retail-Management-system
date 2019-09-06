package fpt.edu.RetailManagementSystem.api;

import fpt.edu.RetailManagementSystem.service.dto.RoleDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
@RequestMapping("/roles")
public interface RoleApi {
    @ApiOperation(tags = {"Role",}, notes = "", value = "Create new role")
    @PostMapping("")
    ResponseEntity<RoleDTO> insert(@RequestBody RoleDTO roleDTO);

    @ApiOperation(tags = {"Role",}, notes = "", value = "Delete role")
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id);

    @ApiOperation(tags = {"Role",}, notes = "", value = "Get All Account")
    @GetMapping("")
    ResponseEntity<List<RoleDTO>> getAll();


}
