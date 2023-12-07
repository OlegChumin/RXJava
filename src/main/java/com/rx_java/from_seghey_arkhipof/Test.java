package com.rx_java.from_seghey_arkhipof;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client("Harry Carter", 15, true));
        clients.add(new Client("Adam Atkinson", 5, true));
        clients.add(new Client("Bobby Robertson", 8, true));
        clients.add(new Client("Liam Ellis", 6, false));
        clients.add(new Client("Alex Thomson", 9, true));
        clients.add(new Client("Ryan Ayala", 4, false));
        clients.add(new Client("Kale Molina", 3, true));
        clients.add(new Client("Otto Holman", 7, false));


        printActiveClients(clients);
        System.out.println("_______________________________");

        Flowable<Client> clientFlowable = Flowable.create(new FlowableOnSubscribe<Client>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Client> emitter) throws Throwable {
                for (Client client : clients) {
                    emitter.onNext(client);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        clientFlowable
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.io())
                .filter(Client::isActive)
                .map(client -> client.getName() + " " + client.getAge())
                .forEach(System.out::println);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(" Программа успешно завершила работу! ");
    }

    public static void printActiveClients(List<Client> clients) {
        clients.stream()
                .filter(Client::isActive) // фильтруем по признаку isActive
                .map(client -> client.getName() + " " + client.getAge()) // получаем список имен
                .forEach(System.out::println); // выводим на экран
    }
}

class Client {
    private String name;
    private int age;
    private boolean isActive;

    public Client(String name, int age, boolean isActive) {
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age && isActive == client.isActive && Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isActive);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                '}';
    }
}

// record Client(String name, int age, boolean isActive) { }

