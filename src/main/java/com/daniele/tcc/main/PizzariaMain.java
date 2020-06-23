package com.daniele.tcc.main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.daniele.tcc.actors.Atendente;
import com.daniele.tcc.actors.Cliente;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class PizzariaMain {

    public static void main(String[] args) throws Exception {
        final Config config = ConfigFactory.load();

        // Cria o ActorSystem, container Akka
        final ActorSystem system = ActorSystem.create("PizzariaSystem", config);
        try {
            // Cria o ator Atendente
            ActorRef atendente = system.actorOf(Atendente.props(), "Atendente");
            // Cria os atores clientes
            system.actorOf(Cliente.props(atendente), "Dario");
            system.actorOf(Cliente.props(atendente), "Davi");
            system.actorOf(Cliente.props(atendente), "Poliana");
            system.actorOf(Cliente.props(atendente), "Dani");

        } catch (Exception e) {
            system.terminate();
            throw e;
        }
    }
}
