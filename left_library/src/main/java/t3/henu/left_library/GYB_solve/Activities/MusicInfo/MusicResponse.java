package t3.henu.left_library.GYB_solve.Activities.MusicInfo;

import android.os.Parcel;
import android.os.Parcelable;



public class MusicResponse implements Parcelable {
    public static final Parcelable.Creator<MusicResponse> CREATOR = new Parcelable.Creator<MusicResponse>() {
        @Override
        public MusicResponse createFromParcel(Parcel source) {
            return new MusicResponse(source);
        }

        @Override
        public MusicResponse[] newArray(int size) {
            return new MusicResponse[size];
        }
    };
    private Result result;
    private int code;

    public MusicResponse() {
    }

    protected MusicResponse(Parcel in) {
        this.result = in.readParcelable(Result.class.getClassLoader());
        this.code = in.readInt();
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.result, flags);
        dest.writeInt(this.code);
    }
}
