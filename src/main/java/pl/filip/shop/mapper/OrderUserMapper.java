package pl.filip.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.filip.shop.dto.OrderUserDto;
import pl.filip.shop.model.OrderUser;


@Mapper
public interface OrderUserMapper {

    @Mappings({
            @Mapping(target = "id", source = "orderUserDto.id"),
            @Mapping(target = "productInOrders", source = "orderUserDto.productInOrders"),
            @Mapping(target = "sysUser", source = "orderUserDto.sysUser"),
            @Mapping(target = "done", source = "orderUserDto.done"),
            @Mapping(target = "finish", source = "orderUserDto.finish")})
    OrderUser toOrderUser(OrderUserDto orderUserDto);

    @Mappings({
            @Mapping(target = "id", source = "orderUser.id"),
            @Mapping(target = "productInOrders", source = "orderUser.productInOrders"),
            @Mapping(target = "sysUser", source = "orderUser.sysUser"),
            @Mapping(target = "done", source = "orderUser.done"),
            @Mapping(target = "finish", source = "orderUser.finish")})
    OrderUserDto toOrderUserDto(OrderUser orderUser);
}
