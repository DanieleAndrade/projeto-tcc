package com.daniele.tcc.domain;

public class Pizza {
    public enum Sabores {
        CALABRESA("calabresa"),
        PORTUGUESA("portuguesa"),
        NORDESTINA("nordestina"),
        QUEIJO("queijo"),
        FRANGO("frango"),
        MARGUERITA("marguerita"),
        CHOCOLATE("chocolate"),
        ATUM("atum");

        private String descricao;

        public String getDescricao(){
            return descricao;
        }

        Sabores(String descricao) {
            this.descricao = descricao;
        }
    }

    public static final Sabores[] types = Sabores.values();

    public final Sabores type;

    private Pizza(final Sabores type) {
        this.type = type;
    }

    public static Pizza ofType(Sabores type) {
        return new Pizza(type);
    }

    @Override
    public String toString() {
        return type.name();
    }
}
