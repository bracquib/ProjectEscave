/*
 * Copyright (C) 2020  Pr. Olivier Gruber
 * Educational software for a basic game development
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created on: March, 2020
 *      Author: Pr. Olivier Gruber
 */
package info3.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import info3.game.graphics.GameCanvasListener;
import info3.game.network.KeyPress;

public class CanvasListener implements GameCanvasListener {
	LocalView view;

	CanvasListener(LocalView lv) {
		this.view = lv;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		KeyPress kp = new KeyPress(e.getKeyCode());
		this.view.controller.keyPressed(kp);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void tick(long elapsed) {
		this.view.tick(elapsed);
		this.view.controller.tick(elapsed);
	}

	@Override
	public void paint(Graphics g) {
		this.view.paint(g);
	}

	@Override
	public void windowOpened() {

	}

	@Override
	public void exit() {
	}

	@Override
	public void endOfPlay(String name) {

	}

	@Override
	public void expired() {

	}
}
