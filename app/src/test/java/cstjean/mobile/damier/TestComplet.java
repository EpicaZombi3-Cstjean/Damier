package cstjean.mobile.damier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test complet.

 * @author Maxime Nobert et Alexandre Lavoie.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestDamier.class,
    TestElementHistorique.class,
    TestPion.class,
    TestDame.class,
})
public class TestComplet {
}
