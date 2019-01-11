package com.example.administrator.myapplication13.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class CartBean implements Parcelable {

    private String message;
    private String status;
    private List<ResultBean> result;
    private final String SUCCESS_PARAMETER="0000";

    protected CartBean(Parcel in) {
        message = in.readString();
        status = in.readString();
        result = in.createTypedArrayList(ResultBean.CREATOR);

    }

    public static final Creator<CartBean> CREATOR = new Creator<CartBean>() {
        @Override
        public CartBean createFromParcel(Parcel in) {
            return new CartBean(in);
        }

        @Override
        public CartBean[] newArray(int size) {
            return new CartBean[size];
        }
    };

    public boolean isSuccess(){
        return status.equals(SUCCESS_PARAMETER);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(status);
        dest.writeTypedList(result);
        dest.writeString(SUCCESS_PARAMETER);
    }

    public static class ResultBean implements Parcelable {

        private int commodityId;
        private String commodityName;
        private int count;
        private String pic;
        private double  price;
        private boolean isCheck=false;

        protected ResultBean(Parcel in) {
            commodityId = in.readInt();
            commodityName = in.readString();
            count = in.readInt();
            pic = in.readString();
            price = in.readDouble();
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double  getPrice() {
            return price;
        }

        public void setPrice(double  price) {
            this.price = price;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(commodityId);
            dest.writeString(commodityName);
            dest.writeInt(count);
            dest.writeString(pic);
            dest.writeDouble(price);
        }
    }
}
