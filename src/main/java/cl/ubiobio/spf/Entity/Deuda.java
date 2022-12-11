package cl.ubiobio.spf.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Deuda")
public class Deuda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deuda", nullable = false, updatable = false)
    private Long idDeuda;

    // true = VIGENTE
    // false = CANCELADA
    @NotNull
    @Column(name = "pendiente")
    private boolean vigente;

    @NotEmpty
    @Size(min = 10, max = 300)
    private String motivo;

    @NotNull
    private Long monto;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @JsonIgnoreProperties({"pedidos", "hibernateLazyInitializer", "handler"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Deuda() {}

    public Deuda(boolean vigente, String motivo, Long monto, LocalDate fecha, Cliente cliente) {
        this.vigente = vigente;
        this.motivo = motivo;
        this.monto = monto;
        this.fecha = fecha;
        this.cliente = cliente;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
