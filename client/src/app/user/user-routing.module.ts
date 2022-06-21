import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from 'src/route-guards/user.guard';
import { UserComponent } from './user.component';

const routes: Routes = [{ path: '', component: UserComponent, canActivate: [UserGuard] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
