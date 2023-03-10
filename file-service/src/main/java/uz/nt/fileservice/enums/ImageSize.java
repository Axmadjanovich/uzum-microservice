package uz.nt.fileservice.enums;

public enum ImageSize {

    LARGE("LARGE"),
    MEDIUM("MEDIUM"),
    SMALL("SMALL");

    ImageSize(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
