package pl.filip.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.filip.shop.dto.CategoryDto;
import pl.filip.shop.model.Category;

@Mapper
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "id", source = "categoryDto.id"),
            @Mapping(target = "category", source = "categoryDto.category"),
            @Mapping(target = "product", source = "categoryDto.product")})
    Category toCategory(CategoryDto categoryDto);

    @Mappings({
            @Mapping(target = "id", source = "category.id"),
            @Mapping(target = "category", source = "category.category"),
            @Mapping(target = "product", source = "category.product")})
    CategoryDto toCategoryDto(Category category);
}
