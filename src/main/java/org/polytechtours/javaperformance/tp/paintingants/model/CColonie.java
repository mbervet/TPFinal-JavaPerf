package org.polytechtours.javaperformance.tp.paintingants.model;

/*
 * CColonie.java
 *
 * Created on 11 avril 2007, 16:35
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
import java.util.Vector;

import org.polytechtours.javaperformance.tp.paintingants.control.PaintingAnts;

public class CColonie implements Runnable {

  private Boolean mContinue = Boolean.TRUE;
  private Vector<CFourmi> mColonie;
  private PaintingAnts mApplis;

  /**
   * Créer une nouvelle instance de CColonie
   * @param pColonie
   * @param pApplis
   */
  public CColonie(Vector<CFourmi> pColonie, PaintingAnts pApplis) {
    mColonie = pColonie;
    mApplis = pApplis;
  }

  /**
   * Arrêter les threads 
   */
  public void pleaseStop() {
    mContinue = false;
  }

  @Override
  public void run() {
    while (mContinue == true) {
      if (!mApplis.getPause()) {
        for (int i = 0; i < mColonie.size(); i++) {
          mColonie.get(i).deplacer();
          mApplis.compteur();
        }
      } else {
        /*
         * try { Thread.sleep(100); } catch (InterruptedException e) { break; }
         */

      }
    }
  }

}
