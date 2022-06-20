import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { LoginRequestData } from 'src/models/login-request-data';
import { SignupRequestData } from 'src/models/signup-request-data';

const headers = new HttpHeaders(
  {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Credentials': 'true',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, PATCH, DELETE, PUT, OPTIONS',
    'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With',
  }
);

@Injectable({
  providedIn: 'root'
})
//The @Injectable() decorator specifies that Angular can use this class in the DI(Dependency injection) system. 
//The metadata, providedIn: 'root', means that the HeroService is visible throughout the application.
export class AuthService {

  baseApiUrl: string = environment.baseApiUrl;

  constructor(private http: HttpClient) { }
  //HttpClient API is available in package @angular/common/http. It replaces the older HttpModule. 
  //The HTTP Client makes use of the RxJs Observables. The Response from the HttpClient is observable, hence it needs to be Subscribed.
  //To use HttpClient, first we need to import the Angular HttpClientModule in the root AppModule(Check AppModule for details).

  login(loginRequestData: LoginRequestData): any {
    return this.http.post(this.baseApiUrl + '/auth/login', loginRequestData, { headers });
  }

  signup(signupRequestData: SignupRequestData): any {
    return this.http.post(this.baseApiUrl + '/auth/signup', signupRequestData, { headers, responseType: 'text' });
  }

  resendActivationEmail(username: string): any {
    return this.http.post(this.baseApiUrl + '/auth/resend-activation-email', username, { responseType: 'text' });
  }

  deleteSession(session: string): any {
    return this.http.get(this.baseApiUrl + '/auth/logout/' + session);
  }
}
