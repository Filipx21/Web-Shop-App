package pl.filip.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.model.SysUser;

import java.util.List;

public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {

    List<OrderUser> findAllBySysUser(SysUser sysUser);
}
