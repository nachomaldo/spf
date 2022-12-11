package cl.ubiobio.spf.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido", nullable = false, updatable = false)
    private Long idPedido;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String nombreDelReceptor;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotEmpty(message = "Se debe ingresar la dirección para reparto")
    @Size(min = 10, max = 80)
    private String direccionDeReparto;

    @NotNull//SUMAR PRECIO DE PRODUCTO
    private Long total;

    // true = PENDIENTE
    // false = ENTREGADO
    @Column(name = "pendiente")
    private boolean pendiente;

    @JsonIgnoreProperties({"pedidos", "hibernateLazyInitializer", "handler"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Productos del pedido
    //@JsonIgnoreProperties(value = {"pedido", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pedido_producto",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "codigo"))
    private List<Producto> productos;

    public Pedido() { }

    public Pedido(String nombreDelReceptor, LocalDate fecha, String direccionDeReparto, Long total, boolean pendiente,
                  Cliente cliente, List<Producto> productos){
        this.nombreDelReceptor = nombreDelReceptor;
        this.fecha = fecha;
        this.direccionDeReparto = direccionDeReparto;
        this.total = total;
        this.pendiente = pendiente;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
    }

    @PrePersist
    private void prePersist() {
        this.pendiente = true;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public String getNombreDelReceptor() {
        return nombreDelReceptor;
    }

    public void setNombreDelReceptor(String nombreDelReceptor) {
        this.nombreDelReceptor = nombreDelReceptor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDireccionDeReparto() {
        return direccionDeReparto;
    }

    public void setDireccionDeReparto(String direccionDeReparto) {
        this.direccionDeReparto = direccionDeReparto;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
