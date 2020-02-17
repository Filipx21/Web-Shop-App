package pl.filip.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.shop.model.Cart;
import pl.filip.shop.model.SysUser;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllBySysUserAndInUse(SysUser sysUser, boolean inUse);

    Cart findBySysUserAndInUse(SysUser sysUser, boolean inUse);
}
