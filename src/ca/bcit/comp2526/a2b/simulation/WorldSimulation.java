package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.OrganismConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game of life world simulation.
 * @author jeremy
 * @version 1.0
 */
public final class WorldSimulation implements Simulation {

    private static final int PACK_SIZE = 2;
    private static final int PACK_VARIANCE = 4;

    private static final int MAX_PROBABILITY = 100;
    
    private final int height;
    private final int width;

    private final Cell[][] cells;

    private final Random random = new Random();

    /**
     * Constructs a new game of life world simulation.
     * @param waterProbability The probability of water.
     * @param organisms The organisms in the world.
     * @param rows The number of rows in the world simulation.
     * @param columns The number of columns in the world simulation.
     */
    public WorldSimulation(float waterProbability, OrganismConfiguration[] organisms, int rows, int columns) {
        this.cells = new Cell[columns][rows];
        this.height = rows;
        this.width = columns;
        init(organisms, waterProbability);
    }

    /*
     * Gets the number of occurances of something in the world given a particular
     * probability of occurance.
     * @param probabilityInHundred The probability of the occurance, out of 100.
     * @return The number of instances of an entity given the probability.
     */
    private int getCellProbabilityCount(int probabilityInHundred) {
        Random r = new Random();
        int cellCount = 0;

        for (int i = 0; i < width * height; i++) {
            int val = random.nextInt(MAX_PROBABILITY) + 1;

            if (val <= probabilityInHundred) {
                cellCount++;
            }
        }

        return cellCount;
    }

    /*
     * Finds an area or cluster in the world.
     * @param waterMap The map of water placement in the game.
     * @param useableCells The number of cells to cluster.
     * @param violationMap Map of cells that cannot be used.
     * @param clusterSize The size of individual clusters.
     * @param clusterVariance The variance in cluster size.
     * @return The number of cells used to place clusters.
     */
    private int placeCluster(boolean[][] waterMap, int useableCells, boolean violationMap[][], int clusterSize, int clusterVariance) {
        int numCellsUsed = 0;

        int pondX = random.nextInt(width);
        int pondY = random.nextInt(height);

        int size = clusterSize + random.nextInt(clusterVariance);

        for (int xOff = 0; xOff < size; xOff++) {
            for (int yOff = 0; yOff < size; yOff++) {
                if (numCellsUsed >= useableCells) {
                    break;
                }

                int x = pondX + xOff;
                int y = pondY + yOff;

                if (x > 0 && y > 0 && x < width && y < height && !waterMap[x][y] && !violationMap[x][y]) {
                    waterMap[x][y] = true;
                    numCellsUsed++;
                }
            }
        }

        return numCellsUsed;
    }

    /*
     * Gets a map of different clusters palced in the simulation world.
     * @param violationMap A map of cells that cannot be used.
     * @param clusterSize The sizes of the indiviudal clusets.
     * @param clusterVariance The size variance.
     * @param numUseableCells The useable number of cells.
     * @return A boolean map of the allocated clusters.
     */
    private boolean[][] getClusterMap(boolean[][] violationMap, int clusterSize, int clusterVariance, int numUseableCells) {
        boolean[][] clusterMap = new boolean[width][height];

        while (numUseableCells > 0) {
            numUseableCells -= placeCluster(clusterMap, numUseableCells, violationMap, clusterSize, clusterVariance);
        }

        return clusterMap;
    }

    /**
     * Gets a cluster map.
     * @param clusterSize The cluster size.
     * @param clusterVariance The variance in size.
     * @param numUseableCells The number of useable cells.
     * @return A boolean map of the allocated clusters.
     */
    private boolean[][] getClusterMap(int clusterSize, int clusterVariance, int numUseableCells) {
        return getClusterMap(new boolean[width][height], clusterSize, clusterVariance, numUseableCells);
    }

    /**
     * Spawns a group of organisms into the world.
     * @param organismConfig The organism to spawn into the world
     */
    private void spawnOrganism(OrganismConfiguration organismConfig) {

        if (organismConfig.isSpawnInPacks()) {
            int cellCount = getCellProbabilityCount((int) (organismConfig.getSpawnProbability() * 100));

            boolean[][] map = getClusterMap(PACK_SIZE, PACK_VARIANCE, cellCount);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Cell c = cells[x][y];

                    if (map[x][y] && c.isEmpty()) {
                        Organism organism = organismConfig.createOrganism(c);
                        c.setEntity(organism);
                        if (!organism.canSurvive()) {
                            c.clearEntity();
                        }
                    }
                }
            }
        } else {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Cell c = cells[x][y];
                    float role = (random.nextInt(100) + 1) / 100F;

                    if (c.isEmpty() && role <= organismConfig.getSpawnProbability()) {
                        Organism organism = organismConfig.createOrganism(c);
                        c.setEntity(organism);
                        if (!organism.canSurvive()) {
                            c.clearEntity();
                        }
                    }
                }
            }
        }
    }

    /**
     * Initializes the world and fills its cells and entities.
     * @param organisms The organisms that can exist in the world.
     * @param waterProbability The probability of water in the world.
     */
    private void init(OrganismConfiguration organisms[], float waterProbability) {
        boolean waterMap[][] = getClusterMap(PACK_SIZE,
                PACK_VARIANCE,
                getCellProbabilityCount((int) (waterProbability * 100)));

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Surface surface = waterMap[x][y] ? new Water() : new Dirt();
                cells[x][y] = new Cell(this, surface, x, y);
            }
        }

        for (OrganismConfiguration o : organisms) {
            spawnOrganism(o);
        }
    }

    /**
     * Gets the cell a the specified location.
     * @param x The x location of the cell to get.
     * @param y The y locaiton of the cell to get.
     * @return The cell at the specified location.
     */
    @Override
    public Cell getCellAt(int x, int y) {
        return cells[x][y];
    }

    /**
     * Gets the height of the world simulation.
     * @return The height of the world simulation.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the world simulation.
     * @return The width of the world simulation.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Updates time-sensitive logic in the simulaiton world.
     * @param interval The time since this method was last called.
     */
    @Override
    public void update(int interval) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell c = cells[x][y];
                c.update(interval);
            }
        }
    }

    /**
     * Simulates one step in the world simulation.
     */
    @Override
    public void simulate() {

        List<Entity> entities = new ArrayList<>();

        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                Cell c = cells[x][y];

                Entity e = c.getEntity();

                if (e != null && !entities.contains(e)) {
                    entities.add(e);
                    e.takeTurn();
                }
            }
        }
    }
}
