package br.ufsc.control;

import br.ufsc.model.Idioma;
import br.ufsc.service.AnyService;
import java.util.ArrayList;
import java.util.List;

public class IdiomaController {

    AnyService as;

    public IdiomaController() {
        as = new AnyService();
    }

    public List<Idioma> getIdiomas() {
        return as.findAll(Idioma.class);
    }

    public ArrayList<String> getListaNomesDosIdiomas() {
        List<Idioma> idiomas = getIdiomas();
        ArrayList<String> resultado = new ArrayList<String>();
        for (Idioma idioma : idiomas) {
            resultado.add(idioma.getNome());
        }
        return resultado;
    }

}
