package cl.luckio.dibuje.models;

/**
 * Created by Esteban Muñoz on 29/08/2016.
 */

public class UserModel {

    private int mId;
    private String mNombre;
    private String mEmail;
    private String mPass;
    private String mImagen;

    public UserModel(int id, String imagen, String pass, String email, String nombre) {
        mId = id;
        mImagen = imagen;
        mPass = pass;
        mEmail = email;
        mNombre = nombre;
    }

    public String getImagen() {
        return mImagen;
    }

    public void setImagen(String imagen) {
        mImagen = imagen;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
