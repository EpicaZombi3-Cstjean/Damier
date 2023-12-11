package cstjean.mobile.damier.logique;

/**
 * Un élément qui sera conservé dans l'historique d'un damier.
 *
 * @author Maxime Nobert et Alexandre Lavoie
 */
public class ElementHistorique {
    /**
     * La position initiale de l'entrée.
     */
    private int positionInitiale;

    /**
     * La position finale de l'entrée.
     */
    private int positionFinale;

    /**
     * La couleur du pion ayant joué de l'entrée.
     */
    private final Pion.Couleur couleurPion;

    /**
     * La position de la prise, s'il y en a une. En cas inverse, est égal à -1.
     */
    private int positionPrise = -1;

    /**
     * La si le pion pris est une dame. Est false s'il n'y a pas eu de prise ou si ce n'est pas une dame.
     */
    private boolean priseEstDame;

    /**
     * Si le déplacement a transformé le pion en dame.
     */
    private final boolean transformationDame;

    /**
     * Le constructeur d'ElementHistorique.

     * @param positionInitiale la position du pion qu'on cherche à déplacer.
     * @param positionFinale la destination du pion qu'on veut déplacer.
     * @param couleurPion la couleur du pion qui s'est déplacé.
     * @param positionPrise la position de la prise (normalement, elle serait de la couleur opposée de couleurPion.
     *                      Est égal à -1 si aucune prise n'a eu lieu.
     * @param transformationDame si le pion se transforme en dame.
     * @param priseEstDame si le pion pris est une dame ou non.
     */
    public ElementHistorique(int positionInitiale, int positionFinale, Pion.Couleur couleurPion, int positionPrise,
                             boolean transformationDame, boolean priseEstDame) {

        if (positionInitiale <= 50 && positionInitiale >= 1) {
            this.positionInitiale = positionInitiale;
        }

        if (positionFinale <= 50 && positionFinale >= 1) {
            this.positionFinale = positionFinale;
        }

        this.couleurPion = couleurPion;

        if (positionPrise <= 50 && positionPrise >= 1 || positionPrise == -1) {
            this.positionPrise = positionPrise;

            this.priseEstDame = priseEstDame;
        }

        this.transformationDame = transformationDame;

    }

    /**
     * Donne une string qui représente l'historique du tour (en manoury).

     * @param damier le damier concerné.
     * @return les mouvements (combinés, en manoury).
     */
    public static String getHistoriqueTour(Damier damier) {

        ElementHistorique[] historiqueTour = damier.getHistoriqueTour();

        if (historiqueTour.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        boolean premierDeplacement = true;

        // l'index est inversé car le top de la stack est le début de l'array,
        // mais la stack s'accumule de gauche à droite, si on la lit à l'envers,
        // le début de l'historique est donc inversé

        // historiqueTour.length - 1 serait donc le début du tour du joueur.
        if (historiqueTour[historiqueTour.length - 1].getCouleur() == Pion.Couleur.Noir) {
            stringBuilder.append('(');
        }

        // l'index sera donc inversé ici aussi!!!
        for (int i = historiqueTour.length - 1; i >= 0; i--) {

            if (premierDeplacement) {

                premierDeplacement = false;

                stringBuilder.append(historiqueTour[i].getPositionInitiale());

                if (historiqueTour[i].positionPrise != -1) {

                    stringBuilder.append('×');

                } else {

                    stringBuilder.append('-');

                }

                stringBuilder.append(historiqueTour[i].getPositionFinale());

            } else {

                if (historiqueTour[i].positionPrise != -1) {

                    stringBuilder.append('×');

                }

                stringBuilder.append(historiqueTour[i].getPositionFinale());

            }

        }

        if (historiqueTour[historiqueTour.length - 1].getCouleur() == Pion.Couleur.Noir) {
            stringBuilder.append(')');
        }

        return stringBuilder.toString();
    }

    /**
     * Donne une string qui représente un mouvement unique (en manoury).

     * @param elementHistorique l'élément d'historique concerné.
     * @return la string du mouvement
     */
    public static String getMouvementString(ElementHistorique elementHistorique) {

        StringBuilder stringBuilder = new StringBuilder();

        if (elementHistorique.getCouleur() == Pion.Couleur.Noir) {
            stringBuilder.append('(');
        }

        stringBuilder.append(elementHistorique.positionInitiale);

        stringBuilder.append(
                elementHistorique.positionPrise == -1 ? '-' : '×'
        );

        stringBuilder.append(elementHistorique.positionFinale);

        if (elementHistorique.getCouleur() == Pion.Couleur.Noir) {
            stringBuilder.append(')');
        }

        return stringBuilder.toString();
    }

    public int getPositionInitiale() {
        return positionInitiale;
    }

    public int getPositionFinale() {
        return positionFinale;
    }

    public int getPositionPrise() {
        return positionPrise;
    }

    public boolean getSiPriseEstDame() {
        return priseEstDame;
    }

    public boolean getSiTransformationEnDame() {
        return transformationDame;
    }

    public Pion.Couleur getCouleur() {
        return couleurPion;
    }
}
