package br.ufsc.main;

import br.ufsc.ui.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        FakeData fake = new FakeData();
        fake.adicionarPessoas();
        fake.adicionarIdiomas();
        fake.adicionarHorarios();
        try {
            fake.adicionarTurmas();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Login l = new Login();
        l.launch();
        l.info();
    }
}
