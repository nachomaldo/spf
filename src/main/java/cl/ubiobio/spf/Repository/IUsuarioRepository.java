package cl.ubiobio.spf.Repository;

import cl.ubiobio.spf.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    // SELECT usuario WHERE username = ?
    public Usuario findByUsername(String username);
}
/*
import cl.ubiobio.spf.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    // SELECT usuario WHERE username = ?
    Usuario findByUsername(String username);
}*/