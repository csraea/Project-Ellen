package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {

    private int initAmmo;
    private int maxAmmo;

    public Firearm(int initAmmo, int maxAmmo){
        this.initAmmo = initAmmo;
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int initAmmo){
        this(initAmmo, initAmmo);
    }

    public abstract Fireable createBullet();

    public int getAmmo() {
        return initAmmo;
    }

    public void reload(int newAmmo){
        initAmmo = (getAmmo() + newAmmo > maxAmmo) ? maxAmmo : initAmmo + newAmmo;
    }

    public Fireable fire(){
        if (initAmmo == 0){
            return null;
        }else{
            initAmmo--;
            return createBullet();
        }
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

}
