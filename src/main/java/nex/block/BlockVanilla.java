/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.block;

import net.minecraft.util.IStringSerializable;

public class BlockVanilla
{
    public enum EnumTypeSlab implements IStringSerializable
    {
        BRICK_NETHER_RED;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }

        public static EnumTypeSlab fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }

    public enum EnumTypeWall implements IStringSerializable
    {
        QUARTZ,
        BRICK_NETHER,
        BRICK_NETHER_RED;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }

        public static EnumTypeWall fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }

    public enum EnumTypeFence implements IStringSerializable
    {
        QUARTZ,
        BRICK_NETHER_RED;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }

        public static EnumTypeFence fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }
}
