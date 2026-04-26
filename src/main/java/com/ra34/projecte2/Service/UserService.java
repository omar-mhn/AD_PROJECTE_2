package com.ra34.projecte2.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ra34.projecte2.DTO.UserDTO;
import com.ra34.projecte2.DTO.UserRequest;
import com.ra34.projecte2.Mapper.UserMapper;
import com.ra34.projecte2.Model.Role;
import com.ra34.projecte2.Model.User;
import com.ra34.projecte2.Repository.RoleRepository;
import com.ra34.projecte2.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    // crear un nou usuari + customer relacionat
    @Transactional  // Garantitzem que User i costumer es guardin a la mateixa transacció
    public UserDTO createUserAndCustomer(UserRequest request) {
        // Si ja existeix un usuari amb un email igual no ha de deixar crear-los.
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "L'usuari amb aquest email ja existeix");
        }
        // mapegem de DTO a Entity
        User user = userMapper.toEntity(request);

        //Guardem a BBDD
        // amb cascade.type.ALL en l'entitat User, guardar l'User automaticamente guardarà el Customer
        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }

    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No s'ha trobat l'usuari amb id: " + id));

        return userMapper.toFullDTO(user);
    }

    @Transactional
    public UserDTO updateUserInfo(Long id, UserRequest request){
            User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuari no trobat"));

            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            if (request.getCustomer() != null && user.getCustomer() != null) {
                user.getCustomer().setFirstName(request.getCustomer().getFirstName());
                user.getCustomer().setLastName(request.getCustomer().getLastName());
                user.getCustomer().setPhone(request.getCustomer().getPhone());
            }
            // Guardamos los cambios. Gracias a CascadeType.ALL en la entidad User
            User updatedUser = userRepository.save(user);

            return userMapper.toFullDTO(updatedUser);

    }

    @Transactional
    public List<UserDTO> findAllUsers(){
       List<User>users = userRepository.findAll();

       List<UserDTO> userDTOs = new ArrayList<>();

       for (User user : users) {
            UserDTO dto = userMapper.toFullDTO(user);
            userDTOs.add(dto);
        }
        
        return userDTOs;
    }
    // Endpoint per afegir rols a l’usuari
    @Transactional
    public UserDTO addRolesToUser(Long userId, List<Long> roleIds) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Buscar cada rol por su ID y añadirlo al usuario
        for (Long rId : roleIds) {
            Role role = roleRepository.findById(rId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado: " + rId));

            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
            }
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Transactional
    public UserDTO deleteRoles(Long id, List<Long> roles) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuari no trobat"));

        // En esborrar-ho de la llista, Hibernate només esborra la relació a la taula intermèdia
        user.getRoles().removeIf(role -> roles.contains(role.getId()));

        User savedUser = userRepository.save(user);

        // Retorna l'usuari amb la informació completa i els rols restants
        return userMapper.toUserWithRolesDTO(savedUser);
    }
}