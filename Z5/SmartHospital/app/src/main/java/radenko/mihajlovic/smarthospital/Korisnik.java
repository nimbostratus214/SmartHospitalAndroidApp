package radenko.mihajlovic.smarthospital;

public class Korisnik {
    private String id;
    private String ime;
    private String prezime;
    private String pol;
    private String datumRodjenja;
    private String lozinka;
    private String pregledi;
    private String username; //nzm da li ce nam zatrebati kasnije

    public Korisnik(String id, String ime, String prezime, String pol, String datumRodjenja, String lozinka, String pregledi) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.pol = pol;
        this.datumRodjenja = datumRodjenja;
        this.lozinka = lozinka;
        this.pregledi = pregledi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getPregledi() {
        return pregledi;
    }

    public void setPregledi(String pregledi) {
        this.pregledi = pregledi;
    }
}
