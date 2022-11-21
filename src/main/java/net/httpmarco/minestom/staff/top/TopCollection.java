package net.httpmarco.minestom.staff.top;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;

public final class TopCollection {

    private final TopPosition[] positions;

    public TopCollection(TopPosition... positions) {
        this.positions = positions;
    }

    public void handle(Point startPosition, Instance instance) {
        for (int i = 0; i < this.positions.length; i++) {
            this.positions[i].build((i+1), startPosition.sub(i, 0,0), instance);
        }
    }
}
