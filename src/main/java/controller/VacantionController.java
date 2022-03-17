package controller;

import model.Destination;
import model.Vacantion;
import service.VacantionService;

import java.time.LocalDate;
import java.util.List;

public class VacantionController {

    private final VacantionService vacantionService;

    public VacantionController() {
        vacantionService = new VacantionService();
    }

    public void createVacantion(String name, Integer price, LocalDate startDate, LocalDate endDate, String details, Integer available_spots, Destination destination) {
        Vacantion vacantion = new Vacantion(name, price, startDate, endDate, details, available_spots, destination);
        vacantionService.createVacantion(vacantion);
    }

    public Vacantion getByName(String name) {
        return vacantionService.getVacantionByName(name);
    }

    public void updateVacantion(String initialName, Vacantion newVacantion) {
        Vacantion vacantion = newVacantion;
        vacantionService.updateVacantion(initialName, vacantion);
    }

    public void deleteVacantion(String name) {
        vacantionService.deleteVacantion(name);
    }

    public List<Vacantion> getAll() {
        return vacantionService.getAllVacantions();
    }
}
