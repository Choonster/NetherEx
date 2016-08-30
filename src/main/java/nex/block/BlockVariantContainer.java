/*
 * Copyright (C) 2016.  LogicTechCorp
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

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * This is an implementation for blocks that use an enum to add variants
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/block/base/BlockMetaVariants.java
 */
public class BlockVariantContainer<T extends Enum<T> & IStringSerializable> extends BlockNetherEx
{
    private static PropertyEnum temporaryProperty;

    private final Class<T> CLS;
    private final PropertyEnum<T> PROPERTY;

    public BlockVariantContainer(String name, boolean disableSubtypes, Material material, SoundType type, String propertyNameIn, Class<T> cls)
    {
        super(name, disableSubtypes, material, type, propertyNameIn, enumToArray(propertyNameIn, cls));

        CLS = cls;
        PROPERTY = temporaryProperty;

        registerAndSetName(name, disableSubtypes);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, getMetaFromState(world.getBlockState(target.getBlockPos())));
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, temporaryProperty);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(PROPERTY, CLS.getEnumConstants()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(PROPERTY == null ? temporaryProperty : PROPERTY).ordinal();
    }

    @Override
    public boolean isBaseClass()
    {
        return false;
    }

    private static String[] enumToArray(String propertyName, Class cls)
    {
        temporaryProperty = PropertyEnum.create(propertyName, cls);
        Enum[] values = (Enum[]) cls.getEnumConstants();
        String[] variants = new String[values.length];

        for(int i = 0; i < values.length; i++)
        {
            variants[i] = values[i].name().toLowerCase();
        }

        return variants;
    }
}