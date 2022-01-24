package com.SweetDreams.sweetDreams.Models;

public enum Perfil {

    admin(1,"ROLE_ADMIN"),
    cliente(2, "ROLE_CLIENTE"),
    vendedor(3, "ROLE_VENDEDOR");

    private int code;
    private String role;

    Perfil(int code, String role) {
        this.code = code;
        this.role = role;
    }

    Perfil() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public static Perfil toEnum(Integer code){
        if (code==null){
            return null;
        }
        for (Perfil n: Perfil.values()){
            if (code.equals(n.getCode())){
                return n;
            }
        }
        throw new IllegalArgumentException("ID inválido: " + code);
    }
}
