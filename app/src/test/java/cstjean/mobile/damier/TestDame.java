package cstjean.mobile.damier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cstjean.mobile.damier.logique.Dame;
import cstjean.mobile.damier.logique.Damier;
import cstjean.mobile.damier.logique.Pion;

/**
 * Classe de test de Dame.
 *
 * @author Maxime Nobert et Alexandre Lavoie
 */
public class TestDame extends TestPion {

    /**
     * Test de création de Dame.
     */
    @Test
    public void testCreer() {
        Dame dame1 = new Dame();
        assertEquals(dame1.getCouleur(), Dame.Couleur.Blanc);

        Dame pion2 = new Dame(Pion.Couleur.Noir);
        assertEquals(pion2.getCouleur(), Dame.Couleur.Noir);

        Dame pion3 = new Dame(Pion.Couleur.Blanc);
        assertEquals(pion3.getCouleur(), Dame.Couleur.Blanc);
    }

    /**
     * Test de représentation de Dame.
     */
    @Test
    @Override
    public void testGetRepresentation() {
        Dame dame1 = new Dame();
        assertEquals('d', dame1.getRepresentation());

        Dame dame2 = new Dame(Pion.Couleur.Noir);
        assertEquals('D', dame2.getRepresentation());

        Dame dame3 = new Dame(Pion.Couleur.Blanc);
        assertEquals('d', dame3.getRepresentation());

        Pion dame4 = new Dame();
        assertEquals('d', dame4.getRepresentation());
    }

    /**
     * Tests pour la fonction instance estDame() de la classe Dame.
     */
    @Test(expected = AssertionError.class)
    public void testEstDame() {
        Damier damier = new Damier();

        damier.ajouterPion(8, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(6, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(50, new Dame(Dame.Couleur.Blanc));

        damier.getTourJoueur();

        damier.getTourJoueur();

        assertTrue(damier.findPion(6).estDame());
        assertTrue(damier.findPion(8).estDame());
        assertTrue(damier.findPion(50).estDame());
    }

}
