package pl.filip.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.filip.shop.dto.EditUserDto;
import pl.filip.shop.model.User;

@Mapper
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "address", source = "user.address"),
            @Mapping(target = "postCode", source = "user.postCode"),
            @Mapping(target = "city", source = "user.city")})
    EditUserDto toEditUser(User user);

    @Mappings({
            @Mapping(target = "id", source = "editUserDto.id"),
            @Mapping(target = "firstName", source = "editUserDto.firstName"),
            @Mapping(target = "lastName", source = "editUserDto.lastName"),
            @Mapping(target = "address", source = "editUserDto.address"),
            @Mapping(target = "postCode", source = "editUserDto.postCode"),
            @Mapping(target = "city", source = "editUserDto.city")})
    User toUser(EditUserDto editUserDto);

}
