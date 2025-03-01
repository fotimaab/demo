package com.task10.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.UUID;

@Data
@DynamoDbBean
public class Reservation {
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlotTimeStart() {
        return slotTimeStart;
    }

    public void setSlotTimeStart(String slotTimeStart) {
        this.slotTimeStart = slotTimeStart;
    }

    public String getSlotTimeEnd() {
        return slotTimeEnd;
    }

    public void setSlotTimeEnd(String slotTimeEnd) {
        this.slotTimeEnd = slotTimeEnd;
    }

    @JsonIgnore
    private String reservationId;
    private int tableNumber;
    private String clientName;
    private String phoneNumber;
    private String date;
    private String slotTimeStart;
    private String slotTimeEnd;

    // @DynamoDbBean annotation requires existence of Default constructor
    public Reservation() {}

    public Reservation(String reservationId, int tableNumber, String clientName, String phoneNumber, String date, String slotTimeStart, String slotTimeEnd) {
        if (reservationId == null) {
            this.reservationId = UUID.randomUUID().toString();
        } else {
            this.reservationId = reservationId;
        }
        this.tableNumber = tableNumber;
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.slotTimeStart = slotTimeStart;
        this.slotTimeEnd = slotTimeEnd;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    @JsonIgnore
    public String getReservationId() {
        if (reservationId == null) {
            this.reservationId = UUID.randomUUID().toString();
        }
        return reservationId;
    }

    public static Reservation fromJson(JSONObject jsonObject) {
        String reservationId;
        try {
            reservationId = jsonObject.getString("reservationId").isEmpty() ? null : jsonObject.getString("reservationId");
        } catch (JSONException e) {
            reservationId = null;
        }

        return new Reservation(
                reservationId,
                jsonObject.getInt("tableNumber"),
                jsonObject.getString("clientName"),
                jsonObject.getString("phoneNumber"),
                jsonObject.getString("date"),
                jsonObject.getString("slotTimeStart"),
                jsonObject.getString("slotTimeEnd")
        );
    }

    public static Reservation fromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return fromJson(jsonObject);
    }
}
