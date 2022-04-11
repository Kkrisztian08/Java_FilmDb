package hu.petrik.filmdb.controllers;

import hu.petrik.filmdb.Controller;
import hu.petrik.filmdb.Film;
import hu.petrik.filmdb.FilmApp;
import hu.petrik.filmdb.FilmDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainController extends Controller {

    @FXML
    private TableView<Film> filmTable;
    @FXML
    private TableColumn<Film, String> colCim;
    @FXML
    private TableColumn<Film, String> colKategoria;
    @FXML
    private TableColumn<Film, Integer> colHossz;
    @FXML
    private TableColumn<Film, Integer> colErtekeles;
    private FilmDb db;

    public void initialize(){
        colCim.setCellValueFactory(new PropertyValueFactory<>("cim"));
        //a tárolt objektumban egy getCim függvényt fog keresni.
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colHossz.setCellValueFactory(new PropertyValueFactory<>("hossz"));
        colErtekeles.setCellValueFactory(new PropertyValueFactory<>("ertekeles"));
        try {
            db = new FilmDb();
            filmListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }



    @FXML
    public void onModositasButtonClick(ActionEvent actionEvent) {
        int selectedIndex = filmTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A módisításhoz előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Film modositando= filmTable.getSelectionModel().getSelectedItem();
        try {
            ModositController modosit=(ModositController) ujAblak("modosit-view","Modosit",320,400);
            modosit.setModositando(modositando);
            modosit.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex=filmTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez elöbb válasszon ki egy elemet");
            return;
        }else {
            Film torlendoFilm=filmTable.getSelectionModel().getSelectedItem();
            if (!confirm("Biztos hogy szeretné törölni az alábbi filmet: "+torlendoFilm.getCim())) {
                return;
            }
            try {
                db.filmTorlese(torlendoFilm.getId());
                alert("Sikeres törés!");
                filmListaFeltolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onHozzadasButtonClick(ActionEvent actionEvent) {
        try {
            Controller hozzadas=ujAblak("hozzaad-view.fxml","FilmDb",320,400);
            hozzadas.getStage().setOnCloseRequest(event->filmListaFeltolt());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    private void filmListaFeltolt(){
        try {
            List<Film> filmList = db.getFilmek();
            filmTable.getItems().clear();
            for(Film film: filmList){
                filmTable.getItems().add(film);
            }
        } catch (SQLException e) {
           hibaKiir(e);
        }

    }


}