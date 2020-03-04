package pl.filip.webShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.webShop.model.SysUser;

import java.util.Optional;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    Optional<SysUser> findByEmail(String email);

}
