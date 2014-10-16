package com.journaldev.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.converter.TaskConverter;
import com.journaldev.mongodb.model.Task;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoDBTaskDAO {

	private DBCollection col;

	public MongoDBTaskDAO(MongoClient mongo) {
		this.col = mongo.getDB("journaldev").getCollection("Task");
	}

	public Task createTask(Task p) {
		DBObject doc = TaskConverter.toDBObject(p);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;
	}

	public void updateTask(Task p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.update(query, TaskConverter.toDBObject(p));
	}

	public List<Task> readAllTask() {
		List<Task> data = new ArrayList<Task>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			Task p = TaskConverter.toTask(doc);
			data.add(p);
		}
		return data;
	}

	public void deleteTask(Task p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.remove(query);
	}

	public Task readTask(Task p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		DBObject data = this.col.findOne(query);
		return TaskConverter.toTask(data);
	}

}
