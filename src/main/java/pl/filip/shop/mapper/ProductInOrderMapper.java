package pl.filip.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.filip.shop.dto.ProductInOrderDto;
import pl.filip.shop.model.ProductInOrder;

@Mapper
public interface ProductInOrderMapper {

    @Mappings({
            @Mapping(target = "id", source = "productInOrderDto.id"),
            @Mapping(target = "productName", source = "productInOrderDto.productName"),
            @Mapping(target = "descript", source = "productInOrderDto.descript"),
            @Mapping(target = "quantity", source = "productInOrderDto.quantity"),
            @Mapping(target = "cost", source = "productInOrderDto.cost"),
            @Mapping(target = "producerName", source = "productInOrderDto.producerName"),
            @Mapping(target = "address", source = "productInOrderDto.address"),
            @Mapping(target = "createdDate", source = "productInOrderDto.createdDate")})
    ProductInOrder toProductInOrder(ProductInOrderDto productInOrderDto);


    @Mappings({
            @Mapping(target = "id", source = "productInOrder.id"),
            @Mapping(target = "productName", source = "productInOrder.productName"),
            @Mapping(target = "descript", source = "productInOrder.descript"),
            @Mapping(target = "quantity", source = "productInOrder.quantity"),
            @Mapping(target = "cost", source = "productInOrder.cost"),
            @Mapping(target = "producerName", source = "productInOrder.producerName"),
            @Mapping(target = "address", source = "productInOrder.address"),
            @Mapping(target = "createdDate", source = "productInOrder.createdDate")})
    ProductInOrderDto toProductInOrderDto(ProductInOrder productInOrder);
}
