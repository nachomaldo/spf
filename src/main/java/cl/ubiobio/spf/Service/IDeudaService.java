package cl.ubiobio.spf.Service;

import cl.ubiobio.spf.Entity.Deuda;

import java.text.ParseException;

public interface IDeudaService {

    // Guardar una deuda
    Deuda saveDeuda(Deuda deuda) throws ParseException;

    // Obtener una deuda por ID
    Deuda getDeuda(Long idDeuda);

    // Actualizar una deuda
    Deuda updateDeuda (Deuda deuda, Long idDeuda);

    //Cambiar estado vigente de una deuda
    void vigenteDeuda (Long idDeuda);
}
