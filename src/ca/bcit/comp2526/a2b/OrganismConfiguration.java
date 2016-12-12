/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bcit.comp2526.a2b;

import ca.bcit.comp2526.a2b.simulation.behavior.CellBasedReproductionBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.FleshHunter;
import ca.bcit.comp2526.a2b.simulation.behavior.GraphicBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.RadiusAccessibilityBehaviour;
import ca.bcit.comp2526.a2b.simulation.behavior.RandomSteerBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.SeekFleshBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.SteeringBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.SteeringBehavior.NullSteeringBehavior;
import ca.bcit.comp2526.a2b.simulation.Cell;
import ca.bcit.comp2526.a2b.simulation.Flesh;
import ca.bcit.comp2526.a2b.simulation.Organism;
import ca.bcit.comp2526.a2b.simulation.OrganismBuilder;
import ca.bcit.comp2526.a2b.simulation.TraverseStratedgy;

/**
 *
 * @author Jeremy Wildsmith
 */
public final class OrganismConfiguration {

    private String speciesName = "";
    private BaseSeekBehavior baseSeekBehavior;

    private int visibility = 0;
    private int foodLife = 0;
    private int maxAge = 0;
    private Flesh flesh = Flesh.None;

    private int minReproduceAge;
    private int maxReproduceAge;
    private int parentsRequired;
    private int foodRequired;
    private int spaceRequired;
    private int minChildren;
    private int maxChildren;

    private boolean spawnInPacks;
    private float spawnProbability;

    private Flesh[] consumes = new Flesh[]{};
    private boolean isCannibal;

    private TraverseStratedgy[] stratedgies = new TraverseStratedgy[]{};
    private int accessibilityRadius;

    private GraphicBehavior graphicBehavior;

    /**
     * Gets the current seek behavior.
     *
     * @return The current seek behavior.
     */
    public BaseSeekBehavior getBaseSeekBehavior() {
        return baseSeekBehavior;
    }

    /**
     * Sets the current seek behavior.
     *
     * @param baseSeekBehavior The new seek behavior.
     */
    public void setBaseSeekBehavior(BaseSeekBehavior baseSeekBehavior) {
        this.baseSeekBehavior = baseSeekBehavior;
    }

    /**
     * Gets the min children.
     *
     * @return The min children.
     */
    public int getMinChildren() {
        return minChildren;
    }

    /**
     * Sets min children.
     *
     * @param minChildren The min children.
     */
    public void setMinChildren(int minChildren) {
        this.minChildren = minChildren;
    }

    /**
     * Gets max children
     *
     * @return max children.
     */
    public int getMaxChildren() {
        return maxChildren;
    }

    /**
     * Sets max children.
     *
     * @param maxChildren max children
     */
    public void setMaxChildren(int maxChildren) {
        this.maxChildren = maxChildren;
    }

    /**
     * Gets accessibility radius.
     *
     * @return accessibility radius.
     */
    public int getAccessibilityRadius() {
        return accessibilityRadius;
    }

    /**
     * Sets the accessibility radius.
     *
     * @param accessibilityRadius accessibilty radius
     */
    public void setAccessibilityRadius(int accessibilityRadius) {
        this.accessibilityRadius = accessibilityRadius;
    }

    /**
     * Gets the species name.
     *
     * @return the species name.
     */
    public String getSpeciesName() {
        return speciesName;
    }

    /**
     * Sets the species name.
     *
     * @param speciesName The species name.
     */
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    /**
     * Gets the visibility.
     *
     * @return Gets the visiblity.
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * Sets the visiblity.
     *
     * @param visibility The visibility
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    /**
     * Gets the food life.
     *
     * @return The food life.
     */
    public int getFoodLife() {
        return foodLife;
    }

    /**
     * Sets the food life.
     *
     * @param foodLife The food life.
     */
    public void setFoodLife(int foodLife) {
        this.foodLife = foodLife;
    }

    /**
     * Gets the max age.
     *
     * @return the max age.
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the max age.
     *
     * @param maxAge The max age.
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Gets the flesh typ.e
     *
     * @return The flesh type.
     */
    public Flesh getFlesh() {
        return flesh;
    }

    /**
     * Sets the flesh type.
     *
     * @param fleshType The flesh type.
     */
    public void setFlesh(Flesh fleshType) {
        this.flesh = fleshType;
    }

    /**
     * Get min reproduction age.
     *
     * @return the min reproduction age.
     */
    public int getMinReproduceAge() {
        return minReproduceAge;
    }

    /**
     * Sets the min reproduction age.
     *
     * @param minReproduceAge The min age for reproduction.
     */
    public void setMinReproduceAge(int minReproduceAge) {
        this.minReproduceAge = minReproduceAge;
    }

    /**
     * Gets max reproduce age.
     *
     * @return max reproduce age.
     */
    public int getMaxReproduceAge() {
        return maxReproduceAge;
    }

    /**
     * Sets max reproduce age.
     *
     * @param maxReproduceAge max reproduce age.
     */
    public void setMaxReproduceAge(int maxReproduceAge) {
        this.maxReproduceAge = maxReproduceAge;
    }

    /**
     * Gets parents required.
     *
     * @return parents required.
     */
    public int getParentsRequired() {
        return parentsRequired;
    }

    /**
     * Sets parents required.
     *
     * @param parentsRequired parentsr required.
     */
    public void setParentsRequired(int parentsRequired) {
        this.parentsRequired = parentsRequired;
    }

    /**
     * Gets food required.
     *
     * @return Food required.
     */
    public int getFoodRequired() {
        return foodRequired;
    }

    /**
     * Sets food required.
     *
     * @param foodRequired food requried.
     */
    public void setFoodRequired(int foodRequired) {
        this.foodRequired = foodRequired;
    }

    /**
     * Gets space required.
     *
     * @return space required.
     */
    public int getSpaceRequired() {
        return spaceRequired;
    }

    /**
     * Sets space required.
     *
     * @param spaceRequired space required.
     */
    public void setSpaceRequired(int spaceRequired) {
        this.spaceRequired = spaceRequired;
    }

    /**
     * Checks if this entity spawns in packs.
     *
     * @return If this entity spawns in packs.
     */
    public boolean isSpawnInPacks() {
        return spawnInPacks;
    }

    /**
     * Sets if this entity spawns in packs.
     *
     * @param spawnInPacks spawns in packs.
     */
    public void setSpawnInPacks(boolean spawnInPacks) {
        this.spawnInPacks = spawnInPacks;
    }

    /**
     * Gets spawn probability.
     *
     * @return spawn probability.
     */
    public float getSpawnProbability() {
        return spawnProbability;
    }

    /**
     * Sets spawn probability
     *
     * @param spawnProbability spawn probability
     */
    public void setSpawnProbability(float spawnProbability) {
        this.spawnProbability = spawnProbability;
    }

    /**
     * Gets consumabel flesh.
     *
     * @return consumable flesh.
     */
    public Flesh[] getConsumes() {
        return consumes;
    }

    /**
     * Sets consumable flesh.
     *
     * @param consumes Consumable flesh.
     */
    public void setConsumes(Flesh[] consumes) {
        this.consumes = consumes;
    }

    /**
     * Checks if is cannibal
     *
     * @return If is cannibal.
     */
    public boolean isCannibal() {
        return isCannibal;
    }

    /**
     * Sets if this is cannibal
     *
     * @param isCannibal is cannibal
     */
    public void setIsCannibal(boolean isCannibal) {
        this.isCannibal = isCannibal;
    }

    /**
     * Gets traversal methods
     *
     * @return traversal methods
     */
    public TraverseStratedgy[] getStratedgies() {
        return stratedgies;
    }

    /**
     * Sets traversal methods
     *
     * @param stratedgies traversal methods.
     */
    public void setStratedgies(TraverseStratedgy[] stratedgies) {
        this.stratedgies = stratedgies;
    }

    /**
     * Gets graphic behavior
     *
     * @return graphic behavior.
     */
    public GraphicBehavior getGraphicBehavior() {
        return graphicBehavior;
    }

    /**
     * Sets graphic behavior
     *
     * @param graphicBehavior graphic behavior.
     */
    public void setGraphicBehavior(GraphicBehavior graphicBehavior) {
        this.graphicBehavior = graphicBehavior;
    }

    /**
     * Creates organism
     *
     * @param location locaiton
     * @return the new organism.
     */
    public Organism createOrganism(Cell location) {
        OrganismBuilder builder = new OrganismBuilder();

        builder.setSteeringBehavior(baseSeekBehavior.create(this));
        builder.setVisibility(visibility);
        builder.setLifeTime(foodLife);
        builder.setFlesh(flesh);
        builder.setReproductionBehavior(new CellBasedReproductionBehavior(1, spaceRequired, parentsRequired, foodRequired, minChildren, maxChildren, minReproduceAge, maxReproduceAge));
        builder.setHuntingBehavior(new FleshHunter(consumes, isCannibal));
        builder.setAccessibilityBehavior(new RadiusAccessibilityBehaviour(accessibilityRadius, stratedgies));
        builder.setGraphicBehavior(graphicBehavior.duplicate());

        return builder.build(speciesName, location);
    }

    /**
     * Gets organism name
     *
     * @return organism name.
     */
    @Override
    public String toString() {
        return this.speciesName;
    }

    /**
     * DIfferent types of seek behvairos.
     */
    public enum BaseSeekBehavior {
        Random,
        SeekFlesh,
        None;

        /**
         * Creates respective steering behavior for organism configuraiton.
         *
         * @param organisms organism
         * @return
         */
        public SteeringBehavior create(OrganismConfiguration organisms) {
            switch (this) {
                case Random:
                    return new RandomSteerBehavior();
                case None:
                default:
                    return new NullSteeringBehavior();
                case SeekFlesh:
                    return new SeekFleshBehavior(organisms.consumes, organisms.isCannibal);
            }
        }
    }
}
