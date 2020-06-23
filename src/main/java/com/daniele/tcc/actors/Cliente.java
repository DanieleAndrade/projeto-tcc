package com.daniele.tcc.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.daniele.tcc.domain.Pedido;
import com.daniele.tcc.domain.Pizza;

import java.util.Random;

import static akka.japi.pf.ReceiveBuilder.match;


public class Cliente extends AbstractLoggingActor {

    private final ActorRef atendente;
    private final Pedido pedido;

    public static Props props(final ActorRef atendente) {
        return Props.create(Cliente.class, () -> new Cliente(atendente));
    }

    @Override
    public void preStart() throws Exception {
        log().info("Olá, quero uma pizza sabor {}, por favor", pedido);
        atendente.tell(pedido, self()); //Passa a mensagem para o atendente
    }

    private Cliente(final ActorRef atendente) {
        this.atendente = atendente;
        pedido = escolherPizza();

        receive(
                match(Pizza.class, pizza -> {
                    log().info("Obrigado, até mais");
                    context().stop(self());
                })
                        .matchAny(this::unhandled)
                        .build()
        );
    }

    private void pedido() {
        log().info("Olá, quero uma pizza sabor {}, por favor", pedido);
        atendente.tell(pedido, self());
    }

    private void finaliza() {
        log().info("Finaliza Atendimento");
        context().stop(self());
    }

    private static final Random orderRandomiser = new Random();

    private Pedido escolherPizza() {
        return Pedido.of(Pizza.types[orderRandomiser.nextInt(Pizza.types.length)]);
    }


}
