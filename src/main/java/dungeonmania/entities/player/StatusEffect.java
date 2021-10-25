package dungeonmania.entities.player;

public class StatusEffect {

    private boolean invincible;
    private int invincibleDuration;
    private boolean invisible;
    private int invinvisibleDuration;


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

    public int getInvinvisibleDuration() {
        return this.invinvisibleDuration;
    }

    public void setInvinvisibleDuration(int invinvisibleDuration) {
        this.invinvisibleDuration = invinvisibleDuration;
    }

}
