package com.esgi.flexges.repository;

import com.esgi.flexges.model.Enterprise;
import com.esgi.flexges.model.Room;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class RoomRepository {

    @Autowired
    private Firestore firestore;

    private final Logger logger = LoggerFactory.getLogger(RoomRepository.class);


    public void addRooms(List<Room> rooms, Enterprise enterprise) throws ExecutionException, InterruptedException {
        for(Room r : rooms){
            ApiFuture<QuerySnapshot> future_get = firestore.collection("rooms")
                    .whereEqualTo("enterprise", r.getEnterprise()).whereEqualTo("name", r.getName()).get();

            QuerySnapshot doc = future_get.get();

            if(!doc.isEmpty()){
                ApiFuture<WriteResult> future_insert = firestore.collection("rooms")
                        .document(enterprise.getName()+r.getName()).set(r);
                logger.info("Document room already exists, updated time : " + future_insert.get().getUpdateTime());

            }else{
                ApiFuture<WriteResult> future_insert = firestore.collection("rooms")
                        .document(enterprise.getName()+r.getName()).set(r);
                logger.info("Document room insert time : " + future_insert.get().getUpdateTime());
            }
        }
    }

    public List<Room> getUserRooms(String enterprise) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection("rooms").whereEqualTo("enterprise", enterprise).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream().map(r -> r.toObject(Room.class)).collect(Collectors.toList());
    }
}
