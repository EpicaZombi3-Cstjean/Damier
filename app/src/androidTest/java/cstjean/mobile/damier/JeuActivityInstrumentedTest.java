package cstjean.mobile.damier;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.os.RemoteException;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Note : Pour les tests, je lie ensemble des couples "Action-Check" afin que ce soit plus lisible.
 */
@RunWith(AndroidJUnit4.class)
public class JeuActivityInstrumentedTest {

    /**
     * Les ID des boutons, utilisé dans les tests instrumentés.
     */
    private int[] buttonIds;

    /**
     * Le setup du test instrumenté.
     */
    @Before
    public void setup() {
    }

    /**
     * La règle servant pour les tests instrumentés.
     */
    @Rule
    public ActivityScenarioRule<MainMenuActivity> rule
            = new ActivityScenarioRule<>(MainMenuActivity.class);

    @Test
    public void testEntreeNoms() {

        final String nom1 = "111";
        final String nom2 = "222";

        onView(withId(R.id.text_joueur1)).check(matches(withText("")));
        onView(withId(R.id.text_joueur2)).check(matches(withText("")));

        onView(withId(R.id.text_joueur1)).perform(typeText(nom1));
        onView(withId(R.id.text_joueur2)).perform(typeText(nom2));

        onView(withId(R.id.text_joueur1)).check(matches(withText(nom1)));
        onView(withId(R.id.text_joueur2)).check(matches(withText(nom2)));

    }

    @Test
    public void testVictoireRotation() throws RemoteException {

        final String nom1 = "111";
        final String nom2 = "222";

        onView(withId(R.id.text_joueur1)).check(matches(withText("")));
        onView(withId(R.id.text_joueur2)).check(matches(withText("")));

        onView(withId(R.id.text_joueur1)).perform(typeText(nom1));
        onView(withId(R.id.text_joueur2)).perform(typeText(nom2));

        onView(withId(R.id.text_joueur1)).check(matches(withText(nom1)));
        onView(withId(R.id.text_joueur2)).check(matches(withText(nom2)));

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.setOrientationLeft();
        device.setOrientationRight();
        device.setOrientationNatural();

        onView(withId(R.id.text_joueur1)).check(matches(withText(nom1)));
        onView(withId(R.id.text_joueur2)).check(matches(withText(nom2)));
    }
}