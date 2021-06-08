package com.esgi.flexges.service;

import com.esgi.flexges.model.Enterprise;
import com.esgi.flexges.model.Room;
import com.esgi.flexges.model.UserApp;
import com.esgi.flexges.repository.EnterpriseRepository;
import com.esgi.flexges.repository.RoomRepository;
import com.esgi.flexges.repository.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class EnterpriseService {

    private final Logger logger = LoggerFactory.getLogger(EnterpriseService.class);

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

//    public void createEnterprise(Enterprise enterprise) throws Exception {
//        enterpriseRepository.addEnterprise(enterprise);
//    }

    public void configureEnterprise(MultipartFile file, Enterprise enterprise) throws Exception {
        List<Room> rooms = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Room tempRoom;
        boolean passHeader = false;
        for (Row currentRow : sheet) {
            if(!passHeader){
                passHeader = true;
                continue;
            }
            tempRoom = new Room();
            for (Cell currentCell : currentRow) {
                if (currentCell.getCellType() == CellType.STRING) {
                    tempRoom.setName(currentCell.getStringCellValue());
                } else if (currentCell.getCellType() == CellType.NUMERIC) {
                    tempRoom.setCapacity((int) currentCell.getNumericCellValue());
                }
            }
            tempRoom.setEnterprise(enterprise.getName());
            rooms.add(tempRoom);
        }
        Sheet sheet2 = workbook.getSheetAt(1);
        passHeader = false;
        List<String> employees = new ArrayList<>();
        for (Row currentRow : sheet2) {
            if(!passHeader){
                passHeader = true;
                continue;
            }
            for (Cell currentCell : currentRow) {
                if (currentCell.getCellType() == CellType.STRING) {
                    employees.add(currentCell.getStringCellValue());
                }
            }
        }


        userRepository.updateUsersRights(employees.stream().map(
                e -> new UserApp(e, enterprise.getName())
                ).collect(Collectors.toList()), false);
        enterpriseRepository.addEnterprise(enterprise);
        roomRepository.addRooms(rooms);
    }


    public List<String> getEmployees(Enterprise enterprise) throws ExecutionException, InterruptedException {
        return enterpriseRepository.getEmployees(enterprise);
    }

    public void addEmployees(List<UserApp> users) throws Exception {
        for(UserApp us : users){
            userRepository.addUser(us);
        }
        userRepository.updateUsersRights(users, false);
    }

    public void updateEmployees(List<UserApp> users) throws ExecutionException, InterruptedException {
        userRepository.updateEmployees(users);
    }


    public void kickEmployees(List<UserApp> users) throws ExecutionException, InterruptedException {
        userRepository.updateUsersRights(users, true);
    }

    public void updateRooms(List<Room> rooms) throws ExecutionException, InterruptedException {
        roomRepository.updateRooms(rooms);
    }

    public void deleteRooms(List<Room> rooms) throws ExecutionException, InterruptedException {
        roomRepository.deleteRooms(rooms);
    }
}
