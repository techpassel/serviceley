import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ServicesRoutingModule } from './services-routing.module';
import { ServicesComponent } from './services.component';
import { AddServiceComponent } from './add-service/add-service.component';
import { UniqueServiceTypeValidatorDirective } from 'src/app/directives/unique-servicetype-validator.directive';

@NgModule({
  declarations: [
    ServicesComponent,
    AddServiceComponent,
    UniqueServiceTypeValidatorDirective
  ],
  imports: [
    CommonModule,
    ServicesRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ServicesModule { }
