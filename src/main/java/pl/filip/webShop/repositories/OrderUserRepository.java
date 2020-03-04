package pl.filip.webShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.webShop.model.OrderUser;
import pl.filip.webShop.model.SysUser;

import java.util.List;

public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {

    List<OrderUser> findAllBySysUser(SysUser sysUser);

    List<OrderUser> findAllByFinish(boolean is);
}
