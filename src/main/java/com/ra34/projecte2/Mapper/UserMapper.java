package com.ra34.projecte2.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ra34.projecte2.DTO.CustomerDTO;
import com.ra34.projecte2.DTO.RoleDTO;
import com.ra34.projecte2.DTO.UserDTO;
import com.ra34.projecte2.DTO.UserRequest;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Model.Role;
import com.ra34.projecte2.Model.User;

@Component
public class UserMapper {

    // ── Request DTO → Entitat JPA ──────────────────────────────────────
    public User toEntity(UserRequest request){
        if (request == null) return null;

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setStatus(true);
        
        if(request.getCustomer() != null){
            Customer customer = new Customer();
            customer.setFirstName(request.getCustomer().getFirstName());
            customer.setLastName(request.getCustomer().getLastName());
            customer.setPhone(request.getCustomer().getPhone());
            customer.setStatus(true);

            user.setCustomer(customer); // sincornitzem la relació bidireccional 1to1
        }
        return user;
    }

    // ── Entitat JPA → Response DTO ─────────────────────────────────────
    public UserDTO toDTO(User user){
        if (user == null) return null;
        return new UserDTO(user.getId(), user.getEmail());
    }

    public UserDTO toFullDTO(User user){
        CustomerDTO customerDTO = (user.getCustomer() != null) ? new CustomerDTO(user.getCustomer().getId(),user.getCustomer().getFirstName(), user.getCustomer().getLastName(), user.getCustomer().getPhone()) : null;
        return new UserDTO(user.getId(), user.getEmail(), customerDTO);
    }

    // ── Mètode exclusiu per retornar l'usuari amb els seus rols ─────────
    public UserDTO toUserWithRolesDTO(User user){
        if (user == null) return null;
        
        CustomerDTO customerDTO = (user.getCustomer() != null) ? new CustomerDTO(user.getCustomer().getId(),user.getCustomer().getFirstName(), user.getCustomer().getLastName(), user.getCustomer().getPhone()) : null;
        UserDTO dto = new UserDTO(user.getId(), user.getEmail(), customerDTO);

        if (user.getRoles() != null) {
            List<RoleDTO> roleDTOs = new ArrayList<>();
            for (Role r : user.getRoles()) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(r.getId());
                roleDTO.setName(r.getName() != null ? r.getName().toString() : null);
                roleDTOs.add(roleDTO);
            }
            dto.setRoles(roleDTOs);
        }
        return dto;
    }
}
