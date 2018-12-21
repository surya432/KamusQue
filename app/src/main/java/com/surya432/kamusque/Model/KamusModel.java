package com.surya432.kamusque.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class KamusModel implements Parcelable {
    public int id;
    public  String key, terjemahan;
    public KamusModel(){
    }
    public KamusModel(String key, String terjemahan){
        this.key = key;
        this.terjemahan = terjemahan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTerjemahan() {
        return terjemahan;
    }

    public void setTerjemahan(String terjemahan) {
        this.terjemahan = terjemahan;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.key);
        dest.writeString(this.terjemahan);
    }

    protected KamusModel(Parcel in) {
        this.id = in.readInt();
        this.key = in.readString();
        this.terjemahan = in.readString();
    }

    public static final Creator<KamusModel> CREATOR = new Creator<KamusModel>() {
        @Override
        public KamusModel createFromParcel(Parcel source) {
            return new KamusModel(source);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };
}
