package com.giraone.soap;

public class BankDetails {

    private String bezeichnung;
    private String bic;
    private String ort;
    private String plz;

    public BankDetails(String bezeichnung, String bic, String ort, String plz) {
        this.bezeichnung = bezeichnung;
        this.bic = bic;
        this.ort = ort;
        this.plz = plz;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getBic() {
        return bic;
    }

    public String getOrt() {
        return ort;
    }

    public String getPlz() {
        return plz;
    }

    @Override
    public String toString() {
        return "BankDetails{" +
            "bezeichnung='" + bezeichnung + '\'' +
            ", bic='" + bic + '\'' +
            ", ort='" + ort + '\'' +
            ", plz='" + plz + '\'' +
            '}';
    }
}
