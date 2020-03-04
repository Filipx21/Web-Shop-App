package pl.filip.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.dto.EditUserDto;

@Mapper
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "sysUser.id"),
            @Mapping(target = "firstName", source = "sysUser.firstName"),
            @Mapping(target = "lastName", source = "sysUser.lastName"),
            @Mapping(target = "address", source = "sysUser.address"),
            @Mapping(target = "postCode", source = "sysUser.postCode"),
            @Mapping(target = "city", source = "sysUser.city")})
    EditUserDto toEditUser(SysUser sysUser);

    @Mappings({
            @Mapping(target = "id", source = "editUserDto.id"),
            @Mapping(target = "firstName", source = "editUserDto.firstName"),
            @Mapping(target = "lastName", source = "editUserDto.lastName"),
            @Mapping(target = "address", source = "editUserDto.address"),
            @Mapping(target = "postCode", source = "editUserDto.postCode"),
            @Mapping(target = "city", source = "editUserDto.city")})
    SysUser toUser(EditUserDto editUserDto);

}
