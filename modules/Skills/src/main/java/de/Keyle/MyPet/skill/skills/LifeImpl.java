/*
 * This file is part of MyPet
 *
 * Copyright © 2011-2018 Keyle
 * MyPet is licensed under the GNU Lesser General Public License.
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.skill.skills;

import de.Keyle.MyPet.api.entity.MyPet;
import de.Keyle.MyPet.api.skill.skills.Life;
import org.bukkit.ChatColor;

public class LifeImpl implements Life {
    protected double extraLife = 0;
    private MyPet myPet;

    public LifeImpl(MyPet myPet) {
        this.myPet = myPet;
    }

    public MyPet getMyPet() {
        return myPet;
    }

    public boolean isActive() {
        return extraLife > 0;
    }

    public String toPrettyString() {
        return "+" + ChatColor.GOLD + extraLife;
    }

    public double getExtraLife() {
        return extraLife;
    }

    public void setExtraLife(double extraLife) {
        this.extraLife = extraLife;
    }

    @Override
    public String toString() {
        return "LifeImpl{" +
                "extraLife=" + extraLife +
                '}';
    }
}