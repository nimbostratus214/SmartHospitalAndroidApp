package radenko.mihajlovic.smarthospital;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import org.w3c.dom.Text;

public class admin_view {
    private String naziv;
    private Drawable slika;
    private boolean prekidac;
    private TextView t1;

    public admin_view(String naziv, Drawable slika, boolean prekidac) {
        this.naziv = naziv;
        this.slika = slika;
        this.prekidac = prekidac;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Drawable getSlika() {
        return slika;
    }

    public void setSlika(Drawable slika) {
        this.slika = slika;
    }

    public boolean isPrekidac() {
        return prekidac;
    }

    public void setPrekidac(boolean prekidac) {
        this.prekidac = prekidac;
    }

    public TextView getT1() {
        return t1;
    }

    public void setT1(TextView t1) {
        this.t1 = t1;
    }
}
