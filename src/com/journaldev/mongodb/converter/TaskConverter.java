package com.journaldev.mongodb.converter;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.model.Task;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class TaskConverter {

	// convert Person Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(Task p) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("task", p.getTask()).append("status", p.getStatus()).append("priority", p.getPriority()).append("duedate", p.getDuedate());
		if (p.getId() != null)
			builder = builder.append("_id", new ObjectId(p.getId()));
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static Task toTask(DBObject doc) {
		Task p = new Task();
		p.setTask((String) doc.get("task"));
		p.setStatus((String) doc.get("status"));
		p.setPriority((String) doc.get("priority"));
		p.setDuedate((String) doc.get("duedate"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;

	}
	
}
