package dungeonmania.entities.player;

public class StatusEffect {

    private boolean invincible;
    private int invincibleDuration;
    private boolean invisible;
    private int invisibleDuration;

    public StatusEffect() {
        this.invincible = false;
        this.invincibleDuration = 0;
        this.invisible = false;
        this.invisibleDuration = 0;
    }

    public boolean isInvincible() {
        return this.invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public int getInvincibleDuration() {
        return this.invincibleDuration;
    }

    public void setInvincibleDuration(int invincibleDuration) {
        this.invincibleDuration = invincibleDuration;
    }

    public boolean isInvisible() {
        return this.invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public int getInvisibleDuration() {
        return this.invisibleDuration;
    }

    public void setInvisibleDuration(int invisibleDuration) {
        this.invisibleDuration = invisibleDuration;
    }

    public void tickDown() {
        if (this.invincibleDuration > 0) {
            this.setInvincibleDuration(getInvincibleDuration() - 1);
        }
        if (this.invisibleDuration > 0) {
            this.setInvisibleDuration(getInvisibleDuration() - 1);
        }
        if (this.invincibleDuration == 0 && isInvincible()) {
            setInvincible(false);
        }
        if (this.invisibleDuration == 0 && isInvisible()) {
            setInvisible(false);
        }
    }
}