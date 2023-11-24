package cstjean.mobile.damier.logique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * La classe utilisée pour le damier.
 * Règles bonus : Prise multiple, prise forcée et prise la plus grande forcée.

 * @author Maxime Nobert et Alexandre Lavoie.
 */
public class Damier {
    /**
     * Les pions sur le damier. Accessible seulement via un getter.
     */
    private final HashMap<Integer, Pion> pions = new HashMap<>();
    /**
     * Le nombre de tour pour déterminer quelle couleur peut jouer;.
     */
    private int tourActuel = 1;

    // should be private
    /**
     * L'historique de déplacement, contient plusieurs informations, comme la position initiale du déplacement,
     *      la position finale, la position de la prise (s'il y en a eu une),
     *      si le pion déplacé s'est transformé en dame et si le pion "pris (dans la prise)" est une dame.
     */
    private final Stack<ElementHistorique> historique = new Stack<>();

    /**
     * L'état de la partie en cours.
     */
    private EtatPartie etatPartie;

    /**
     * Le constructeur du damier.
     */
    public Damier() {
        this.etatPartie = EtatPartie.EnCours;
        initialiser();
    }

    /**
     * Getter des pions du damier.
     *
     * @return pions :D.
     */
    public HashMap<Integer, Pion> getPions() {

        HashMap<Integer, Pion> returnedPions = new HashMap<>();

        for (Map.Entry<Integer, Pion> entry : pions.entrySet()) {
            int key = entry.getKey(); // position
            Pion pion = entry.getValue(); // pion
            returnedPions.put(key, pion);
        }
        return returnedPions;
    }

    /**
     * Getter des pions du damier. (sélection couleur)

     * @param couleur la couleur des pions
     * @return pions :D.
     */
    public HashMap<Integer, Pion> getPions(Pion.Couleur couleur) {

        HashMap<Integer, Pion> returnedPions = new HashMap<>();

        for (Map.Entry<Integer, Pion> entry : pions.entrySet()) {
            int key = entry.getKey(); // position
            Pion pion = entry.getValue(); // pion

            if (pion.getCouleur() == couleur) {
                returnedPions.put(key, pion);
            }
        }
        return returnedPions;
    }

    /**
     * Permet d'obtenir le décompte total des pions.

     * @return Le décompte total des pions
     */
    public int getCount() {
        int count = 0;

        for (Map.Entry<Integer, Pion> entry : getPions().entrySet()) {
            int key = entry.getKey(); // position
            if (key > 0 && key < 51) {
                count++;
            }
        }

        return count;
    }

    /**
     * Permet d'obtenir le décompte des pions d'une couleur.

     * @param couleur la couleur visée
     * @return Le décompte des pions de la couleur indiquée
     */
    public int getCount(Pion.Couleur couleur) {
        int count = 0;

        for (Map.Entry<Integer, Pion> entry : getPions(couleur).entrySet()) {
            int key = entry.getKey(); // position
            Pion pion = entry.getValue(); // pion
            if (pion.getCouleur() == couleur && key > 0 && key < 51) {
                count++;
            }
        }

        return count;
    }

    public EtatPartie getEtatPartie() {
        return etatPartie;
    }

    /**
     * Recherche un pion à la position demandée.

     * @param position la position demandée.
     * @return Pion pion. (Pion peut être Dame)
     */
    public Pion findPion(int position) {
        if (pions.get(position) != null) {
            return pions.get(position);
        } else {
            return null;
        }
    }

    /**
     * Ajoute un pion à la position demandée.
     *
     * @param position la position demandée.
     * @param pion     le pion qui sera ajouté.
     */
    public void ajouterPion(int position, Pion pion) {
        if (pions.get(position) == null && pion != null) {
            pions.put(position, pion);
        }
    }

    /**
     * Retire un pion à la position demandée.

     * @param position la position de retrait
     */
    public void retirerPion(int position) {
        if (pions.get(position) != null) {
            pions.remove(position);
        }
    }

    /**
     * Initialise le damier (utilisé pour débuter une partie).
     */
    public void initialiser() {
        for (int i = 1; i <= 20; i++) {
            ajouterPion(i, new Pion(Pion.Couleur.Blanc));
        }

        for (int i = 31; i <= 50; i++) {
            ajouterPion(i, new Pion(Pion.Couleur.Noir));
        }
        tourActuel = 1;
        historique.clear();
        etatPartie = EtatPartie.EnCours;
    }

    /**
     * Vide un damier. (Similaire à initialiser, mais sans qu'il y ait de pions)
     */
    public void vider() {
        pions.clear();
        historique.clear();
        tourActuel = 1;
        etatPartie = EtatPartie.EnCours;
    }

    /**
     * Détermine tous les dépacements possible à partir d'une position.

     * @param position int qui représente la position du pion.
     *
     * @return un array de tous les déplcements possibles.
     */
    public Integer[] getDeplacementsPossibles(int position, boolean doitEtrePrise) {
        // section validation et
        if (!(position >= 1 && position <= 50)) {
            throw new IllegalArgumentException();
        }

        Pion pionJoueur = findPion(position);

        Integer[] deplacementsPossibles = new Integer[0];

        // pour éviter que l'algo crash, il return immédiatement déplacementspossibles, qui est vide...
        if (pionJoueur == null) {
            return deplacementsPossibles;
        }

        ArrayList<Integer[]> listeDePrises = new ArrayList<>();

        Integer[] dernieresPrises = getPrisesFromHistorique(pionJoueur.getCouleur());

        // regarder les prises
        // modifie une liste grâce à la référence des valeurs passés en objets.
        regarderPrises(listeDePrises, new Integer[0], position, pionJoueur, dernieresPrises);

        boolean aAutresPionsPlusDePrises = aAutrePionPlusDePrises(pionJoueur, listeDePrises);

        if (aAutresPionsPlusDePrises) {
            return deplacementsPossibles;
        }


        if (!listeDePrises.isEmpty()) {

            int highestCount = 0;

            ArrayList<Integer> chosenMoves = new ArrayList<>();

            for (Integer[] listeDePrise : listeDePrises) {

                if (listeDePrise.length >= highestCount) {

                    if (listeDePrise.length > highestCount) {
                        // reset les Chosen1stMoves.
                        chosenMoves.clear();
                        highestCount = listeDePrise.length;
                    }

                    Direction directionVersPrise = calculateDirection(position, listeDePrise[0]);

                    // 2 prochaines cases parce que c'est là que le pion va.
                    chosenMoves.add(prochaineCase(listeDePrise[0],
                            directionVersPrise));

                }
            }

            deplacementsPossibles = chosenMoves.toArray(new Integer[0]); // unsure si ça marche


        } else {
            if (!doitEtrePrise) {
                // pas de prise, donc déplacement "normal"
                deplacementsPossibles = getDeplacementsNormauxPossibles(position, pionJoueur);
            }
        }

        return deplacementsPossibles;
    }


    /**
     *
     */
    private Integer[] getDeplacementsNormauxPossibles(int position, Pion pionJoueur) {
        ArrayList<Integer> movesPossibles = new ArrayList<>();

        if (!pionJoueur.estDame()) {

            if (pionJoueur.getCouleur() == Pion.Couleur.Blanc) {

                int nextpos = prochaineCase(position, Direction.BasDroite);
                if (nextpos != -1 && findPion(nextpos) == null) {
                    movesPossibles.add(nextpos);
                }

                nextpos = prochaineCase(position, Direction.BasGauche);
                if (nextpos != -1 && findPion(nextpos) == null) {
                    movesPossibles.add(nextpos);
                }

            } else {

                int nextpos = prochaineCase(position, Direction.HautDroite);
                if (nextpos != -1 && findPion(nextpos) == null) {
                    movesPossibles.add(nextpos);
                }

                nextpos = prochaineCase(position, Direction.HautGauche);
                if (nextpos != -1 && findPion(nextpos) == null) {
                    movesPossibles.add(nextpos);
                }

            }

            return movesPossibles.toArray(new Integer[0]);

        } else {

            for (Direction direction : Direction.values()) {

                int nextPos = prochaineCase(position, direction);

                for (;;) {

                    if ((nextPos != -1 && findPion(nextPos) == null)) {
                        movesPossibles.add(nextPos);

                        nextPos = prochaineCase(nextPos, direction); // continuer à regarder + loin
                    } else {
                        break;
                    }
                }

            }

            return movesPossibles.toArray(new Integer[0]);

        }
    }


    private boolean aAutrePionPlusDePrises(Pion pionJoueur,
                                           ArrayList<Integer[]> listeDePrises) {
        /* Force la plus grande prise */
        HashMap<Integer, Pion> pionsCouleur = getPions(pionJoueur.getCouleur());

        // essaie d'empêcher le pion de jouer si un autre pion peut faire plus de prises
        for (Map.Entry<Integer, Pion> entry : pionsCouleur.entrySet()) {
            int key = entry.getKey(); // position
            Pion pion = entry.getValue(); // pion

            if (pion != pionJoueur) {
                ArrayList<Integer[]> prisesIntermediaire = new ArrayList<>();
                regarderPrises(prisesIntermediaire, new Integer[0], key, pion, new Integer[0]);

                // si un autre pion peut faire une meilleure prise
                if (prisesIntermediaire.size() > listeDePrises.size()) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Cette fonction récursive utilise l'information qu'on lui envoie afin de voir s'il est possible pour
     * un Pion/Dame X de faire des prises. Elle update la liste au fur et à mesure.

     * @param listesDePrisesPossibles la liste complète des possibilités en matière de liste de prises.
     * @param prisesActuelle la liste complète des possibilités en matière de liste de prises.
     * @param position la position de début de la prise (initiale ou intermédiaire).
     * @param pion le pion qui fait les prises.
     */
    private void regarderPrises(ArrayList<Integer[]> listesDePrisesPossibles,
                                       Integer[] prisesActuelle, int position, Pion pion, Integer[] ignoreList) {

        if (!(position >= 1 && position <= 50)) {

            return;

        }

        if (!pion.estDame()) {

            for (Direction direction : Direction.values()) {

                int nextPos = prochaineCase(position, direction);

                boolean estDejaPris = estPositionDansArray(nextPos, prisesActuelle);

                if (nextPos == -1 || estDejaPris || estPositionDansArray(nextPos, ignoreList)) {
                    continue; // skip cette direction-là
                }

                Pion pionTrouve = findPion(nextPos);

                if (pionTrouve != null && pionTrouve.getCouleur() != pion.getCouleur()) {

                    int positionApresPrise = prochaineCase(nextPos, direction);

                    if (findPion(positionApresPrise) == null && positionApresPrise != -1) {

                        // le nouvel array
                        Integer[] neoPrisesActuelles = new Integer[prisesActuelle.length + 1];

                        for (int i = 0; i < prisesActuelle.length; i++) {
                            neoPrisesActuelles[i] = prisesActuelle[i];
                        }
                        neoPrisesActuelles[neoPrisesActuelles.length - 1] = nextPos;

                        // ajout du nouvel array dans la liste.
                        listesDePrisesPossibles.add(neoPrisesActuelles);

                        regarderPrises(listesDePrisesPossibles, neoPrisesActuelles,
                                positionApresPrise, pion, ignoreList);

                    }

                }

            }

        } else {

            for (Direction direction : Direction.values()) {

                int nextPos = prochaineCase(position, direction);

                for (;;) {

                    boolean estDejaPris = estPositionDansArray(nextPos, prisesActuelle);

                    if (nextPos == -1 || estDejaPris || estPositionDansArray(nextPos, ignoreList)) {
                        break; // skip
                    }

                    Pion pionTrouve = findPion(nextPos);

                    if (pionTrouve != null && pionTrouve.getCouleur() != pion.getCouleur()) {

                        int positionApresPrise = prochaineCase(nextPos, direction);

                        if (findPion(positionApresPrise) == null && positionApresPrise != -1) {

                            // le nouvel array
                            Integer[] neoPrisesActuelles = new Integer[prisesActuelle.length + 1];
                            for (int i = 0; i < prisesActuelle.length; i++) {
                                neoPrisesActuelles[i] = prisesActuelle[i];
                            }
                            neoPrisesActuelles[neoPrisesActuelles.length - 1] = nextPos;

                            // ajout du nouvel array dans la liste.
                            listesDePrisesPossibles.add(neoPrisesActuelles);

                            regarderPrises(listesDePrisesPossibles, neoPrisesActuelles,
                                    positionApresPrise, pion, ignoreList);
                        } else {
                            break;
                        }
                    } else if (pionTrouve != null && pionTrouve.getCouleur() == pion.getCouleur()) {
                        break;
                    }
                    nextPos = prochaineCase(nextPos, direction);

                }
            }
        }
    }

    /**
     * Cette fonction permet de regarder une case dans 4 directions.

     * @param position la position où la recherche a commencé.
     * @param direction la direction dans laquelle la recherche sera faite. (Combinaison Haut/Bas avec Gauche/Droite).
        pris dans une prise... (peut-être que je devrais le retirer et placer la logique à un autre endroit... -AL)

     * @return le pion qu'il a trouvé dans la direction demandée. Si la position retournée est -1, celà signifie
     *     que la nouvelle position est invalide (sortir du damier, par exemple).
     */
    private int prochaineCase(int position, Direction direction) {

        int nouvellePosition = -1;

        if (direction == Direction.BasGauche) {

            if (getRow(position) % 2 == 1) {
                nouvellePosition = (position + 5); // à vérifier.
            } else {

                if (getColumn(position) != 1) {

                    nouvellePosition = (position + 4); // à vérifier.

                }
            }

        } else if (direction == Direction.BasDroite) {

            if (getRow(position) % 2 == 1) {

                if (getColumn(position) != 5) {

                    nouvellePosition = (position + 6); // à vérifier.

                }

            } else {
                nouvellePosition = (position + 5); // à vérifier.

            }

        } else if (direction == Direction.HautGauche) {

            if (getRow(position) % 2 == 1) {
                nouvellePosition = (position - 5); // à vérifier.
            } else {

                if (getColumn(position) != 1) {

                    nouvellePosition = (position - 6); // à vérifier.

                }
            }

        } else if (direction == Direction.HautDroite) {

            if (getRow(position) % 2 == 1) {

                if (getColumn(position) != 5) {

                    nouvellePosition = (position - 4); // à vérifier.

                }
            } else {
                nouvellePosition = (position - 5); // à vérifier.
            }
        }

        if ((direction == Direction.HautGauche || direction == Direction.HautDroite) && getRow(position) == 1) {
            nouvellePosition = -1;
        }
        if ((direction == Direction.BasGauche || direction == Direction.BasDroite) && getRow(position) == 10) {
            nouvellePosition = -1;
        }
        return nouvellePosition;
    }

    /**
     * get la rangée d'une position.

     * @param position la position sur laquelle on obtient l'info.

     * @return la colonne.
     */
    public static int getRow(int position) {
        return 1 + (position - 1) / 5;
    }

    /**
     * get la colonne d'une position.

     * @param position la position sur laquelle on obtient l'info.

     * @return la colonne.
     */
    public static int getColumn(int position) {
        return position % 5 == 0 ? 5 : position % 5;
    }

    /**
     * Cette fonction détermine si la position fait partie d'un array.

     * @param position la position mentionnée.
     * @param array l'array mentionné.

     * @return un boolean déterminant si la position existe.
     */
    public static boolean estPositionDansArray(int position, Integer[] array) {

        boolean estDedans = false;

        for (Integer integer : array) {
            if (position == integer) {
                estDedans = true;
                break;
            }
        }

        return estDedans;
    }

    /**
     * Trouve la couleur du joueur qui doit jouer.

     * @return une couleur qui correspond à la couleur du joueur en cours
     */
    public Pion.Couleur getTourJoueur() {

        if (tourActuel % 2 == 0) {
            return Pion.Couleur.Noir;
        }

        return Pion.Couleur.Blanc;
    }

    /**
     * Déplace un pion.

     * @param positionInitiale la position du pion avant de le délacer.
     * @param positionFinale la position du pion après le déplacement.
     */
    public void deplacerPion(int positionInitiale, int positionFinale) {

        // regarde le pion.
        Pion pionJoueur = findPion(positionInitiale);

        if (!(positionInitiale >= 1 && positionInitiale <= 50)) {
            throw new IllegalArgumentException();
        }

        if (!(positionFinale >= 1 && positionFinale <= 50)) {
            throw new IllegalArgumentException();
        }

        if (pionJoueur == null) {
            return; // pas de pion à déplacer... donc ouin
        }

        Pion.Couleur tour = getTourJoueur();

        // vérifie si le joueur peut jouer.
        if (tour != pionJoueur.getCouleur() || getEtatPartie() != EtatPartie.EnCours) {
            return; // joueur ne peut pas jouer.
        }


        Integer[] deplacementsPossibles;

        if (!historique.empty() &&
                historique.peek().getCouleur() == pionJoueur.getCouleur()) {
            // si prise multiple

            if (!(historique.peek().getPositionFinale() == positionInitiale)) {
                // si le dernier pion joué n'est pas le même que lui qu'on essaie de déplacer right now.
                return; // car le joueur ne devrait pas pouvoir jouer.
            }
            // prise forcée
            deplacementsPossibles = getDeplacementsPossibles(positionInitiale, true);

        } else {

            deplacementsPossibles = getDeplacementsPossibles(positionInitiale, false);

        }

        boolean hasPosition = estPositionDansArray(positionFinale, deplacementsPossibles);
        if (hasPosition) {
            // c'est en ce moment-ci que le "déplacement" du pion se fait

            ajouterPion(positionFinale, pionJoueur);

            // regarde s'il y a eu une prise lors de ce tours-ci
            int positionPrise = prochaineCase(positionFinale, calculateDirection(positionFinale, positionInitiale));
            if (findPion(positionPrise) == null || findPion(positionPrise).getCouleur() == pionJoueur.getCouleur()) {
                positionPrise = -1;
            }

            ElementHistorique newElement = new ElementHistorique(
                    positionInitiale,
                    positionFinale,
                    pionJoueur.getCouleur(),
                    positionPrise,
                    false, // sera changé plus tard si c'est le cas
                    positionPrise != -1 && findPion(positionPrise).estDame()
            );

            historique.push(newElement);

            Integer[] deplacementsPossiblesViaPosFinale = getDeplacementsPossibles(positionFinale, true);

            if (deplacementsPossiblesViaPosFinale.length == 0 || historique.peek().getPositionPrise() == -1) {
                /* FIN DE TOUR!!!!! */

                boolean transformationEnDame; // faux par défaut.

                // regarde si le pion peut se transformer en dame
                // si !pionJoueur.estDame et (pion noir à la row 1 ou pion blanc à la row 10)
                transformationEnDame = !pionJoueur.estDame() &&
                        (
                                (getRow(positionFinale) == 1 && pionJoueur.getCouleur() == Pion.Couleur.Noir) ||
                                (getRow(positionFinale) == 10 && pionJoueur.getCouleur() == Pion.Couleur.Blanc)
                        );

                if (transformationEnDame) {
                    pionJoueur = new Dame(pionJoueur.getCouleur());
                    retirerPion(positionFinale);
                    ajouterPion(positionFinale, pionJoueur);

                    newElement = new ElementHistorique(newElement.getPositionInitiale(),
                            newElement.getPositionFinale(),
                            newElement.getCouleur(),
                            newElement.getPositionPrise(),
                            true,
                            positionPrise != -1 && findPion(positionPrise).estDame()
                    );
                    historique.pop();
                    historique.push(newElement);
                }

                Integer[] prisesEffectuees = getPrisesFromHistorique(pionJoueur.getCouleur());

                // le nettoyage final (enlève les pions pris.)
                for (Integer posPrise : prisesEffectuees) {
                    if (posPrise != -1 && findPion(posPrise) != null &&
                            findPion(posPrise).getCouleur() != pionJoueur.getCouleur()) {
                        retirerPion(posPrise); // retire tous les pions qui ont étés pris dans la prise.
                    }
                }

                tourActuel = tourActuel + 1;

                verifierSiPartieTerminee();

            }
            // sinon, le joueur peut rejouer, donc la transformation en dame
            //      devrait être impossible. donc on ne la modifie pas.

            retirerPion(positionInitiale);
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Set si la partie est terminée ou pas .
     */
    private void verifierSiPartieTerminee() {

        HashMap<Integer, Pion> pionsCouleur = getPions(getTourJoueur());

        HashMap<Integer, Pion> pions = getPions();

        if (pionsCouleur.isEmpty()) {

            if (getTourJoueur() == Pion.Couleur.Blanc) {

                etatPartie = EtatPartie.NoirGagne;

            } else {

                etatPartie = EtatPartie.BlancGagne;

            }

        }

        boolean joueurPeutJouer = false;

        for (Map.Entry<Integer, Pion> entry : pions.entrySet()) {
            int key = entry.getKey(); // position
            Pion pion = entry.getValue(); // pion

            if (pion != null
                    && getDeplacementsPossibles(key, false).length > 0) {

                joueurPeutJouer = true;
            }
        }

        if (!joueurPeutJouer) {
            etatPartie = getTourJoueur() == Pion.Couleur.Blanc ? EtatPartie.NoirGagne : EtatPartie.BlancGagne;
        }
    }

    /**
     * Cette fonction permet au joueur de retourner à l'arrière.
     */
    public void retourArriere() {
        if (historique.isEmpty()) {
            return;
        }

        ElementHistorique retour = historique.peek();
        Pion p = findPion(retour.getPositionFinale());

        Pion.Couleur couleurInverse = retour.getCouleur() ==
                Pion.Couleur.Blanc ? Pion.Couleur.Noir : Pion.Couleur.Blanc;

        // Si le pion s'était transformé en dame, le change en pion AVANT de
        // revenir à sa position initiale
        if (retour.getSiTransformationEnDame()) {
            retirerPion(retour.getPositionFinale());
            p = new Pion(retour.getCouleur());
            ajouterPion(retour.getPositionFinale(), p);
        }

        // Remet le pion OU la dame a sa position initiale
        if (p.estDame()) {
            retirerPion(retour.getPositionFinale());
            ajouterPion(retour.getPositionInitiale(), new Dame(retour.getCouleur()));
        } else {
            retirerPion(retour.getPositionFinale());
            ajouterPion(retour.getPositionInitiale(), new Pion(retour.getCouleur()));
        }

        if (getTourJoueur() == couleurInverse) {

            ElementHistorique[] elements = getHistoriqueTour();

            for (ElementHistorique element : elements) {

                if (element.getPositionPrise() != -1 && findPion(element.getPositionPrise()) == null) {

                    ajouterPion(element.getPositionPrise(),
                            element.getSiPriseEstDame() ? new Dame(couleurInverse) :
                                    new Pion(couleurInverse)
                    );
                }
            }

            tourActuel -= 1;
        }

        historique.pop();
    }

    /**
     * Permet d'obtenir les derniers éléments de l'historique (selon la dernière entrée).

     * @return les derniers éléments de l'historique (en ordre inverse).
     */
    public ElementHistorique[] getHistoriqueTour() {
        if (!historique.empty()) {

            Pion.Couleur couleur = historique.peek().getCouleur();

            // si l'historique est vide ou que le dernier déplacement est fait par le même joueur.

            ArrayList<ElementHistorique> derniersElementsHistorique = new ArrayList<>();

            // note : puisque c'est lu de droite à gauche, l'historique retourné sera À L'ENVERS!!!
            for (int i = historique.size() - 1; i >= 0 && historique.get(i).getCouleur() == couleur; i--) {

                derniersElementsHistorique.add(historique.get(i));

            }

            return derniersElementsHistorique.toArray(new ElementHistorique[0]);
        }
        return new ElementHistorique[0];
    }

    /**
     * Permet d'obtenir les prises du dernier tour via l'historique.

     * @param couleur la couleur.
     * @return les prises du dernier tour
     */
    public Integer[] getPrisesFromHistorique(Pion.Couleur couleur) {

        if (!historique.empty() &&
                historique.peek().getCouleur() == couleur) {
            // si l'historique est vide ou que le dernier déplacement est fait par le même joueur.

            ArrayList<Integer> derniersDeplacementsPrise = new ArrayList<>();

            for (int i = historique.size() - 1; i >= 0 && historique.get(i).getCouleur() == couleur; i--) {

                derniersDeplacementsPrise.add(historique.get(i).getPositionPrise());

            }

            return derniersDeplacementsPrise.toArray(new Integer[0]);

        }

        return new Integer[0];
    }

    /**
     * Calcule la direction de position.

     * @param positionToLookAt la position à laquelle positionInitiale essaiera de pointer.
     * @param positionInitiale la position initial.
     * @return une direction.
     */
    private static Direction calculateDirection(int positionInitiale, int positionToLookAt) {

        if (getRow(positionInitiale) > getRow(positionToLookAt)) {
            if (getRow(positionInitiale) % 2 == 1) {
                if (getColumn(positionInitiale) >= getColumn(positionToLookAt)) {

                    return Direction.HautGauche;

                } else {

                    return Direction.HautDroite;

                }

            } else {

                if (getColumn(positionInitiale) > getColumn(positionToLookAt)) {

                    return Direction.HautGauche;

                } else {

                    return Direction.HautDroite;

                }

            }

        } else {

            if (getRow(positionInitiale) % 2 == 1) {

                if (getColumn(positionInitiale) >= getColumn(positionToLookAt)) {

                    return Direction.BasGauche;

                } else {

                    return Direction.BasDroite;

                }

            } else {

                if (getColumn(positionInitiale) > getColumn(positionToLookAt)) {

                    return Direction.BasGauche;

                } else {

                    return Direction.BasDroite;

                }
            }
        }
    }

    /**
     * Ancienne fonction d'affichage de damier. Le prof ne voulait pas qu'on l'enlève.

     * @return la représentation en caractères.
     */
    public String getRepresentation() {
        StringBuilder sb = new StringBuilder();

        boolean estImpair = true;

        for (int i = 1; i <= 50; i++) {
            if (estImpair) {
                sb.append("-");
            }

            if (findPion(i) == null) {
                sb.append("-");
            } else {
                sb.append(findPion(i).getRepresentation());
            }

            if (!estImpair) {
                sb.append("-");
            }

            if (i % 5 == 0) {
                sb.append("\n");
                estImpair = !estImpair;
            }
        }
        return sb.toString();
    }

    /**
     * Les directions dans laquelles les pions peuvent aller.
     */
    public enum Direction {
        /**
         * Haut-gauche.
         */
        HautGauche,
        /**
         * Haut-droite.
         */
        HautDroite,
        /**
         * Haut-gauche.
         */
        BasGauche,
        /**
         * Bas-droite.
         */
        BasDroite,
    }

    /**
     * Les différents états de partie qui existent.
     */
    public enum EtatPartie {

        /**
         * Partie est en cours.
         */
        EnCours,

        /**
         * Blanc a gagné.
         */
        BlancGagne,

        /**
         * Noir a gagné.
         */
        NoirGagne

    }
}