/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

import java.sql.ResultSet;

/**
 *
 * @author nicos
 */
public class Main {
    public static void main(String[] args) {
        database db = new database();
        new FormLogin().setVisible(true);
    }
}
