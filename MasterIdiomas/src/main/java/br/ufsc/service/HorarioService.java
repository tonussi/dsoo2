package br.ufsc.service;

import br.ufsc.model.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;

public class HorarioService extends GenericService {

    @Override
    public Object findById(Class clazz, Integer id) {
        return super.findById(clazz, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAll(Class clazz) {
        return super.findAll(clazz); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object obj) {
        super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(Object obj) {
        super.saveOrUpdate(obj);
    }

    public Horario findById(Integer dia, Date horario) {
        startOperation();
        Query query = session.createQuery("from Horario where horario = :horario and dia = :dia");
        query.setParameter("horario", horario);
        query.setParameter("dia", dia);
        List objects = query.list();
        Object obj = null;
        if (objects.size() > 0) {
            obj = objects.get(0);
        }
        endOperation();
        return (Horario) obj;
    }

    private Date parseHorario(String horario) {
        LocalTime lt = LocalTime.parse(horario);
        Instant instant = lt.atDate(LocalDate.ofYearDay(2019, 1)).atZone(ZoneId.systemDefault()).toInstant();
        Date time = Date.from(instant);
        return time;
    }

    public Horario findById(Integer dia, String horario) {
        startOperation();
        Query query = session.createQuery("from Horario where horario = :horario and dia = :dia");
        query.setParameter("horario", parseHorario(horario));
        query.setParameter("dia", dia);
        List objects = query.list();
        Object obj = null;
        if (objects.size() > 0) {
            obj = objects.get(0);
        }
        endOperation();
        return (Horario) obj;
    }    
}
