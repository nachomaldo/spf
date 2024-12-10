package cl.ubiobio.spf.Service;

import cl.ubiobio.spf.Entity.Usuario;

public interface IUsuarioService {

    public Usuario findByUsername(String username);
}
/*
import cl.ubiobio.spf.Entity.Usuario;

public interface IUsuarioService {

    /** Devuelve un usuario por su username */
/*
    Usuario getUserByUsername(String username);
}*/