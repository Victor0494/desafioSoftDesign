package br.com.challange.softDesign.application.exception.handlerException;

import br.com.challange.softDesign.application.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RestExceptionHandlerTest {

    @Autowired
    private RestExceptionHandler handler;

    private ResponseEntity<Object> response;

    @Test
    @DisplayName("handle invalid document exception")
    void handleInvalidDocumentException() {
        response = handler.handleInvalidDocumentException(new InvalidDocumentException(INVALID_DOCUMENT.getMessage()));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("handle unable vote exception")
    void handleUnableVoteException() {
        response = handler.handleUnableVoteException(new UnableVoteException(UNABLE_TO_VOTE.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("handle already voted exception")
    void handleAlreadyVotedException() {
        response = handler.handleAlreadyVotedException(new AlreadyVotedException(ALREADY_VOTED.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("handle topic not found exception")
    void handleTopicNotFoundException() {
        response = handler.handleTopicNotFoundException(new TopicNotFoundException(TOPIC_NOT_FOUND.getMessage()));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("handle session finished exception")
    void handleSessionFinishedException() {
        response = handler.handleSessionFinishedException(new SessionFinishedException(SESSION_FINISHED.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("handle session exist exception")
    void handleSessionExistException() {
        response = handler.handleSessionExistException(new SessionExistException(SESSION_ALREADY_EXIST.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("handle session not found exception")
    void handleSessionNotFoundException() {
        response = handler.handleSessionNotFoundException(new SessionNotFoundException(SESSION_NOT_FOUND.getMessage()));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("handle topic already created exception")
    void handleTopicAlreadyCreatedException() {
        response = handler.handleTopicAlreadyCreatedException(new TopicAlreadyCreatedException(TOPIC_ALREADY_CREATED.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("handle session already exist exception")
    void handleSessionAlreadyExistException() {
        response = handler.handleSessionAlreadyExistException(new SessionAlreadyExistException(TOPIC_ALREADY_CREATED.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("handle user not found exception")
    void handleUserNotFoundException() {
        response = handler.handleUserNotFoundException(new UserNotFoundException(TOPIC_ALREADY_CREATED.getMessage()));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("handle user already exist exception")
    void handleUserAlreadyExistException() {
        response = handler.handleUserAlreadyExistException(new UserAlreadyExistException(TOPIC_ALREADY_CREATED.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}