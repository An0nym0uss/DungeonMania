package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class Arrow extends CollectableEntity{

    public Arrow(Position position) {
        super("arrow", position, false);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Arrow clone() {
        return new Arrow(this.getPosition());
    }
    
}

