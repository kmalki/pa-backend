package com.esgi.flexges.repository;

import com.esgi.flexges.model.Enterprise;
import com.esgi.flexges.model.Room;
import com.esgi.flexges.model.UserApp;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class EnterpriseRepository {

    @Autowired
    private Firestore firestore;

    private final Logger logger = LoggerFactory.getLogger(EnterpriseRepository.class);

    public void addEnterprise(Enterprise enterprise) throws Exception {

        ApiFuture<DocumentSnapshot> future_get = firestore.collection("enterprises").document(enterprise.getName()).get();

        DocumentSnapshot doc = future_get.get();

        if(doc.exists()){
            ApiFuture<WriteResult> future_insert = firestore.collection("enterprises")
                    .document(enterprise.getName()).set(enterprise);
            logger.info("Document enterprise already exists, updated : " + future_insert.get().getUpdateTime());

        }else{
            ApiFuture<WriteResult> future_insert = firestore.collection("enterprises")
                    .document(enterprise.getName()).set(enterprise);
            logger.info("Document enterprise insert time : " + future_insert.get().getUpdateTime());
        }
    }


    public List<UserApp> getEmployees(String enterprise) throws ExecutionException, InterruptedException {
//        logger.info(enterprise.getName());
        ApiFuture<QuerySnapshot> future_get = firestore.collection("users").whereEqualTo("enterprise", enterprise).get();
        return future_get.get().toObjects(UserApp.class);
    }
}
