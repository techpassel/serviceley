import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { UserData } from 'src/models/user-data';

@Injectable({
    providedIn: 'root'
})
//The @Injectable() decorator specifies that Angular can use this class in the DI(Dependency injection) system. 
//The metadata, providedIn: 'root', means that the HeroService is visible throughout the application.
export class OnboardingService {
    baseApiUrl: string = environment.baseApiUrl;

    constructor(private http: HttpClient) { 
    }

    getUser(id: number): any {
        return this.http.get(this.baseApiUrl + '/user/'+id);
    }

    updateUser(userData: UserData): any {
        return this.http.put(this.baseApiUrl + '/user/', userData);
    }
}