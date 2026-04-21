package com.ra34.projecte2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ra34.projecte2.DTO.UserDTO;
import com.ra34.projecte2.DTO.UserRequest;
import com.ra34.projecte2.Mapper.UserMapper;
import com.ra34.projecte2.Model.User;
import com.ra34.projecte2.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional  // Garantitzem que User i costumer es guardin a la mateixa transacció
    public UserDTO createUserAndCustomer(UserRequest request) {
        // Si ja existeix un usuari amb un email igual no ha de deixar crear-los.
        if (userRepository.findByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "L'usuari amb aquest email ja existeix");
        }
        // mapegem de DTO a Entity
        User user = userMapper.toEntity(request);

        //Guardem a BBDD
        // amb cascade.type.ALL en l'entitat User, guardar l'User automaticamente guardarà el Customer
        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }
}