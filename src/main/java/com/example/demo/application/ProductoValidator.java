package com.example.demo.application;

import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ProductoValidator {
    public boolean esValido(String nombre){
        return nombre != null && !nombre.trim().isEmpty();
    }
}
