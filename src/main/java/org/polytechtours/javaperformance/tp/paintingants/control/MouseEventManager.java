package org.polytechtours.javaperformance.tp.paintingants.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.polytechtours.javaperformance.tp.paintingants.model.CPainting;

public class MouseEventManager implements MouseListener{

	private CPainting painting;
	
	public MouseEventManager(CPainting painting)
	{
		this.painting = painting;
	}
	
	@Override
	public void mouseClicked(MouseEvent pMouseEvent) {
		pMouseEvent.consume();
	    if (pMouseEvent.getButton() == MouseEvent.BUTTON1) {
	      // double clic sur le bouton gauche = effacer et recommencer
	      if (pMouseEvent.getClickCount() == 2) {
	    	  painting.init();
	      }
	      // simple clic = suspendre les calculs et l'affichage
	      painting.Pause();
	    } else {
	      // bouton du milieu (roulette) = suspendre l'affichage mais
	      // continuer les calculs
	      if (pMouseEvent.getButton() == MouseEvent.BUTTON2) {
	    	  painting.suspendre();
	      } else {
	        // clic bouton droit = effacer et recommencer
	        // case pMouseEvent.BUTTON3:
	    	  painting.init();
	      }
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
}
