package com.daniele.tcc.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.daniele.tcc.domain.Pedido;
import com.daniele.tcc.domain.Pizza;
import com.daniele.tcc.domain.Pizzaiolo;


public class Atendente extends AbstractLoggingActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private Long numeroPedido = 0L;
    final Pizzaiolo pizzaiolo = new Pizzaiolo();

    private Atendente() {
        log().info("A pizzaria está aberta");
        receive(ReceiveBuilder
                .match(Pedido.class, this::onOrdens) // Mensagens que podem ser manipuladas pelo ator
                .matchAny(o -> log.info("Mensagem desconhecida")) // Mensagens não manipuláveis e desconhecidas pelo ator
                .build()
        );
    }

    private void onOrdens(Pedido pedido) {
        numeroPedido++;
        if (numeroPedido % 4 == 0) {
            log().info("Desculpe {}, tivemos muitos pedidos não poderei te atender", nomeDoCliente());
            throw new RuntimeException("Encerra atores");
        } else {
            log().info("Pedido número " + numeroPedido);
            log().info("Ok {},Iremos prepara sua pizza sabor {}", nomeDoCliente(), pedido);
            log().info("Enviando pedido para o pizzaoilo");
            final Pizza pizza = pizzaiolo.prepara(pedido.type);
            log().info("{}, seu pedido de número {} está pronto", nomeDoCliente(), numeroPedido);
            sender().tell(pizza, self());

        }
    }

    public String nomeDoCliente() {
        return sender().path().elements().last();
    }

    public static Props props() {
        return Props.create(Atendente.class, () -> new Atendente());
    }
}

