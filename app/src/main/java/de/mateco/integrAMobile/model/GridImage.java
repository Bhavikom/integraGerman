package de.mateco.integrAMobile.model;


public class GridImage
{
    private int Id;
    private String Path;
    private String Name;
    private int flag;
    private int BvoId;

    public GridImage()
    {

    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public int getBvoId() {
        return BvoId;
    }

    public void setBvoId(int bvoId) {
        BvoId = bvoId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

}
