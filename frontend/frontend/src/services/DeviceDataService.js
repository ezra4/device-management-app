import http from "../http-common";

class DeviceDataService {
    getAll(){
        return http.get("/devices");
    }

    create(data){
        return http.post("/devices/addDevice",data);
    }

    update(id,data){
        return http.put(`/devices/updateDevice/${id}`,data);
    }

    delete(id){
        return http.delete(`/devices/deleteDevice/${id}`);
    }

    getConsumptionsByDate(id,data){
        return http.post(`/devices/${id}/consumptions`,data);
    }
}

export default new DeviceDataService();