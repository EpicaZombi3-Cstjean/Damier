package cstjean.mobile.damier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import cstjean.mobile.damier.logique.Damier;
import cstjean.mobile.damier.logique.Pion;

/** Teste la classe Pion.
 * La classe de pion qu'on dépose sur un damier.

 * @author Maxime Nobert et Alexandre Lavoie.
 */
public class TestPion {

    private Damier damier;
    /**
     * La méthode de Setup.
     */
    @Before
    public void setUp() {
        damier = Damier.getInstance();
    }
    /**
     * Teste la création du pion.
     */
    @Test
    public void testCreer() {
        Pion pion1 = new Pion();
        assertEquals(pion1.getCouleur(), Pion.Couleur.Blanc);

        Pion pion2 = new Pion(Pion.Couleur.Noir);
        assertEquals(pion2.getCouleur(), Pion.Couleur.Noir);

        Pion pion3 = new Pion(Pion.Couleur.Blanc);
        assertEquals(pion3.getCouleur(), Pion.Couleur.Blanc);
    }

    /**
     * Test de la représentation du damier.
     */
    @Test
    public void testGetRepresentation() {
        assertEquals('p', new Pion(Pion.Couleur.Blanc).getRepresentation());

        assertEquals('P', new Pion(Pion.Couleur.Noir).getRepresentation());
    }

    /**
     * Test du "clonage".
     */
    @Test
    public void testClone() {
        assertEquals(Pion.Couleur.Blanc, Pion.clone(new Pion()).getCouleur());

        assertEquals(Pion.Couleur.Blanc, Pion.clone(new Pion(Pion.Couleur.Blanc)).getCouleur());

        assertEquals(Pion.Couleur.Noir, Pion.clone(new Pion(Pion.Couleur.Noir)).getCouleur());

        assertNull(Pion.clone(damier.findPion(24)));
    }
}
