import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServicesRoutingModule } from './services-routing.module';
import { ServicesComponent } from './services.component';
import { AddServiceComponent } from './add-service/add-service.component';


@NgModule({
  declarations: [
    ServicesComponent,
    AddServiceComponent
  ],
  imports: [
    CommonModule,
    ServicesRoutingModule
  ]
})
export class ServicesModule { }
