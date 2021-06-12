package radenko.mihajlovic.smarthospital;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
//dodali smo id-uredjaja u tabelu uredjaja! wtf
public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "smartHospital.db";
    public static final int DBVersion = 1;

    public static final String TABLE_NAME = "korisnici";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_IME = "IME";
    public static final String COLUMN_PREZIME = "PREZIME";
    public static final String COLUMN_POL = "POL";
    public static final String COLUMN_DR = "DATUM_RODJENJA";
    public static final String COLUMN_LOZINKA = "LOZINKA";
    public static final String COLUMN_PREGLEDI = "PREGLEDI";

    //elementi tabele PREGLEDI
    //user_view definise izgled jednog pregleda npr. !
    public static final String TABLE_NAME_P = "PREGLEDI";
    public static final String COLUMN_ID_P = "ID";
    public static final String COLUMN_DATUM_P = "DATUM";
    public static final String COLUMN_PREGLED_P = "PREGLED";

    //elementi tabele UREDJAJI
    public static final String TABLE_NAME_U = "UREDJAJI";
    public static final String COLUMN_ID_U = "ID";
    public static final String COLUMN_NAZIV = "Naziv";
    public static final String COLUMN_SLIKA = "Slika";
    public static final String COLUMN_PREKIDAC = "Prekidac";


    public DBHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //inicijalizacija baze podataka
        //sobzirom da za id koristimo AUTOINCREMENT, treba povesti racuna da
        //prilikom brisanja korisnika sa odredjenim id-em iz bilo koje tabele
        //treba ga ukloniti iz svih tabela, kako ne bi doslo do kolizije
        //!!!osmisliti bolji nacin za kreiranje UID-a korisnika
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IME + " TEXT, " + COLUMN_PREZIME + " TEXT, " + COLUMN_POL + " TEXT, " +
                COLUMN_DR + " TEXT, " + COLUMN_LOZINKA + " TEXT, " + COLUMN_PREGLEDI + " TEXT);" );

        db.execSQL("CREATE TABLE " + TABLE_NAME_P + "(" +
                    COLUMN_ID_P + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATUM_P + " TEXT, " + COLUMN_PREGLED_P + " TEXT);" );

        db.execSQL("CREATE TABLE " + TABLE_NAME_U + "(" + COLUMN_ID_U + " TEXT, " + COLUMN_NAZIV + " TEXT, " + COLUMN_SLIKA + " INTEGER, " + COLUMN_PREKIDAC + " INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(Korisnik korisnik){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        //ContentValues values1 = new ContentValues();
        //values.put(COLUMN_ID, korisnik.getId());
        values.put(COLUMN_IME, korisnik.getIme());
        values.put(COLUMN_PREZIME, korisnik.getPrezime());
        values.put(COLUMN_POL, korisnik.getPol());
        values.put(COLUMN_DR, korisnik.getDatumRodjenja());
        values.put(COLUMN_LOZINKA, korisnik.getLozinka());
        values.put(COLUMN_PREGLEDI, korisnik.getPregledi());

        db.insert(TABLE_NAME, null, values);

        close();
    }
    //metoda prima objekat klase user_view, dakle za svakog korisnika ce
    //se upisati lista odredjenih pregleda u bazu u tabelu
    //treba sa iscupati te preglede iz tabele KORISNICI, iz odredjene kolone!
    public void insertInPregledi(user_view pregled){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATUM_P, pregled.getDatum());
        values.put(COLUMN_PREGLED_P, pregled.getPregled());

        db.insert(TABLE_NAME_P, null, values);
        close();
    }

    public void insertInUredjaji(admin_view uredjaj){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(COLUMN_ID_U, uredjaj.)
        values.put(COLUMN_ID_U, uredjaj.getID());
        values.put(COLUMN_NAZIV, uredjaj.getNaziv());
        values.put(COLUMN_SLIKA, uredjaj.getSlika());
        //ispravno sacuvano stanje prekidaca u bazu :D
        if(uredjaj.isPrekidac() ==  true)
            values.put(COLUMN_PREKIDAC, 1);
        else
            values.put(COLUMN_PREKIDAC, 0);

        Log.d("Insertovan uredjaj: ",  "id " + uredjaj.getID() + " stanje: " + uredjaj.isPrekidac());

        db.insert(TABLE_NAME_U, null, values);

        close();
    }

    //treba mi metoda koja ce da vrati ime i prezime korisnika po id-u
    //metoda koja ce da vrati sve informacije o korisniku osim lozinke
    //metoda koja ce da na osnovu id-a, vrati lozinku odredjenog korisnika

    public Korisnik readKorisnik(String ime, String prezime){ //String id
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_IME + "=? AND " + COLUMN_PREZIME + "=?", new String[] {ime, prezime}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        Korisnik korisnik = createKorisnik(cursor);
        close();
        return korisnik;
    }

    //metoda pretrazuje bazu u zavisnosti od username-a i pasvorda, ako korisnik ne postoji, ispisacemo gresku prilikom prijave
    public Korisnik readK(String ime, String prezime, String lozinka){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_IME + "=? AND " + COLUMN_PREZIME + "=? AND " + COLUMN_LOZINKA + "=?",
                                        new String[] {ime, prezime, lozinka}, null, null, null);

        if(cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        Korisnik korisnik = createKorisnik(cursor);
        close();
        return korisnik;
    }

    //napraviti metodu koja iz tabele pregleda iscitava sve preglede korisnika na
    //osnovu prosledjenog id-a

    public user_view readPregled(String id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_P, null, COLUMN_ID + "=?", new String[] {id}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        user_view user_view = createPregled(cursor);
        close();

        return user_view;
    }

    public admin_view[] readUredjaji() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_U, null, null, null, null, null, null);

        if (cursor.getCount() < 0){
            return null;
        }

        admin_view[] uredjaji = new admin_view[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            uredjaji[i++] = createUredjaj(cursor);
        }

        close();
        return uredjaji;
    }
    //dodati metodu koja ce da menja stanje uredjaja u bazi, da upise da li je aktivan ili nije u adapteru valjda



    //potrebno je izbirsati korisnika iz svih tabela ukoliko postoji zahtev za brisanjem
    public void deleteKorisnik(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] {id});
        db.delete(TABLE_NAME_P, COLUMN_ID + "=?", new String[] {id});
        close();
    }
    //obrisi sve uredjaje
    public void deleteUredjaji() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_U, null, null);
        close();
    }

    private Korisnik createKorisnik(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        String ime = cursor.getString(cursor.getColumnIndex(COLUMN_IME));
        String prezime = cursor.getString(cursor.getColumnIndex(COLUMN_PREZIME));
        String pol = cursor.getString(cursor.getColumnIndex(COLUMN_POL));
        String dr = cursor.getString(cursor.getColumnIndex(COLUMN_DR));
        String lozinka = cursor.getString(cursor.getColumnIndex(COLUMN_LOZINKA));
        String pregledi = cursor.getString(cursor.getColumnIndex(COLUMN_PREGLEDI));
        return new Korisnik(id,ime, prezime, pol, dr, lozinka, pregledi);
    }

    private user_view createPregled(Cursor cursor){
        String datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATUM_P));
        String pregled = cursor.getString(cursor.getColumnIndex(COLUMN_PREGLED_P));
        return new user_view(datum, pregled);
    }

    private admin_view createUredjaj(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID_U));
        String naziv = cursor.getString(cursor.getColumnIndex(COLUMN_NAZIV));
        int slika = cursor.getInt(cursor.getColumnIndex(COLUMN_SLIKA));
        int prekidac = cursor.getInt(cursor.getColumnIndex(COLUMN_PREKIDAC));

        boolean stanje_prekidaca;

        if(prekidac == 1)
            stanje_prekidaca = true;
        else
            stanje_prekidaca = false;

        return new admin_view(id, naziv, slika, stanje_prekidaca);
    }

}
