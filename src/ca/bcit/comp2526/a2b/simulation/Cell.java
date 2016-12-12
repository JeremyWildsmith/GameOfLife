package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.graphic.Graphic.NullGraphic;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;

/**
 * A single cell in the game world, which contains an entity.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class Cell extends JPanel {

    private static final Color BORDER_COLOR = new Color(0, 0, 0, 60);

    private final WorldSimulation world;
    private final Point location;
    private final Surface surface;

    private Entity containedEntity;

    /**
     * Constructs a new cell for the specified world.
     *
     * @param world The world this cell is to be placed on.
     * @param surface The surface of this cell.
     * @param x The x location of this cell in the simulation.
     * @param y The y location of this cell in the simulation.
     */
    public Cell(WorldSimulation world, Surface surface, int x, int y) {
        this.world = world;
        this.location = new Point(x, y);
        this.surface = surface;

        //Since initial repaint may have been invalid, repaint again.
        repaint();
    }

    /**
     * Sets the entity in this cell to the specified entity.
     * @param entity The entity to place into this cell
     */
    public void setEntity(Entity entity) {
        containedEntity = entity;
        repaint();
    }

    /**
     * Removes any entities currently on this cell.
     */
    public void clearEntity() {
        setEntity(null);
    }

    /**
     * Gets the entity currently on this cell.
     * @return The entity currently on this cell.
     */
    public Entity getEntity() {
        return containedEntity;
    }

    /**
     * Checks whether this cell is empty.
     * @return Whether this cell is empty or not.
     */
    public boolean isEmpty() {
        return containedEntity == null;
    }

    /**
     * Gets the location of this cell in the world.
     * @return The location of this cell in the world.
     */
    @Override
    public Point getLocation() {
        return location;
    }

    /**
     * Gets adjacent cells within the specified radius.
     * @param radius The radius around this cell to get adjacent cells for.
     * @return The adjacent cells in the specified radius.
     */
    public Cell[] getAdjacentCells(int radius) {
        List<Cell> adjacent = new ArrayList<>();

        for (int offX = -radius; offX <= radius; offX++) {
            for (int offY = -radius; offY <= radius; offY++) {
                if (offX == 0 && offY == 0) {
                    continue;
                }

                int cellX = location.x + offX;
                int cellY = location.y + offY;

                if (cellY >= 0 && cellX >= 0
                        && cellX < world.getWidth()
                        && cellY < world.getHeight()) {

                    adjacent.add(world.getCellAt(cellX, cellY));
                }
            }
        }

        return adjacent.toArray(new Cell[adjacent.size()]);
    }

    /**
     * Gets only immediately adjacent cells to this cell.
     * @return Immediately adjacent cells.
     */
    public Cell[] getAdjacentCells() {
        return getAdjacentCells(1);
    }

    /**
     * Updates the logic for the contained entity in this cell.
     * @param deltaTime The time since this method was last called.
     */
    public void update(int deltaTime) {
        if (containedEntity != null) {
            containedEntity.update(deltaTime);
        }

        surface.update(deltaTime);
        if (surface.update(deltaTime)) {
            repaint();
        }
    }

    /**
     * Gets the graphic visual for this cell in its current state.
     * @return The graphic visual for this cell in its current state.
     */
    public Graphic getGraphic() {
        final Graphic surfaceGraphic = surface.getGraphic();
        final Graphic entityGraphic = containedEntity == null ? new NullGraphic() : containedEntity.getGraphic();

        return new CellGraphic(surfaceGraphic, entityGraphic);
    }

    /**
     * Paints the visual representation of this cell onto the specified graphic
     * context.
     * @param g Graphic contexts on which this cell is painted.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Super constructor calls repaint before this classes constructor has fully
        //executed, so surface may initially be null when this method is invoked.
        if (surface == null) {
            return;
        }

        getGraphic().draw((Graphics2D) g, this.getWidth(), this.getHeight());
    }

    /**
     * Gets the traversal method necessary to walk over this cell.
     * @return The traversal method necessary to move onto this cell.
     */
    public TraverseStratedgy getTraverseStratedgy() {
        return surface.getTraverseStratedgy();
    }

    /* The graphic representation of a cell.
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    private static class CellGraphic implements Graphic {

        private final Graphic surfaceGraphic;
        private final Graphic entityGraphic;

        /**
         * Creates a new cel graphic.
         * @param surfaceGraphic The surface graphic
         * @param entityGraphic The entity graphic.
         */
        public CellGraphic(Graphic surfaceGraphic, Graphic entityGraphic) {
            this.surfaceGraphic = surfaceGraphic;
            this.entityGraphic = entityGraphic;
        }

        /**
         * Draws this graphic onto the specified graphics context.
         * @param g The graphics context.
         * @param width The destination width.
         * @param height The destination height.
         */
        @Override
        public void draw(Graphics2D g, int width, int height) {
            surfaceGraphic.draw(g, width, height);
            entityGraphic.draw(g, width, height);
            g.setColor(BORDER_COLOR);
            g.drawRect(0, 0, width, height);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 47 * hash + Objects.hashCode(this.surfaceGraphic);
            hash = 47 * hash + Objects.hashCode(this.entityGraphic);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final CellGraphic other = (CellGraphic) obj;
            if (!Objects.equals(this.surfaceGraphic, other.surfaceGraphic)) {
                return false;
            }
            return Objects.equals(this.entityGraphic, other.entityGraphic);
        }

        /**
         * Duplicates this graphic.
         * @return A duplicate of this graphic.
         */
        @Override
        public Graphic duplicate() {
            return new CellGraphic(surfaceGraphic, entityGraphic);
        }
    }
}
