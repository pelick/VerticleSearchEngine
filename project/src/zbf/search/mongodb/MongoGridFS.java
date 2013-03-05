package zbf.search.mongodb;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoGridFS {
	
	private Mongo connection;  
	private DB db;  
	private DBCollection collection;  
	private GridFS myFS;  
  
	private String mongoDBHost = "localhost";  
	private int mongoDBPort = 27017;  
	private String dbName = "pdf";  
	private String collectionName = "fs";  
  
    public static void main(String[] args) throws MongoException, IOException, NoSuchAlgorithmException {  
    	MongoGridFS t = new MongoGridFS();  
        
        String fileName = "E:\\2.pdf";  
        String name = "2.pdf";  
        
        //把文件保存到gridfs中，并以文件的md5值为id  
        t.save(new FileInputStream(fileName), name);  
          
        //据文件名从gridfs中读取到文件  
        
        GridFSDBFile gridFSDBFile = t.getByFileName(name);  
        if(gridFSDBFile != null){  
            System.out.println("filename:" + gridFSDBFile.getFilename());  
            System.out.println("md5:" + gridFSDBFile.getMD5());  
            System.out.println("length:" + gridFSDBFile.getLength());  
            System.out.println("uploadDate:" + gridFSDBFile.getUploadDate());  
              
            System.out.println("--------------------------------------");  
            //gridFSDBFile.writeTo(System.out);  
        }else{  
            System.out.println("can not get file by name:" + name);  
        }  
    }  
  
    public MongoGridFS() throws UnknownHostException, MongoException, NoSuchAlgorithmException {  
        _init();  
    }  
      
  
    public MongoGridFS(String mongoDBHost, int mongoDBPort, String dbName,  
            String collectionName) throws UnknownHostException, MongoException, NoSuchAlgorithmException {  
        this.mongoDBHost = mongoDBHost;  
        this.mongoDBPort = mongoDBPort;  
        this.dbName = dbName;  
        this.collectionName = collectionName;  
        _init();  
    }  
      
      
    private void _init() throws UnknownHostException, MongoException, NoSuchAlgorithmException{  
        connection = new Mongo(mongoDBHost, mongoDBPort);  
        db = connection.getDB(dbName);  
        collection = db.getCollection(collectionName);  
        myFS = new GridFS(db);  
    }  
      
    /** 
     * 用给出的id，保存文件，透明处理已存在的情况 
     * id 可以是string，long，int，org.bson.types.ObjectId 类型 
     * @param in 
     * @param id 
     */  
    public void save(InputStream in, String filename){  
        DBObject query  = new BasicDBObject("_id", filename);  
        GridFSDBFile gridFSDBFile = myFS.findOne(query);  
          
        if(gridFSDBFile != null)  
            return;  
        
        GridFSInputFile gridFSInputFile = myFS.createFile(in);
        gridFSInputFile.setFilename(filename);
        gridFSInputFile.setId(filename);
        gridFSInputFile.save();  
        return;  
    }  
      
    /** 
     * 据id返回文件 
     * @param id 
     * @return 
     */  
    public GridFSDBFile getById(Object id){  
        DBObject query  = new BasicDBObject("_id", id);  
        GridFSDBFile gridFSDBFile = myFS.findOne(query);  
        return gridFSDBFile;  
    }  
      
    /** 
     * 据文件名返回文件，只返回第一个 
     * @param fileName 
     * @return 
     */  
    public GridFSDBFile getByFileName(String fileName){  
        DBObject query  = new BasicDBObject("_id", fileName);  
        GridFSDBFile gridFSDBFile = myFS.findOne(query);  
        return gridFSDBFile;  
    }  
}
