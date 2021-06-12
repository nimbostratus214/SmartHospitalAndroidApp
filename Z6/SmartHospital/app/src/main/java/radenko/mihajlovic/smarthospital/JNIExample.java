package radenko.mihajlovic.smarthospital;

public class JNIExample {

    static {
        System.loadLibrary("PassHash");
    }
    //metode vrste sifrovanje i desifrovanje korisnicke sifre pomocu kljuca
    //hashKey = br_ind + godina_upisa (214+2018 or 2142018 ?)
    public native int hashPassword(int passToHash, int hashKey);
    public native int unhashPassword(int unhashKey, int hashedPass);

}
