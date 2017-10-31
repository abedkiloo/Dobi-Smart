package com.abedkiloo.abednego.dobismart.activities;

/**
 * Created by abedkiloo on 10/31/17.
 */

public class Artisan {
    String artisan_id;
    String artisan_first_name;
    String artisan_second_name;
    String artisan_phone_number;
    String artisan_specilization;
    String artisan_opreation_locality;
    String artisan_opreation_password;

    public Artisan() {
    }

    public Artisan(String _id,String first_name, String second_name, String phone_number, String specialization,
                   String locality, String password) {
        this.artisan_id = _id;
        this.artisan_first_name = first_name;
        this.artisan_second_name = second_name;
        this.artisan_phone_number = phone_number;
        this.artisan_specilization = specialization;
        this.artisan_opreation_locality = locality;
        this.artisan_opreation_password = password;
    }

    public String getArtisan_first_name() {
        return artisan_first_name;
    }

    public void setArtisan_first_name(String artisan_first_name) {
        this.artisan_first_name = artisan_first_name;
    }

    public String getArtisan_second_name() {
        return artisan_second_name;
    }

    public void setArtisan_second_name(String artisan_second_name) {
        this.artisan_second_name = artisan_second_name;
    }

    public String getArtisan_phone_number() {
        return artisan_phone_number;
    }

    public void setArtisan_phone_number(String artisan_phone_number) {
        this.artisan_phone_number = artisan_phone_number;
    }

    public String getArtisan_specilization() {
        return artisan_specilization;
    }

    public void setArtisan_specilization(String artisan_specilization) {
        this.artisan_specilization = artisan_specilization;
    }

    public String getArtisan_opreation_locality() {
        return artisan_opreation_locality;
    }

    public void setArtisan_opreation_locality(String artisan_opreation_locality) {
        this.artisan_opreation_locality = artisan_opreation_locality;
    }
}
