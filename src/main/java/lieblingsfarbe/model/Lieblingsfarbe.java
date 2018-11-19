package main.java.lieblingsfarbe.model;

public class Lieblingsfarbe {

    private String lieblingsfarbe;

    public Lieblingsfarbe(String lieblingsfarbe){
        setLieblingsfarbe(lieblingsfarbe);
    }

    public String getLieblingsfarbe() {
        return lieblingsfarbe;
    }

    public void setLieblingsfarbe(String lieblingsfarbe) {
        this.lieblingsfarbe = lieblingsfarbe;
    }

    public boolean isValid(){
        return (this.lieblingsfarbe != null && !this.lieblingsfarbe.isEmpty());
    }

}
