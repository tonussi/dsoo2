package br.ufsc.control;

import br.ufsc.model.Horario;
import br.ufsc.service.HorarioService;
import java.util.ArrayList;
import java.util.List;
import util.Util;

public class HorarioController {
    HorarioService horos = new HorarioService();
    public ArrayList<String[]> getBlocosDisponíveis() {
        List<Horario> horarios = horos.findAll(Horario.class);
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        for (Horario horario : horarios) {
            resultado.add(new String[]{
                String.valueOf(horario.getDia()),
                Util.formatHorario(horario.getHorario())
            });
        }
        return resultado;
    }
}
