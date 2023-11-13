package cstjean.mobile.damier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * L'ensemble des tests pour la classe Damier.

 * @author Maxime Nobert et Alexandre Lavoie.
 */
public class TestDamier {
    /**
     * Le damier utilisé dans le setup.
     */
    private Damier damier;

    /**
     * La méthode de Setup.
     */
    @Before
    public void setUp() {
        damier = new Damier();
    }

    /**
     * Tests effectué lors de la création du damier.
     */
    @Test
    public void testCreer() {
        assertEquals(0, damier.getCount());
    }

    /**
     * Tests effectué lors de "l'ajout" d'un pion.
     */
    @Test
    public void testAjouter() {
        damier.vider();
        damier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        assertEquals(Pion.Couleur.Noir, damier.findPion(38).getCouleur());
        assertEquals(1, damier.getCount());
        damier.ajouterPion(1, new Pion());
        assertEquals(Pion.Couleur.Blanc, damier.findPion(1).getCouleur());
        assertEquals(2, damier.getCount());

        damier.ajouterPion(0, new Pion());
    }

    /**
     * Teste le retrait d'un pion du damier.
     */
    @Test
    public void testRetirer() {

        damier.ajouterPion(1, new Dame());
        damier.retirerPion(1);

        assertNull(damier.findPion(1));

    }

    /**
     * Teste la méthode servant à trouver un pion à l'aide d'une position dans le damier.
     */
    @Test
    public void testFindPion() {
        damier.ajouterPion(2, new Pion(Pion.Couleur.Blanc));
        assertEquals(Pion.Couleur.Blanc, damier.findPion(2).getCouleur());
        damier.ajouterPion(5, new Pion(Pion.Couleur.Noir));
        assertEquals(Pion.Couleur.Noir, damier.findPion(5).getCouleur());
        assertNull(damier.findPion(22));
    }

    /**
     * Teste la méthode "initialiser" du damier.
     */
    @Test
    public void testIntitialiser() {
        damier.initialiser();
        assertEquals(Pion.Couleur.Blanc, damier.findPion(4).getCouleur());
        assertNull(damier.findPion(24));
        assertEquals(Pion.Couleur.Noir, damier.findPion(50).getCouleur());
        assertEquals(40, damier.getCount());
    }

    /**
     * Test de getPions().
     */
    @Test
    public void testGetPions() {
        damier.initialiser();
        assertEquals(Pion.Couleur.Blanc, damier.getPions().get(1).getCouleur());
        assertNull(damier.getPions().get(24));
        assertEquals(Pion.Couleur.Noir, damier.getPions().get(50).getCouleur());

    }

    /**
     * Test de la fonction getCount() sans couleur fixe.
     */
    @Test
    public void testGetCount() {
        damier.vider();
        assertEquals(0, damier.getCount());
    }

    /**
     * Test de la fonction getCount() avec couleur fixe.
     */
    @Test
    public void testGetCountCouleur() {
        damier.vider();
        assertEquals(0, damier.getCount(Pion.Couleur.Blanc));
        assertEquals(0, damier.getPions(Pion.Couleur.Blanc).size());
        damier.initialiser();
        assertEquals(20, damier.getPions(Pion.Couleur.Blanc).size());
        assertEquals(20, damier.getCount(Pion.Couleur.Blanc));
    }

    /**
     * Utilisé pour aider les tests unitaires... Trouve S'il y a tel nb dans un array.

     * @param integer le nb mentionné.
     * @param array l'array mentionné.

     * @return si le nb est présent dans l'array.
     */
    private static boolean findIntegerInArray(Integer integer, Integer[] array) {
        for (Integer integur : array) {
            if (integur.equals(integer)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Le test utilisé pour la fonction getDeplacementsPossibles.
     * Note : Entre chaques "sous-tests", je rénitialise le damier même si ce n'est pas le plus efficace
     * pour l'ordinateur, pour des raisons de lisibilité, etc.
     */
    @Test
    public void testGetDeplacementsPossiblesPion() {

        Integer[] positionsPossibles;

        /* TESTS POUR PION BLANC */

        // Test PionBlanc Avance Gauche-Droite

        damier.initialiser();

        positionsPossibles = damier.getDeplacementsPossibles(16);
        assertEquals(1, positionsPossibles.length);
        assertTrue(findIntegerInArray(21, positionsPossibles));

        // il faudra bouger ce test lol
        positionsPossibles = damier.getDeplacementsPossibles(18);
        assertEquals(2, positionsPossibles.length);
        assertTrue(findIntegerInArray(22, positionsPossibles));
        assertTrue(findIntegerInArray(23, positionsPossibles));

        // stb accéder aux anciens tests... git te le permettra...
    }

    /**
     * Test de getDeplacementsPossibleDame().
     */
    @Test
    public void testGetDeplacementsPossiblesDame() {
        Integer[] positionsDamePossibles;
        damier.vider();
        damier.ajouterPion(1, new Dame(Pion.Couleur.Blanc));
        positionsDamePossibles = damier.getDeplacementsPossibles(1);
        // 6, 7, 12, 18, 23, 29, 34, 40, 45
        assertEquals(9, positionsDamePossibles.length);

        damier.ajouterPion(13, new Dame(Pion.Couleur.Blanc));
        positionsDamePossibles = damier.getDeplacementsPossibles(13);
        // 1, 8, 9, 4, 13, 18, 22, 27, 31, 36, 19, 24, 30, 35
        assertEquals(13, positionsDamePossibles.length);

        damier.ajouterPion(28, new Dame(Pion.Couleur.Noir));
        positionsDamePossibles = damier.getDeplacementsPossibles(13);
        // 6, 11, 17, 22, 32, 37, 41, 46, 23, 19, 14, 10, 5, 33, 39, 44, 50
        assertEquals(13, positionsDamePossibles.length);
    }

    /**
     * Teste pour voir si les pions qui atteint les cases du fond.
     * deviennent des dames // TODO MAX RÉVISE CE TEST PLZ
     */
    @Test
    public void testTransformationDame() {

        damier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));
        assertFalse(damier.findPion(43).estDame());

        damier.deplacerPion(43, 48); // change le tour, donc pion devient dame!
        assertTrue(damier.findPion(48).estDame());

        damier.ajouterPion(8, new Pion(Pion.Couleur.Noir));
        assertFalse(damier.findPion(8).estDame());

        damier.deplacerPion(8, 3); // change le tour, donc pion devient dame!
        assertTrue(damier.findPion(3).estDame());
        // normalement, le tour termine.

        damier.vider();

        damier.ajouterPion(3, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(12, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(17, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(34, new Pion(Pion.Couleur.Blanc)); // pion qui aide le test.

        damier.deplacerPion(3, 8); // change le tour

        damier.deplacerPion(12, 3);

        assertEquals(Pion.Couleur.Noir, damier.findPion(3).getCouleur());

        assertTrue(damier.findPion(3).estDame());
    }

    /**
     * Test pour voir si on peut déplacer un pion adéquatement.
     */
    @Test
    public void testDeplacerPion() {
        damier.initialiser();

        damier.deplacerPion(20, 25); // positionTarget, nouvellePosition
        assertEquals(Pion.Couleur.Blanc, damier.getPions().get(25).getCouleur());
        assertNull(damier.getPions().get(20));
        
        damier.vider();

        damier.initialiser();
        damier.deplacerPion(19, 24); // positionTarget, nouvellePosition
        damier.deplacerPion(31, 27);
        assertEquals(Pion.Couleur.Noir, damier.findPion(27).getCouleur());

        damier.vider();

        damier.initialiser();
        damier.deplacerPion(20, 24); // positionTarget, nouvellePosition
        assertEquals(Pion.Couleur.Blanc, damier.findPion(24).getCouleur());

    }
    
    @Test
    public void testDeplacerDame() {
        // Possiblement non fini TODO somehow marche, mais je sens que la logique ne marche pas!!!
        damier.vider();

        // Déplacement haut-gauche
        damier.ajouterPion(50, new Dame(Pion.Couleur.Blanc));
        damier.deplacerPion(50, 22);
        assertEquals(Pion.Couleur.Blanc, damier.findPion(22).getCouleur());

        // Tour du joueur noir
        // Déplacement bas-droite
        damier.ajouterPion(1, new Dame(Pion.Couleur.Noir));
        damier.deplacerPion(1, 45);
        assertEquals(Pion.Couleur.Noir, damier.findPion(45).getCouleur());

        // Déplacement haut-droite
        damier.ajouterPion(37, new Dame(Pion.Couleur.Blanc));
        damier.deplacerPion(37, 5);
        assertEquals(Pion.Couleur.Blanc, damier.findPion(5).getCouleur());

        // Déplacement bas-gauche
        damier.ajouterPion(2, new Dame(Pion.Couleur.Noir));
        damier.deplacerPion(2, 16);
        assertEquals(Pion.Couleur.Noir, damier.findPion(16).getCouleur());
    }

    /**
     * Test de déplacement de dame lorsqu'elle est bloquée.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeplacementDameBloque() {

        damier.ajouterPion(50, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(39, new Dame(Pion.Couleur.Blanc));

        assertFalse(findIntegerInArray(33, damier.getDeplacementsPossibles(50)));
        
        damier.deplacerPion(50, 22);
        assertNull(damier.findPion(22));
    }

    /**
     * Test pour voir si un déplacement invalide ne marche pas.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeplacementOutOfBound1() throws IllegalArgumentException {
        // Déplacement haut-gauche (essaie d'obtenir la position -1)

        damier.vider();

        damier.deplacerPion(1, 51);
    }

    /**
     * Essaie de déplacer un pion avec une position finale.

     * @throws IllegalArgumentException l'impossibilité de déplacer le pion.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeplacementOutOfBound2() throws IllegalArgumentException {
        // Déplacement haut-gauche (essaie d'obtenir la position -1)

        damier.vider();

        damier.deplacerPion(0, 50);
    }

    @Test
    public void testRetourArriere() {
        damier.vider();
        damier.ajouterPion(3, new Pion(Pion.Couleur.Blanc));
        damier.deplacerPion(3, 9);
        assertNull(damier.findPion(3));
        damier.retourArriere();
        assertEquals(Pion.Couleur.Blanc, damier.findPion(3).getCouleur());

        damier.vider();
        damier.ajouterPion(14, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(19, new Dame(Pion.Couleur.Noir));
        damier.deplacerPion(14, 23);
        assertEquals(Pion.Couleur.Blanc, damier.findPion(23).getCouleur());
        assertNull(damier.findPion(19));
        damier.retourArriere();
        assertEquals(Pion.Couleur.Noir, damier.findPion(19).getCouleur());
        assertEquals(Pion.Couleur.Blanc, damier.findPion(14).getCouleur());

        damier.vider();

        damier.ajouterPion(43, new Pion());
        damier.deplacerPion(43, 48);
        damier.retourArriere();
        assertNotNull(damier.findPion(43));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeplacerPionInvalide() {
        // Possiblement non fini TODO!!!
        damier.initialiser();

        damier.initialiser();

        damier.deplacerPion(20, 23); // ne devrait pas fonctionner
        assertNull(damier.findPion(23));

    }

    /**
     * Teste pour voir à qui est le tour.
     */
    @Test
    public void testTourJoueur() {
        damier.initialiser();
        assertEquals(Pion.Couleur.Blanc, damier.getTourJoueur());
        // Le joueur blanc fait son tour
        damier.deplacerPion(20, 24);
        // C'est rendu le tour du noir
        assertEquals(Pion.Couleur.Noir, damier.getTourJoueur());

        damier.vider();

        damier.ajouterPion(28, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(19, new Pion(Pion.Couleur.Blanc));
        damier.deplacerPion(19, 23);
        damier.deplacerPion(28, 19);

        assertEquals(Pion.Couleur.Noir, damier.findPion(19).getCouleur());
    }

    /**
     * Test de getPrisesFromHistorique. Quand l'historique est vide.
     */
    @Test

    public void getPrisesFromHistoriqueVide() {
        damier.initialiser();

        assertEquals(0, damier.getPrisesFromHistorique(damier.getTourJoueur()).length);

    }

    /**
     * Test de getDeplacementPossible().
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetDeplacementsPossibles() {

        damier.ajouterPion(50, new Pion(Pion.Couleur.Blanc));

        assertEquals(0, damier.getDeplacementsPossibles(49).length);

        assertEquals(0, damier.getDeplacementsPossibles(50).length);
        damier.getDeplacementsPossibles(51);

    }

    /**
     * Test de getDeplacementPossiblePriseforcee().
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetDeplacementsPossiblesPriseForcee() {

        damier.ajouterPion(50, new Pion(Pion.Couleur.Blanc));

        assertEquals(0, damier.getDeplacementsPossiblesPriseForcee(49).length);

        assertEquals(0, damier.getDeplacementsPossiblesPriseForcee(50).length);
        damier.getDeplacementsPossiblesPriseForcee(51);

    }

    /**
     * Test qui regarde si la partie est terminée.
     */
    @Test
    public void testPartieTerminee() {

        damier.ajouterPion(23, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(28, new Pion(Pion.Couleur.Noir));

        assertEquals(damier.getEtatPartie(), Damier.EtatPartie.EnCours);

        damier.deplacerPion(23, 32);

        assertEquals(damier.getEtatPartie(), Damier.EtatPartie.BlancGagne);

        damier.vider();

        damier.ajouterPion(10, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(23, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(28, new Pion(Pion.Couleur.Noir));

        damier.deplacerPion(10, 14);

        damier.deplacerPion(28, 19);
        damier.deplacerPion(19, 10);

        assertEquals(damier.getEtatPartie(), Damier.EtatPartie.NoirGagne);

        damier.vider();

        damier.ajouterPion(50, new Pion(Pion.Couleur.Blanc));

        damier.ajouterPion(23, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(28, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(14, new Pion(Pion.Couleur.Blanc));

        damier.deplacerPion(14, 19);

        damier.deplacerPion(23, 14);

        assertEquals(damier.getEtatPartie(), Damier.EtatPartie.NoirGagne);

    }

    /**
     * Test de prise lorsqu'il y a un autre pion qui peut faire une plus grande prise.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPrisePrioritaire() {

        damier.ajouterPion(33, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(13, new Pion(Pion.Couleur.Blanc));

        damier.ajouterPion(19, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(18, new Pion(Pion.Couleur.Noir));

        damier.ajouterPion(28, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(30, new Pion(Pion.Couleur.Noir));

        damier.deplacerPion(28, 39);

        damier.deplacerPion(13, 24);
        assertNotNull(damier.findPion(24));

        damier.retourArriere();

        damier.deplacerPion(13, 22);
        assertNull(damier.findPion(22));

    }

    /**
     * Test représentation d'exercice 3 je crois.
     */
    @Test
    public void testRepresentation() {
        damier.initialiser();
        assertEquals("""
                        -p-p-p-p-p
                        p-p-p-p-p-
                        -p-p-p-p-p
                        p-p-p-p-p-
                        ----------
                        ----------
                        -P-P-P-P-P
                        P-P-P-P-P-
                        -P-P-P-P-P
                        P-P-P-P-P-
                        """,
                damier.getRepresentation()
        );

        damier.retirerPion(5);
        damier.ajouterPion(5, new Dame(Dame.Couleur.Blanc));

        damier.retirerPion(1);
        damier.ajouterPion(1, new Dame());

        damier.retirerPion(50);
        damier.ajouterPion(50, new Dame(Dame.Couleur.Noir));
        assertEquals("""
                        -d-p-p-p-d
                        p-p-p-p-p-
                        -p-p-p-p-p
                        p-p-p-p-p-
                        ----------
                        ----------
                        -P-P-P-P-P
                        P-P-P-P-P-
                        -P-P-P-P-P
                        P-P-P-P-D-
                        """,
                damier.getRepresentation()
        );
    }
}