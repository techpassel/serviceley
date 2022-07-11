import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-service',
  templateUrl: './add-service.component.html',
  styleUrls: ['./add-service.component.scss']
})
export class AddServiceComponent implements OnInit {
  id!: string | null;

  constructor(private activatedRoute: ActivatedRoute) { }
  ngOnInit(): void {
    if (this.activatedRoute.routeConfig?.path == "add") {
      //Add service is called.
    } else {
      //Update service is called
      this.id = this.activatedRoute.snapshot.paramMap.get('id')
      //Now we need to make an API call and get the existing information about the given service.
    }
  }

}
