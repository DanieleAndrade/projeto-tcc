package com.daniele.tcc.domain;

public class Pizzaiolo {
    public Pizza prepara(Pizza.Sabores sabores) {
        return Pizza.ofType(sabores);
    }
}
