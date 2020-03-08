package pl.filip.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.filip.shop.dto.ProductDto;
import pl.filip.shop.model.Product;

@Mapper
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "id", source = "product.id"),
            @Mapping(target = "productName", source = "product.productName"),
            @Mapping(target = "descript", source = "product.descript"),
            @Mapping(target = "quantity", source = "product.quantity"),
            @Mapping(target = "cost", source = "product.cost"),
            @Mapping(target = "producer", source = "product.producer"),
            @Mapping(target = "category", source = "product.category"),
            @Mapping(target = "createdDate", source = "product.createdDate")})
    ProductDto toProductDto (Product product);

    @Mappings({
            @Mapping(target = "id", source = "productDto.id"),
            @Mapping(target = "productName", source = "productDto.productName"),
            @Mapping(target = "descript", source = "productDto.descript"),
            @Mapping(target = "quantity", source = "productDto.quantity"),
            @Mapping(target = "cost", source = "productDto.cost"),
            @Mapping(target = "producer", source = "productDto.producer"),
            @Mapping(target = "category", source = "productDto.category"),
            @Mapping(target = "createdDate", source = "productDto.createdDate")})
    Product toProduct (ProductDto productDto);
}
