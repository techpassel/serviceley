import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddServiceComponent } from './add-service/add-service.component';
import { ServicesComponent } from './services.component';

const routes: Routes = [
  { path: '', component: ServicesComponent },
  { path: 'add', component: AddServiceComponent },
  { path: 'update/:id', component: AddServiceComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServicesRoutingModule { }
