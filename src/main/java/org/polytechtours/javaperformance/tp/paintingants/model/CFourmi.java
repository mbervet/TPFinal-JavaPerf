package org.polytechtours.javaperformance.tp.paintingants.model;
// package PaintingAnts_v3;
// version : 4.0

import java.awt.Color;
import java.util.Random;

import org.polytechtours.javaperformance.tp.paintingants.control.PaintingAnts;

public class CFourmi {
<<<<<<< HEAD:src/main/java/org/polytechtours/javaperformance/tp/paintingants/model/CFourmi.java
  // Tableau des incrementations a effectuer sur la position des fourmis
  // en fonction de la direction du deplacement
  static private int[][] mIncDirection = new int[8][2];
  // le generateur aleatoire (Random est thread safe donc on la partage)
  private static Random GenerateurAleatoire = new Random();
  // couleur depose par la fourmi
=======

/**
  * Tableau des incrémentations à effectuer sur la position des fourmis
  * en fonction de la direction du deplacement
  */
  private static final int[][] mIncDirection = {
          {0, -1},
          {1, -1},
          {1, 0},
          {1, 1},
          {0, 1},
          {-1, 1},
          {-1, 0},
          {-1, -1}
  };
  /** 
   * le generateur aléatoire (Random est thread safe donc on la partage)
   */ 
  private Random GenerateurAleatoire = new Random();
  /**
   * couleur déposé par la fourmi
   */
>>>>>>> V2-AjoutLog:src/main/java/org/polytechtours/javaperformance/tp/paintingants/CFourmi.java
  private Color mCouleurDeposee;
  /**
   * Luminance Couleur Suivie par la fourmi
   */
  private float mLuminanceCouleurSuivie;
  /**
   * objet graphique sur lequel les fourmis peuvent peindre
   */
  private CPainting mPainting;
<<<<<<< HEAD:src/main/java/org/polytechtours/javaperformance/tp/paintingants/model/CFourmi.java
  // Coordonnees de la fourmi
=======
  /**
   * Coordonées de la fourmi
   */
>>>>>>> V2-AjoutLog:src/main/java/org/polytechtours/javaperformance/tp/paintingants/CFourmi.java
  private int x, y;
  /**
   * Proba d'aller a gauche, en face, a droite, de suivre la couleur
   */
  private float[] mProba = new float[4];
<<<<<<< HEAD:src/main/java/org/polytechtours/javaperformance/tp/paintingants/model/CFourmi.java
  // Numero de la direction dans laquelle la fourmi regarde
  private int mDirection;
  // Taille de la trace de pheromones deposee par la fourmi
  private int mTaille;
  // Pas d'incrementation des directions suivant le nombre de directions
  // allouees a la fourmies
=======
  /**
   * Numéro de la direction dans laquelle la fourmi regarde
   */
  private int mDirection;
  /**
   * Taille de la trace de phéromones déposée par la fourmi
   */
  private int mTaille;
  /**
   * Pas d'incrémentation des directions suivant le nombre de directions
   * allouées à la fourmies
   */
>>>>>>> V2-AjoutLog:src/main/java/org/polytechtours/javaperformance/tp/paintingants/CFourmi.java
  private int mDecalDir;
  /**
   * l'applet
   */
  private PaintingAnts mApplis;
<<<<<<< HEAD:src/main/java/org/polytechtours/javaperformance/tp/paintingants/model/CFourmi.java
  // seuil de luminance pour la detection de la couleur recherchee
  private float mSeuilLuminance;
  // nombre de deplacements de la fourmi
=======
  /**
   * seuil de luminance pour la détection de la couleur recherchée
   */
  private float mSeuilLuminance;
  /**
   * nombre de déplacements de la fourmi
   */
>>>>>>> V2-AjoutLog:src/main/java/org/polytechtours/javaperformance/tp/paintingants/CFourmi.java
  private long mNbDeplacements;

  /**
   * Constructeur 
   * @param pCouleurDeposee
   * @param pCouleurSuivie
   * @param pProbaTD
   * @param pProbaG
   * @param pProbaD
   * @param pProbaSuivre
   * @param pPainting
   * @param pTypeDeplacement
   * @param pInit_x
   * @param pInit_y
   * @param pInitDirection
   * @param pTaille
   * @param pSeuilLuminance
   * @param pApplis
   */
  public CFourmi(Color pCouleurDeposee, Color pCouleurSuivie, float pProbaTD, float pProbaG, float pProbaD,
      float pProbaSuivre, char pTypeDeplacement, float pInit_x, float pInit_y, int pInitDirection,
      int pTaille, float pSeuilLuminance, PaintingAnts pApplis) {

    mCouleurDeposee = pCouleurDeposee;
    mLuminanceCouleurSuivie = 0.2426f * pCouleurDeposee.getRed() + 0.7152f * pCouleurDeposee.getGreen()
        + 0.0722f * pCouleurDeposee.getBlue();
    mApplis = pApplis;
    mPainting = mApplis.getPainting();

    // direction de depart
    mDirection = pInitDirection;

    // taille du trait
    mTaille = pTaille;

    // initialisation des probas
    mProba[0] = pProbaG; // proba d'aller a gauche
    mProba[1] = pProbaTD; // proba d'aller tout droit
    mProba[2] = pProbaD; // proba d'aller a droite
    mProba[3] = pProbaSuivre; // proba de suivre la couleur

    // nombre de directions pouvant être prises : 2 types de deplacement
    // possibles
    if (pTypeDeplacement == 'd') {
      mDecalDir = 2;
    } else {
      mDecalDir = 1;
    }

    mSeuilLuminance = pSeuilLuminance;
    mNbDeplacements = 0;
  }

  /**
   * Fonction de deplacement de la fourmi
   */
  public void deplacer() {
    float tirage, prob1, prob2, prob3, total;
    int[] dir = new int[3];
    int i, j;
    Color lCouleur;

    mNbDeplacements++;

    dir[0] = 0;
    dir[1] = 0;
    dir[2] = 0;

    // le tableau dir contient 0 si la direction concernee ne contient pas la
    // couleur
    // a suivre, et 1 sinon (dir[0]=gauche, dir[1]=tt_droit, dir[2]=droite)
    i = modulo(x + CFourmi.mIncDirection[modulo(mDirection - mDecalDir, 8)][0], mPainting.getLargeur());
    j = modulo(y + CFourmi.mIncDirection[modulo(mDirection - mDecalDir, 8)][1], mPainting.getHauteur());
    if (mApplis.mBaseImage != null) {
      lCouleur = new Color(mApplis.mBaseImage.getRGB(i, j));
    } else {
      lCouleur = new Color(mPainting.getCouleur(i, j).getRGB());
    }
    if (testCouleur(lCouleur)) {
      dir[0] = 1;
    }

    i = modulo(x + CFourmi.mIncDirection[mDirection][0], mPainting.getLargeur());
    j = modulo(y + CFourmi.mIncDirection[mDirection][1], mPainting.getHauteur());
    if (mApplis.mBaseImage != null) {
      lCouleur = new Color(mApplis.mBaseImage.getRGB(i, j));
    } else {
      lCouleur = new Color(mPainting.getCouleur(i, j).getRGB());
    }
    if (testCouleur(lCouleur)) {
      dir[1] = 1;
    }
    i = modulo(x + CFourmi.mIncDirection[modulo(mDirection + mDecalDir, 8)][0], mPainting.getLargeur());
    j = modulo(y + CFourmi.mIncDirection[modulo(mDirection + mDecalDir, 8)][1], mPainting.getHauteur());
    if (mApplis.mBaseImage != null) {
      lCouleur = new Color(mApplis.mBaseImage.getRGB(i, j));
    } else {
      lCouleur = new Color(mPainting.getCouleur(i, j).getRGB());
    }
    if (testCouleur(lCouleur)) {
      dir[2] = 1;
    }

    // tirage d'un nombre aleatoire permettant de savoir si la fourmi va suivre
    // ou non la couleur
    tirage = GenerateurAleatoire.nextFloat();// Math.random();

    // la fourmi suit la couleur
    if (((tirage <= mProba[3]) && ((dir[0] + dir[1] + dir[2]) > 0)) || ((dir[0] + dir[1] + dir[2]) == 3)) {
      prob1 = (dir[0]) * mProba[0];
      prob2 = (dir[1]) * mProba[1];
      prob3 = (dir[2]) * mProba[2];
    }
    // la fourmi ne suit pas la couleur
    else {
      prob1 = (1 - dir[0]) * mProba[0];
      prob2 = (1 - dir[1]) * mProba[1];
      prob3 = (1 - dir[2]) * mProba[2];
    }
    total = prob1 + prob2 + prob3;
    prob1 = prob1 / total;
    prob2 = prob2 / total + prob1;
    prob3 = prob3 / total + prob2;

    // incrementation de la direction de la fourmi selon la direction choisie
    tirage = GenerateurAleatoire.nextFloat();// Math.random();
    if (tirage < prob1) {
      mDirection = modulo(mDirection - mDecalDir, 8);
    } else {
      if (tirage < prob2) {
        /* rien, on va tout droit */
      } else {
        mDirection = modulo(mDirection + mDecalDir, 8);
      }
    }

    x += CFourmi.mIncDirection[mDirection][0];
    y += CFourmi.mIncDirection[mDirection][1];

    x = modulo(x, mPainting.getLargeur());
    y = modulo(y, mPainting.getHauteur());

    // coloration de la nouvelle position de la fourmi
    mPainting.setCouleur(x, y, mCouleurDeposee, mTaille);

    mApplis.IncrementFpsCounter();
  }

/**
  * Retourne le nombre de déplacements de la fourmi
  * @return Le nombre de déplacement de la fourmi
  */ 
  final public long getNbDeplacements() {
    return mNbDeplacements;
  }
 /**
  * Retourne la coordonnée en X de la fourmi
  * @return la coordonnée en X de la fourmi
  */
  final public int getX() {
    return x;
  }

  /**
  * Retourne la coordonnée en Y de la fourmi
  * @return la coordonnée en Y de la fourmi
  */
  final public int getY() {
    return y;
  }

  /**
   * Fcontion de modulo permettant au fourmi de
   * reapparaitre de l autre coté du Canvas lorsque qu'elle sorte de ce dernier
   * @param x valeur
   * @param m
   * @return int
   */
  private int modulo(int x, int m) {
    return (x + m) % m;
  }

  /**
   * fonction testant l'égalité d'une couleur avec la couleur suivie
   * @param pCouleur
   * @return boolean le résultat de test
   */
  private boolean testCouleur(Color pCouleur) {
    boolean lReponse = false;
    float lLuminance;

    /* on calcule la luminance */
    lLuminance = 0.2426f * pCouleur.getRed() + 0.7152f * pCouleur.getGreen() + 0.0722f * pCouleur.getBlue();

    /* test */
    if (Math.abs(mLuminanceCouleurSuivie - lLuminance) < mSeuilLuminance) {
      lReponse = true;
      // System.out.print(x);
    }

    return lReponse;
  }
}
