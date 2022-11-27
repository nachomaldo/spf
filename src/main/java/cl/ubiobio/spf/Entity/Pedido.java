package cl.ubiobio.spf.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

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
    //@Pattern(regexp = "^(?!0*(\\.0+)?$)(\\d+|\\d*\\.\\d+)$")
    private Long total;

    //0 = PENDIENTE
    //1 = ENTREGADO
    @NotNull
    @Column(name = "pendiente")
    private int pendiente;

    public Pedido(){}

    public Pedido(String nombreDelReceptor, LocalDate fecha, String direccionDeReparto, Long total, int pendiente){
        this.nombreDelReceptor = nombreDelReceptor;
        this.fecha = fecha;
        this.direccionDeReparto = direccionDeReparto;
        this.total = total;
        this.pendiente = pendiente;
    }

    @PrePersist
    private void prePersist() {
        this.pendiente = 0;
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

    public int getPendiente() {
        return pendiente;
    }

    public void setPendiente(int pendiente) {
        this.pendiente = pendiente;
    }

}
