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
package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

/**
 * A simple class that holds the images of a sprite for an animated cowbow.
 *
 */
public class Cowboy extends Entity {
	int moveElapsed;
	int maxX = 500;

	public Cowboy(Controller controller) {
		super(controller);
		this.position = new Vec2(0.0f, 0.0f);
		this.avatar = this.controller.createAvatar(this.position, "cowboy.png", 24, 200);
	}

	@Override
	public void tick(long elapsed) {
		super.tick(elapsed);
		this.moveElapsed += elapsed;
		if (this.moveElapsed > 24) {
			this.moveElapsed = 0;
			this.position.setX((this.position.getX() + 2) % maxX);
			this.avatar.setPosition(this.position);
		}
	}
}
