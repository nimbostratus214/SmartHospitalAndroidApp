package radenko.mihajlovic.smarthospital;

public class user_view {
    private String datum;
    private String pregled;

    public user_view(String datum, String pregled) {
        this.datum = datum;
        this.pregled = pregled;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getPregled() {
        return pregled;
    }

    public void setPregled(String pregled) {
        this.pregled = pregled;
    }
}
