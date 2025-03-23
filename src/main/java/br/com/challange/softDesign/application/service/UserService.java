package br.com.challange.softDesign.application.service;

import br.com.challange.softDesign.application.dto.request.UserRequestDTO;
import br.com.challange.softDesign.application.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
}
