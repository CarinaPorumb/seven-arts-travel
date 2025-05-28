package ro.sevenartstravel.mapper;

import org.mapstruct.*;
import ro.sevenartstravel.dto.UserCreateDTO;
import ro.sevenartstravel.dto.UserDTO;
import ro.sevenartstravel.dto.UserUpdateDTO;
import ro.sevenartstravel.entity.Role;
import ro.sevenartstravel.entity.User;
import ro.sevenartstravel.service.crud.CrudMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends CrudMapper<UserDTO, User> {

    @Override
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordConfirm", ignore = true)
    @Mapping(target = "randomToken", ignore = true)
    @Mapping(target = "randomTokenEmail", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "version", ignore = true)
    User toEntity(UserDTO dto);

    @Override
    @Mapping(target = "roles", expression = "java(mapRolesToNames(user))")
    UserDTO toDTO(User user);

    @Override
    @Mapping(target = "roles", ignore = true)
    void updateEntity(@MappingTarget User entity, UserDTO dto);

    @Override
    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget User entity, UserDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "randomToken", ignore = true)
    @Mapping(target = "randomTokenEmail", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "version", ignore = true)
    User toEntity(UserCreateDTO dto);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "randomToken", ignore = true)
    @Mapping(target = "randomTokenEmail", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "version", ignore = true)
    User toEntity(UserUpdateDTO dto);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User entity, UserUpdateDTO dto);

    default Set<String> mapRolesToNames(User user) {
        if (user.getRoles() == null) return Set.of();

        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

}