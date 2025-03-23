package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.request.UserRequestDTO;
import br.com.challange.softDesign.application.dto.response.UserResponseDTO;
import br.com.challange.softDesign.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("UserController.createUser - Start - Input: userRequestDTO: {}", userRequestDTO);
        UserResponseDTO response = userService.createUser(userRequestDTO);

        URI location = URI.create("/user/" + response.id());

        log.debug("UserController.createUser - End - Output: topicResponse: {}", response);
        return ResponseEntity.created(location).body(response);
    }
}
