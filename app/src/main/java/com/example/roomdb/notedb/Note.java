package com.example.roomdb.notedb;


import android.provider.SyncStateContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = Constants.TABLE_NAME_NOTE)
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long note_id;

    @ColumnInfo(name="note_content")
    private String content;
    private  String title;
    private Date date;

    public Note (String content, String title ) {

        this.content = content;
        this.title = title;
        this.date =new Date(System.currentTimeMillis());
    }

    @Ignore
    public Note(){


    }

    public Date getDate(){
        return  date;
    }

    public  void setDate(Date date){
        this.date=date;
    }

    public long getNote_id(){
        return  note_id;
    }
    public  void setNote_id(long note_id){
        this.note_id=note_id;
    }
    public  String getContent(){
        return  content;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getTitle(){
        return  title;
    }
    public  void setTitle(String title){
        this.title=title;
    }

    @Override
    public  boolean equals(@Nullable Object obj){
        if(this==obj){
            return true;
        }
        if(!(obj instanceof Note)) return false;

        Note note=(Note) obj;

        if(note_id !=note.note_id) return  false;

        return title!=null ? title.equals(note.title) : note.title==null;

    }

    @Override
    public int hashCode() {
        int result=(int) note_id;
        result=31*result+(title!=null ? title.hashCode():0);

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "None{" +
                "note_id="+note_id +
                ",content='"+content+'-'+
                ",title='"+title+'-'+
                ",date="+date+
                '}';

    }
}
