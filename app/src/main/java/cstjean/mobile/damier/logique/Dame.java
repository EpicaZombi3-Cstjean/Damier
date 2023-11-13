package cstjean.mobile.damier.logique;

/**
 * Classe de Dame utilisé dans les Dames.

 * @author Maxime Nobert et Alexandre Lavoie
 */
public class Dame extends Pion {
    /**
     * Constructeur publique de Dame (couleur par défaut).
     *
     * @param couleur la couleur de la dame.
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }

    /**
     * Constructeur par défaut lorsqu'aucun argument n'est passé.
     */
    public Dame() {
        super();
    }

    // J'ai volontairement omis cette javadoc car je suis incertain si l'utilisation d'une valeur booléenne
    // comme celle-ci est une bonne idée...
    // note : copier le javadoc de l'autre.
    @Override
    public boolean estDame() {
        return true;
    }
    /**
     * Détermine la couleur d'une dame avec une lettre.
     *
     * @return un caractère représentant la couleur si il y
     *     a un pion.
     */

    @Override
    public char getRepresentation() {
        if (this.getCouleur() == Couleur.Blanc) {
            return 'd';
        } else {
            return 'D';
        }
    }
}
