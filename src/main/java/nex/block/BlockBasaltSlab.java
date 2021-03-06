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

import com.google.common.base.CaseFormat;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import nex.init.NetherExBlocks;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BlockBasaltSlab extends BlockNetherExSlab
{
    public static final PropertyEnum<BlockBasalt.EnumType> TYPE = PropertyEnum.create("type", BlockBasalt.EnumType.class);

    public BlockBasaltSlab(boolean isDoubleIn)
    {
        super("slab_basalt", Material.ROCK, isDoubleIn);

        setHardness(2.0F);
        setResistance(10F);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            list.add(new ItemStack(item, 1, type.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockBasalt.EnumType.fromMeta(meta).getName());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherExBlocks.SLAB_BASALT);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockBasalt.EnumType.fromMeta(stack.getMetadata() & 7);
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return TYPE;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = getDefaultState().withProperty(TYPE, BlockBasalt.EnumType.fromMeta(meta & 7));

        if(!isDouble())
        {
            state = state.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(TYPE).ordinal();

        if(!isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
        {
            meta |= 8;
        }

        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return !isDoubleStatic ? new BlockStateContainer(this, getVariantProperty(), HALF) : new BlockStateContainer(this, getVariantProperty());
    }
}
