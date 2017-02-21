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

package nex.world.biome;

import com.google.common.collect.Sets;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntityWight;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
import nex.world.gen.feature.WorldGenGlowStone;
import nex.world.gen.feature.WorldGenMinableMeta;
import nex.world.gen.feature.WorldGenPit;
import nex.world.gen.structure.WorldGenStructure;

import java.util.Random;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class BiomeArcticAbyss extends BiomeNetherEx
{
    private final Set<IBlockState> allowedBlocks = Sets.newHashSet(
            NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY),
            NetherExBlocks.BLOCK_ICE_FROSTBURN.getDefaultState(),
            NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY)
    );

    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartzOre = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY), 14, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY));
    private WorldGenerator rimeOre = new WorldGenMinableMeta(NetherExBlocks.ORE_RIME.getDefaultState(), 7, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY));
    private WorldGenerator ichorPit = new WorldGenPit(NetherExBlocks.FLUID_ICHOR, NetherExBlocks.BLOCK_ICE_FROSTBURN.getDefaultState(), Blocks.MAGMA.getDefaultState());
    private WorldGenerator crypt = new WorldGenStructure("arctic_abyss", "crypt", new String[]{""}, allowedBlocks, null, true);
    private WorldGenerator grave = new WorldGenStructure("arctic_abyss", "grave", new String[]{"chest", "empty"}, allowedBlocks, null, true);
    private WorldGenerator graveyard = new WorldGenStructure("arctic_abyss", "graveyard", new String[]{""}, allowedBlocks, new ResourceLocation(NetherEx.MOD_ID + ":monster_wight"), true);
    private WorldGenerator sarcophagus = new WorldGenStructure("arctic_abyss", "sarcophagus", new String[]{""}, allowedBlocks, new ResourceLocation(NetherEx.MOD_ID + ":monster_wight"), true);
    private WorldGenerator lighthouse = new WorldGenStructure("temple", "lighthouse", new String[]{""}, allowedBlocks, new ResourceLocation(NetherEx.MOD_ID + ":monster_wight"), true);
    private WorldGenerator specimen = new WorldGenStructure("temple", "specimen", new String[]{""}, allowedBlocks, new ResourceLocation(NetherEx.MOD_ID + ":monster_wight"), false);
    private WorldGenerator temple = new WorldGenStructure("temple", "temple", new String[]{"hard", "medium", "easy"}, allowedBlocks, new ResourceLocation(NetherEx.MOD_ID + ":monster_wight"), true);

    public BiomeArcticAbyss()
    {
        super(new BiomeProperties("Arctic Abyss").setTemperature(0.0F).setRainfall(0.0F).setRainDisabled(), "arctic_abyss");

        spawnableMonsterList.add(new SpawnListEntry(EntityWight.class, 100, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 50, 1, 1));

        topBlock = NetherExBlocks.BLOCK_ICE_FROSTBURN.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY);

        if(ConfigHandler.Biome.ArcticAbyss.generateBiome)
        {
            NetherExBiomes.addBiome(this, ConfigHandler.Biome.ArcticAbyss.biomeRarity, new ItemStack(Blocks.MAGMA, 1, 0));
        }
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.ArcticAbyss.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.ArcticAbyss.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.Biome.ArcticAbyss.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.ArcticAbyss.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.ArcticAbyss.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateRimeOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.ArcticAbyss.rimeOreRarity; i++)
            {
                rimeOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.Biome.ArcticAbyss.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateLighthouses)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.lighthouseRarity) == 0)
            {
                temple.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateSpecimen)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.specimenRarity) == 0)
            {
                temple.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateTemples)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.templeRarity) == 0)
            {
                temple.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateIchorPits)
        {
            BlockPos newPos = pos.add(rand.nextInt(16) + 8, rand.nextInt(64) + 32, rand.nextInt(16) + 8);

            if(world.getBiomeForCoordsBody(newPos) == this)
            {
                if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.ichorPitRarity) == 0)
                {
                    ichorPit.generate(world, rand, newPos);
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
