package pl.filip.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.filip.shop.dto.CartDto;
import pl.filip.shop.model.Cart;

@Mapper
public interface CartMapper {

    @Mappings({
            @Mapping(target = "id", source = "cartDto.id"),
            @Mapping(target = "products", source = "cartDto.products"),
            @Mapping(target = "sysUser", source = "cartDto.sysUser"),
            @Mapping(target = "inUse", source = "cartDto.inUse")})
    Cart toCart(CartDto cartDto);

    @Mappings({
            @Mapping(target = "id", source = "cart.id"),
            @Mapping(target = "products", source = "cart.products"),
            @Mapping(target = "sysUser", source = "cart.sysUser"),
            @Mapping(target = "inUse", source = "cart.inUse")})
    CartDto toCartDto(Cart cart);
}
