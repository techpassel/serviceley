import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PublicGuard } from 'src/route-guards/public.guard';
import { PublicComponent } from './public.component';

const routes: Routes = [{ path: '', component: PublicComponent, canActivate: [PublicGuard] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
