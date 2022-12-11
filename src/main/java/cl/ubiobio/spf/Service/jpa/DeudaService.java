package cl.ubiobio.spf.Service.jpa;

import cl.ubiobio.spf.Entity.Deuda;
import cl.ubiobio.spf.Repository.IDeudaRepository;
import cl.ubiobio.spf.Service.IDeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collections;

@Service
public class DeudaService implements IDeudaService {

    @Autowired
    private IDeudaRepository deudaRepository;

    @Override
    public Deuda saveDeuda(Deuda deuda) throws ParseException {
        if (deuda != null) {
            return deudaRepository.save(deuda);
        }
        return null;
    }

    @Override
    public Deuda getDeuda(Long idDeuda) {
        return deudaRepository.findById(idDeuda).orElse(null);
    }

    @Override
    public Deuda updateDeuda(Deuda deuda, Long idDeuda) {
        Deuda deudaToUpdate = getDeuda(idDeuda);

        try {
            if (deudaToUpdate.getMotivo() != deuda.getMotivo()) deudaToUpdate.setMotivo(deuda.getMotivo()
            );

            if (deudaToUpdate.getMonto() != deuda.getMonto()) deudaToUpdate.setMonto(deuda.getMonto()
            );

            if (deudaToUpdate.getFecha() != deuda.getFecha()) deudaToUpdate.setFecha(deuda.getFecha()
            );

            deudaRepository.save(deudaToUpdate);
            return deudaToUpdate;
        }
        catch (NullPointerException e) { return null; }
    }

    @Override
    public void vigenteDeuda(Long idDeuda) {
        Deuda deuda = getDeuda(idDeuda);
        deuda.setVigente(!deuda.isVigente());
    }
}
