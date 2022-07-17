import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceType } from 'src/models/service-type.data';
import { ManageService } from 'src/services/api-related/admin/service/manage-service.service';

@Component({
  selector: 'app-add-service',
  templateUrl: './add-service.component.html',
  styleUrls: ['./add-service.component.scss']
})
export class AddServiceComponent implements OnInit {
  submitted = false;
  initialServiceName = "";
  serviceType = new ServiceType();
  @ViewChild('serviceForm') serviceForm!: NgForm;

  constructor(
    private activatedRoute: ActivatedRoute,
    private managerService: ManageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    if (this.activatedRoute.routeConfig?.path == "add") {
      //Add service is called.
    } else {
      //Update service is called
      this.serviceType.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
      this.managerService.getServiceDetails(this.serviceType.id).subscribe(
        (response: any) => {
          this.serviceType = response;
          this.initialServiceName = response.type;
        },
        (error: any) => {
          console.log(error);
        }
      )
      // this.submitted = true;
      //Now we need to make an API call and get the existing information about the given service.
    }
  }

  onSubmit = () => {
    this.submitted = true;
    // this.serviceType.type = this.serviceType.type?.trim();
    this.serviceType.type?.trim();
    //minlength directive was not working properly. Was counting leading and trailing whitespaces also in 4 characters.
    //So applied trim() condition here.
    if (this.serviceForm.form.valid) {
      //Now you can make API call to update data on server.
      console.log(this.serviceType);
      this.managerService.addUpdateService(this.serviceType).subscribe(
        (response: any) => {
          console.log(response);
          this.router.navigateByUrl("/admin/service")
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
  }
}
