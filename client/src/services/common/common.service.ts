import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
//The @Injectable() decorator specifies that Angular can use this class in the DI(Dependency injection) system. 
//The metadata, providedIn: 'root', means that the HeroService is visible throughout the application.
export class CommonService {
  capitalizeFirstLetter(str: string): string {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }

  constructor() { }
}
