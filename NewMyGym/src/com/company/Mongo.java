package com.company;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Mongo {
    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
}
