package net.feltmc.feltapi.api.ore_feature.v1;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class WorldGenVanillaOreBuilder {
    @Nullable
    private BiFunction<BlockState, RandomSource, BlockState> function;
    @Nullable
    private Integer weight;
    @Nullable
    private Integer maxY;
    @Nullable
    private Integer minY;
    @Nullable
    private Integer size;
    @Nullable
    private Integer plateau;
    @Nullable
    private Float secondaryChance;
    @Nullable
    private Float discardOnExposureChance;
    @Nullable ResourceLocation id;
    List<ResourceLocation> dimensions = new ArrayList<>();
    List<String> biomes = new ArrayList<>();
    boolean biomeBlacklist = true, rare = false, triangle = false, spawnOnOceanFloor = false;

    public WorldGenVanillaOreBuilder() {
    }

    final public WorldGenVanillaOre buildMaterial() {
        if (this.id == null){
            throw new RuntimeException("id is required");
        }
        if (this.weight == null) {
            throw new RuntimeException("weight is required");
        }
        if (this.size == null) {
            throw new RuntimeException("size is required");
        }
        if (this.function == null) {
            throw new RuntimeException("function is required");
        }
        if (this.dimensions.isEmpty()) {
            this.dimensions.add(new ResourceLocation("overworld"));
        }
        WorldGenVanillaOre vanillaOre =  new WorldGenVanillaOre(
                id,
                this.function,
                this.discardOnExposureChance == null ? 0.0f : this.discardOnExposureChance,
                this.minY != null ? this.minY : Integer.MIN_VALUE,
                this.maxY != null ? this.maxY : Integer.MAX_VALUE,
                weight,
                size,
                rare,
                triangle,
                this.plateau == null ? 0 : this.plateau,
                this.spawnOnOceanFloor,
                this.dimensions,
                this.biomes,
                this.biomeBlacklist
        );
        return vanillaOre;
    }

    final public WorldGenVanillaOreBuilder withBlockState(BlockState ore, BlockState fill) {
        this.function =  (blockstate, random) -> {
            if (blockstate != fill) return null;
            return ore;
        };
        return this;
    }

    final public WorldGenVanillaOreBuilder withBlockState(BlockState ore, RuleTest fill) {
        this.function =  (blockstate, random) -> {
            if (!fill.test(blockstate, random)) return null;
            return ore;
        };
        return this;
    }

    final public WorldGenVanillaOreBuilder withBlockState(BlockState ore) {
        this.function =  (blockstate, random) -> ore;
        return this;
    }
    final public WorldGenVanillaOreBuilder withBlockStateFunction(BiFunction<BlockState, RandomSource, BlockState> function) {
        this.function =  function;
        return this;
    }

    final public WorldGenVanillaOreBuilder withWeight(int weight) {
        this.weight = weight;
        return this;
    }

    final public WorldGenVanillaOreBuilder withSize(int size){
        this.size = size;
        return this;
    }

    final public WorldGenVanillaOreBuilder withDiscardOnExposureChance(float discardOnExposureChance){
        this.discardOnExposureChance = discardOnExposureChance;
        return this;
    }

    final public WorldGenVanillaOreBuilder atHeight(int minY, int maxY) {
        this.minY = minY;
        this.maxY = maxY;
        return this;
    }

    final public WorldGenVanillaOreBuilder withCustomId(ResourceLocation id){
        this.id = id;
        return this;
    }

    final public WorldGenVanillaOreBuilder withBiomes(String... biomes) {
        Collections.addAll(this.biomes, biomes);
        return this;
    }

    final public WorldGenVanillaOreBuilder withDimensions(ResourceLocation... dimensions) {
        Collections.addAll(this.dimensions, dimensions);
        return this;
    }

    final public WorldGenVanillaOreBuilder setBiomeBlacklist(boolean blacklist) {
        this.biomeBlacklist = blacklist;
        return this;
    }

    final public WorldGenVanillaOreBuilder setRare(boolean rare){
        this.rare = rare;
        return this;
    }

    final public WorldGenVanillaOreBuilder setHasTriangleHeight(boolean triangle){
        this.triangle = triangle;
        return this;
    }

    final public WorldGenVanillaOreBuilder setHasTriangleHeight(boolean triangle, int plateau){
        this.triangle = triangle;
        this.plateau = plateau;
        return this;
    }

    final public WorldGenVanillaOreBuilder setSpawnOnOceanFloor(boolean spawnOnOceanFloor){
        this.spawnOnOceanFloor = spawnOnOceanFloor;
        return this;
    }
}
