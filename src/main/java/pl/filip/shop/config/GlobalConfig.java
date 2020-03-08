package pl.filip.shop.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.filip.shop.mapper.*;

@Configuration
public class GlobalConfig {

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public OrderUserMapper orderUserMapper() {
        return Mappers.getMapper(OrderUserMapper.class);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return Mappers.getMapper(CategoryMapper.class);
    }

    @Bean
    public CartMapper cartMapper() {
        return Mappers.getMapper(CartMapper.class);
    }

    @Bean
    public ProductInOrderMapper productInOrderMapper() {
        return Mappers.getMapper(ProductInOrderMapper.class);
    }
}
