package com.daniele.tcc.domain;

public class Pedido {
    public final Pizza.Sabores type;

    private Pedido(final Pizza.Sabores type) {
        this.type = type;
    }

    public static Pedido of(Pizza.Sabores type) {
        return new Pedido(type);
    }

    @Override
    public String toString() {
        return type.getDescricao();
    }
}
