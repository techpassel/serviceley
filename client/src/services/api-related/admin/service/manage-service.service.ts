import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { ServiceType } from "src/models/service-type.data";

@Injectable({
    providedIn: 'root'
})
//The @Injectable() decorator specifies that Angular can use this class in the DI(Dependency injection) system. 
//The metadata, providedIn: 'root', means that the AuthService is visible throughout the application.
export class ManageService {
    baseApiUrl: string = environment.baseApiUrl;
    constructor(private http: HttpClient) {
    }

    isServiceExist(serviceName: string): any {
        return this.http.get(this.baseApiUrl + "/user/service/check/" + serviceName)
    }

    addUpdateService(serviceType: ServiceType): any {
        return this.http.post(this.baseApiUrl + "/admin/service", serviceType);
    }

    getServiceDetails(id: number): any {
        return this.http.get(this.baseApiUrl + "/user/service/" + id);
    }
}