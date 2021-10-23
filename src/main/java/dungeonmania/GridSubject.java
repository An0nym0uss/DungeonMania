package dungeonmania;

import dungeonmania.entities.ObserverEntity;

/**
 * Subject for Observer pattern.
 * 
 * @author Enoch Kavur (z5258204)
 */
public interface GridSubject {
    
    /**
     * Add an observer entity to grid.
     * 
     * @param o observer entity to add.
     */
    public void attach(ObserverEntity o);

    /**
     * Removes an observer entity from grid.
     * 
     * @param o observer entity to remove.
     */
    public void dettach(ObserverEntity o);

    /**
     * Notify all Observer Entites and call update on them.
     * 
     */
    public void notifyObserverEntities();
}
