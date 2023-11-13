package cstjean.mobile.damier.logique;

// Damier2
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import cstjean.mobile.damier.logique.Pion;

/**
 * La classe Main.
 */
public class Main {

    /**
     * La fonction "Main" appelée.

     * @param args passé lors de l'exécution du programme je crois.
     */
    public static void main(String[] args) {

        Damier damier = new Damier();

        damier.vider();

        System.out.println("Test 1"); // devrait donner 6 et 7
        damier.ajouterPion(1, new Pion(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(1));

        damier.ajouterPion(7, new Pion(Pion.Couleur.Noir));
        System.out.println("Test 2");
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(1));

        damier.ajouterPion(18, new Pion(Pion.Couleur.Noir));
        System.out.println("Test 3");
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(1));

        System.out.println("Test 4"); // devrait donner 1 et 23
        damier.retirerPion(1);
        damier.ajouterPion(12, new Pion(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(12));

        System.out.println("Test 5"); // devrait ne donner que 23. car plus longue prise
        damier.retirerPion(1);
        damier.ajouterPion(29, new Pion(Pion.Couleur.Noir));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(12));

        damier.vider();

        System.out.println("Test 6"); // milieu
        System.out.println("pion blanc");
        damier.ajouterPion(23, new Pion(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));

        System.out.println("pion blanc");
        damier.retirerPion(23);
        damier.ajouterPion(23, new Pion(Pion.Couleur.Noir));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));

        damier.vider();

        System.out.println("Test 7"); // Droite 1 solution
        System.out.println("pion blanc");
        damier.ajouterPion(25, new Pion(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(25));

        System.out.println("pion noir");
        damier.retirerPion(25);
        damier.ajouterPion(25, new Pion(Pion.Couleur.Noir));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(25));

        System.out.println("Test 8"); // Gauche
        System.out.println("pion blanc");
        damier.ajouterPion(26, new Pion(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(26));

        System.out.println("pion noir");
        damier.retirerPion(26);
        damier.ajouterPion(26, new Pion(Pion.Couleur.Noir));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(26));

        damier.vider();

        System.out.println("Test 9");
        System.out.println("pion blanc");
        damier.ajouterPion(25, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(20, new Pion(Pion.Couleur.Noir));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(25));

        System.out.println("pion noir");
        damier.retirerPion(25);
        damier.ajouterPion(25, new Pion(Pion.Couleur.Noir));

        damier.retirerPion(20);
        damier.ajouterPion(30, new Pion(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(25));

        damier.vider();

        System.out.println("Test 10"); // null car 0 pions
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(1));

        damier.vider();

        System.out.println("Test 11");
        damier.ajouterPion(1, new Dame(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(1));

        damier.vider();

        System.out.println("Test 12");
        System.out.println("dame blanche");
        damier.ajouterPion(23, new Dame(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));
        damier.retirerPion(23);

        System.out.println("dame noire");
        damier.ajouterPion(23, new Dame(Pion.Couleur.Noir));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));

        damier.vider();

        System.out.println("Test 13");
        System.out.println("dame blanche");

        damier.ajouterPion(23, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(18, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(19, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(14, new Dame(Pion.Couleur.Noir));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));

        damier.vider();

        System.out.println("dame noire");

        damier.ajouterPion(23, new Dame(Pion.Couleur.Noir));

        damier.ajouterPion(18, new Dame(Pion.Couleur.Noir));

        damier.ajouterPion(19, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(14, new Dame(Pion.Couleur.Blanc));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));

        damier.vider();

        System.out.println("Test 14");
        System.out.println("dame blanche");

        damier.ajouterPion(23, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(18, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(14, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(10, new Dame(Pion.Couleur.Noir));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));

        damier.vider();

        System.out.println("dame noire");

        damier.ajouterPion(23, new Dame(Pion.Couleur.Noir));

        damier.ajouterPion(18, new Dame(Pion.Couleur.Noir));

        damier.ajouterPion(14, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(10, new Dame(Pion.Couleur.Blanc));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(23));

        System.out.println("Test 15 (Prises multiples dames)");
        System.out.println("dame blanche");

        damier.ajouterPion(20, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(29, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(22, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(8, new Dame(Pion.Couleur.Noir));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(20));

        damier.vider();

        System.out.println("dame noire");

        damier.ajouterPion(20, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(29, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(22, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(8, new Dame(Pion.Couleur.Blanc));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(20));

        System.out.println("Test 16 (Prises multiples dames avec plus grand)");
        System.out.println("dame blanche");

        damier.ajouterPion(33, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(22, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(8, new Dame(Pion.Couleur.Noir));

        damier.ajouterPion(42, new Dame(Pion.Couleur.Noir)); // ignoré

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(33));

        damier.vider();

        System.out.println("dame noire");

        damier.ajouterPion(33, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(22, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(8, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(42, new Dame(Pion.Couleur.Blanc)); // ignoré

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(33));

        damier.vider();

        // PRISES MULTIPLES AVEC PIONS DÉJÀ PRIS

        System.out.println("Test 17 (Prises multiples pion avec pions dja pris)");

        damier.ajouterPion(12, new Pion(Pion.Couleur.Blanc));

        damier.ajouterPion(18, new Pion(Pion.Couleur.Noir));

        damier.ajouterPion(29, new Pion(Pion.Couleur.Noir));

        System.out.println("Voir déplacements possibles case 12");
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(12));

        System.out.println("Déplacement 12x23");
        damier.deplacerPion(12, 23);

        System.out.println("Voir déplacements possibles case 23");
        analyserMouvementsPossibles(damier.getDeplacementsPossiblesPriseForcee(23));

        System.out.println("Déplacement 23x34");
        damier.deplacerPion(23, 34);

        System.out.println("Voir déplacements possibles case 34");
        analyserMouvementsPossibles(damier.getDeplacementsPossiblesPriseForcee(34));

        damier.vider();

        System.out.println("Test 18 (Prises multiples forcée)");
        damier.ajouterPion(33, new Dame(Pion.Couleur.Noir));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(33));
        analyserMouvementsPossibles(damier.getDeplacementsPossibles(50));
        damier.ajouterPion(13, new Pion(Pion.Couleur.Blanc));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(13));

        damier.ajouterPion(12, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(19, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(30, new Pion(Pion.Couleur.Noir));

        damier.ajouterPion(19, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(18, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(30, new Pion(Pion.Couleur.Noir));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(13));

        System.out.println("Pion qui ne devrait pas pouvoir bouger");

        System.out.println("tentative ratée!!!");
        System.out.println((damier.findPion(12) != null) + "12");
        System.out.println((damier.findPion(23) != null) + "23");
        System.out.println("déplacement");
        damier.deplacerPion(12, 23);
        System.out.println((damier.findPion(12) != null) + "12");
        System.out.println((damier.findPion(23) != null) + "23");

        System.out.println("tentative réussie? mais pion ne peut pas car meilleure prise existe");
        System.out.println((damier.findPion(13) != null) + "13");
        System.out.println((damier.findPion(22) != null) + "22");
        System.out.println("déplacement");
        damier.deplacerPion(13, 22);
        System.out.println((damier.findPion(13) != null) + "13");
        System.out.println((damier.findPion(22) != null) + "22");

        System.out.println("Pion qui devrait pouvoir bouger (plus grande prise (19, 30)");
        System.out.println((damier.findPion(13) != null) + "13");
        System.out.println((damier.findPion(24) != null) + "24");
        System.out.println("déplacement");
        damier.deplacerPion(13, 24);
        System.out.println((damier.findPion(13) != null) + "13");
        System.out.println((damier.findPion(24) != null) + "24");

        System.out.println("pions de la prise : (19 et 30)");
        System.out.println((damier.findPion(19) != null) + "19");
        System.out.println((damier.findPion(30) != null) + "30");

        damier.deplacerPion(24, 35);

        System.out.println("Tour terminé, donc les pions (19 et 30) sont retirés");
        System.out.println((damier.findPion(19) != null) + "19");
        System.out.println((damier.findPion(30) != null) + "30");

        damier.vider();

        System.out.println("Test 19 (Transformation en dame)");
        System.out.println("Tour : " + damier.getTourJoueur());

        damier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));

        damier.deplacerPion(43, 48);

        System.out.println((damier.findPion(48) != null) + "48");
        System.out.println((damier.findPion(48) != null && damier.findPion(48).estDame()) + " estDame?");

        System.out.println("Tour : " + damier.getTourJoueur());

        damier.ajouterPion(8, new Pion(Pion.Couleur.Noir));

        damier.deplacerPion(8, 3);

        System.out.println((damier.findPion(3) != null) + "3");
        System.out.println((damier.findPion(3) != null && damier.findPion(3).estDame()) + " estDame?");

        damier.vider();

        System.out.println("Test 20 (Prise bloquée par pion!)");

        damier.ajouterPion(1, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(7, new Pion(Pion.Couleur.Blanc));

        damier.ajouterPion(12, new Pion(Pion.Couleur.Noir));

        analyserMouvementsPossibles(damier.getDeplacementsPossibles(1)); // devrait être 6 en fait

    }

    /**
     * pas de javadocs!.

     * @param ints pas de docs!
     */
    public static void analyserMouvementsPossibles(Integer[] ints) {

        if (ints.length == 0) {
            System.out.println("Null");
        }

        for (Integer entier : ints) {
            System.out.print(entier);
            System.out.print(";");
        }
        System.out.println('\n');

    }
}