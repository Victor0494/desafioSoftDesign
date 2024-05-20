package br.com.desafio.notification.constant;

public enum Rating {

    ONE_STAR(1),
    TWO_STAR(2),
    THREE_STAR(3),
    FOR_STAR(4),
    FIVE_STAR(5);

    private final int valor;

    Rating(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
