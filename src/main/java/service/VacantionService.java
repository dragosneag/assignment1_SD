package service;

import model.Destination;
import model.Vacantion;
import repository.VacantionRepository;

import java.util.List;

public class VacantionService {

    private final VacantionRepository vacantionRepository;

    public VacantionService() {
        vacantionRepository = new VacantionRepository();
    }

    public void createVacantion(Vacantion vacantion) {
        if (vacantion.getName() != null && vacantion.getPrice() != null && vacantion.getStartDate() != null && vacantion.getEndDate() != null && vacantion.getAvailable_spots() != null) {
            vacantionRepository.insertVacantion(vacantion);
        } else {
            System.out.println("Invalid vacantion. Could not insert in database.");
        }
    }

    public Vacantion getVacantionByName(String name) {
        if (name != null && !name.isEmpty()) {
            return vacantionRepository.findByVacantionName(name);
        } else {
            System.out.println("Invalid name. Could not find in database.");
            return null;
        }
    }

    public void updateVacantion(String name, Vacantion vacantion) {
        if (name != null && !name.isEmpty() && vacantion.getName() != null && vacantion.getPrice() != null && vacantion.getStartDate() != null && vacantion.getEndDate() != null && vacantion.getAvailable_spots() != null) {
            vacantionRepository.updateVacantion(name, vacantion);
        } else {
            System.out.println("Invalid vacantion. Could not update in database.");
        }
    }

    public void deleteVacantion(String name) {
        if (name != null && !name.isEmpty()) {
            vacantionRepository.deleteVacantion(name);
        } else {
            System.out.println("Invalid name. Could not delete in database.");
        }
    }

    public List<Vacantion> getAllVacantions() {
        return vacantionRepository.getAllVacantions();
    }
}
