package main;

import java.util.ArrayList;

public final class Player {
    String role;                                          //serif sau comerciant
	int sheriff = 0;                                      //0 pt comerciant si 1 pt serif
	private final int fifty = 50;
	int visterie = fifty;
	ArrayList<Bunuri> cards = new ArrayList<Bunuri>();             //cartile din mana
	ArrayList<Bunuri> sack = new ArrayList<Bunuri>();              //bunurile din sac
	ArrayList<Bunuri> taraba = new ArrayList<Bunuri>();             //bunurile de pe taraba
	String strategy;
	int mita = 0;
	int declaration;
	int points = 0;                          //punctaj final
	int greedyRound = 0;                       //contor pt rundele in care greedy e comerciant
	int mere = 0;                           //contoare
	int paine = 0;                          //pt produsele
	int pui = 0;                            //legale de
	int branza = 0;                         //pe taraba
	private final int five = 5;
	private final int three = 3;
	private final int six = 6;
	private final int ten = 10;

	Player(final String strategy) {
		this.strategy = strategy;
	}

	ArrayList<Bunuri> assetFreq(final ArrayList<Bunuri> pcards) {

		ArrayList<Bunuri> mereArr = new ArrayList<Bunuri>();   //cate o lista pt
		ArrayList<Bunuri> paineArr = new ArrayList<Bunuri>();   //fiecare tip de bun legal
		ArrayList<Bunuri> puiArr = new ArrayList<Bunuri>();
		ArrayList<Bunuri> branzaArr = new ArrayList<Bunuri>();

		ArrayList<Bunuri> elemMax = new ArrayList<Bunuri>();  //tipurile cu frecventa maxima
		ArrayList<Integer> max = new ArrayList<Integer>(); //va retine dimensiunile listelor

		elemMax.clear();

		for (Bunuri card : pcards) {      //se adauga bunurile in listele corespunzatoare
			if (card.id == 0) {
				mereArr.add(card);
			}
			if (card.id == 1) {
				branzaArr.add(card);
			}
			if (card.id == 2) {
				paineArr.add(card);
			}
			if (card.id == three) {
				puiArr.add(card);
			}
		}

		max.add(mereArr.size());                //se adauga dimensiunile listelor
		max.add(branzaArr.size());
		max.add(paineArr.size());
		max.add(puiArr.size());

		int max1 = 0;
		for (Integer it : max) {        //se stabileste valoarea maxima din lista "max"
		    if (it.intValue() > max1) {
		    	max1 = it.intValue();
		    }
		}
		if (mereArr.size() == max1) {    //daca dimensiunea listei de bunuri este egala
			elemMax.add(mereArr.get(0));  //cu valoarea maxima, se aduga un bun din
		}                               //aceasta lista in lista "elemMax"
		if (puiArr.size() == max1) {
			elemMax.add(puiArr.get(0));
		}
		if (branzaArr.size() == max1) {
			elemMax.add(branzaArr.get(0));
		}
		if (paineArr.size() == max1) {
			elemMax.add(paineArr.get(0));
		}
		mereArr.clear();
		puiArr.clear();
		paineArr.clear();
		branzaArr.clear();
		max.clear();

		return elemMax;               //returneaza tipurile de bunuri cu frecventa maxima
	}

	void metodaBasic() {

		ArrayList<Bunuri> elemMax = new ArrayList<Bunuri>();
		ArrayList<Bunuri> legale = new ArrayList<Bunuri>();
		ArrayList<Bunuri> scoase = new ArrayList<Bunuri>();

		for (Bunuri it : cards) {                 //se selecteaza bunurile legale din mana
			if (it.type == "Legal") {
				legale.add(it);
			}
		}

		if (legale.size() == 0) {    //daca nu sunt bunuri legale, se stabileste profitul
			int profit = six;         //maxim dintre cartile din mana,
			for (Bunuri it : cards) {      //apoi se aduga in sac o carte de acel tip
				if (it.profit > profit) {     //si se declara mere
					profit = it.profit;
				}
			}
			for (Bunuri it : cards) {
				if (it.profit == profit) {
					sack.add(it);
					scoase.add(it);   //se adauga in lista de
					this.declaration = 0;  //scoase bunul care a fost bagat
					break;            // in sac, pt a fi apoi eleiminat din mana
				}
			}
		} else {
			for (Bunuri it : assetFreq(legale)) {  //daca sunt bunuri legale in mana
				elemMax.add(it);
			}
			//daca exista un singur bun cu nr maxim de
			//aparitii se adauga in sac toate bunurile
		    //de acel tip si se declara acel tip
		    //se adauga in lista de scoase
			//bunul care a fost bagat in sac, pt a fi apoi
		    //eleiminate din mana
			if (elemMax.size() == 1) {
				for (Bunuri it : cards) {
					if (it.id == elemMax.get(0).id) {
						sack.add(it);
						scoase.add(it);
					}
				}
				this.declaration = elemMax.get(0).id;
			} else {
			    //daca sunt mai mlte tipuri cu aceeasi
				//frecventa se alege cel cu profitul cel mai mare
				int asset = -1;
				int profMax = 0;
				for (Bunuri it : elemMax) {    //se stabileste profitul maxim
					if (it.profit > profMax) {
						profMax = it.profit;
					}
				}
				//daca sunt mai multe tipuri cu
				//aceeasi frecventa si acelasi
				//profit se alege cel care
			    // apare primul in mana
				for (Bunuri card : cards) {
					for (Bunuri it : elemMax) {
						if (card.profit == profMax && card.id == it.id) {
							asset = card.id;
							break;
						}
					}
					if (asset != -1) {
						break;
					}
				}
				this.declaration = asset;        //se declara tipul adaugat in sac
				for (Bunuri card : cards) {
					if (card.id == asset) {
						sack.add(card);
						scoase.add(card);  //se adauga bunurile care vor
					}                      //fi scoase din mana
				}
			}
		}
		for (Bunuri it : scoase) {
			cards.remove(it);                //se scot din mana bunurile puse in sac
		}
		scoase.clear();
		elemMax.clear();
		legale.clear();
	}

	void setSack() {

		switch (strategy) {

		case "basic":

			metodaBasic();
			break;

		case "greedy":
			if (sheriff == 0) { //se incrementeaza rundele in care greedy
				greedyRound++;  //a fost comerciant
			}
			metodaBasic();


			if (greedyRound % 2 == 0) {   //daca runda greedy e para, el va adauga
				if (sack.size() < five) {   //in sac si un bun ilegal se verifica
					int profit = six;   //daca sacul nu este plin
					for (Bunuri it : cards) {  //se stabileste bunul
						if (it.profit > profit) { //ilegal cu profit maxim
							profit = it.profit;
						}
					}
					for (Bunuri card : cards) { //se adauga in sac si
						if (card.profit == profit) {  //se scoate din mana
							sack.add(card);
							cards.remove(card);
							break;
						}
					}
				}
			}
			break;

		case "bribed":

			this.mita = 0;
			ArrayList<Bunuri> ilegale = new ArrayList<Bunuri>();
			ArrayList<Bunuri> scoase = new ArrayList<Bunuri>();

			for (Bunuri it : cards) {           //se stabilesc bunurile ilegale
				if (it.type == "Illegal") {
					ilegale.add(it);
				}
			}
			//daca nu exista bunuri
			//ilegale sau nu are suficienti bani, se aplica basic
			if (ilegale.size() == 0 || this.visterie < five) {
				metodaBasic();
			}
			//daca are legale, se stabileste
			//in functie de cati bani are,
			//cate bunuri ilegale
			//poate adauga in sac; daca
			//isi permite sa bage maxim
			//doua bunuri ilegale si are in mana
			//maxim doua bunuri ilegale, le aduaga pe acestea
			if (ilegale.size() > 0) {
				if (this.visterie >= five && this.visterie < ten) {
					if (ilegale.size() <= 2) {
						for (Bunuri it : ilegale) {
							sack.add(it);
							scoase.add(it);
						}
					} else {
						//daca are mai multe
						//decat isi permite sa
						//adauge, le aduga
						//pe cele cu profitul
						//cel mai mare
						while (sack.size() < 2) {
							int profit = six;
							for (Bunuri it : ilegale) {
								if (it.profit > profit) {
									profit = it.profit;
								}
							}
							//se adauga cele care au fost
							//adaugate, pt a fi scoase din mana
							for (Bunuri card : ilegale) {
								if (card.profit == profit) {
									sack.add(card);
									scoase.add(card);
									ilegale.remove(card);
									break;
								}
							}
						}
					}
					this.mita = five;           //se stabileste mita
					this.visterie -= five;
				} else {
					//daca isi permite sa adauge mai mult de
					//doua bunuri ilegale, dar nu are mai mult
					//de doua, le aduga pe acestea
					if (this.visterie >= ten) {
						if (ilegale.size() <= 2) {
							for (Bunuri it : ilegale) {
								sack.add(it);
								scoase.add(it);
							}
						this.mita = five;
						this.visterie -= five;
						} else {
							//daca are mai mult de doua, le adauga
							//pe cele cu profitul cel mai mare pana
							//la umplerea sacului
							while (sack.size() < five
									&& ilegale.size() != 0) {
								int profit = six;
								for (Bunuri it : ilegale) {
									if (it.profit > profit) {
										profit = it.profit;
									}
								}
								for (Bunuri card : ilegale) {
									if (card.profit == profit) {
	                  //se adauga cele care au fost
					  //adaugate, pt a fi scoase din mana
										sack.add(card);
										scoase.add(card);
									   ilegale.remove(card);
										break;
									}
								}
							}
							this.mita = ten;       //se stabileste mita
							this.visterie -= ten;
						}
					}
				}
				this.declaration = 0;            //se declara mere
			}
			for (Bunuri it : scoase) {
				cards.remove(it);        //se scot din mana cartile adaugate in sac
			}
			ilegale.clear();
			break;

		case "wizard":   // wizard aplica metoda basic la inspectie,
			metodaBasic(); // diferenta fata de basic se face la inspectie
			break;

		default:
			break;

		}
	}

	void checkSack(final Player psheriff, final Player player,
			final ArrayList<Bunuri> pachetBun) {
		ArrayList<Bunuri> confiscate = new ArrayList<Bunuri>();

		for (Bunuri it : sack) {
			if (it.id != declaration) {
  //se stabilesc bunurile nedeclarate si se adauga la confiscate
				confiscate.add(it);
  //se plateste serifului pealizarea pentru acestea
				psheriff.visterie += it.penalty;
				player.visterie -= it.penalty;
			}
		}

		if (confiscate.size() != 0) {
			for (Bunuri it : confiscate) {
		//se scot din sac si se adauga la sfarsitul pachetului
				pachetBun.add(it);
				sack.remove(it);
			}
			confiscate.clear();
		} else {
            //daca nu exista confiscate, seriful
			//ii plateste o penalizare comerciantului
			psheriff.visterie -= sack.size() * sack.get(0).penalty;
			player.visterie += sack.size() * sack.get(0).penalty;
		}
	}
}
