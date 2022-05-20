import { Component, OnInit } from '@angular/core';
import { CommonService } from 'src/services/common/common.service';

@Component({
  selector: 'app-public',
  templateUrl: './public.component.html',
  styleUrls: ['./public.component.scss']
})
export class PublicComponent implements OnInit {

  constructor(private commonService: CommonService) { }

  ngOnInit(): void {
    this.commonService.emitAuthRouteEvent(false);
  }

}
