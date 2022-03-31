package model;

/**
 * an enemy is a simple entity with a name, a initiative and some optional description
 *
 * @author Cornelius
 */
public class Enemy {

    String name;
    String desc;
    int ini;

    public Enemy(String name, int ini) {
        this.name = name;
        this.ini = ini;
    }

    public Enemy(String name, String desc, int ini) {
        this.name = name;
        this.desc = desc;
        this.ini = ini;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIni() {
        return ini;
    }

    public void setIni(int ini) {
        this.ini = ini;
    }
}
