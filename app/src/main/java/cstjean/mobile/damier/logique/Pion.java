package cstjean.mobile.damier.logique;

/**
 * Classe de pion utilisé dans les Dames.

 * @author Maxime Nobert et Alexandre Lavoie
 */
public class Pion {
    /**
     * la couleur du pion.
     */
    private final Couleur couleur;

    /**
     * Constructeur publique de Pion (couleur par défaut).
     *
     * @param couleur la couleur du pion.
     */
    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Constructeur publique de Pion (couleur par défaut).
     */
    public Pion() {
        couleur = Couleur.Blanc;
    }

    /**
     * Un getter pour obtenir la couleur.
     *
     * @return _couleur (string).
     */

    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Détermine la couleur d'un pion avec une lettre.
     *
     * @return un caractère représentant la couleur si il y
     *     a un pion.
     */
    public char getRepresentation() {
        if (this.getCouleur() == Couleur.Blanc) {
            return 'p';
        } else {
            return 'P';
        }
    }

    /**
     * Utilisé pour savoir si le pion est une dame.

     * @return si le pion est une dame.
     */
    public boolean estDame() {
        return false;
    }

    /**
     * Pion.

     * @param pion ee.
     * @return a new pion with the same proprieties.
     */
    public static Pion clone(Pion pion) {
        if (pion != null) {
            return new Pion(pion.couleur);
        } else {
            return null;
        }
    }

    /**
     * La couleur du pion.
     */
    public enum Couleur {
        /**
         * La couleur BLANC.
         */
        Blanc,
        /**
         * La couleur NOIR.
         */
        Noir
    }
}
