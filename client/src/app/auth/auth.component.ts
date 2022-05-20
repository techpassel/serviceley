import { Component, HostListener, OnInit } from '@angular/core';
import { CommonService } from 'src/services/common/common.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  constructor(private commonService: CommonService) { }

  ngOnInit(): void {
    this.commonService.emitAuthRouteEvent(true);
  }

}
