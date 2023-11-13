package cstjean.mobile.damier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * La classe d'élément d'historique utilisée dans le travail.

 * @author Maxime Nobert et Alexandre Lavoie.
 */
public class TestElementHistorique {

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
     * Test de "print" du dernier tour.
     */
    @Test
    public void testPrint() {

        assertEquals("Empty", ElementHistorique.getHistoriqueTour(damier));

        damier.ajouterPion(18, new Dame(Pion.Couleur.Blanc));

        damier.ajouterPion(23, new Dame(Pion.Couleur.Noir));
        damier.ajouterPion(34, new Pion(Pion.Couleur.Noir));

        damier.deplacerPion(18, 29);

        assertEquals("18×29", ElementHistorique.getHistoriqueTour(damier));

        damier.deplacerPion(29, 40);

        assertEquals("18×29×40", ElementHistorique.getHistoriqueTour(damier));

        // changement de tour

        damier.ajouterPion(1, new Pion(Pion.Couleur.Blanc));
        damier.deplacerPion(1, 6);

        damier.ajouterPion(50, new Pion(Pion.Couleur.Noir));

        damier.deplacerPion(50, 45);

        assertEquals("(50-45)", ElementHistorique.getHistoriqueTour(damier));

    }

}
