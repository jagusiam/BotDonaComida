package org.botdonacomida.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int idUsuarioDb;
    private String nombreUsuario;
    private String apellidos;
    private Long idUsuarioTlgrm;
    private String nomUsuarioTlgrm;


    public Usuario() {
    }

    public Usuario(String nombreUsuario, String apellidos, Long idUsuarioTlgrm, String nomUsuarioTlgrm) {
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.idUsuarioTlgrm = idUsuarioTlgrm;
        this.nomUsuarioTlgrm = nomUsuarioTlgrm;
    }

    public Usuario(int idUsuarioDb, String nombreUsuario, String apellidos, Long idUsuarioTlgrm, String nomUsuarioTlgrm) {
        this.idUsuarioDb = idUsuarioDb;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.idUsuarioTlgrm = idUsuarioTlgrm;
        this.nomUsuarioTlgrm = nomUsuarioTlgrm;
    }

    public int getIdUsuarioDb() {
        return idUsuarioDb;
    }

    public void setIdUsuarioDb(int idUsuarioDb) {
        this.idUsuarioDb = idUsuarioDb;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getIdUsuarioTlgrm() {
        return idUsuarioTlgrm;
    }

    public void setIdUsuarioTlgrm(Long idUsuarioTlgrm) {
        this.idUsuarioTlgrm = idUsuarioTlgrm;
    }

    public String getNomUsuarioTlgrm() {
        return nomUsuarioTlgrm;
    }

    public void setNomUsuarioTlgrm(String nomUsuarioTlgrm) {
        this.nomUsuarioTlgrm = nomUsuarioTlgrm;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuarioDb=" + idUsuarioDb +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", idUsuarioTlgrm=" + idUsuarioTlgrm +
                ", nomUsuarioTlgrm='" + nomUsuarioTlgrm + '\'' +
                '}';
    }
}
