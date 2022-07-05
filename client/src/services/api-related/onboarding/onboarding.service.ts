import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Address } from 'src/models/address-data';
import { UserData } from 'src/models/user-data';

@Injectable({
    providedIn: 'root'
})
//The @Injectable() decorator specifies that Angular can use this class in the DI(Dependency injection) system. 
//The metadata, providedIn: 'root', means that the HeroService is visible throughout the application.
export class OnboardingService {
    private baseApiUrl: string = environment.baseApiUrl;

    constructor(private http: HttpClient) {
    }

    getUser(id: number): any {
        return this.http.get(this.baseApiUrl + '/user/' + id);
    }

    updateUser(userData: UserData): any {
        return this.http.put(this.baseApiUrl + '/user/', userData);
    }

    updateAddress(address: Address): any {
        return this.http.post(this.baseApiUrl + "/user/address", address);
    }

    getUserAddresses(userId: number): any {
        return this.http.get(this.baseApiUrl + '/user/address/' + userId);
    }

    updatePhone(data: any): any {
        return this.http.post(this.baseApiUrl + "/user/phone", data, { responseType: 'text' });
    }

    verifyOtp(data: any): any {
        return this.http.post(this.baseApiUrl + '/user/otp', data, { responseType: 'text' });
    }
}