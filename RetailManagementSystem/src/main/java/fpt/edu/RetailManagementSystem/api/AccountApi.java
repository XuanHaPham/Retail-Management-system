package fpt.edu.RetailManagementSystem.api;
import fpt.edu.RetailManagementSystem.service.dto.AccountDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Component
@RequestMapping("/accounts")
public interface AccountApi {

    @ApiOperation(tags = {"Account",}, notes = "", value = "Create new Account")
    @PostMapping("")
    ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO);

    @ApiOperation(tags = {"Account",}, notes = "", value = "Update Account")
    @PutMapping("")
    ResponseEntity<AccountDTO> update(@RequestBody AccountDTO accountDTO);

    @ApiOperation(tags = {"Account",}, notes = "", value = "Get All Account")
    @GetMapping("")
    ResponseEntity<List<AccountDTO>> getAll();

    @ApiOperation(tags = {"Account",}, notes = "", value = "Delete Account")
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id);

}