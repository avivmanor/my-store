package com.company;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


public class Mongo {
    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    DB database = mongoClient.getDB("MyGym");
    DBCollection collection = database.getCollection("GymClients");

}
