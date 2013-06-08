import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Homework31 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("school");

		DBCollection collection = db.getCollection("students");

		DBCursor cursor = collection.find().sort(new BasicDBObject("_id", 1));

		try {
			while (cursor.hasNext()) {
				BasicDBObject dbObject = (BasicDBObject) cursor.next();

				int id = (Integer) dbObject.get("_id");

				ArrayList<BasicDBObject> scores = (ArrayList<BasicDBObject>) dbObject
						.get("scores");

				long lowScore = 0L;

				for (BasicDBObject scoreObject : scores) {
					if ("homework".equals(scoreObject.get("type"))) {
						long score = scoreObject.getLong("score");
						if (lowScore == 0L) {
							lowScore = score;
						}

						if (score < lowScore) {
							lowScore = score;
						}
					}
				}

				Iterator iterator = scores.iterator();

				while (iterator.hasNext()) {
					BasicDBObject scoreObject = (BasicDBObject) iterator.next();

					if ("homework".equals(scoreObject.get("type"))) {
						long score = scoreObject.getLong("score");
						if (score == lowScore) {
							iterator.remove();
						}
					}

				}
				//
				// collection.update(new BasicDBObject("_id", id),
				// new BasicDBObject("$set", new BasicDBObject("scores",
				// scores)));

			}

		} finally {
			cursor.close();
		}
	}
}
