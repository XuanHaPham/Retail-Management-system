package fpt.edu.RetailManagementSystem.service.impl;
import fpt.edu.RetailManagementSystem.persistent.entity.Account;
import fpt.edu.RetailManagementSystem.persistent.entity.AccountRole;
import fpt.edu.RetailManagementSystem.persistent.entity.Role;
import fpt.edu.RetailManagementSystem.persistent.repository.AccountRepository;
import fpt.edu.RetailManagementSystem.persistent.repository.AccountRoleRepository;
import fpt.edu.RetailManagementSystem.persistent.repository.RoleRepository;
import fpt.edu.RetailManagementSystem.service.AccountService;
import fpt.edu.RetailManagementSystem.service.dto.AccountDTO;
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
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;

    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountRoleRepository = accountRoleRepository;
    }


    @Override
    public AccountDTO getByUsernameAndPassword(String username, String password) {
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        if(ObjectUtils.isEmpty(account)) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public List<AccountDTO> getAll() {
        List<Account> accounts = accountRepository.findAllByIsDelete();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Account acc : accounts ) {
            AccountDTO accountDTO = modelMapper.map(acc, AccountDTO.class);
            if(accountRoleRepository.getRoleIDByAccountID(acc.getId()) != null)
            {
                int roleId = accountRoleRepository.getRoleIDByAccountID(acc.getId());
                if(!roleRepository.getRoleNameByRoleId(roleId).equals("Admin")){
                    accountDTO.setRoleName(roleRepository.getRoleNameByRoleId(roleId));
                    accountDTOS.add(accountDTO);
                }
            }
        }
        return accountDTOS;
    }

    @Override
    public Boolean delete(Integer id) {
        Optional.ofNullable(accountRepository.findById(id)).orElseThrow(() ->new EntityNotFoundException());
        accountRepository.deleteByID(id);
        return true;
    }

    @Override
    public AccountDTO insert(AccountDTO accountDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class);
        account = accountRepository.save(account);
        int roleId = roleRepository.getRoleIDByRoleName(accountDTO.getRoleName());
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountID(account.getId());
        accountRole.setRoleID(roleId);
        accountRole.setStatus(true);
        accountRoleRepository.save(accountRole);
        AccountDTO dto = modelMapper.map(account, AccountDTO.class);
        return dto;
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        Optional.ofNullable(accountRepository.findById(accountDTO.getId())).orElseThrow(() -> new EntityNotFoundException());
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class);
        account = accountRepository.saveAndFlush(account);
        AccountDTO dto = modelMapper.map(account, AccountDTO.class);
        return dto;
    }
}