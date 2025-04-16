package it.unibo.michelito.model.util.hitbox.impl;

import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.api.HitBoxFactory;

import java.util.Objects;

/**
 * A hitbox is a feature of every maze object that represent his physicality.
 */
public final class HitBoxFactoryImpl implements HitBoxFactory {
    private static final double X_DIMENSION_ENTITY = 1;
    private static final double Y_DIMENSION_ENTITY = 2;
    private static final double DIMENSION_SQUARE = 3;

    private abstract static class HitBoxImpl implements HitBox {
        private final Position center;

        HitBoxImpl(final Position center) {
            this.center = center;
        }

        @Override
        public Position getCenter() {
            return this.center;
        }

        @Override
        public HitBox getHitBox() {
            return this;
        }

        @Override
        public boolean collision(final HitBox hitBox) {
            return Math.abs(center.x() - hitBox.getCenter().x()) <= hitBox.getHalfWidth() + getHalfWidth()
                    && Math.abs(center.y() - hitBox.getCenter().y()) <= hitBox.getHalfHeight() + getHalfHeight();
        }

        @Override
        public boolean inner(final Position position) {
            return Math.abs(center.x() - position.x()) <= getHalfWidth()
                    && Math.abs(center.y() - position.y()) <= getHalfHeight();
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final HitBox other = (HitBox) obj;
            return other.getCenter().equals(center);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.center);
        }

        @Override
        public double getHalfWidth() {
            return wideDimension();
        }

        @Override
        public double getHalfHeight() {
            return heightDimension();
        }

        abstract double wideDimension();

        abstract double heightDimension();
    }

    @Override
    public HitBox squareHitBox(final Position position) {
        return new HitBoxImpl(position) {
            @Override
            double wideDimension() {
                return DIMENSION_SQUARE;
            }

            @Override
            double heightDimension() {
                return DIMENSION_SQUARE;
            }
        };
    }

    @Override
    public HitBox entityeHitBox(final Position position) {
        return new HitBoxImpl(position) {
            @Override
            double wideDimension() {
                return X_DIMENSION_ENTITY;
            }

            @Override
            double heightDimension() {
                return Y_DIMENSION_ENTITY;
            }
        };
    }
}
