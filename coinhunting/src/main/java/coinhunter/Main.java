/**
 * System Requisites:
 *
 * Grove
 *
 * [feito] 20 pots 4 coins each
 * [feito] 3 coin hunter (yellow, green, blue)
 * [feito] 2 tracker dogs each hunter
 *
 * [feito] each hunter sends his dogs to tracker down coins
 * [não sei como fazer] each dog can hold up to 3 coins of the pot
 * [feito] each dog seek a new pot "randomly"
 * [feito] if a dog happens to find an empty pot then he will go into sleep
 * [feito] until the red dog life-saver puts a coin in the empty pot where the dog slept
 * [feito] the red dog keeps visiting each pot (from 1 to 20) to put a coin if its empty
 *
 * rules:
 * [feito] up to 3 dogs can enter into the grove simultaneously
 * [feito] each pot can be accessed by only one dog (critical region)
 * [feito] if a dog is holding 20 coins then this dog must deliver the 20 coins to his coin-hunter master
 *
 * [feito] there is a queue in the groove entrance each dog will enter in the queue
 * [feito] if a coin-hunter holds at least 50 coins he is the winner
 * [feito] each dog takes 1 unit time to reach from one pot to another
 * [feito] each dog takes 1 unit time to grab a coin inside the pot if exists
 *
 * [feito] the program must be in Java
 * [feito] each dog is a thread, i.e. 2 yellow, 2 blue, 2 green + 1 red = 7 threads
 * [feito] grove is a class
 * [não usei] ExecutorService can be used to limit 3 dogs into Grove's queue
 * [feito] grove class should have 20 pots, array of pot objects of size 20
 * [feito] pot object could have a counter for the coin quantity as a attribute
 * [feito] pot object could have its connections as attribute to know the edges to neighbors
 * [feito] pot object could have reference to the sleeping dog threads
 *
 * [não usei] pot could have a method takeCoinInsidePot(i)
 * [não usei] or grove could have a method takeCoin()
 * [não usei] after invoking one of those a tracer-dog thread should be able to verify if there are coins to grab
 * [não fiz] if there are coins grab 3 at least
 * if there are not coins inside the pot the tracer-dog thread should sleep until some coin is available
 * the red-dog should notify() sleeping threads that are blocking because of the lack of coins inside a pot
 * grove class could have a method for the red-dog called putCoin()
 * every time a tracer dog finds a pot without a single coin then it will sleep(60)
 * [feito] the pot access must be exclusive
 * a pot could have up to three dogs sleeping(60) and waiting for a coin to be put in
 *
 * the red-dog thread should sleep(2) after at each pot visit if the pot is empty put 1 coin there
 * after putting a coin there the red-dog should check for sleeping tracer-dogs if there is none then leave
 * if there are tracer-dogs sleeping in the pot then wake up them
 * if they are waiting together for a coin them they will compete to grab that coin
 * just one tracer-dog should pick up the coin the other ones will sleep(3) this time
 *
 * the red-dog life-saver could be implemented using ScheduledExecutorService
 * for each interval of 2 u.t. this thread will visit every pot-node and put a coin in the empty ones
 *
 * each thread should have a local variable int indicating the quantity of coins collected
 * if this variable turns out to be 20 then the dog must deliver the coins to his owner
 *
 * the problem must print the ranking of the teams of dogs alongside with the quantity of coins collected
 *
 * one unit of time could be 100 ms
 *
 * Grove Graph
 * Node 1: 2, 15
 * Node 2: 1, 3, 4, 5
 * Node 3: 2, 4, 9
 * Node 4: 2, 9, 10
 * Node 5: 2, 6
 * Node 6: 7, 8
 * Node 7: 6
 * Node 8: 6
 * Node 9: 3, 4, 15, 18
 * Node 10: 4, 12
 * Node 11: 12, 14, 17
 * Node 12: 10, 11, 13
 * Node 13: 12
 * Node 14: 11, 16
 * Node 15: 1, 9
 * Node 16: 14, 17, 18, 20
 * Node 17: 11, 16
 * Node 18: 9, 16, 19
 * Node 19: 18, 20
 * Node 20: 16, 19
 *
 */
package coinhunter;

public class Main {

    public static void main(String[] args) {
        Woods grove = new Woods();
        grove.init();
        grove.start();
        try {
            grove.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
