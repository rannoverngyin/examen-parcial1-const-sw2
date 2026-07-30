package com.examen.medio.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examen.medio.domain.Ensayo;

@Service
public class EnsayoService {

    public List<Ensayo> listarEnsayos() {
        List<Ensayo> lista = new ArrayList<>();

        lista.add(new Ensayo("Sistema Académico", true, "Operativo"));
        lista.add(new Ensayo("Sistema de Bibliotéca", false, "Obsoleto"));
        lista.add(new Ensayo("Portal de Notas", true, "En Revisión"));

        return lista;
    }
}