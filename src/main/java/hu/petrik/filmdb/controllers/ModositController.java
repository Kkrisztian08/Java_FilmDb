package hu.petrik.filmdb.controllers;

import hu.petrik.filmdb.Controller;
import hu.petrik.filmdb.Film;
import hu.petrik.filmdb.FilmDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class ModositController extends Controller {
    @FXML
    private TextField inputCim;
    @FXML
    private TextField inputKategoria;
    @FXML
    private Spinner<Integer> inputHossz;
    @FXML
    private ChoiceBox<Integer> inputErtekeles;
    private Film modositando;

    @FXML
    public void onModositButtonClick(ActionEvent actionEvent) {
        String cim = inputCim.getText().trim();
        String kategoria = inputKategoria.getText().trim();
        int hossz = 0;
        int ertekelesIndex = inputErtekeles.getSelectionModel().getSelectedIndex();
        if (cim.isEmpty()){
            alert("Cím megadása kötelező");
            return;
        }
        if (kategoria.isEmpty()){
            alert("Kategória megadása kötelező");
            return;
        }
        try {
            hossz = inputHossz.getValue();
        } catch (NullPointerException ex){
            alert("A hossz megadása kötelező");
            return;
        } catch (Exception ex){
            System.out.println(ex);
            alert("A hossz csak 1 és 999 közötti szám lehet");
            return;
        }
        if (hossz < 1 || hossz > 999) {
            alert("A hossz csak 1 és 999 közötti szám lehet");
            return;
        }
        if (ertekelesIndex == -1){
            alert("Értékelés kiválasztása köztelező");
            return;
        }
        System.out.println(hossz);
        int ertekeles = inputErtekeles.getValue();

        modositando.setCim(cim);
        modositando.setKategoria(kategoria);
        modositando.setHossz(hossz);
        modositando.setErtekeles(ertekeles);


        try {
            FilmDb db=new FilmDb();
            if (db.filmModositasa(modositando)) {
                alertWait("Sikeres modositas");
                this.stage.close();
            }else {
                alert("Sikertelen modositas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Film getModositando() {
        return modositando;
    }

    public void setModositando(Film modositando) {
        this.modositando = modositando;
    }
}
