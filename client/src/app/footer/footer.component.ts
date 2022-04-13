import { Component, OnInit } from '@angular/core';
import { faFacebookSquare, faInstagramSquare, faLinkedin, faTwitterSquare } from '@fortawesome/free-brands-svg-icons';
import {
  faArrowUpRightFromSquare,
  faLocationDot,
  faEnvelope,
  faPhoneSquare,
  faCopyright
} from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
  faLocationDot = faLocationDot;
  faEnvelope = faEnvelope;
  faPhoneSquare = faPhoneSquare;
  faArrowUpRightFromSquare = faArrowUpRightFromSquare;
  faCopyright = faCopyright;
  faFacebookSquare = faFacebookSquare;
  faInstagramSquare = faInstagramSquare;
  faLinkedin = faLinkedin;
  faTwitterSquare = faTwitterSquare;    

  constructor() { }

  ngOnInit(): void {
  }

}
