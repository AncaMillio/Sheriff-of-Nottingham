package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Game {

    private List<String> players;
    private List<Integer> pachet;
    ArrayList<Player> playersArray;
    ArrayList<Bunuri> pachetBun;
    private final int three = 3;
    private final int five = 5;
    private final int six = 6;
    private final int ten = 10;
    private final int fifteen = 15;
    private final int twenty = 20;
    private final int eleven = 11;
    private final int twelve = 12;

    void setplayers(final List<String> s) {
        players = s;
    }

    void setpachet(final List<Integer> i) {
        pachet = i;
    }

    Game(final GameInput input) {
        players = new ArrayList<String>();
        players = input.getPlayerNames();
        pachet = new ArrayList<Integer>();
        pachet = input.getAssetIds();
        playersArray = new ArrayList<Player>();
        pachetBun = new ArrayList<Bunuri>();
        }

  //se creeaza o lista cu obiecte de tip Bunuri pe baza pachetului de la input
    void pachetBunuri() {
        while (pachet.size() != 0) {
            Bunuri b = new Bunuri(pachet.remove(0));
            pachetBun.add(b);

        }
    }

  //se creeaza o lista cu obiecte de tip Player pe baza listei cu obiecte de tip String
    void alocStrategy() {
        for (int i = 0; i < players.size(); i++) {
        Player player = new Player(players.get(i));
        playersArray.add(player);
        }
    }

    void alocCards() {
        for (Player it: playersArray) {
            for (int i = it.cards.size(); i < six; i++) {  //se completeaza cartile din mana
                it.cards.add(pachetBun.remove(0));                //cartile se si scot din pachet
            }
        }
    }

    void setSack() {
        for (Player it : playersArray) {
            if (it.sheriff == 0) {                     //sacul se seteaza doar pt comercianti
                it.setSack();
            }
        }
    }

    void setSheriff(final int round) {
        for (Player it : playersArray) {
            it.sheriff = 0;
        }
        playersArray.get(round % playersArray.size()).sheriff = 1; //seriful se seteaza
    }                                                             //in functie de numarul rundei

    void inspection() {

        for (Player p : playersArray) {
            if (p.sheriff == 1) {
                switch (p.strategy) {             //in fnctie de strategia serifului

                case "basic":
                    for (Player it : playersArray) {
                        if (it.sheriff == 0) {               //verifica toti comerciantii
                        it.checkSack(p, it, pachetBun);

                        if (it.mita != 0) {      //nu accepta mita, deci aceasta
                            it.visterie += it.mita;  //se intoarce in visteria comerciantului
                        }
                        }
                    }
                    break;

                case "greedy":
                    for (Player it : playersArray) {
                        if (it.sheriff == 0) {
                            if (it.mita == 0) {       //verifica doar comerciantii care nu dau mita
                                it.checkSack(p, it, pachetBun);
                            } else {
                                p.visterie += it.mita;         //accepta mita daca aceasta exista
                                }
                            }
                        }
                    break;

                case "bribed":

                   for (Player it : playersArray) {
                       if (it.sheriff == 0) {                    //verifica toti comerciantii

                           it.checkSack(p, it, pachetBun);
                           if (it.mita != 0) {          //nu accepta mita, deci aceasta
                               it.visterie += it.mita;   //se intoarce in visteria comerciantului
                                   }
                               }
                           }
                       break;

                case "wizard":  // wizard ii inspecteaza doar pe cei care propun mita
                   for (Player it : playersArray) {   // considerandu-i suspecti
                       if (it.sheriff == 0) {
                           if (it.mita > 0) {
                              it.checkSack(p, it, pachetBun);
                             }
                         }
                      }
                     break;

                default:
                    break;
                    }
                }
            }
        }

    void mutarePeTaraba() {
        for (Player it : playersArray) {
            while (it.sack.size() != 0) {
                it.taraba.add(it.sack.remove(0));          //se adauga bunurile din sac pe taraba
            }
        }
    }

    void kingAndQueen() {

        for (Player it : playersArray) {
            ArrayList<Bunuri> legale = new ArrayList<Bunuri>();

            for (Bunuri card : it.taraba) {
                if (card.type == "Legal") {
                    legale.add(card);                 //se stabilesc bunurile legale
                }
            }
            for (Bunuri card : legale) {

                if (card.id == 0) {     //se numara cate bunuri din fiecare tip legal exista
                    it.mere++;
                }
                if (card.id == 1) {
                    it.branza++;
                }
                if (card.id == 2) {
                    it.paine++;
                }
                if (card.id == three) {
                    it.pui++;
                }
            }
            legale.clear();
        }

        int appleMax = 0;
        for (Player it : playersArray) {
            if (it.mere > appleMax) {
            appleMax = it.mere;                 //se stabileste maximul pt mere
            }
        }
        int appleMax2 = -1;
        for (Player it : playersArray) {
            if (it.mere > appleMax2 && it.mere != appleMax) {
                appleMax2 = it.mere;             //se stabileste a doua cea mai mare valoare pt mere
            }
        }

        for (Player it : playersArray) {
            if (it.mere == appleMax) {
                it.visterie += twenty;      //se gaseste jucatorul care detine maximul de mere
            }                               //si i se aduga bonusul de king in visterie
            if (it.mere == appleMax2) {
                it.visterie += ten;     //se gaseste jucatorul care detine al doilea cel mai mare
            }                       // numar de mere si i se adauga bonusul de queen in visterie
        }

        int breadMax = 0;                            //analog pentru paine
        for (Player it : playersArray) {
            if (it.paine > breadMax) {
            breadMax = it.paine;
            }
        }
        int breadMax2 = -1;
        for (Player it : playersArray) {
            if (it.paine > breadMax2 && it.paine != breadMax) {
                breadMax2 = it.paine;
            }
        }

        for (Player it : playersArray) {
            if (it.paine == breadMax) {
                it.visterie += fifteen;
            }
            if (it.paine == breadMax2) {
                it.visterie += ten;
            }
        }

        int chickenMax = 0;                     //analog pentru pui
        for (Player it : playersArray) {
            if (it.pui > chickenMax) {
                chickenMax = it.pui;
            }
        }
        int chickenMax2 = -1;
        for (Player it : playersArray) {
            if (it.pui > chickenMax2 && it.pui != chickenMax) {
                chickenMax2 = it.pui;
            }
        }

        for (Player it : playersArray) {
            if (it.pui == chickenMax) {
                it.visterie += ten;
            }
            if (it.pui == chickenMax2) {
                it.visterie += five;
            }
        }

        int cheeseMax = 0;                          //analog pentru branza
        for (Player it : playersArray) {
            if (it.branza > cheeseMax) {
                cheeseMax = it.branza;
            }
        }
        int cheeseMax2 = -1;
        for (Player it : playersArray) {
            if (it.branza > cheeseMax2 && it.branza != cheeseMax) {
                cheeseMax2 = it.branza;
            }
        }

        for (Player it : playersArray) {
            if (it.branza == cheeseMax) {
                it.visterie += fifteen;
            }
            if (it.branza == cheeseMax2) {
                it.visterie += ten;
            }
        }

    }

    void bonusIllegal() {

        Bunuri breadBuff = new Bunuri(2);
        Bunuri chickenBuff = new Bunuri(three);
        Bunuri cheeseBuff = new Bunuri(1);

        for (Player it : playersArray) {
            int cheese = 0;
            int chicken = 0;
            int bread = 0;
            for (Bunuri bun : it.taraba) {
                if (bun.id == ten) {      //pt fiecare bun ilegal cu id 10
                    cheese += three;      //se adauga in contor 3 bunuri de tip branza
                }
                if (bun.id == eleven) {   //pt fiecare bun ilegal cu id 11
                    chicken += 2;         //se aduga in contor 2 bunuri de tip pui
                }
                if (bun.id == twelve) {  //pt fiecare bun ilegal cu id 12
                    bread += 2;          //se aduga in contor 2 bunuri de tip paine
                }
            }
            for (int i = 0; i < cheese; i++) {
                it.taraba.add(cheeseBuff);  //se adauga pe taraba
            }                               //toate bunurile legale numarate mai sus
            for (int i = 0; i < chicken; i++) {
                it.taraba.add(chickenBuff);
            }
            for (int i = 0; i < bread; i++) {
                it.taraba.add(breadBuff);
            }
        }
    }
    public final class PointsComparator implements Comparator<Player> {
        public int compare(final Player obj1, final Player obj2) {
            return obj1.points > obj2.points          //se compara punctele detinute de jucatori
                ? -1 : obj1.points == obj2.points ? 0 : 1;
        }
    }

    void scor() {

        ArrayList<Player> rezultat = new ArrayList<Player>();

        for (Player it : playersArray) {
            int scor = 0;         //se adauga visteria care
            scor += it.visterie;    //contine deja bonusurile pt King si Queen
            for (Bunuri bun : it.taraba) {
                scor += bun.profit;                //se adauga profitul bunurilor de pe taraba
            }
            it.points = scor;           //se stabileste punctejul final
            rezultat.add(it);           //se adauga in lista de sortat
        }

        Collections.sort(playersArray, new PointsComparator());   //se sorteaza descrescator
                                                               //cu comparatorul descris mai sus
        for (Player it : playersArray) {              //se afiseaza clasamentul
            System.out.println(it.strategy.toUpperCase() + ": " + it.points);
        }
    }
}
