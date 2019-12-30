package org.polytechtours.javaperformance.tp.paintingants;
// package PaintingAnts_v2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

// version : 2.0

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <p>
 * Titre : Painting Ants
 * </p>
 * <p>
 * Description :
 * </p>
 * <p>
 * Copyright : Copyright (c) 2003
 * </p>
 * <p>
 * Société : Equipe Réseaux/TIC - Laboratoire d'Informatique de l'Université de
 * Tours
 * </p>
 *
 * @author Nicolas Monmarché
 * @version 1.0
 */

public class CPainting extends Canvas implements MouseListener {
	private static final long serialVersionUID = 1L;
	/**
	 * matrice servant pour le produit de convolution
	 */
//  static private float[][] mMatriceConv9 = new float[3][3];
//  static private float[][] mMatriceConv25 = new float[5][5];
//  static private float[][] mMatriceConv49 = new float[7][7];
	/**
	 * la matrice de convolution : lissage moyen sur 9 cases 
	 */
	private static final float[][] mMatriceConv9 = { 
			{ 1 / 16f, 2 / 16f, 1 / 16f }, 
			{ 2 / 16f, 4 / 16f, 2 / 16f },
			{ 1 / 16f, 2 / 16f, 1 / 16f } 
		};
	/**
	 * la matrice de convolution : lissage moyen sur 25 cases 
	 */
	private static final float[][] mMatriceConv25 = { 
			{ 1 / 44f, 1 / 44f, 2 / 44f, 1 / 44f, 1 / 44f },
			{ 1 / 44f, 2 / 44f, 3 / 44f, 2 / 44f, 1 / 44f },
			{ 2 / 44f, 3 / 44f, 4 / 44f, 3 / 44f, 2 / 44f },
			{ 1 / 44f, 2 / 44f, 3 / 44f, 2 / 44f, 1 / 44f }, 
			{ 1 / 44f, 1 / 44f, 2 / 44f, 1 / 44f, 1 / 44f } 
		};
	/**
	 * la matrice de convolution : lissage moyen sur 49 cases 
	 */
	private static final float[][] mMatriceConv49 = {
			{ 1 / 128f, 1 / 128f, 2 / 128f, 2 / 128f, 2 / 128f, 1 / 128f, 1 / 128f },
			{ 1 / 128f, 2 / 128f, 3 / 128f, 4 / 128f, 3 / 128f, 2 / 128f, 1 / 128f },
			{ 2 / 128f, 3 / 128f, 4 / 128f, 5 / 128f, 4 / 128f, 3 / 128f, 2 / 128f },
			{ 2 / 128f, 4 / 128f, 5 / 128f, 8 / 128f, 5 / 128f, 4 / 128f, 2 / 128f },
			{ 2 / 128f, 3 / 128f, 4 / 128f, 5 / 128f, 4 / 128f, 3 / 128f, 2 / 128f },
			{ 1 / 128f, 2 / 128f, 3 / 128f, 4 / 128f, 3 / 128f, 2 / 128f, 1 / 128f },
			{ 1 / 128f, 1 / 128f, 2 / 128f, 2 / 128f, 2 / 128f, 1 / 128f, 1 / 128f } 
		};

	/**
	 * Objet de type Graphics permettant de manipuler l'affichage du Canvas
	 */
	private Graphics mGraphics;

	/**
	 * Objet ne servant que pour les bloc synchronized pour la manipulation du
	 * tableau des couleurs
	 */
	private Object mMutexCouleurs = new Object();

	/**
	 * tableau des couleurs, il permert de conserver en memoire l'état de chaque
	 * pixel du canvas, ce qui est necessaire au deplacemet des fourmi il sert aussi
	 * pour la fonction paint du Canvas
	 */
	private Color[][] mCouleurs;

	/**
	 * couleur du fond
	 */
	private Color mCouleurFond = new Color(255, 255, 255);

	/**
	 * dimensions
	 */
	private Dimension mDimension = new Dimension();

	/**
	 * 
	 */
	private PaintingAnts mApplis;

	/**
	 * 
	 */
	private boolean mSuspendu = false;

	/**
	 * Constructeur de la classe
	 * 
	 * @param pDimension
	 * @param pApplis
	 */
	public CPainting(Dimension pDimension, PaintingAnts pApplis) {
		int i, j;
		addMouseListener(this);

		mApplis = pApplis;

		mDimension = pDimension;
		setBounds(new Rectangle(0, 0, mDimension.width, mDimension.height));

		this.setBackground(mCouleurFond);

		// initialisation de la matrice des couleurs
		mCouleurs = new Color[mDimension.width][mDimension.height];
		synchronized (mMutexCouleurs) {
			for (i = 0; i != mDimension.width; i++) {
				for (j = 0; j != mDimension.height; j++) {
					mCouleurs[i][j] = new Color(mCouleurFond.getRed(), mCouleurFond.getGreen(), mCouleurFond.getBlue());
				}
			}
		}

	}

	/**
	 * Cette fonction renvoie la couleur d'une case
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	final public Color getCouleur(int x, int y) {
		synchronized (mMutexCouleurs) {
			return mCouleurs[x][y];
		}
	}

	/**
	 * Cette fonction renvoie la dimension de la peinture
	 * 
	 * @return la dimension
	 */
	final public Dimension getDimension() {
		return mDimension;
	}

	/**
	 * Cette fonction renvoie la hauteur de la peinture
	 * 
	 * @return la hauteur
	 */
	final public int getHauteur() {
		return mDimension.height;
	}

	/**
	 * Cette fonction renvoie la hauteur de la peinture
	 * 
	 * @return la hauteur
	 */
	final public int getLargeur() {
		return mDimension.width;
	}

	/**
	 * Initialise le fond a la couleur blanche et initialise le tableau des couleurs
	 * avec la couleur blanche
	 */
	public void init() {
		int i, j;
		mGraphics = getGraphics();
		synchronized (mMutexCouleurs) {
			mGraphics.clearRect(0, 0, mDimension.width, mDimension.height);

			// initialisation de la matrice des couleurs

			for (i = 0; i != mDimension.width; i++) {
				for (j = 0; j != mDimension.height; j++) {
					mCouleurs[i][j] = new Color(mCouleurFond.getRed(), mCouleurFond.getGreen(), mCouleurFond.getBlue());
				}
			}
		}
		mSuspendu = false;
	}

	/**
	 * Action quand on clique sur la peinture
	 */
	public void mouseClicked(MouseEvent pMouseEvent) {
		pMouseEvent.consume();
		if (pMouseEvent.getButton() == MouseEvent.BUTTON1) {
			// double clic sur le bouton gauche = effacer et recommencer
			if (pMouseEvent.getClickCount() == 2) {
				init();
			}
			// simple clic = suspendre les calculs et l'affichage
			mApplis.pause();
		} else {
			// bouton du milieu (roulette) = suspendre l'affichage mais
			// continuer les calculs
			if (pMouseEvent.getButton() == MouseEvent.BUTTON2) {
				suspendre();
			} else {
				// clic bouton droit = effacer et recommencer
				// case pMouseEvent.BUTTON3:
				init();
			}
		}
	}

	/****************************************************************************/
	public void mouseEntered(MouseEvent pMouseEvent) {
	}

	/****************************************************************************/
	public void mouseExited(MouseEvent pMouseEvent) {
	}

	/****************************************************************************/
	public void mousePressed(MouseEvent pMouseEvent) {

	}

	/****************************************************************************/
	public void mouseReleased(MouseEvent pMouseEvent) {
	}

	/**
	 * Surcharge de la fonction qui est appelé lorsque le composant doit être
	 * redessiné
	 * 
	 * @param pGraphics
	 */
	@Override
	public void paint(Graphics pGraphics) {
		int i, j;

		synchronized (mMutexCouleurs) {
			for (i = 0; i < mDimension.width; i++) {
				for (j = 0; j < mDimension.height; j++) {
					pGraphics.setColor(mCouleurs[i][j]);
					pGraphics.fillRect(i, j, 1, 1);
				}
			}
		}
	}


	/**
	 * Cette fonction va colorer le pixel correspondant 
	 * et mettre a jour le tabmleau des couleurs
	 * {@value #mMatriceConv9}
	 * {@value #mMatriceConv25}
	 * {@value #mMatriceConv49}
	 * @param x coordonnées de l'axe des x
	 * @param y coordonnées de l'axe des y
	 * @param c Color d'un pixel
	 * @param pTaille
	 */
	public void setCouleur(int x, int y, Color c, int pTaille) {
		int i, j, k, l, m, n;
		float R, G, B;
		Color lColor;

		synchronized (mMutexCouleurs) {
			if (!mSuspendu) {
				// on colorie la case sur laquelle se trouve la fourmi
				mGraphics.setColor(c);
				mGraphics.fillRect(x, y, 1, 1);
			}

			mCouleurs[x][y] = c;

			// on fait diffuser la couleur :
			switch (pTaille) {
			case 0:
				// on ne fait rien = pas de diffusion
				break;
			case 1:
				// produit de convolution discrete sur 9 cases
				for (i = 0; i < 3; i++) {
					for (j = 0; j < 3; j++) {
						R = G = B = 0f;

						for (k = 0; k < 3; k++) {
							for (l = 0; l < 3; l++) {
								m = (x + i + k - 2 + mDimension.width) % mDimension.width;
								n = (y + j + l - 2 + mDimension.height) % mDimension.height;
								R += CPainting.mMatriceConv9[k][l] * mCouleurs[m][n].getRed();
								G += CPainting.mMatriceConv9[k][l] * mCouleurs[m][n].getGreen();
								B += CPainting.mMatriceConv9[k][l] * mCouleurs[m][n].getBlue();
							}
						}
						lColor = new Color((int) R, (int) G, (int) B);

						mGraphics.setColor(lColor);

						m = (x + i - 1 + mDimension.width) % mDimension.width;
						n = (y + j - 1 + mDimension.height) % mDimension.height;
						mCouleurs[m][n] = lColor;
						if (!mSuspendu) {
							mGraphics.fillRect(m, n, 1, 1);
						}
					}
				}
				break;
			case 2:
				// produit de convolution discrete sur 25 cases
				for (i = 0; i < 5; i++) {
					for (j = 0; j < 5; j++) {
						R = G = B = 0f;

						for (k = 0; k < 5; k++) {
							for (l = 0; l < 5; l++) {
								m = (x + i + k - 4 + mDimension.width) % mDimension.width;
								n = (y + j + l - 4 + mDimension.height) % mDimension.height;
								R += CPainting.mMatriceConv25[k][l] * mCouleurs[m][n].getRed();
								G += CPainting.mMatriceConv25[k][l] * mCouleurs[m][n].getGreen();
								B += CPainting.mMatriceConv25[k][l] * mCouleurs[m][n].getBlue();
							}
						}
						lColor = new Color((int) R, (int) G, (int) B);
						mGraphics.setColor(lColor);
						m = (x + i - 2 + mDimension.width) % mDimension.width;
						n = (y + j - 2 + mDimension.height) % mDimension.height;

						mCouleurs[m][n] = lColor;
						if (!mSuspendu) {
							mGraphics.fillRect(m, n, 1, 1);
						}

					}
				}
				break;
			case 3:
				// produit de convolution discrete sur 49 cases
				for (i = 0; i < 7; i++) {
					for (j = 0; j < 7; j++) {
						R = G = B = 0f;

						for (k = 0; k < 7; k++) {
							for (l = 0; l < 7; l++) {
								m = (x + i + k - 6 + mDimension.width) % mDimension.width;
								n = (y + j + l - 6 + mDimension.height) % mDimension.height;
								R += CPainting.mMatriceConv49[k][l] * mCouleurs[m][n].getRed();
								G += CPainting.mMatriceConv49[k][l] * mCouleurs[m][n].getGreen();
								B += CPainting.mMatriceConv49[k][l] * mCouleurs[m][n].getBlue();
							}
						}
						lColor = new Color((int) R, (int) G, (int) B);
						mGraphics.setColor(lColor);
						m = (x + i - 3 + mDimension.width) % mDimension.width;
						n = (y + j - 3 + mDimension.height) % mDimension.height;

						mCouleurs[m][n] = lColor;
						if (!mSuspendu) {
							mGraphics.fillRect(m, n, 1, 1);
						}

					}
				}
				break;
			}// end switch
		}
	}

	/**
	 * Cette fonction change l'état de suspension
	 */
	public void suspendre() {
		mSuspendu = !mSuspendu;
		if (!mSuspendu) {
			repaint();
		}
	}
}
