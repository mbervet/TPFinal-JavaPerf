package org.polytechtours.javaperformance.tp.paintingants.control;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.Timer;

import org.polytechtours.javaperformance.tp.paintingants.model.CColonie;
import org.polytechtours.javaperformance.tp.paintingants.model.CPainting;

public class PaintingAnts extends java.applet.Applet implements Runnable {
  private static final long serialVersionUID = 1L;

  /**
   * parametres largeur
   */
  private int mLargeur;

  /**
   * parametres hauteur
   */
  private int mHauteur;

  /**
   * l'objet graphique lui meme
   */
  private CPainting mPainting;
  
  private ParameterManager ParamManager;
 
  /**
  * Colony containing the ants
  */
  private CColonie mColony;
  private Thread mApplis, mThreadColony;

  /**
   * Dimensions
   */
  private Dimension mDimension;
  private long mCompteur = 0;
  private Object mMutexCompteur = new Object();
  
  /**
   * le status de thread
   */
  private boolean mPause = false;

  /**
   * Base image
   */
  public BufferedImage mBaseImage;
  private Timer fpsTimer;

  /** Fourmis per second */
  private AtomicLong fpsCounter = new AtomicLong(0);
  /** stocke la valeur du compteur lors du dernier timer */
  private AtomicLong lastFps = new AtomicLong(0);

  /**
   * incrementer le compteur
   */
  public void compteur() {
    synchronized (mMutexCompteur) {
      mCompteur++;
    }
  }

 
  /**
   * Detruire l'applet
   */
  @Override
  public void destroy() {
    // System.out.println(this.getName()+ ":destroy()");

    if (mApplis != null) {
      mApplis = null;
    }
  }

  /**
   * Obtenir l'information Applet
   *
   */
  @Override
  public String getAppletInfo() {
    return "Painting Ants";
  }

  /**
   * Obtenir l'information Applet
   *
   */

  @Override
  public String[][] getParameterInfo() {
    String[][] lInfo = { { "SeuilLuminance", "string", "Seuil de luminance" }, { "Img", "string", "Image" },
        { "NbFourmis", "string", "Nombre de fourmis" }, { "Fourmis", "string",
            "Parametres des fourmis (RGB_deposee)(RGB_suivie)(x,y,direction,taille)(TypeDeplacement,ProbaG,ProbaTD,ProbaD,ProbaSuivre);...;" } };
    return lInfo;
  }

  /**
   * Obtenir l'etat de pause
   *
   */
  final public boolean getPause() {
    return mPause;
  }

  public CPainting getPainting() {
	  return mPainting;
  }
  
  public void IncrementFpsCounter() {
    fpsCounter.incrementAndGet();
  }

  /**
   * Initialisation de l'applet
   *
   */
  @Override
  public void init() {
    URL lFileName;
    URLClassLoader urlLoader = (URLClassLoader) this.getClass().getClassLoader();

    // lecture des parametres de l'applet

    mDimension = getSize();
    mLargeur = mDimension.width;
    mHauteur = mDimension.height;

    mPainting = new CPainting(mDimension, this);
    add(mPainting);

    // lecture de l'image
    lFileName = urlLoader.findResource("images/" + getParameter("Img"));
    try {
      if (lFileName != null) {
        mBaseImage = javax.imageio.ImageIO.read(lFileName);
      }
    } catch (java.io.IOException ex) {
    }

    if (mBaseImage != null) {
      mLargeur = mBaseImage.getWidth();
      mHauteur = mBaseImage.getHeight();
      mDimension.setSize(mLargeur, mHauteur);
      resize(mDimension);
    }

    ParamManager = new ParameterManager(this);
    ParamManager.readParameterFourmis();

    setLayout(null);
  }

  /**
   * Paint the image and all active highlights.
   */
  @Override
  public void paint(Graphics g) {

    if (mBaseImage == null) {
      return;
    }
    g.drawImage(mBaseImage, 0, 0, this);
  }
  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/
  /****************************************************************************/

  /****************************************************************************/
  /**
   * Mettre en pause
   *
   */
  public void pause() {
    mPause = !mPause;
    // if (!mPause)
    // {
    // notify();
    // }
  }

  /**
   * fonction testant l'egalite de deux couleurs
>>>>>>> V2-AjoutLog:src/main/java/org/polytechtours/javaperformance/tp/paintingants/PaintingAnts.java
   */
  @Override
  public void run() {
    // System.out.println(this.getName()+ ":run()");

    int i;
    String lMessage;

    mPainting.init();

    Thread currentThread = Thread.currentThread();

    /*
     * for ( i=0 ; i<mColonie.size() ; i++ ) {
     * ((CFourmi)mColonie.elementAt(i)).start(); }
     */

    mThreadColony.start();

    while (mApplis == currentThread) {
      if (mPause) {
        lMessage = "pause";
      } else {
        synchronized (this) {
          lMessage = "running (" + lastFps + ") ";
        }

        synchronized (mMutexCompteur) {
          mCompteur %= 10000;
          for (i = 0; i < mCompteur / 1000; i++) {
            lMessage += ".";
          }
        }

      }
      showStatus(lMessage);

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        showStatus(e.toString());
      }
    }
  }

/**
   * Lancer l'applet
   */
  @Override
  public void start() {
    // System.out.println(this.getName()+ ":start()");
    mColony = new CColonie(ParamManager.getListFourmis(), this);
    mThreadColony = new Thread(mColony);
    mThreadColony.setPriority(Thread.MIN_PRIORITY);

    fpsTimer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateFPS();
      }
    });
    fpsTimer.setRepeats(true);
    fpsTimer.start();

    showStatus("starting...");
    // Create the thread.
    mApplis = new Thread(this);
    // and let it start running
    mApplis.setPriority(Thread.MIN_PRIORITY);
    mApplis.start();
  }

  /****************************************************************************/
  /**
   * Arreter l'applet
   */
  @Override
  public void stop() {
    showStatus("stopped...");

    fpsTimer.stop();

    // On demande au Thread Colony de s'arreter et on attend qu'il s'arrete
    mColony.pleaseStop();
    try {
      mThreadColony.join();
    } catch (Exception e) {
    }

    mThreadColony = null;
    mApplis = null;
  }

  /**
   * update Fourmis per second
   */
  private void updateFPS() {
    lastFps.compareAndSet(lastFps.get(), fpsCounter.get());
    fpsCounter.compareAndSet(fpsCounter.longValue(), 0);
  }
}
