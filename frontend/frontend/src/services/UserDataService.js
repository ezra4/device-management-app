import http from "../http-common";

class UserDataService{
    getAll() {
        return http.get("/admin");
    }

    create(data){
        return http.post("/admin/addUser",data);
    }

    update(id,data){
        return http.put(`/admin/updateUser/${id}`,data);
    }

    delete(id){
        return http.delete(`/admin/deleteUser/${id}`);
    }

    deleteAll(){
        return http.delete("/admin/deleteAllUsers");
    }

    addDevice2User(id1,id2){
        return http.put(`/admin/addDevice2User/user-${id1}&device-${id2}`);
    }

    getClientDevices(id){
        return http.get(`/client/${id}`);
    }
}

export default new UserDataService();