package de.mateco.integrAMobile.Helper;

public class NavigationChild {
    private int id;

    private int respurceId;

    private String name;

    public int getRespurceId() {
        return respurceId;
    }

    public void setRespurceId(int respurceId) {
        this.respurceId = respurceId;
    }

    public NavigationChild(int id, String name, int resourceId) {
        this.name = name;
        this.respurceId = resourceId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
