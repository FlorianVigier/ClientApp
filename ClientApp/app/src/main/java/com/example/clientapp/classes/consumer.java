package com.example.clientapp.classes;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import org.bson.types.ObjectId;


public class consumer extends RealmObject {
    @PrimaryKey
    private String email;
    private ObjectId _id;
    private String clientid;
    private String password;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    public String getClientid() { return clientid; }
    public void setClientid(String clientid) { this.clientid = clientid; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}