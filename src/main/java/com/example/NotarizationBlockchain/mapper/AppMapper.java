package com.example.NotarizationBlockchain.mapper;

import com.example.NotarizationBlockchain.model.Block;
import com.example.NotarizationBlockchain.model.User;
import com.example.NotarizationBlockchain.repository.entities.BlockEntity;
import com.example.NotarizationBlockchain.repository.entities.UserEntity;
import com.example.NotarizationBlockchain.controller.dto.request.RegisterRequestDTO;
import com.example.NotarizationBlockchain.controller.dto.response.BlockResponseDTO;
import com.example.NotarizationBlockchain.controller.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // Habilita el mapper como bean de Spring
public interface AppMapper {

    // Mapeo de RegisterRequestDTO -> User
    default User registerRequestDTOToUser(RegisterRequestDTO registerRequestDTO) {
        if (registerRequestDTO == null) {
            throw new IllegalArgumentException("El DTO de registro no puede ser nulo");
        }
        // Validaciones para evitar datos nulos
        if (registerRequestDTO.getUsername() == null || registerRequestDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        if (registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (registerRequestDTO.getEmail() == null || registerRequestDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }

        // Realiza el mapeo manual
        return User.builder()
                .username(registerRequestDTO.getUsername())
                .password(registerRequestDTO.getPassword())
                .email(registerRequestDTO.getEmail())
                .build();
    }

    // Otros mapeos
    UserResponseDTO userToUserResponseDTO(User user);
    UserEntity userToUserEntity(User user);
    User userEntityToUser(UserEntity userEntity);
    BlockResponseDTO blockToBlockResponseDTO(Block block);
    List<BlockResponseDTO> blocksToBlockResponseDTOs(List<Block> blocks);
    Block blockEntityToBlock(BlockEntity blockEntity);
    BlockEntity blockToBlockEntity(Block block);
}

