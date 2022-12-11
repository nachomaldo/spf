package cl.ubiobio.spf.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false, updatable = false)
    private Long idCliente;

    @NotEmpty(message = "El nombre del cliente no puede estar vacío.")
    @Size(min = 3, max = 30, message = "El nombre debe contener entre 3 y 30 caracteres.")
    @Pattern(regexp = "^(?!\\s)^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s]+$",
            message = "El nombre del cliente no debe comenzar con un espacio en blanco, ni contener caracteres especiales o números.")
    private String nombre;

    @NotEmpty(message = "El apellido del cliente no puede estar vacío")
    @Size(min = 3, max = 30, message = "El apellido debe contener entre 3 y 30 caracteres.")
    @Pattern(regexp = "^(?!\\s)^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s]+$",
            message = "El apellido del cliente no debe comenzar con un espacio en blanco, ni contener caracteres especiales o números.")
    private String apellido;

    @NotEmpty(message = "El número de teléfono del cliente no puede estar vacío.")
    @NotBlank(message = "El teléfono del cliente no puede quedar en blanco.")
    @Size(min = 8, max = 12, message = "El teléfono debe contener un total de 8 caracteres.")
    private String telefono;

    @Size(max = 100, message = "La direccion del cliente debe tener un máximo de 100 caracteres.")
    @NotEmpty(message = "La direccion del cliente no puede estar vacía.")
    @NotBlank(message = "La direccion del cliente no puede quedar en blanco.")
    private String direccion;

    @Email(message = "Debe ingresar un e-mail válido.")
    private String email;

    private String estado;

    // Pedidos del cliente
    @JsonIgnoreProperties(value = {"cliente", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    // Deudas del cliente
    @JsonIgnoreProperties(value = {"cliente", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Deuda> deudas;

    public Cliente() {
        this.pedidos = new ArrayList<>();
        this.deudas = new ArrayList<>();
    }

    public Cliente(Long idCliente, String nombre, String apellido, String telefono, String direccion, String email,
                   String estado, List<Pedido> pedidos, List<Deuda> deudas) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.estado = estado;
        this.pedidos = new ArrayList<>();
        this.deudas = new ArrayList<>();
    }

    @PrePersist
    private void PrePersist() {
        this.estado = "Activo";
        this.telefono = "+569" + this.telefono;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Deuda> getDeudas() {
        return deudas;
    }

    public void setDeudas(List<Deuda> deudas) {
        this.deudas = deudas;
    }
}
