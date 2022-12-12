package cl.ubiobio.spf.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Producto")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false, updatable = false, unique = false)
    private Long codigo;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String nombre;

    @NotNull
    private Long precio;

    @NotNull
    private Long stock;

    @NotEmpty
    @Size(min = 10, max = 300)
    private String descripcion;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String categoria;

    private String imageName;

   /* @Lob
    private byte[] imageBytes;*/

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "productos")
    private List<Pedido> pedidos;

    public Producto(){}

    public Producto(String nombre, Long precio, Long stock, String descripcion, String categoria, String imageName, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.imageName = imageName;
        this.pedidos = pedidos;
    }


    public Long getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

/*    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }*/
}
